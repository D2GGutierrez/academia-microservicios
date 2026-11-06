
package com.academia.inscripcionesservice.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cursos-service")
public interface CursosClient {

    @GetMapping("/api/cursos/{id}")
    Map<String, Object> buscarCurso(
            @PathVariable("id") Long id
    );
}
