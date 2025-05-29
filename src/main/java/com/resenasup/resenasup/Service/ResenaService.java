package com.resenasup.resenasup.service;

import com.resenasup.resenasup.model.Resena;
import com.resenasup.resenasup.repository.ResenaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResenaService {
    private final ResenaRepository resenaRepository;

    public Resena crearResena(@Valid Resena resena) {
        return resenaRepository.save(resena);
    }

    public Resena obtenerResenaPorId(Long id) {
        return resenaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reseña no encontrada"));
    }

    public List<Resena> obtenerTodasResenas() {
        return resenaRepository.findAll();
    }

    public List<Resena> obtenerResenasPorProducto(String idProducto) {
        if (idProducto == null || idProducto.isBlank()) {
            throw new IllegalArgumentException("El ID del producto no puede estar vacío");
        }
        return resenaRepository.findByIdProducto(idProducto);
    }

    public void eliminarResena(Long id) {
        if (!resenaRepository.existsById(id)) {
            throw new IllegalArgumentException("Reseña no encontrada");
        }
        resenaRepository.deleteById(id);
    }
}