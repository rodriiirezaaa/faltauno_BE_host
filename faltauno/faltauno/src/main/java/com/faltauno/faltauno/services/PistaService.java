package com.faltauno.faltauno.services;

import com.faltauno.faltauno.models.Pista;
import com.faltauno.faltauno.repositories.PistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PistaService {

    @Autowired
    private PistaRepository pistaRepository;
    @Transactional
    public List<Pista> obtenerTodas() {
        return pistaRepository.findAll();
    }

    // Usamos el Enum Pista.EstadoPista en lugar de String
    @Transactional
    public List<Pista> obtenerPorEstado(Pista.EstadoPista estado) {
        return pistaRepository.findByEstado(estado);
    }
    @Transactional
    public Pista guardarPista(Pista pista) {
        // Asignamos el Enum PENDIENTE si no tiene estado
        if (pista.getEstado() == null) {
            pista.setEstado(Pista.EstadoPista.PENDIENTE);
        }
        return pistaRepository.save(pista);
    }

    // Cambiamos el id a Long y el nuevoEstado al Enum
    @Transactional
    public Pista cambiarEstado(Long id, Pista.EstadoPista nuevoEstado) {
        Optional<Pista> pistaOpt = pistaRepository.findById(id);
        if (pistaOpt.isPresent()) {
            Pista pista = pistaOpt.get();
            pista.setEstado(nuevoEstado);
            return pistaRepository.save(pista);
        }
        return null;
    }
}
