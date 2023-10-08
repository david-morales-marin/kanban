package com.example.kanban.services;

import com.example.kanban.constantes.Constantes;
import com.example.kanban.entitys.credenciales.Usuario;
import com.example.kanban.repositorys.UsuarioRepository;
//import com.example.kanban.security.CustomerDetailsService;
import com.example.kanban.security.jwt.JwtUtilService;
import com.example.kanban.utils.ProjectUtils;
import io.micrometer.common.util.internal.logging.InternalLogger;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UsuarioServices implements UserDetailsService {

   // @Autowired
   // private UsuarioRepository usuarioRepository;

   // @Autowired
   // private AuthenticationManager authenticationManager;

    // @Autowired
    // private CustomerDetailsService customerDetailsService;

    //@Autowired
    //private JwtUtilService jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String , String> usuarios = Map.of(
                "david", "USER",
                "KAREN", "admin"
        );

        String rol = usuarios.get(username);
        if (rol != null){
            User.UserBuilder userBuilder = User.withUsername(username);
            String encryptedPassword = "$dwefrkofnewouiewdiwenfefkwepmlkemf";
            userBuilder.password(encryptedPassword).roles(rol);
            return  userBuilder.build();
        }else{
            throw new UsernameNotFoundException(username);
        }

    }



  /*  InternalLogger log;
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
    }*/

    /*private Usuario getUserFromMap(Map<String , String> requestMap){

        Usuario usuario = new Usuario();
        usuario.setUsuario(requestMap.get("usuario"));
        usuario.setEmail(requestMap.get("email"));
        usuario.setPassword(requestMap.get("password"));
        usuario.setStatus(requestMap.get("false"));
        usuario.setRol("usuario");

        return usuario;

    }*/

  /*  public ResponseEntity<String> login(Map<String , String> requestMap){

        log.info("Dentro del login");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );

            if(authentication.isAuthenticated()){
                if(customerDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token de acceso\":\"" + jwtUtil.generateToken(customerDetailsService.getUserDetail().getEmail(),
                            customerDetailsService.getUserDetail().getRol()) + "\"}", HttpStatus.OK);
                }else{
                    return new ResponseEntity<String>("Espere la aprobación del administrador " , HttpStatus.BAD_REQUEST);
                }
            }

        }catch (Exception e){
            log.error("{}" , e);
        }
        return new ResponseEntity<String>("Credenciales incorrectas" , HttpStatus.BAD_REQUEST);
    }*/
}
