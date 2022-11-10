package com.example.third.Exception;

public class RequestFailException extends RuntimeException{
    public RequestFailException(String Message){
        super(Message);
    }
    
}
