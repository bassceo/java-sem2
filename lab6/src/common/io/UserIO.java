package common.io;

public interface UserIO {
    public void print(String data);
    public void println(String data);
    public String readLine();
    public void close();
}
