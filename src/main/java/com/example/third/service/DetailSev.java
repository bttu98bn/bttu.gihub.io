package com.example.third.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.third.entity.OrderDetail;
import com.example.third.entity.cartitem;

@Service

public interface DetailSev {
    public List<OrderDetail> getDetail();
    public void addOrderDetail(OrderDetail orderDetail);
    public ResponseEntity<Object> findDeatilbyid(String Num);
    public void addDetail(HashMap<String,cartitem> carts);
    public ResponseEntity<Object> findDeatilbyOrder(int Order_id);
}
