package com.faltauno.faltauno.services;

import com.faltauno.faltauno.models.Usuario;
import com.faltauno.faltauno.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // <-- Importación nueva
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    public Usuario guardarUsuario(Usuario usuario) {
        String passwordEncriptada = passwordEncoder.encode(usuario.getPasswd());

        usuario.setPasswd(passwordEncriptada);

        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }
    public Usuario cambiarEstado(Long id, String nuevoEstado) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario u = usuarioOpt.get();
            u.setEstado(nuevoEstado);
            return usuarioRepository.save(u);
        }
        return null;
    }
}
