package com.faltauno.faltauno.controllers;

import com.faltauno.faltauno.models.Pista;
import com.faltauno.faltauno.services.PistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pistas")
public class PistaController {

    @Autowired
    private PistaService pistaService;

    // GET /api/pistas -> Devuelve SOLO las ACTIVAS (¡Cambiado de APROBADA a ACTIVA para conectar con tu SQL!)
    @GetMapping
    public List<Pista> listarPistasMapa() {
        return pistaService.obtenerPorEstado(Pista.EstadoPista.ACTIVA);
    }

    // GET /api/pistas/pendientes -> Devuelve las PENDIENTES (para panelAdmin.html)
    @GetMapping("/pendientes")
    public List<Pista> listarPistasPendientes() {
        return pistaService.obtenerPorEstado(Pista.EstadoPista.PENDIENTE);
    }

    // POST /api/pistas -> Usuario propone una cancha (proponerCancha.html)
    @PostMapping
    public Pista crearPista(@RequestBody Pista pista) {
        return pistaService.guardarPista(pista);
    }

    // PUT /api/pistas/1/estado -> Admin aprueba o rechaza (panelAdmin.html)
    // Corregido: id ahora usa Long y convertimos el String del JSON al Enum de Java
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            String estadoStr = body.get("estado");
            if (estadoStr == null) {
                return ResponseEntity.badRequest().body("Falta el campo 'estado' en el cuerpo de la petición.");
            }

            // Convertimos el texto (ej: "ACTIVA" o "PENDIENTE") al Enum de Java automáticamente
            Pista.EstadoPista nuevoEstado = Pista.EstadoPista.valueOf(estadoStr.toUpperCase());

            Pista actualizada = pistaService.cambiarEstado(id, nuevoEstado);

            if (actualizada != null) {
                return ResponseEntity.ok(actualizada);
            }
            return ResponseEntity.notFound().build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Estado no válido. Los estados permitidos son: ACTIVA, PENDIENTE, INACTIVA, MANTENIMIENTO");
        }
    }
}