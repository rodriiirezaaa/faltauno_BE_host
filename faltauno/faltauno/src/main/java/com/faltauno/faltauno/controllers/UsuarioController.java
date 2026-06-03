package com.faltauno.faltauno.controllers;

import com.faltauno.faltauno.models.Usuario;
import com.faltauno.faltauno.services.UsuarioService;
import com.faltauno.faltauno.repositories.AlertaRepository; // Importación nueva
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private com.faltauno.faltauno.repositories.ValoracionRepository valoracionRepository;

    // Inyectamos el repositorio de alertas para poder contarlas
    @Autowired
    private AlertaRepository alertaRepository;

    // ENDPOINT 1: REGISTRO REAL
    @PostMapping
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario nuevoUsuario) {
        if (usuarioService.buscarPorEmail(nuevoUsuario.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: El email ya está registrado");
        }

        Usuario usuarioGuardado = usuarioService.guardarUsuario(nuevoUsuario);
        return ResponseEntity.ok(usuarioGuardado);
    }

    // ENDPOINT 2: LOGIN REAL
    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody Usuario credenciales) {
        Optional<Usuario> usuarioDb = usuarioService.buscarPorEmail(credenciales.getEmail());

        if (usuarioDb.isPresent()) {
            Usuario u = usuarioDb.get();

            if (u.getPasswd() != null && passwordEncoder.matches(credenciales.getPasswd(), u.getPasswd())) {
                if ("BANEADO".equals(u.getEstado())) {
                    return ResponseEntity.status(403).body("Error: Tu cuenta ha sido suspendida.");
                }
                return ResponseEntity.ok(u);
            }
        }
        return ResponseEntity.status(401).body("Error: Email o contraseña incorrectos");
    }

    // ENDPOINT 3: OBTENER TODOS LOS USUARIOS
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    // ENDPOINT 4: OBTENER ESTADÍSTICAS REALES
    @GetMapping("/{id}/estadisticas")
    public ResponseEntity<?> obtenerEstadisticasUsuario(@PathVariable Long id) {
        // 1. Buscamos al usuario en MySQL por su ID
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(id);

        int partidosReales = 0;
        if (usuarioOpt.isPresent()) {
            // Si el usuario existe, extraemos sus partidos jugados de la BD
            partidosReales = usuarioOpt.get().getPartidosJugados();
        }

        // 2. Contamos las alertas reales que este usuario tiene guardadas en MySQL
        long alertasReales = alertaRepository.countByUsuarioId(id);

        // 3. Preparamos el paquete de datos (JSON)
        Map<String, Object> stats = new HashMap<>();

        stats.put("alertasEnviadas", alertasReales);
        stats.put("partidosJugados", partidosReales); // ¡AHORA 100% DINÁMICO DE MYSQL!
        stats.put("pistasDescubiertas", 5); // Este lo cambiaremos cuando hagamos su tabla

        // 4. Generamos también su lista de actividad en tiempo real
        List<String> actividad = new ArrayList<>();
        actividad.add("Has publicado un total de " + alertasReales + " alertas de partidos en el tablón.");
        actividad.add("Has participado en un total de " + partidosReales + " partidos con la comunidad.");
        stats.put("actividadReciente", actividad);

        return ResponseEntity.ok(stats);
    }
    // --- NUEVO ENDPOINT 5: CAMBIAR ESTADO DE USUARIO (BANEAR/DESBANEAR) ---
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstadoUsuario(@PathVariable Long id, @RequestBody java.util.Map<String, String> request) {
        String nuevoEstado = request.get("estado"); // Recibe "BANEADO" o "ACTIVO"
        Usuario usuarioActualizado = usuarioService.cambiarEstado(id, nuevoEstado);

        if (usuarioActualizado != null) {
            return ResponseEntity.ok(usuarioActualizado);
        }
        return ResponseEntity.badRequest().body("Error: Usuario no encontrado");
    }
    // ENDPOINT 6: VALORAR A UN USUARIO Y ACTUALIZAR SU MEDIA
    @PostMapping("/{id}/valorar")
    public ResponseEntity<?> valorarUsuario(@PathVariable Long id, @RequestBody java.util.Map<String, Integer> payload) {
        // Recibimos quién vota y qué nota le pone
        Long idAutor = payload.get("idAutor").longValue();
        Integer estrellas = payload.get("estrellas");

        // Buscamos a los dos usuarios en la base de datos
        Optional<Usuario> autorOpt = usuarioService.buscarPorId(idAutor);
        Optional<Usuario> destinoOpt = usuarioService.buscarPorId(id);

        if (autorOpt.isPresent() && destinoOpt.isPresent()) {
            Usuario destino = destinoOpt.get();

            // 1. Guardamos la nueva valoración en la tabla 'valoraciones'
            com.faltauno.faltauno.models.Valoracion nuevaValoracion = new com.faltauno.faltauno.models.Valoracion();
            nuevaValoracion.setAutor(autorOpt.get());
            nuevaValoracion.setDestino(destino);
            nuevaValoracion.setEstrellas(estrellas);
            valoracionRepository.save(nuevaValoracion);

            // 2. Calculamos la nueva media usando el repositorio
            Double nuevaMedia = valoracionRepository.calcularNotaMedia(id);

            // 3. Actualizamos la columna 'med_val' en la tabla 'usuarios'
            // (Asegúrate de que en Usuario.java tu variable medVal sea de tipo Double o Float)
            destino.setMedVal(nuevaMedia != null ? nuevaMedia : 0.0);
            usuarioService.guardarUsuario(destino);

            return ResponseEntity.ok("Valoración guardada y media actualizada a: " + nuevaMedia);
        }

        return ResponseEntity.badRequest().body("Error: No se encontraron los usuarios");
    }
}
