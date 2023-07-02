package server;

import common.CommandRequest;
import common.ErrorHandler;
import common.Request;
import common.Response;
import common.io.BufferIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RecursiveTask;

import static org.apache.commons.lang3.SerializationUtils.deserialize;

public class Server {
    private static final int BUFFER_SIZE = 65536;
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private static int PORT = 8081;
    private static final BufferIO bufferIO = new BufferIO();
    public static Application app;

    private static final ForkJoinPool readPool = new ForkJoinPool();

    private static final ForkJoinPool processPool = new ForkJoinPool();

    private static final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        if(args.length==1){
            try {
                PORT = Integer.parseInt(args[0]);
            }catch (NumberFormatException e) {
                System.out.println("Invalid port number. Will be used 8081!");
            }
        }
        app = new Application(bufferIO, getFileNameFromEnv());
        app.loadDataFromFile(getFileNameFromEnv());

        try{
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            logger.info("Server started!");
            Selector selector = Selector.open();
            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);

            logger.info("Waiting for new connection!");
            while(true){
                if(selector.select(5000)==0){
                    continue;
                }
                for (SelectionKey key : selector.selectedKeys()){
                    if(key.isAcceptable()){
                        SocketChannel channel = serverSocketChannel.accept();
                        logger.info("New connection from " + channel.getRemoteAddress() + " accepted!");
                        channel.configureBlocking(false);
                        channel.register(selector,SelectionKey.OP_READ);
                    }else if(key.isReadable()){
                        readPool.invoke(new ReadRequest(key,selector));
                    }else if(key.isWritable()){
                        handleWrite(key);
                        cachedThreadPool.execute(() -> {
                            handleWrite(key);
                        });
                        key.channel().register(selector,SelectionKey.OP_READ);
                    }
                    selector.selectedKeys().remove(key);
                }
            }
        }catch (Exception e){
            logger.info("[ERROR] " + e.getMessage());
            System.exit(0);
        }

    }

    private static void handleWrite(SelectionKey key){
        try{
            SocketChannel channel = (SocketChannel) key.channel();
            byte []data =  (byte[])(key.attachment());
            ByteBuffer buffer = ByteBuffer.wrap(data);
            channel.write(buffer);
            logger.info("Sending response to " + channel.getRemoteAddress() +"!");
        }catch (Exception e) {
            logger.info("Exception: " + e.getMessage() +"!");
        }

    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        objectOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return objectInputStream.readObject();
    }
    private static String getFileNameFromEnv() {
        String fileName = System.getenv("SAVED_COLLECTION");
//        if (fileName == null || fileName.isEmpty()) {
//            ErrorHandler.logError("Не указано имя файла в переменной окружения SAVED_COLLECTION!");
//            System.exit(0);
//        }
        return fileName;
    }

    public static String getCurrentDateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return "[" + currentDateTime.format(formatter) + "]";
    }

    static class ReadRequest extends RecursiveTask<Boolean> {

        private SelectionKey key;
        private Selector selector;

        public ReadRequest(SelectionKey key, Selector selector) {
            this.key = key;
            this.selector = selector;
        }

        @Override
        protected Boolean compute() {
            try {
                SocketChannel channel = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
                int readBytes = channel.read(buffer);

                if (readBytes == -1){
                    channel.close();
                    return false;
                }

                byte [] data = buffer.array();

                Object receivedObject = deserialize(data);

                logger.info("Received request from client "+channel.getRemoteAddress() +"!" );

                // Обработка
                Response response = new Response("[SERVER:ERROR] Wrong Request Type");

                if(receivedObject instanceof Request){
                    if(((Request)receivedObject).getCommand().equals("exit")){
                        channel.close();
                        return false;
                    }else if(((Request)receivedObject).getCommand().equals("save")){
                        if(!channel.getRemoteAddress().toString().contains("127.0.0.1")){
                            response = new Response("[SERVER:ERROR] Недостаточно прав!");
                        }else{
                            response = proccessRequest(((Request) receivedObject));
                        }
                    }else {
                        response = proccessRequest(((Request) receivedObject));
                    }
                }
                channel.register(selector, SelectionKey.OP_WRITE, serialize(response));
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        private Response proccessRequest(Request request){
            return processPool.invoke(new ProcessRequest(request));
        }
    }

    static class ProcessRequest extends RecursiveTask<Response> {

        private Request request;

        public ProcessRequest(Request request) {
            this.request = request;
        }

        @Override
        protected Response compute() {
            try {
                logger.info("Proccessing request with command " + request.getCommand());
                try {
                    app.processCommand(request);
                }catch (Exception e){
                    return new Response("[SERVER:ERROR] " + e.getMessage());
                }
                String response = bufferIO.getBuffer();
                bufferIO.clean();
                return new Response(response);
            } catch (Exception e) {
                return null;
            }
        }
    }

    static class WriteResponse extends RecursiveTask<Boolean> {

        private SelectionKey key;

        public WriteResponse(SelectionKey key) {
            this.key = key;
        }

        @Override
        protected Boolean compute() {
            try {
                SocketChannel channel = (SocketChannel) key.channel();
                byte []data =  (byte[])(key.attachment());
                ByteBuffer buffer = ByteBuffer.wrap(data);
                channel.write(buffer);
                logger.info("Sending response to " + channel.getRemoteAddress() +"!");
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }
}
