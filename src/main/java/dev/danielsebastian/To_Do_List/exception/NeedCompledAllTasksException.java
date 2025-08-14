package dev.danielsebastian.To_Do_List.exception;

public class NeedCompledAllTasksException extends RuntimeException {
    public NeedCompledAllTasksException(String message) {
        super(message);
    }
}
