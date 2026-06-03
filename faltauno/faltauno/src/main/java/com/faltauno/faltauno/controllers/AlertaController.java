package com.faltauno.faltauno.controllers;

import com.faltauno.faltauno.models.Alerta;
import com.faltauno.faltauno.services.AlertaService;
import com.faltauno.faltauno.services.UsuarioService; // ⚠️ NUEVO
import com.faltauno.faltauno.repositories.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alertas")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private UsuarioService usuarioService; // ⚠️ NUEVO: Para poder actualizar al usuario

    // GET: Ver las alertas activas
    @GetMapping
    public List<Alerta> listarAlertas() {
        return alertaService.obtenerAlertasActivas();
    }

    // POST: Lanzar una nueva alerta
    @PostMapping
    public Alerta crearAlerta(@RequestBody Alerta alerta) {
        return alertaService.guardarAlerta(alerta);
    }

    // PUT: Unirse a un partido
    @PutMapping("/{id}/unirse")
    public ResponseEntity<?> unirseAlerta(@PathVariable Long id, @RequestBody Map<String, Long> payload) {
        // 1. Extraemos el ID del usuario que nos manda Javascript
        Long usuarioId = payload.get("usuarioId");

        return alertaRepository.findById(id).map(alerta -> {
            if (alerta.getJugadoresFaltantes() > 0) {
                // 2. Le restamos 1 a las plazas libres de la alerta
                alerta.setJugadoresFaltantes(alerta.getJugadoresFaltantes() - 1);
                alertaRepository.save(alerta);

                // 3. ⚠️ NUEVO: Le sumamos 1 partido jugado al usuario
                if (usuarioId != null) {
                    usuarioService.buscarPorId(usuarioId).ifPresent(usuario -> {
                        // Obtenemos sus partidos (si es null, usamos 0) y le sumamos 1
                        int partidosActuales = usuario.getPartidosJugados() != null ? usuario.getPartidosJugados() : 0;
                        usuario.setPartidosJugados(partidosActuales + 1);
                        usuarioService.guardarUsuario(usuario); // Guardamos en MySQL
                    });
                }

                return ResponseEntity.ok(alerta);
            } else {
                return ResponseEntity.badRequest().body("El partido ya está completo");
            }
        }).orElse(ResponseEntity.notFound().build());
    }
}