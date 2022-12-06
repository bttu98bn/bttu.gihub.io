package com.example.third.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class user {
    String user_name;
    String password;
    String user_role;
    public boolean enable;
    String verification;

    public user(String user_name, String password, String user_role) {
        this.user_name = user_name;
        this.password = password;
        this.user_role = user_role;
    }

    public user(String user_name, String password) {
        this.user_name = user_name;
        this.password = password;
    }

}
