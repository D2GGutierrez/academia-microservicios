
package com.academia.cursos_service.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;

public record CursoRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 150)
        String nombre,

        @NotNull(message = "La duracion es obligatoria")
        @Positive(message = "La duracion debe ser positiva")
        Integer duracion,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(
                value = "0.01",
                message = "El precio debe ser mayor que cero"
        )
        @Digits(integer = 8, fraction = 2)
        BigDecimal precio,

        @NotNull(message = "El docente es obligatorio")
        @Positive(message = "El ID del docente debe ser positivo")
        Long idDocente

) {}
