package com.chatapp.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long expiration;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration) {
        this.secretKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8));

        this.expiration = expiration;
    }

    public String generateToken(Long userId, String email) {

        Date now = new Date();

        return Jwts.builder()
                .subject(userId.toString())
                .claim("email", email)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiration))
                .signWith(secretKey)
                .compact();
    }

    public Long extractUserId(String token) {
        String subject = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

        return Long.valueOf(subject);
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (Exception exception) {
            // TODO: handle exception
            return false;
        }
    }
}