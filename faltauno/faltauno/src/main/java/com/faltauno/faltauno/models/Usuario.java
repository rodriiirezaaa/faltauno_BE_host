package com.faltauno.faltauno.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellidos;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwd;

    @Column(name = "med_val")
    private Double medVal = 0.0;

    @Column(name = "partidos_jugados")
    private Integer partidosJugados = 0; // Por defecto empezará en 0 partidos


    private String ciudad = "Ourense";
    private String rol = "USER"; // USER o ADMIN


    private String estado = "ACTIVO"; // ACTIVO o BANEADO

    private Double valoracionMedia = 5.0;
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    // GETTERS Y SETTERS MANUALES

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getCiudad() {
        return ciudad;
    }
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }


    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getValoracionMedia() {
        return valoracionMedia;
    }
    public void setValoracionMedia(Double valoracionMedia) {
        this.valoracionMedia = valoracionMedia;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Double getMedVal() {
        return medVal;
    }

    public void setMedVal(Double medVal) {
        this.medVal = medVal;
    }
    public Integer getPartidosJugados() {
        return partidosJugados;
    }

    public void setPartidosJugados(Integer partidosJugados) {
        this.partidosJugados = partidosJugados;
    }
}