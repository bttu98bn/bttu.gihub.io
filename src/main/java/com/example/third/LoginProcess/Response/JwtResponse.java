package com.example.third.LoginProcess.Response;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    public JwtResponse(String token, String name, Collection<? extends GrantedAuthority> roles) {
        this.token = token;
        this.name = name;
        this.roles = roles;
    }

    String token;
    private String type = "Bearer";
    private String name;
    private Collection<? extends GrantedAuthority> roles;

}
