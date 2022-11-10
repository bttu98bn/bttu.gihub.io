package com.example.third.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.third.entity.cartitem;
import com.example.third.entity.product;
import com.example.third.service.ProductInterface;

@Repository
public class cartitemDao {

    @Autowired
    ProductInterface productService;

    public HashMap<String, cartitem> Addcart(String code, HashMap<String, cartitem> carts) {
        cartitem item = new cartitem();
        product product = productService.findbyid(code);
        if (product != null && carts.containsKey(code)) {
            item = carts.get(code);
            item.setQuantity(item.getQuantity() + 1);
            item.setAmount(item.getProduct().getPrice() * item.getQuantity());
        } else if (product != null) {
            item.setProduct(product);
            item.setQuantity(1);
            item.setAmount(product.getPrice());
        }
        carts.put(code, item);
        return carts;

    }

    public HashMap<String, cartitem> updateCart(String code, int quantity, HashMap<String, cartitem> carts) {
        cartitem item = new cartitem();
        if (carts.containsKey(code)) {
            item = carts.get(code);
            item.setQuantity(quantity);
            item.setAmount(quantity * item.getProduct().getPrice());
        }
        carts.put(code, item);
        return carts;
    }

    public HashMap<String, cartitem> removecart(String code, HashMap<String, cartitem> carts) {
        if (carts == null) {
            return carts;
        }
        if (carts.containsKey(code)) {
            carts.remove(code);
        }
        return carts;
    }

    public int totalQuantity(HashMap<String, cartitem> carts) {
        int total = 0;
        for (Map.Entry<String, cartitem> cart : carts.entrySet()) {
            total += cart.getValue().getQuantity();

        }
        return total;
    }

    public float totalPrice(HashMap<String, cartitem> carts) {
        float totalPrice = 0;
        for (Map.Entry<String, cartitem> cart : carts.entrySet()) {
            totalPrice += cart.getValue().getAmout();

        }
        return totalPrice;
    }

    public List<cartitem> getallCart(HashMap<String, cartitem> carts) {
        List<cartitem> listcarts = new ArrayList<>(carts.values());
        return listcarts;
    }

}
