package com.example.kanban.security;

import com.example.kanban.entitys.credenciales.Usuario;
import com.example.kanban.repositorys.UsuarioRepository;
import io.micrometer.common.util.internal.logging.InternalLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service
public class CustomerDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario userDetail;

    InternalLogger log;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("Dentro de loadUserByUsername {}" , username);
        userDetail = usuarioRepository.findByEmail(username);

        if(!Objects.isNull(userDetail)){
            return new org.springframework.security.core.userdetails.User(userDetail.getEmail(), userDetail.getPassword(), new ArrayList<>())
        }else{
            throw new UsernameNotFoundException("Usuario no encontrado ");
        }
    }

    public Usuario getUserDetail(){
        return userDetail;
    }
}
