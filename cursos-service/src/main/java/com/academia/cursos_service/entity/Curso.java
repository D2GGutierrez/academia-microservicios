
package com.academia.cursos_service.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false)
    private Integer duracion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "id_docente", nullable = false)
    private Long idDocente;

    protected Curso() {
    }

    public Curso(String nombre, Integer duracion,
                 BigDecimal precio, Long idDocente) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.precio = precio;
        this.idDocente = idDocente;
    }

    public void actualizar(String nombre, Integer duracion,
                           BigDecimal precio, Long idDocente) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.precio = precio;
        this.idDocente = idDocente;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public Long getIdDocente() {
        return idDocente;
    }
}
