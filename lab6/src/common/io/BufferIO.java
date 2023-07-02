package common.io;

public class BufferIO implements UserIO {

    private StringBuilder responseBuffer;

    public BufferIO() {
        responseBuffer = new StringBuilder();
        return;
    }

    @Override
    public void print(String data) {
        responseBuffer.append(data);
        return;
    }

    @Override
    public void println(String data) {
        if(responseBuffer.length()>0)responseBuffer.append("\n" + data);
        else responseBuffer.append(data);
    }

    @Override
    public String readLine() {
        return null;
    }

    @Override
    public void close() {

    }

    public void clean(){
        responseBuffer.delete(0,responseBuffer.length());
        return;
    }

    public String getBuffer(){
        return responseBuffer.toString();
    }
}
