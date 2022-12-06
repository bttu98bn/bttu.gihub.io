package com.example.third.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.third.Sercurity.JWT.JwtEntry;
import com.example.third.Sercurity.JWT.JwtTokenFilter;
import com.example.third.Sercurity.UserDetail.UserDetailSV;

@Configuration
@EnableWebSecurity
@Deprecated
public class Sercurityconfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailSV userDetailSV;
    @Autowired
    JwtEntry jwtEntry;

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailSV).passwordEncoder(passwordEncoder());

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasAnyAuthority("manager")
                .antMatchers("/employee/**").hasAnyAuthority("manager", "employee")
                .anyRequest().permitAll()

                .and().exceptionHandling()
                .authenticationEntryPoint(jwtEntry)

                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}