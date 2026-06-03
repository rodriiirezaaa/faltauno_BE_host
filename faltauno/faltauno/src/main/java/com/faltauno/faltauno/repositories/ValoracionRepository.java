package com.faltauno.faltauno.repositories;

import com.faltauno.faltauno.models.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ValoracionRepository extends JpaRepository<Valoracion, Integer> {

    // Le pedimos a MySQL que calcule la media de las estrellas de un usuario concreto
    @Query("SELECT AVG(v.estrellas) FROM Valoracion v WHERE v.destino.id = :usuarioId")
    Double calcularNotaMedia(@Param("usuarioId") Long usuarioId);
}