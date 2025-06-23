package com.resenasup.resenasup.controller;

import com.resenasup.resenasup.model.Resena;
import com.resenasup.resenasup.service.ResenaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//DOCUMENTACION SWAGGERUI
//http://localhost:8081/doc/swagger-ui/index.html#/

@RestController
@RequestMapping("/api/v1/resenas")
@RequiredArgsConstructor
public class ResenaController {
    private final ResenaService resenaService;

    @PostMapping
    public ResponseEntity<Resena> createResena(@Valid @RequestBody Resena resena) {
        Resena creada = resenaService.crearResena(resena);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resena> getResenaById(@PathVariable Long id) {
        Resena resena = resenaService.obtenerResenaPorId(id);
        return ResponseEntity.ok(resena);
    }

    @GetMapping
    public ResponseEntity<List<Resena>> getTodasResenas() {
        List<Resena> resenas = resenaService.obtenerTodasResenas();
        return ResponseEntity.ok(resenas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResena(@PathVariable Long id) {
        resenaService.eliminarResena(id);
        return ResponseEntity.noContent().build();
    }
}