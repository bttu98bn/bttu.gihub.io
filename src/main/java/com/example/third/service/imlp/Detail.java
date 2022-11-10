package com.example.third.service.imlp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.third.Exception.NotfoundException;

import com.example.third.Exception.RequestFailException;
import com.example.third.entity.OrderDetail;
import com.example.third.entity.cartitem;
import com.example.third.service.DetailSev;
import com.example.third.service.OrderSev;

@Repository
public class Detail implements DetailSev {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    OrderSev orderSev;


    public List<OrderDetail> getDetail(){
        List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
        String sql="Select * from Order_Detail";
        orderDetails=jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(OrderDetail.class));
        return orderDetails;

    }

    public void addOrderDetail(OrderDetail orderDetail){
        
            try {
                String sql="insert into order_details(amount,price,quanity,order_id,product_id) values(?,?,?,?,?)";
            Object[] args={orderDetail.getAmount(),orderDetail.getPrice(),orderDetail.getQuantity(),orderDetail.getOrder_ID(),orderDetail.getProduct_ID()};
            int i=jdbcTemplate.update(sql, args);
            if(i==0){
                throw new RequestFailException("Request Fail!");
            }
            
            } catch (Exception e) {
                throw new RequestFailException("Request Fail!");
            }

        }
       
    
    

    public ResponseEntity<Object> findDeatilbyid(String Num){
        ResponseEntity<Object> responseEntity=null;
        List<OrderDetail> details = new ArrayList<OrderDetail>();
       
            String sql="select * from Order_Details where Num=?";
            
            details = jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(OrderDetail.class),Num);
            if(details.size()==0){
               throw new NotfoundException("not found detail id");
            }
            else{
                responseEntity= new ResponseEntity<>(details, HttpStatus.OK);
            }
            return responseEntity;

            
    
}

public ResponseEntity<Object> findDeatilbyOrder(int Order_id){
    ResponseEntity<Object> responseEntity=null;
    List<OrderDetail> details = new ArrayList<OrderDetail>();
    try {
        String sql="select * from Order_Details where Order_ID=?";
        
        details = jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(OrderDetail.class),Order_id);
        if(details.size()==0){
           throw new NotfoundException("not found order id ");
        }
        else{
            responseEntity= new ResponseEntity<>(details, HttpStatus.OK);
        }
        return responseEntity;
        
    } catch (NumberFormatException e) {
        throw new NumberFormatException("loi dinh dang order id");
        
    }
   
       
      

        

}


public void addDetail(HashMap<String,cartitem> carts){
    String id=orderSev.getLastOrder();
    for(Map.Entry<String,cartitem> cartitem :carts.entrySet()){
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setOrder_ID(id);
        orderDetail.setProduct_ID(cartitem.getValue().getProduct().getCode());
        orderDetail.setQuantity(cartitem.getValue().getQuantity());
        orderDetail.setAmount(cartitem.getValue().getAmout());
        orderDetail.setPrice(cartitem.getValue().getProduct().getPrice());
        addOrderDetail(orderDetail);
    }
}
}
