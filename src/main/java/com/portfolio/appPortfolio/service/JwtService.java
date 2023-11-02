package com.portfolio.appPortfolio.service;

import com.portfolio.appPortfolio.SpringApplicationContext;
import com.portfolio.appPortfolio.security.AppProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@Component
public class JwtService {
    static final long ExpirationTime= 86400000;//1 day
    static final String PREFIX="Bearer";
    public static String getTokenSecret(){
        AppProperties appProperties= (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    };
    public String getToken(String adminName){
        String token =
                Jwts.builder().setSubject(adminName).setExpiration(new Date(System.currentTimeMillis()+ ExpirationTime)).signWith(SignatureAlgorithm.HS512, getTokenSecret()).compact();
        return token;
    }
    public String getAuthAdmin(HttpServletRequest request){
        String token= request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token!=null){
            String admin =
                    Jwts.parserBuilder().setSigningKey(getTokenSecret()).build().parseClaimsJws(token.replace(PREFIX,
                            "")).getBody().getSubject();
            if (admin!=null){
                return admin;
            }
        }
        return null;
    }

}
