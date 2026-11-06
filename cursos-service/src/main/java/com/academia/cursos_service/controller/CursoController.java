
package com.academia.cursos_service.controller;

import java.net.URI;
import java.util.List;

import com.academia.cursos_service.dto.CursoRequest;
import com.academia.cursos_service.entity.Curso;
import com.academia.cursos_service.service.CursoService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    // Listar todos los cursos
    @GetMapping
    public List<Curso> listar() {
        return service.listar();
    }

    // Buscar un curso por ID
    @GetMapping("/{id}")
    public Curso buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    // Registrar un nuevo curso
    @PostMapping
    public ResponseEntity<Curso> registrar(
            @Valid @RequestBody CursoRequest datos) {

        Curso curso = service.registrar(datos);

        return ResponseEntity
                .created(URI.create("/api/cursos/" + curso.getId()))
                .body(curso);
    }

    // Actualizar un curso
    @PutMapping("/{id}")
    public Curso actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CursoRequest datos) {

        return service.actualizar(id, datos);
    }

    // Eliminar un curso
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
