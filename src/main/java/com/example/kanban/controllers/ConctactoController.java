/*package com.example.kanban.controllers;

import com.example.kanban.entitys.credenciales.Contacto;
import com.example.kanban.repositorys.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contactos")
@AllArgsConstructor
public class ConctactoController {

    @Autowired
    private  ContactRepository contactRepository;
   // private final ContactRepository contactRepository;

    @GetMapping
    public List<Contacto> listConctatos(){
        return contactRepository.findAll();
    }

}*/
