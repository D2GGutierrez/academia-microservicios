
package com.academia.inscripcionesservice.dto;

import com.academia.inscripcionesservice.entity.EstadoInscripcion;
import jakarta.validation.constraints.NotNull;

public record EstadoRequest(

        @NotNull(message = "El estado es obligatorio")
        EstadoInscripcion estado

) {}
