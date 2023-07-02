package common.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileIO implements UserIO {

    private FileReader reader;

    private BufferedReader bufferedReader;

    public FileIO(String filename) throws IOException {
        this.reader = new FileReader(filename);
        this.bufferedReader = new BufferedReader(reader);
        return;
    }

    @Override
    public void print(String data) {
        System.out.print(data);
        return;
    }

    @Override
    public void println(String data) {
        System.out.println(data);
        return;
    }

    @Override
    public String readLine() {
        try {
            return this.bufferedReader.readLine();
        } catch (IOException e) {
            ErrorHandler.logError(String.valueOf(e));
            return null;
        }
    }

    @Override
    public void close() {
        try {
            this.reader.close();
            this.bufferedReader.close();
        } catch (IOException e) {
            ErrorHandler.logError(String.valueOf(e));
        }
        return;
    }
}
