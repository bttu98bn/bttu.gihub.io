package com.example.third.Sercurity.JWT;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.third.Sercurity.UserDetail.UserDetail;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(JwtProvider.class);
    private String jwtSercert = "bui.trong.tu";
    private int jwtExpiration = 86400;

    public String createToken(Authentication authentication) {

        UserDetail userPrinciple = (UserDetail) authentication.getPrincipal();

        return Jwts.builder().setSubject(userPrinciple.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpiration * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSercert).compact();

    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSercert).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("sai signature");
        } catch (MalformedJwtException e) {
            logger.error("sai dinh dang");
        } catch (UnsupportedJwtException e) {
            logger.error("unsupport");
        } catch (ExpiredJwtException e) {
            logger.error("het han token");
        } catch (IllegalArgumentException e) {
            logger.error("jwt claim is empty");
        }
        return false;
    }

    public String getUsernameFromtoken(String token) {
        String User_name = Jwts.parser().setSigningKey(jwtSercert).parseClaimsJws(token).getBody().getSubject();
        return User_name;
    }
}
