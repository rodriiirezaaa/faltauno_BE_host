package com.faltauno.faltauno.models; // Ojo a la "s" final si la usas

import jakarta.persistence.*;

@Entity
@Table(name = "pistas")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pista {

    // Si quieres usar el estado como Enum en Java para mayor seguridad
    public enum EstadoPista {
        ACTIVA, INACTIVA, MANTENIMIENTO, PENDIENTE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private Double latitud;

    @Column(nullable = false)
    private Double longitud;

    @Column(nullable = false)
    private String deporte;

    @Column(name = "estado_conservacion")
    private String estadoConservacion = "Bueno";

    @Column(columnDefinition = "boolean default false")
    private Boolean verificada = false;

    // MAGIA AQUÍ: Le decimos a Java que en la base de datos es un VARCHAR (String)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPista estado = EstadoPista.ACTIVA;

    // ==========================================
    // Constructores vacíos y con parámetros
    // ==========================================
    public Pista() {
    }

    // ==========================================
    // Getters y Setters (Imprescindibles para que Spring lea los datos)
    // ==========================================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }

    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }

    public String getDeporte() { return deporte; }
    public void setDeporte(String deporte) { this.deporte = deporte; }

    public String getEstadoConservacion() { return estadoConservacion; }
    public void setEstadoConservacion(String estadoConservacion) { this.estadoConservacion = estadoConservacion; }

    public Boolean getVerificada() { return verificada; }
    public void setVerificada(Boolean verificada) { this.verificada = verificada; }

    public EstadoPista getEstado() { return estado; }
    public void setEstado(EstadoPista estado) { this.estado = estado; }
}
