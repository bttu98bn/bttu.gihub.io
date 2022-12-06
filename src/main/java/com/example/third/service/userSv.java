package com.example.third.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.third.entity.user;

@Service
public interface userSv {
    public List<user> getAll();

    public ResponseEntity<Object> getbyName(String name);

    public ResponseEntity<Object> getRolebyUsername(String name);

    public ResponseEntity<Object> Signup(user User, String siteURL)
            throws UnsupportedEncodingException, MessagingException;

    public boolean verify(String verificationCode);

}
