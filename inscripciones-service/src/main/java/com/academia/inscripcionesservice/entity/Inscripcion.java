
package com.academia.inscripcionesservice.entity;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "inscripciones")
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_curso", nullable = false)
    private Long idCurso;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false, length = 150)
    private String correo;

    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(nullable = false)
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoInscripcion estado;

    protected Inscripcion() {
    }

    public Inscripcion(Long idCurso, String nombres,
                       String apellidos, String correo,
                       String telefono) {

        this.idCurso = idCurso;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;

        this.fecha = LocalDate.now();
        this.estado = EstadoInscripcion.PENDIENTE;
    }

    public void actualizar(Long idCurso, String nombres,
                           String apellidos, String correo,
                           String telefono) {

        this.idCurso = idCurso;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
    }

    public void cambiarEstado(EstadoInscripcion estado) {
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public Long getIdCurso() {
        return idCurso;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public EstadoInscripcion getEstado() {
        return estado;
    }
}
