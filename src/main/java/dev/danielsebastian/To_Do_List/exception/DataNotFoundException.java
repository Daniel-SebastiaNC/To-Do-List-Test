package dev.danielsebastian.To_Do_List.exception;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
}
