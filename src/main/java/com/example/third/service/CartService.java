package com.example.third.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.third.entity.cartitem;

@Service
public interface CartService {
    public HashMap<String, cartitem> Addcart(String code, HashMap<String, cartitem> carts);

    public HashMap<String, cartitem> updateCart(String code, int quantity, HashMap<String, cartitem> carts);

    public HashMap<String, cartitem> removecart(String code, HashMap<String, cartitem> carts);

    public int totalQuantity(HashMap<String, cartitem> carts);

    public float totalPrice(HashMap<String, cartitem> carts);

    public List<cartitem> getallCart(HashMap<String, cartitem> carts);
}
