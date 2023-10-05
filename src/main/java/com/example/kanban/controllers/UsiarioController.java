package com.example.kanban.controllers;

import com.example.kanban.constantes.Constantes;
import com.example.kanban.services.UsuarioServices;
import com.example.kanban.utils.ProjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/usuario")
public class UsiarioController {

    @Autowired
    UsuarioServices usuarioServices;

    @PostMapping("/signup")
    public ResponseEntity<String> registrarUsuario(@RequestBody(required = true)Map<String , String> requestMap){

        try{
             usuarioServices.signUp(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
         return ProjectUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
