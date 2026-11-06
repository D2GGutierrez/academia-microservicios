
package com.academia.docentesservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "docentes")
public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false, unique = true, length = 150)
    private String correo;

    @Column(nullable = false, length = 100)
    private String especialidad;

    protected Docente() {
    }

    public Docente(String nombres, String apellidos,
                   String correo, String especialidad) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.especialidad = especialidad;
    }

    public void actualizar(String nombres, String apellidos,
                           String correo, String especialidad) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.especialidad = especialidad;
    }

    public Long getId() {
        return id;
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

    public String getEspecialidad() {
        return especialidad;
    }
}
