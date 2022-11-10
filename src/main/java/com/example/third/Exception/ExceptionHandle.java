package com.example.third.Exception;

import java.net.HttpURLConnection;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(NotfoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleNotfoundException(NotfoundException ex, WebRequest request) {
        return new ErrorMessage(HttpURLConnection.HTTP_NOT_FOUND, HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage DuplicateEx(DuplicateException ex, WebRequest request) {
        return new ErrorMessage(HttpURLConnection.HTTP_BAD_REQUEST, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(RequestFailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage RequestFail(RequestFailException ex, WebRequest request) {
        return new ErrorMessage(HttpURLConnection.HTTP_BAD_REQUEST, HttpStatus.BAD_REQUEST, ex.getMessage());

    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage erroformat(NumberFormatException ex, WebRequest request) {
        return new ErrorMessage(HttpURLConnection.HTTP_BAD_REQUEST, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

}
