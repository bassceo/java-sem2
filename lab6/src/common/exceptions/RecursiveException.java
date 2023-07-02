package common.exceptions;

public class RecursiveException extends Exception{
    public RecursiveException() {
        super("");
    }
    public RecursiveException(String message) {
        super(message);
    }
}
