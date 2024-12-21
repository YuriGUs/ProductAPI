package com.example.springboot.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "your-256-bit-secret-your-256-bit-secret"; // Use exatamente 32 caracteres
    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public static String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email) // Representa o usuário (email)
                .claim("role", role) // Adiciona o papel do usuário (admin ou user)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(key)
                .compact();
    }

    public static Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String extractSubject(String token) {
        return extractAllClaims(token).getSubject();
    }

    public static String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public static boolean isTokenValid(String token) {
        try {
            extractAllClaims(token); // Verifica a validade do token
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
