package com.example.third.service.imlp;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;
import com.example.third.Exception.NotfoundException;
import com.example.third.Exception.RequestFailException;
import com.example.third.entity.user;
import com.example.third.service.userSv;

import net.bytebuddy.utility.RandomString;

@Repository
public class Userimp implements userSv {
    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    JavaMailSender mailSender;

    @Override
    public List<user> getAll() {
        List<user> listUser = new ArrayList<>();
        try {
            String sql = "select * from Accounts";
            listUser = jdbc.query(sql, BeanPropertyRowMapper.newInstance(user.class));

        } catch (Exception e) {
            throw new RequestFailException("Request Fail");
        }
        return listUser;
    }

    @Override
    public ResponseEntity<Object> getRolebyUsername(String name) {
        ResponseEntity<Object> responseEntity = null;
        List<user> listUser = new ArrayList<user>();
        try {
            String sql = "select user_role from Accounts where User_Name=?";
            Object[] args = { name };
            listUser = jdbc.query(sql, BeanPropertyRowMapper.newInstance(user.class), args);
            responseEntity = new ResponseEntity<>(listUser, HttpStatus.OK);
        } catch (Exception e) {
            throw new NotfoundException("Not found Role");
        }
        return responseEntity;
    }

    @Override
    public ResponseEntity<Object> getbyName(String name) {
        ResponseEntity<Object> responseEntity = null;
        user User = new user();
        try {
            String sql = "select * from Accounts where User_Name=?";
            Object[] args = { name };
            User = jdbc.queryForObject(sql, BeanPropertyRowMapper.newInstance(user.class), args);
            responseEntity = new ResponseEntity<>(User, HttpStatus.OK);
        } catch (Exception e) {
            throw new NotfoundException("Not Found User_Name");
        }
        return responseEntity;
    }

    @Override
    public ResponseEntity<Object> Signup(user User, String siteURL)
            throws UnsupportedEncodingException, MessagingException {
        ResponseEntity<Object> responseEntity = null;
        String randomCode = RandomString.make(64);
        User.setVerification(randomCode);
        String sql = "insert into Accounts(user_name,password,VERIFICATION) values(?,?,?)";
        Object[] args = { User.getUser_name(), User.getPassword(), User.getVerification() };
        int i = jdbc.update(sql, args);
        sendVerificationEmail(User, siteURL);
        if (i != 0) {
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } else
            throw new RequestFailException("Request Fail");

        return responseEntity;
    }

    public void sendVerificationEmail(user User, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = User.getUser_name();
        String fromAddress = "testbt1998bn@gmail.com";
        String senderName = "Golden Company";
        String subject = "Please verify your registration";
        String content = "Dear Tu,<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Tu.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        String verifyURL = siteURL + "/auth/verify?code=" + User.getVerification();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

        System.out.println("Email has been sent");
    }

    public user getbyVerify(String Verify) {
        user User = new user();
        try {
            String sql = "select * from Accounts where VERIFICATION=?";
            Object[] args = { Verify };
            User = jdbc.queryForObject(sql,
                    BeanPropertyRowMapper.newInstance(user.class), args);
        } catch (Exception e) {
            throw new NotfoundException("Not Found User_Name");
        }
        return User;
    }

    public void updateUser(user User) {
        try {
            Object[] args = { User.getPassword(), User.getUser_role(), User.isEnable(),
                    User.getVerification(),
                    User.getUser_name() };
            int res = jdbc.update(
                    "update accounts set password=?,user_role=?,enable=?,verification=? where user_name=?", args);
            if (res == 0)
                throw new RequestFailException("Request Fail !");

        } catch (Exception ex) {
            throw new RequestFailException("Request Fail !");
        }
    }

    @Override
    public boolean verify(String verificationCode) {
        user User = getbyVerify(verificationCode);

        if (User == null || User.isEnable()) {
            return false;
        } else {
            User.setVerification(null);
            ;
            User.setEnable(true);
            ;
            updateUser(User);

            return true;
        }

    }

}
