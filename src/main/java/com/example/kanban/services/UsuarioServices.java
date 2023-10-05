package com.example.kanban.services;

import com.example.kanban.constantes.Constantes;
import com.example.kanban.entitys.credenciales.Usuario;
import com.example.kanban.repositorys.UsuarioRepository;
import com.example.kanban.utils.ProjectUtils;
import io.micrometer.common.util.internal.logging.InternalLogger;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UsuarioServices {

    @Autowired
    private UsuarioRepository usuarioRepository;

    InternalLogger log;
    public ResponseEntity<String> signUp(Map<String , String> requestMap){
        log.info("Registro interno de un usuario {} " , requestMap);
        try{
            if(validateSignUpMap(requestMap)){
                Usuario usuario = usuarioRepository.findByEmail(requestMap.get("email"));
                if(Objects.isNull(usuario)){
                    usuarioRepository.save(getUserFromMap(requestMap));
                    return ProjectUtils.getResponseEntity("El usuario ha sido creado con exito" , HttpStatus.CREATED);

                }else{
                    return ProjectUtils.getResponseEntity("El usuario con ese email ya existe" , HttpStatus.BAD_REQUEST);
                }
            }else{
                return  ProjectUtils.getResponseEntity(Constantes.INVALID_DATA , HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUpMap(Map<String , String> requestMap){
        if(requestMap.containsKey("nombre") && requestMap.containsKey("email")
          && requestMap.containsKey("password")){
            return true;
        }
        return false;
    }

    private Usuario getUserFromMap(Map<String , String> requestMap){

        Usuario usuario = new Usuario();
        usuario.setNombre(requestMap.get("nombre"));
        usuario.setEmail(requestMap.get("email"));
        usuario.setPassword(requestMap.get("password"));
        usuario.setStatus(requestMap.get("false"));
        usuario.setRol("usuario");

        return usuario;

    }

    public ResponseEntity<String> login(Map<String , String> requestMap){
        return null;
    }
}
