package com.poly.petfoster.config;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.poly.petfoster.constant.Constant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {
    
    SecretKey key = Keys.hmacShaKeyFor(Constant.SECRET_KEY.getBytes());


    public String generateToken(Authentication authentication) {

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 360000000))
                .claim("username", authentication.getName())
                .claim("authorities", getAuthoritiesAsString(authentication))
                .signWith(key).compact();
    }

    private String getAuthoritiesAsString(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    // Get username form jwt
    public String getUsernameFromToken(String jwt) {

        jwt = jwt.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        return String.valueOf(claims.get("username"));

    }
}
