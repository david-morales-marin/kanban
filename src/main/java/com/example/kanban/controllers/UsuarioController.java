package com.example.kanban.controllers;

import ch.qos.logback.core.subst.Token;
import com.example.kanban.KanbanApplication;
import com.example.kanban.constantes.Constantes;
import com.example.kanban.entitys.credenciales.Usuario;
import com.example.kanban.models.TokenInfo;
import com.example.kanban.security.jwt.JwtUtilService;
import com.example.kanban.services.UsuarioServices;
import com.example.kanban.utils.ProjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/usuario")
public class UsuarioController {

    @Autowired
    UsuarioServices usuarioServices;

     @Autowired(required = true)
     private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    UserDetailsService usuarioDetailsService;


    private static final Logger logger = LoggerFactory.getLogger(KanbanApplication.class);

  /*  @PostMapping("/signup")
    public ResponseEntity<String> registrarUsuario(@RequestBody(required = true)Map<String , String> requestMap){

        try{
            return usuarioServices.signUp(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
         return ProjectUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
    }*/


 /*   @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody(required = true) Map<String , String> requestMap){
        try{
             return usuarioServices.login(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  ProjectUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    //nuevo infor del video tutorial nuevo

    @GetMapping("/mensaje")
    public ResponseEntity<?> getMensaje(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Datos del Usuario: {} ", auth.getPrincipal());
        logger.info("Datos de los Permisos {}" , auth.getAuthorities());
        logger.info("Esta autenticado {}", auth.isAuthenticated());

        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("Contenido","Bienvenido a tus organizador de Projectos Kanba");

        return ResponseEntity.ok(mensaje);
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getMensajeAdmin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Datos del Usuario: {} ", auth.getPrincipal());
        logger.info("Datos de los Permisos {}" , auth.getAuthorities());
        logger.info("Esta autenticado {}", auth.isAuthenticated());

        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("Contenido","Bienvenido a tus organizador de Projectos Kanba, estas como administrador ");

        return ResponseEntity.ok(mensaje);
    }

    @GetMapping("/empleado")
    public ResponseEntity<?> getMensajePublico(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Datos del Usuario: {} ", auth.getPrincipal());
        logger.info("Datos de los Permisos {}" , auth.getAuthorities());
        logger.info("Esta autenticado {}", auth.isAuthenticated());

        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("Contenido","Bienvenido a tus organizador de Projectos Kanba, estas como empleado ");

        return ResponseEntity.ok(mensaje);
    }

    @PostMapping("/publico/authenticate")
    public ResponseEntity<TokenInfo> authenticate(
            @RequestBody Usuario authenticationReq){
        logger.info("Autenticando al usuario {}" , authenticationReq.getUsuario());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationReq.getUsuario(),
                        authenticationReq.getPassword()));

        final UserDetails userDetails = usuarioServices.loadUserByUsername(
                authenticationReq.getUsuario());

        final String jwt = jwtUtilService.generateToken(userDetails);

        TokenInfo token = new TokenInfo(jwt);

        return ResponseEntity.ok(token);
    }

}
