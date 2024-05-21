package com.javaguides.todomanagement.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String secret;

    @Value("${app.jet-expiration-milliseconds}")
    private long expirationDate;

    // Generate JWT token
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + 604800000);

        String token = Jwts.builder().setSubject(username).setIssuedAt(currentDate).setExpiration(expirationDate).signWith(getSecretKey()).compact();

        return token;
    }

    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    // Get username from JWT token
    public String getUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody();

        String username = claims.getSubject();

        return username;
    }

    // Validate JWT token
    public boolean validateToken(String token) {
        Jwts.parser().setSigningKey(getSecretKey()).build().parse(token);

        return true;
    }
}
