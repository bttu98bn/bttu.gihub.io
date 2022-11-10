package com.example.third.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.third.entity.product;

@Service
public interface ProductInterface {
    List<product> getProductList();

    ResponseEntity<Object> create(product product);

    ResponseEntity<String> update(product product, String code);

    ResponseEntity<String> deletebyID(String code);

    public product findbyid(String code);

}