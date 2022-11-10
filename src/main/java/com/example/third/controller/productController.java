package com.example.third.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.third.Exception.NotfoundException;
import com.example.third.entity.Orders;
import com.example.third.entity.cartitem;
import com.example.third.entity.product;
import com.example.third.service.CartService;
import com.example.third.service.DetailSev;
import com.example.third.service.OrderSev;
import com.example.third.service.ProductInterface;

@CrossOrigin

@RestController
@RequestMapping("/product")
public class productController {
    @Autowired
    ProductInterface product;
    @Autowired
    OrderSev orderSev;
    @Autowired
    DetailSev detailSev;
    @Autowired
    CartService cartService;

    @GetMapping("/")
    public List<product> getProductList() {
        return product.getProductList();
    }

    @PostMapping("/add")
    public ResponseEntity<Object> create(@RequestBody product pr) {
        return this.product.create(pr);
    }

    @PutMapping("/update/{code}")
    public String update(@RequestBody product pr1, @PathVariable(name = "code") String code) {
        return product.update(pr1, code).getBody();
    }

    @DeleteMapping("/{code}")
    public String delete(@PathVariable String code) {
        return product.deletebyID(code).getBody();
    }

    @GetMapping("/a/{code}")
    public product findproduct(@PathVariable String code) {
        return product.findbyid(code);
    }

    @PostMapping("/CartCustomer")
    public void customerinfor(@RequestBody Orders order, HttpSession session) {
        session.setAttribute("order", order);
    }

    @PostMapping("/send")

    public void send(HttpSession session, Orders orders) {
        HashMap<String, cartitem> carts = (HashMap<String, cartitem>) session.getAttribute("Carts");
        orders = (Orders) session.getAttribute("order");
        orders.setAmount(cartService.totalPrice(carts));

        if (orderSev.addOrder(orders) > 0) {

            if (carts == null) {
                throw new NotfoundException("cart null");
            }

            detailSev.addDetail(carts);

        }
        session.removeAttribute("carts");

    }

}
