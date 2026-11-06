
package com.academia.cursos_service.service;

import java.util.List;

import com.academia.cursos_service.client.DocentesClient;
import com.academia.cursos_service.dto.CursoRequest;
import com.academia.cursos_service.entity.Curso;
import com.academia.cursos_service.repository.CursoRepository;

import feign.FeignException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CursoService {

    private final CursoRepository repository;
    private final DocentesClient docentesClient;

    public CursoService(CursoRepository repository,
                        DocentesClient docentesClient) {
        this.repository = repository;
        this.docentesClient = docentesClient;
    }

    // Listar todos los cursos
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return repository.findAll();
    }

    // Buscar un curso por su ID
    @Transactional(readOnly = true)
    public Curso buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Curso no encontrado"
                ));
    }

    // Registrar un nuevo curso
    @Transactional
    public Curso registrar(CursoRequest datos) {

        validarDocente(datos.idDocente());

        Curso curso = new Curso(
                datos.nombre().trim(),
                datos.duracion(),
                datos.precio(),
                datos.idDocente()
        );

        return repository.save(curso);
    }

    // Actualizar un curso existente
    @Transactional
    public Curso actualizar(Long id, CursoRequest datos) {

        Curso curso = buscar(id);

        validarDocente(datos.idDocente());

        curso.actualizar(
                datos.nombre().trim(),
                datos.duracion(),
                datos.precio(),
                datos.idDocente()
        );

        return repository.save(curso);
    }

    // Eliminar un curso
    @Transactional
    public void eliminar(Long id) {

        Curso curso = buscar(id);
        repository.delete(curso);
    }

    // Comprobar que el docente existe
    private void validarDocente(Long idDocente) {

        try {
            docentesClient.buscarDocente(idDocente);

        } catch (FeignException.NotFound e) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El docente indicado no existe"
            );

        } catch (FeignException e) {

            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "No se pudo verificar el docente",
                    e
            );
        }
    }
}
