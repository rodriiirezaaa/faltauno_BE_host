package com.faltauno.faltauno.models;

import jakarta.persistence.*;

@Entity
@Table(name = "valoraciones")
public class Valoracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Quien pone la nota
    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    private Usuario autor;

    // Quien recibe la nota
    @ManyToOne
    @JoinColumn(name = "id_destino", nullable = false)
    private Usuario destino;

    @Column(nullable = false)
    private Integer estrellas;

    // --- GETTERS Y SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Usuario getAutor() { return autor; }
    public void setAutor(Usuario autor) { this.autor = autor; }

    public Usuario getDestino() { return destino; }
    public void setDestino(Usuario destino) { this.destino = destino; }

    public Integer getEstrellas() { return estrellas; }
    public void setEstrellas(Integer estrellas) { this.estrellas = estrellas; }
}