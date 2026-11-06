package com.academia.docentesservice.repository;

import com.academia.docentesservice.entity.Docente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocenteRepository
        extends JpaRepository<Docente, Long> {

    boolean existsByCorreoIgnoreCase(String correo);

    boolean existsByCorreoIgnoreCaseAndIdNot(
            String correo, Long id
    );
}
