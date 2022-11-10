package com.example.third.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.third.entity.Orders;

@Service
public interface OrderSev {
    public int addOrder(Orders orders);
    public List<Orders> getAllOder();
    public String getLastOrder();
    
}
