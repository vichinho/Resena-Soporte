package com.resenasup.resenasup.Service;

import com.resenasup.resenasup.Model.Calificacion;
import com.resenasup.resenasup.Model.Resena;
import com.resenasup.resenasup.Repository.CalificacionRepository;
import com.resenasup.resenasup.Repository.ResenaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CalificacionService {
    private final CalificacionRepository calificacionRepository;
    private final ResenaRepository resenaRepository;

    public CalificacionService(CalificacionRepository calificacionRepository, 
                             ResenaRepository resenaRepository) {
        this.calificacionRepository = calificacionRepository;
        this.resenaRepository = resenaRepository;
    }
    
    @Transactional
    public Resena agregarCalificacion(Long resenaId, Integer puntuacion) {
        Resena resena = resenaRepository.findById(resenaId)
            .orElseThrow(() -> new RuntimeException("Reseña no encontrada"));
        
        if (resena.getCalificacion() != null) {
            calificacionRepository.delete(resena.getCalificacion());
        }
        
        Calificacion calificacion = new Calificacion(puntuacion, resena);
        calificacionRepository.save(calificacion);
        resena.setCalificacion(calificacion);
        
        return resenaRepository.save(resena);
    }
}