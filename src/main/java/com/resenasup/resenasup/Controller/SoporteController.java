package com.resenasup.resenasup.Controller;



import com.resenasup.resenasup.Model.Soporte;
import com.resenasup.resenasup.Service.SoporteService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/soporte")
public class SoporteController {
    private final SoporteService soporteService;

    public SoporteController(SoporteService soporteService) {
        this.soporteService = soporteService;
    }
    
    @PostMapping
    public ResponseEntity<Soporte> crearSolicitud(
            @RequestParam String tipo,
            @RequestParam String mensaje) {
        Soporte soporte = soporteService.crearSolicitudSoporte(tipo, mensaje);
        return ResponseEntity.ok(soporte);
    }
    
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Soporte> actualizarEstado(
            @PathVariable String id,
            @RequestParam Soporte.Estado nuevoEstado) {
        Soporte soporte = soporteService.actualizarEstado(id, nuevoEstado);
        return ResponseEntity.ok(soporte);
    }
}