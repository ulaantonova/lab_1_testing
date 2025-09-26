package com.example.practice1antonovayulia.config;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expiration}")
  private int jwtExpirationMs;

  public String generateToken(String email) {
    return Jwts.builder()
      .setSubject(email)
      .setIssuedAt(new Date())
      .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
      .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes())
      .compact();
  }

  public String getEmailFromJwt(String token) {
    return Jwts.parser()
      .setSigningKey(jwtSecret.getBytes())
      .parseClaimsJws(token)
      .getBody()
      .getSubject();
  }

  public boolean validateJwt(String token) {
    try {
      Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token);
      return true;
    } catch (JwtException e) {
      return false;
    }
  }
}
