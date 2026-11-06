
package com.academia.inscripcionesservice.controller;

import java.net.URI;
import java.util.List;

import com.academia.inscripcionesservice.dto.EstadoRequest;
import com.academia.inscripcionesservice.dto.InscripcionRequest;
import com.academia.inscripcionesservice.entity.Inscripcion;
import com.academia.inscripcionesservice.service.InscripcionService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final InscripcionService service;

    public InscripcionController(InscripcionService service) {
        this.service = service;
    }

    // Listar inscripciones
    @GetMapping
    public List<Inscripcion> listar() {
        return service.listar();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public Inscripcion buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    // Registrar inscripcion
    @PostMapping
    public ResponseEntity<Inscripcion> registrar(
            @Valid @RequestBody InscripcionRequest datos) {

        Inscripcion inscripcion = service.registrar(datos);

        return ResponseEntity
                .created(URI.create(
                        "/api/inscripciones/" + inscripcion.getId()
                ))
                .body(inscripcion);
    }

    // Actualizar inscripcion
    @PutMapping("/{id}")
    public Inscripcion actualizar(
            @PathVariable Long id,
            @Valid @RequestBody InscripcionRequest datos) {

        return service.actualizar(id, datos);
    }

    // Cambiar el estado
    @PatchMapping("/{id}/estado")
    public Inscripcion cambiarEstado(
            @PathVariable Long id,
            @Valid @RequestBody EstadoRequest datos) {

        return service.cambiarEstado(id, datos.estado());
    }

    // Eliminar inscripcion
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
