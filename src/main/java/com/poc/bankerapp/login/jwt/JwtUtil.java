package com.poc.bankerapp.login.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Change this to a secure random secret key

    @SuppressWarnings("deprecation")
	public String generateToken(int userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 86400000); // Token expires in 24 hours

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
    
    @SuppressWarnings("deprecation")
	public Integer getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(secretKey)
                            .parseClaimsJws(token)
                            .getBody();

        return Integer.valueOf(claims.getSubject());
    }

}

