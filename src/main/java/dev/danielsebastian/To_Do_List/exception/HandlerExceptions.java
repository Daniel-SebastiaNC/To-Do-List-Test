package dev.danielsebastian.To_Do_List.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class HandlerExceptions {
    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> exceptionHandlerDataNotFoundException(DataNotFoundException e) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("message", e.getMessage());
        errors.put("error", "Data not found");
        return errors;
    }

    @ExceptionHandler(UsernameOrPasswordInvaldException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequestException(UsernameOrPasswordInvaldException e){
        return e.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            errors.put(((FieldError) error).getField(), error.getDefaultMessage());
        });

        return errors;
    }
}
