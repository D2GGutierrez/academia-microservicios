
package com.academia.inscripcionesservice.repository;

import com.academia.inscripcionesservice.entity.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscripcionRepository
        extends JpaRepository<Inscripcion, Long> {
}
