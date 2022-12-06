package com.example.third.service.imlp;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.third.dao.cartitemDao;
import com.example.third.entity.cartitem;
import com.example.third.service.CartService;

@Repository
public class CartServiceimpl implements CartService {
    @Autowired
    cartitemDao cartitemDao;

    @Override
    public HashMap<String, cartitem> Addcart(String code, HashMap<String, cartitem> carts) {
        return cartitemDao.Addcart(code, carts);

    }

    @Override
    public HashMap<String, cartitem> removecart(String code, HashMap<String, cartitem> carts) {
        return cartitemDao.removecart(code, carts);
    }

    @Override
    public HashMap<String, cartitem> updateCart(String code, int quantity, HashMap<String, cartitem> carts) {

        return cartitemDao.updateCart(code, quantity, carts);
    }

    @Override
    public float totalPrice(HashMap<String, cartitem> carts) {

        return cartitemDao.totalPrice(carts);
    }

    @Override
    public int totalQuantity(HashMap<String, cartitem> carts) {
        return cartitemDao.totalQuantity(carts);

    }

    @Override
    public List<cartitem> getallCart(HashMap<String, cartitem> carts) {
        return cartitemDao.getallCart(carts);
    }

}
