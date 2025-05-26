package com.resenasup.resenasup.Controller;

import com.resenasup.resenasup.Model.Resena;
import com.resenasup.resenasup.Service.CalificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resenas/{resenaId}/calificacion")
public class CalificacionController {
    private final CalificacionService calificacionService;

    public CalificacionController(CalificacionService calificacionService) {
        this.calificacionService = calificacionService;
    }
    
    @PostMapping
    public ResponseEntity<Resena> agregarCalificacion(
            @PathVariable Long resenaId,
            @RequestParam Integer puntuacion) {
        
        Resena resena = calificacionService.agregarCalificacion(resenaId, puntuacion);
        return ResponseEntity.ok(resena);
    }
}