/* package com.example.kanban.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public Authentication attempAuthentication(HttpServletRequest request,
                                              HttpServletResponse response) throws AuthenticationException{
        AuthCredencials authCredencials = new AuthCredencials();

        try{
            authCredencials = new ObjectMapper().readValue(request.getReader(), AuthCredencials.class );
        }catch (IOException e){
            UsernamePasswordAuthenticationToken usernamePat = new UsernamePasswordAuthenticationToken(
                    authCredencials.getEmail(),
                    authCredencials.getPassword(),
                    Collections.emptyList()
            );

            return getAuthenticationManager().authenticate(usernamePat);
        }

        protected void successfullAuthentication(HttpServletRequest request, HttpServletResponse response,
                FilterChain chain, Authentication authResult)throws IOException, ServletException{
            UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
            String token = TokenUtils.createToken(userDetails.getNombre(), userDetails.getUsername());

            response.addHeader("Authorization", "Bearer " + token);

            response.getWriter().flush();
            super.successfulAuthentication(request, response, chain, authResult);
        }
    }

} */
