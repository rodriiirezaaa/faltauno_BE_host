package com.faltauno.faltauno.repositories;

import com.faltauno.faltauno.models.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    // Método para buscar solo los partidos que aún buscan gente
    List<Alerta> findByEstado(Alerta.Estado estado);

    long countByUsuarioId(Long usuarioId);

}