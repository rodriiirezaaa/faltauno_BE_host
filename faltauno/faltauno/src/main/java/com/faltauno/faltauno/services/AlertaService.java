package com.faltauno.faltauno.services;

import com.faltauno.faltauno.models.Alerta;
import com.faltauno.faltauno.repositories.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository alertaRepository;

    // Obtener solo las alertas activas para el "Feed" principal
    public List<Alerta> obtenerAlertasActivas() {
        return alertaRepository.findByEstado(Alerta.Estado.ACTIVA);
    }

    // Crear una nueva alerta (Lanzar un "Falta Uno")
    public Alerta guardarAlerta(Alerta alerta) {
        // Al crearla, siempre empieza como ACTIVA y guardamos la hora exacta
        alerta.setEstado(Alerta.Estado.ACTIVA);
        alerta.setFechaCreacion(LocalDateTime.now());

        return alertaRepository.save(alerta);
    }
}