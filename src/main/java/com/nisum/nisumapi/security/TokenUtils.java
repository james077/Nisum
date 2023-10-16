package com.nisum.nisumapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {

    private final static String ACCESS_TOKEN_SECRET = "UDRzc3cwcmQuUHIwZHVjYzEwbl9uaXN1bQ==";
    private final static Long ACCESS_TOKEN_VALIDITY_MILISECONDS = 3600_000L;

    public static String createToken(String nombre, String email){
        Date expirationDate = new Date(System.currentTimeMillis()+ACCESS_TOKEN_VALIDITY_MILISECONDS);

        Map<String, Object> extra = new HashMap<>();
        extra.put("nombre", nombre);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .addClaims(extra)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String email = claims.getSubject();

        try{
            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        }catch(JwtException e){
            return null;
        }
    }
}
