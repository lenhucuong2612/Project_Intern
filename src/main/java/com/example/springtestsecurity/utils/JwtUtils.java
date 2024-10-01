package com.example.springtestsecurity.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;
    @Value("${auth.token.expirationInMils}")
    private int jwtExpirationMs;

    //tao token từ username
    public String generateToken(String username){
        String token= Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256,jwtSecret)
                .compact();
        return token;
    }
//    public String getUsernameFromToken(String token){
//        System.out.println(getClaimsFromToken(token).getSubject());
//        return getClaimsFromToken(token).getSubject();
//    }
    public String getUsernameFromToken(String token){
        if(validateToken(token)){
            Claims claims=Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return claims.getSubject();
        }
        throw new ExpiredJwtException(null, null,"Token has expired");
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            System.err.println("Invalid JWT signature");
        } catch (MalformedJwtException e) {
            System.err.println("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            System.err.println("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            System.err.println("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            System.err.println("JWT claims string is empty.");
        }
        return false;
    }

}
