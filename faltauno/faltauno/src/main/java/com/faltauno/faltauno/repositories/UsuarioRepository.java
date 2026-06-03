package com.faltauno.faltauno.repositories;

import com.faltauno.faltauno.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Método personalizado para encontrar un usuario por su email
    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findById(Long id);
}