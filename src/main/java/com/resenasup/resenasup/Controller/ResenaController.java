package com.resenasup.resenasup.Controller;

import com.resenasup.resenasup.Model.Resena;
import com.resenasup.resenasup.Service.ResenaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {
    private final ResenaService resenaService;

    public ResenaController(ResenaService resenaService) {
        this.resenaService = resenaService;
    }
    
    @PostMapping
    public ResponseEntity<Resena> crearResena(@RequestParam String comentario) {
        Resena nuevaResena = resenaService.crearResena(comentario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaResena);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Resena> obtenerResena(@PathVariable Long id) {
        return ResponseEntity.ok(resenaService.obtenerResena(id));
    }
}