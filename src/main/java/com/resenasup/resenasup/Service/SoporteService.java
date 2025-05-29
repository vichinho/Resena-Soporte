package com.resenasup.resenasup.service;

import com.resenasup.resenasup.model.Soporte;
import com.resenasup.resenasup.repository.SoporteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SoporteService {
    private final SoporteRepository soporteRepository;

    public Soporte crearSoporte(@Valid Soporte soporte) {
        return soporteRepository.save(soporte);
    }

    public Soporte actualizarEstado(Long idSoporte, String nuevoEstado) {
        Soporte soporte = soporteRepository.findById(idSoporte)
                .orElseThrow(() -> new IllegalArgumentException("Soporte no encontrado"));
        soporte.actualizarEstado(nuevoEstado);
        return soporteRepository.save(soporte);
    }

    public Soporte actualizarTipo(Long idSoporte, String nuevoTipo) {
        Soporte soporte = soporteRepository.findById(idSoporte)
                .orElseThrow(() -> new IllegalArgumentException("Soporte no encontrado"));
        soporte.actualizarTipo(nuevoTipo);
        return soporteRepository.save(soporte);
    }

    public List<Soporte> obtenerSoportesPorUsuario(Long idUsuario) {
        if (idUsuario == null) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo");
        }
        return soporteRepository.findByIdUsuario(idUsuario);
    }

    public List<Soporte> obtenerTodosSoportes() {
        return soporteRepository.findAll();
    }
}