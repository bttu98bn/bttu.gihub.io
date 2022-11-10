package com.example.third.configuration;


import javax.sql.DataSource;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 
@Configuration
@EnableWebSecurity
@Deprecated
public class Sercurityconfig extends WebSecurityConfigurerAdapter {
 
    @Autowired
    private DataSource dataSource;
     
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
            .dataSource(dataSource)
            .usersByUsernameQuery("select user_name, password, active from accounts where user_name=?")
            .authoritiesByUsernameQuery("select user_name, user_role from accounts where user_name=?")
        ;
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http .cors().and().csrf().disable()
            .authorizeRequests()
            .antMatchers("/admin/**").hasAnyRole("MANAGER")
            .antMatchers("/employee/**").hasAnyRole("EMPLOYEE","MANAGER")
            .anyRequest().permitAll()   
            .and()
            .formLogin().permitAll()
            .and()
            .logout().permitAll();
             
    }
}