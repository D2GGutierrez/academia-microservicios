
package com.academia.inscripcionesservice.dto;

import jakarta.validation.constraints.*;

public record InscripcionRequest(

        @NotNull(message = "El curso es obligatorio")
        @Positive(message = "El ID del curso debe ser positivo")
        Long idCurso,

        @NotBlank(message = "Los nombres son obligatorios")
        @Size(max = 100)
        String nombres,

        @NotBlank(message = "Los apellidos son obligatorios")
        @Size(max = 100)
        String apellidos,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no es valido")
        @Size(max = 150)
        String correo,

        @NotBlank(message = "El telefono es obligatorio")
        @Size(max = 20)
        String telefono

) {}
