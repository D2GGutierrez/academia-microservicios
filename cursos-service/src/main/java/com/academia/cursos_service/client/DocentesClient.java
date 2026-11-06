
package com.academia.cursos_service.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "docentes-service")
public interface DocentesClient {

    @GetMapping("/api/docentes/{id}")
    Map<String, Object> buscarDocente(
            @PathVariable("id") Long id
    );
}
