package com.example.third.Sercurity.UserDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.third.entity.user;
import com.example.third.service.userSv;

@Component
public class UserDetailSV implements UserDetailsService {
    @Autowired
    userSv userSv;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            user User = (user) userSv.getbyName(username).getBody();
            return UserDetail.build(User);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("ko tim thay user name");

        }
    }

}
