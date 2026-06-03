package com.faltauno.faltauno.repositories;

import com.faltauno.faltauno.models.Pista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PistaRepository extends JpaRepository<Pista, Long> {

    // Busca las pistas usando el Enum que acabamos de crear
    List<Pista> findByEstado(Pista.EstadoPista estado);
}