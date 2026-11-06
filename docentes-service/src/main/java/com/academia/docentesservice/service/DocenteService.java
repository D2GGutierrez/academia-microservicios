
package com.academia.docentesservice.service;

import java.util.List;
import java.util.Locale;

import com.academia.docentesservice.dto.DocenteRequest;
import com.academia.docentesservice.entity.Docente;
import com.academia.docentesservice.repository.DocenteRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DocenteService {

    private final DocenteRepository repository;

    public DocenteService(DocenteRepository repository) {
        this.repository = repository;
    }

    // Listar todos los docentes
    @Transactional(readOnly = true)
    public List<Docente> listar() {
        return repository.findAll();
    }

    // Buscar docente por ID
    @Transactional(readOnly = true)
    public Docente buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Docente no encontrado"
                ));
    }

    // Registrar un docente
    @Transactional
    public Docente registrar(DocenteRequest datos) {

        String correo = normalizarCorreo(datos.correo());

        if (repository.existsByCorreoIgnoreCase(correo)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El correo ya esta registrado"
            );
        }

        Docente docente = new Docente(
                datos.nombres().trim(),
                datos.apellidos().trim(),
                correo,
                datos.especialidad().trim()
        );

        return repository.save(docente);
    }

    // Actualizar un docente
    @Transactional
    public Docente actualizar(Long id, DocenteRequest datos) {

        Docente docente = buscar(id);
        String correo = normalizarCorreo(datos.correo());

        if (repository.existsByCorreoIgnoreCaseAndIdNot(correo, id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El correo ya esta registrado"
            );
        }

        docente.actualizar(
                datos.nombres().trim(),
                datos.apellidos().trim(),
                correo,
                datos.especialidad().trim()
        );

        return repository.save(docente);
    }

    // Eliminar un docente
    @Transactional
    public void eliminar(Long id) {
        Docente docente = buscar(id);
        repository.delete(docente);
    }

    private String normalizarCorreo(String correo) {
        return correo.trim().toLowerCase(Locale.ROOT);
    }
}
