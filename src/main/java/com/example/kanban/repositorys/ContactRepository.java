package com.example.kanban.repositorys;

import com.example.kanban.entitys.credenciales.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contacto, Integer> {
}
