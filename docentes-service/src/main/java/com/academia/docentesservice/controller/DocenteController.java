
package com.academia.docentesservice.controller;

import java.net.URI;
import java.util.List;

import com.academia.docentesservice.dto.DocenteRequest;
import com.academia.docentesservice.entity.Docente;
import com.academia.docentesservice.service.DocenteService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/docentes")
public class DocenteController {

    private final DocenteService service;

    public DocenteController(DocenteService service) {
        this.service = service;
    }

    // Listar docentes
    @GetMapping
    public List<Docente> listar() {
        return service.listar();
    }

    // Buscar docente por ID
    @GetMapping("/{id}")
    public Docente buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    // Registrar docente
    @PostMapping
    public ResponseEntity<Docente> registrar(
            @Valid @RequestBody DocenteRequest datos) {

        Docente docente = service.registrar(datos);

        return ResponseEntity
                .created(URI.create("/api/docentes/" + docente.getId()))
                .body(docente);
    }

    // Actualizar docente
    @PutMapping("/{id}")
    public Docente actualizar(
            @PathVariable Long id,
            @Valid @RequestBody DocenteRequest datos) {

        return service.actualizar(id, datos);
    }

    // Eliminar docente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
