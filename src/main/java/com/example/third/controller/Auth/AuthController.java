package com.example.third.controller.Auth;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.third.LoginProcess.Request.SigninForm;
import com.example.third.LoginProcess.Request.SignupForm;
import com.example.third.LoginProcess.Response.JwtResponse;
import com.example.third.Sercurity.JWT.JwtProvider;
import com.example.third.Sercurity.UserDetail.UserDetail;
import com.example.third.entity.user;
import com.example.third.service.userSv;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    userSv userSv;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager auth;
    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> Signin(@RequestBody SigninForm signinform) {
        Authentication authentication = auth.authenticate(
                new UsernamePasswordAuthenticationToken(signinform.getUser_name(), signinform.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.createToken(authentication);
        UserDetail customUserDetail = (UserDetail) authentication.getPrincipal();

        return ResponseEntity
                .ok(new JwtResponse(token, customUserDetail.getUsername(), customUserDetail.getAuthorities()));

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignupForm signupForm, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {
        user User = new user(signupForm.getUser_name(), passwordEncoder.encode(signupForm.getPassword()));

        return userSv.Signup(User, getSiteURL(request));

    }

    public String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userSv.verify(code)) {
            System.out.print(code);
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

}
