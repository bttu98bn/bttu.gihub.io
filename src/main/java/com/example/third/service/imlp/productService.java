package com.example.third.service.imlp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Service;

import com.example.third.Exception.DuplicateException;
import com.example.third.Exception.NotfoundException;
import com.example.third.Exception.RequestFailException;
import com.example.third.entity.product;
import com.example.third.service.ProductInterface;

import java.util.ArrayList;
import java.util.List;

@Service
public class productService implements ProductInterface {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<product> getProductList() {
        List<product> pList = new ArrayList<product>();
        try {
            String sql = "select * from products";
            pList = this.jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(product.class));
        } catch (Exception ex) {
            throw new RequestFailException("Request Fail !");
        }

        return pList;
    }

    public ResponseEntity<Object> create(product product) {
        ResponseEntity<Object> responseEntity = null;

        if (findbyid(product.getCode()) != null) {
            throw new DuplicateException("code da ton tai");
        } else {
            try {
                int res = jdbcTemplate.update("insert into products values(?,?,?, ?, ?, ? )", product.toArray());

                if (res == 1) {
                    responseEntity = new ResponseEntity<>("created", HttpStatus.CREATED);
                } else {
                    responseEntity = new ResponseEntity<>("resquest fail", HttpStatus.BAD_REQUEST);
                }

            } catch (Exception e) {
                throw new RequestFailException("Request Fail!");
            }
        }

        return responseEntity;
    }

    @Override
    public ResponseEntity<String> update(product product, String code) {
        ResponseEntity<String> responseEntity = null;
        findbyid(code);
        if (!code.equals(product.getCode())) {
            responseEntity = new ResponseEntity<>("code khong giong nhau", HttpStatus.CONFLICT);
        }
        try {
            int res = this.jdbcTemplate.update(
                    "update products set name=?,price=?,image=?,create_date=?,active=? where code=?",
                    product.toArrayForUpdate());

            if (res == 1) {
                responseEntity = new ResponseEntity<>("done", HttpStatus.OK);
            } else
                throw new RequestFailException("Request Fail !");

        } catch (Exception ex) {
            throw new RequestFailException("Request Fail !");
        }
        return responseEntity;
    }

    @Override
    public ResponseEntity<String> deletebyID(String code) {
        ResponseEntity<String> responseEntity = null;
        findbyid(code);

        String sql = "delete from products where code=?";
        Object[] args = { code };
        int result = 0;
        result = jdbcTemplate.update(sql, args);
        if (result == 1) {
            responseEntity = new ResponseEntity<>("deleted", HttpStatus.OK);
        } else {
            throw new RequestFailException("Request Fail !");
        }
        return responseEntity;
    }

    public product findbyid(String code) {
        String sql = "select * from products";
        List<product> pList = this.jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(product.class));

        for (product product : pList) {
            if (code.equals(product.getCode()))
                return product;
        }
        throw new NotfoundException("Khong tim thay id");
    }

}
