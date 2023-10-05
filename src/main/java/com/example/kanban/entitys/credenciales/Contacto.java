package com.example.kanban.entitys.credenciales;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contacto")
    private Integer id;

    private String nombre;

    @Column(name = "fecha_nac")
    private LocalDate fechaNacimiento;

    private String celular;

    private String email;

}