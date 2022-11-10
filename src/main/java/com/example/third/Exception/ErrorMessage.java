package com.example.third.Exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ErrorMessage {
  private  int StatusCode;
  private HttpStatus Status;
    private String Message;
  
}
