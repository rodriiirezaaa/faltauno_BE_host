package com.faltauno.faltauno.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alertas")
public class Alerta {

    // Cambiado para que coincida con tu base de datos (ACTIVA o CERRADA)
    public enum Estado {
        ACTIVA, CERRADA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String deporte;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "num_jugadores", nullable = false)
    private Integer numJugadores;

    // --- ¡AQUÍ ESTÁ EL CULPABLE SOLUCIONADO! ---
    @Column(name = "jugadores_faltantes", nullable = false)
    private Integer jugadoresFaltantes;

    @Column(name = "rango_edad_min")
    private Integer rangoEdadMin;

    @Column(name = "rango_edad_max")
    private Integer rangoEdadMax;

    @Column(name = "informacion_adicional", columnDefinition = "TEXT")
    private String informacionAdicional;

    // --- CORRECCIÓN DE LA RELACIÓN CON LA PISTA ---
    // En tu SQL la clave obligatoria es id_pista, no id_cancha
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pista", nullable = false)
    private Pista pista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado = Estado.ACTIVA;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();


    // ==========================================
    // Constructores
    // ==========================================
    public Alerta() {
    }

    // ==========================================
    // Getters y Setters
    // ==========================================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDeporte() { return deporte; }
    public void setDeporte(String deporte) { this.deporte = deporte; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public Integer getNumJugadores() { return numJugadores; }
    public void setNumJugadores(Integer numJugadores) { this.numJugadores = numJugadores; }

    // --- Getters y Setters NUEVOS ---
    public Integer getJugadoresFaltantes() { return jugadoresFaltantes; }
    public void setJugadoresFaltantes(Integer jugadoresFaltantes) { this.jugadoresFaltantes = jugadoresFaltantes; }

    public Integer getRangoEdadMin() { return rangoEdadMin; }
    public void setRangoEdadMin(Integer rangoEdadMin) { this.rangoEdadMin = rangoEdadMin; }

    public Integer getRangoEdadMax() { return rangoEdadMax; }
    public void setRangoEdadMax(Integer rangoEdadMax) { this.rangoEdadMax = rangoEdadMax; }

    public String getInformacionAdicional() { return informacionAdicional; }
    public void setInformacionAdicional(String informacionAdicional) { this.informacionAdicional = informacionAdicional; }

    public Pista getPista() { return pista; }
    public void setPista(Pista pista) { this.pista = pista; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
