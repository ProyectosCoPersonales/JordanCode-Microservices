package com.Gateway.Gateway.Config;

import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;

import javax.crypto.SecretKey;

public class JwtProvider {
    static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECREATE_KEY.getBytes());
    public static String generateToken(Authentication auth){
        return Jwts.builder().setIssuedAt(new Date())
        .setExpiration(new Date(new Date().getTime()+9000000))
        .claim("email",auth.getName())
        .signWith(key)
        .compact();
    }
    public static String getEmailFromToken(String jwt){
        jwt = jwt.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        return String.valueOf(claims.get("email"));
    }
}
