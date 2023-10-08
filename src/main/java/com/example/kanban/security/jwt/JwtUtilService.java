package com.example.kanban.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtilService {

    private String secret = "springboot";

    private static final String JWT_SECRET_KEY = "TEwdDSFREGdsacSDFDEW=";

    private  static final long JWT_TOKEN_VALIDATE = 1000*60*60*(long) 8;

    public String extractUsername(String token){

        return extractClains(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClains(token, Claims::getExpiration);
    }

    public <T> T extractClains(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

   /* public String generateToken1(String username, String role){
        Map<String , Object> claims = new HashMap<>();
        claims.put("role" , role);
        return createToken(claims , username);
    }*/

    public String generateToken(UserDetails userDetails){
        Map<String , Object> claims = new HashMap<>();
        return createToken(claims , userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDATE))
                .signWith(SignatureAlgorithm.HS256 , JWT_SECRET_KEY).compact();
    }

    public Boolean validateToken(String token , UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
