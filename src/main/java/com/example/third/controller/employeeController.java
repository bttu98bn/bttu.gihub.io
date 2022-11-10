package com.example.third.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.third.entity.Orders;
import com.example.third.service.DetailSev;
import com.example.third.service.OrderSev;

@RestController
@RequestMapping("/employee")
public class employeeController {

    @Autowired
    OrderSev orderSev;
    @Autowired
    DetailSev detailSev;


    @GetMapping("/Orderlist")
   public List<Orders> ListOrder(){
    return orderSev.getAllOder();
   }
   @GetMapping("/Orderlist/{Order_id}")
   public ResponseEntity<Object> Orderdetail(@PathVariable int Order_id){
    return detailSev.findDeatilbyOrder(Order_id);

   }

    
}
