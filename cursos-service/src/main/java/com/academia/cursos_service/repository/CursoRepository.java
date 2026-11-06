
package com.academia.cursos_service.repository;

import com.academia.cursos_service.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository
        extends JpaRepository<Curso, Long> {
}
