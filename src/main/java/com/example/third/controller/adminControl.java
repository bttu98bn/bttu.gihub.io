package com.example.third.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.third.entity.Orders;
import com.example.third.entity.product;
import com.example.third.service.DetailSev;
import com.example.third.service.OrderSev;
import com.example.third.service.ProductInterface;

@RestController
@RequestMapping("/admin")
public class adminControl {
    @Autowired
    ProductInterface product;
    @Autowired
    OrderSev orderSev;
    @Autowired
    DetailSev detailSev;

    @GetMapping("/product")
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

    @GetMapping("/Orderlist")
    public List<Orders> ListOrder() {
        return orderSev.getAllOder();
    }

    @GetMapping("/orderlist/{Order_id}")
    public ResponseEntity<Object> Orderdetail(@PathVariable int Order_id) {
        return detailSev.findDeatilbyOrder(Order_id);

    }
}
