package com.resenasup.resenasup.controller;

import com.resenasup.resenasup.model.Resena;
import com.resenasup.resenasup.model.Soporte;
import com.resenasup.resenasup.service.SoporteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/soportes")
@RequiredArgsConstructor
public class SoporteController {
    private final SoporteService soporteService;

    @PostMapping
    public ResponseEntity<Soporte> crearSoporte(@Valid @RequestBody Soporte soporte) {
        Soporte nuevoSoporte = soporteService.crearSoporte(soporte);
        return new ResponseEntity<>(nuevoSoporte, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Soporte> actualizarEstado(@PathVariable Long id, @RequestBody String nuevoEstado) {
        Soporte soporteActualizado = soporteService.actualizarEstado(id, nuevoEstado);
        return ResponseEntity.ok(soporteActualizado);
    }

    @PutMapping("/{id}/tipo")
    public ResponseEntity<Soporte> actualizarTipo(@PathVariable Long id, @RequestBody String nuevoTipo) {
        Soporte soporteActualizado = soporteService.actualizarTipo(id, nuevoTipo);
        return ResponseEntity.ok(soporteActualizado);
    }
    @GetMapping
    public ResponseEntity<List<Soporte>> getTodoSoporte() {
        List<Soporte> soporte = soporteService.obtenerTodosSoportes();
        return ResponseEntity.ok(soporte);
    }
}