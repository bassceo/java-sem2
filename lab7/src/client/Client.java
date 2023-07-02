package client;


import common.Request;
import common.Response;
import common.exceptions.RecursiveException;
import common.io.ConsoleIO;
import common.io.UserIO;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Queue;

public class Client {
    private static final int BUFFER_SIZE = 65536;
    private static String host = "localhost";
    private static int port = 8081;

    private static RequestGenerator requestGenerator;

    private static UserIO userIO;

    public static void main(String[] args) {
        userIO = new ConsoleIO();
        requestGenerator = new RequestGenerator(userIO);
        if (args.length != 2) {
            System.err.println("Usage: java Client <hostname> <port>");
            System.err.println("Will be used localhost:8081");
        }else{
            host = args[0];
            port = Integer.parseInt(args[1]);
        }
        InetSocketAddress address = new InetSocketAddress(host, port);

        try {
            SocketChannel socketChannel = SocketChannel.open();
            while (true) {
                try {
                    socketChannel = SocketChannel.open();
                    socketChannel.connect(address);
                    break;
                } catch (ConnectException e) {
                    userIO.println("[WARNING] Server is not responding! Reconnecting to Server in 5s...");
                    Thread.sleep(5000);
                }

            }
            Boolean closed = false;
            ByteBuffer response = ByteBuffer.allocate(BUFFER_SIZE);
            ByteBuffer buffer;
            while(!closed){
                Queue<Request> queue;
                try{
                     queue = requestGenerator.generateRequests();
                } catch (RecursiveException e){
                    userIO.println("[ERROR] " + e.getMessage());
                    continue;
                }

                while(!queue.isEmpty()){
                    Request currentRequest = queue.remove();
                    if (currentRequest.getCommand().equals("exit")){
                        userIO.println("[SYSTEM] Exit of Application");
                        closed = true;
                        break;
                    }
                    buffer = ByteBuffer.wrap(serialize(currentRequest));
                    socketChannel.write(buffer);
                    response.clear();

                    socketChannel.read(response);
                    byte[] bytes = response.array();
                    Object server = deserialize(bytes);
                    if (server instanceof Response){
                        userIO.println( ((Response)server).getMessage());
                    }else{
                        userIO.println("[ERROR] WRONG SERVER RESPONSE TYPE");
                    }
                }
                requestGenerator.clear();
            }
            socketChannel.close();
        }catch (Exception e){
            userIO.println(e.getMessage());
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
}