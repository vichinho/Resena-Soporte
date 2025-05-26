package com.resenasup.resenasup.Service;

import com.resenasup.resenasup.Model.Resena;
import com.resenasup.resenasup.Repository.ResenaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResenaService {
    private final ResenaRepository resenaRepository;

    public ResenaService(ResenaRepository resenaRepository) {
        this.resenaRepository = resenaRepository;
    }
    
    @Transactional
    public Resena crearResena(String comentario) {
        Resena resena = new Resena(comentario);
        return resenaRepository.save(resena);
    }
    
    public Resena obtenerResena(Long id) {
        return resenaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reseña no encontrada"));
    }
}