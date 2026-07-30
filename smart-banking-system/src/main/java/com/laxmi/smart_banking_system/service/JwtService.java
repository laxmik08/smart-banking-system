package com.laxmi.smart_banking_system.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "MySecretKeyForSmartBankingSystem123456789012345";

    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // Generate JWT Token
    public String generateToken(String email) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract All Claims
    public Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Extract Email
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Validate Token
    public boolean validateToken(String token) {

        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}