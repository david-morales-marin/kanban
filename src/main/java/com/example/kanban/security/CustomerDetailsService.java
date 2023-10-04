package com.example.kanban.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
public class CustomerDetailsService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    private User userDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       // return null;
        log.info("Dentro de loadUserByUsername {}" , username);
        userDetail = userDAO.findByEmail(username);

        if(!Object.isNull(userDetail)){
            return new org.springframework.security.core.userdetails.User(userDetail.getEmail(), userDetail.getPassword(), new ArrayList<>())
        }else{
            throw new UsernameNotFoundException("Usuario no encontrado ");
        }
    }

    public User getUserDetail(){
        return userDetail;
    }
}
