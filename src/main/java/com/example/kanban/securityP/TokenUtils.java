/*package com.example.kanban.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.JwtException;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {
    //creear la configuracion del token

    private final static String ACCESS_TOKEN_SECRET = "4qhq8LrEBfycaRH"; //DEBERIA IR EN EL PROPERTIES

    private final static Long ACCESS_TOKEN_VALIDITY_SECONS = 2_592_000l;

    public static String createToken(String nombre, String email){
        long expirationTime = ACCESS_TOKEN_VALIDITY_SECONS * 1000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String , Object> extra = new HashMap<>();
        extra.put("nombre", nombre);

        return Jwts.Builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .addClaims(extra)
                .sugnWith(SignatureAlgorithm.HS256, ACCESS_TOKEN_SECRET)
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();

            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        }catch (JwtException e){
            return null;
        }
    }

} */
