package com.example.third.service.imlp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.third.Exception.DuplicateException;
import com.example.third.Exception.NotfoundException;
import com.example.third.Exception.RequestFailException;
import com.example.third.entity.Orders;
import com.example.third.service.OrderSev;

@Repository
public class orderSevimpl implements OrderSev {
@Autowired
JdbcTemplate jdbcTemplate;

public List<Orders> getAllOder(){
    List<Orders> orders= new ArrayList<Orders>();
    try {
        String sql="Select * from Orders";
        orders=jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Orders.class));
    } catch (Exception e) {
        e.printStackTrace();
    }
return orders;
}
public ResponseEntity<Object> findOrderbyid(String Id){
    ResponseEntity<Object> responseEntity=null;
    List<Orders> order=new ArrayList<Orders>();
   
        String sql="select * from Orders where Id=?";
        
        order= jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Orders.class),Id);
        if(order.size()==0){
           throw new NotfoundException("ID khong ton tai");
        }
        else{
            responseEntity= new ResponseEntity<>(order, HttpStatus.OK);
        }
   
    return responseEntity;
}
public int addOrder(Orders orders){
   
    int i;
    try {
       
        if(orders!=null){
            String sql="insert into orders (amount,customer_address,customer_email,customer_name,customer_phone) values(?,?,?,?,?)";
            Object[] args={orders.getAmount(),orders.getCustomer_Address(),orders.getCustomer_Email(),orders.getCustomer_Name(),orders.getCustomer_Phone()};
             i = jdbcTemplate.update(sql,args);
            if(i==0){
                throw new RequestFailException("Request Fail !!");
            }
            
        }
        else {
            throw new DuplicateException("Order Id da ton tai");
        }
        
    } catch (Exception e) {
       throw new RequestFailException("Request Fail !!");
    }
return i;
}


@Deprecated
public String getLastOrder(){
    String sql="select MAX(id) from orders";
        String id=jdbcTemplate.queryForObject(sql, new Object[]{}, String.class);

      return id;
}


    
}
