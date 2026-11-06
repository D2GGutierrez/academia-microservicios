
package com.academia.inscripcionesservice.service;

import java.util.List;
import java.util.Locale;

import com.academia.inscripcionesservice.client.CursosClient;
import com.academia.inscripcionesservice.dto.InscripcionRequest;
import com.academia.inscripcionesservice.entity.EstadoInscripcion;
import com.academia.inscripcionesservice.entity.Inscripcion;
import com.academia.inscripcionesservice.repository.InscripcionRepository;

import feign.FeignException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InscripcionService {

    private final InscripcionRepository repository;
    private final CursosClient cursosClient;

    public InscripcionService(
            InscripcionRepository repository,
            CursosClient cursosClient) {

        this.repository = repository;
        this.cursosClient = cursosClient;
    }

    // Listar inscripciones
    @Transactional(readOnly = true)
    public List<Inscripcion> listar() {
        return repository.findAll();
    }

    // Buscar inscripcion por ID
    @Transactional(readOnly = true)
    public Inscripcion buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Inscripcion no encontrada"
                ));
    }

    // Registrar inscripcion
    @Transactional
    public Inscripcion registrar(InscripcionRequest datos) {

        validarCurso(datos.idCurso());

        Inscripcion inscripcion = new Inscripcion(
                datos.idCurso(),
                datos.nombres().trim(),
                datos.apellidos().trim(),
                normalizarCorreo(datos.correo()),
                datos.telefono().trim()
        );

        return repository.save(inscripcion);
    }

    // Actualizar datos de la inscripcion
    @Transactional
    public Inscripcion actualizar(
            Long id, InscripcionRequest datos) {

        Inscripcion inscripcion = buscar(id);

        validarCurso(datos.idCurso());

        inscripcion.actualizar(
                datos.idCurso(),
                datos.nombres().trim(),
                datos.apellidos().trim(),
                normalizarCorreo(datos.correo()),
                datos.telefono().trim()
        );

        return repository.save(inscripcion);
    }

    // Cambiar estado de una inscripcion
    @Transactional
    public Inscripcion cambiarEstado(
            Long id, EstadoInscripcion estado) {

        Inscripcion inscripcion = buscar(id);

        inscripcion.cambiarEstado(estado);

        return repository.save(inscripcion);
    }

    // Eliminar inscripcion
    @Transactional
    public void eliminar(Long id) {

        Inscripcion inscripcion = buscar(id);

        repository.delete(inscripcion);
    }

    // Verificar que el curso exista
    private void validarCurso(Long idCurso) {

        try {
            cursosClient.buscarCurso(idCurso);

        } catch (FeignException.NotFound e) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El curso indicado no existe"
            );

        } catch (FeignException e) {

            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "No se pudo verificar el curso",
                    e
            );
        }
    }

    private String normalizarCorreo(String correo) {
        return correo.trim().toLowerCase(Locale.ROOT);
    }
}
