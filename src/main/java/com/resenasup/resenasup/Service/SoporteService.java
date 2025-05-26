package com.resenasup.resenasup.Service;

import com.resenasup.resenasup.Model.Soporte;
import com.resenasup.resenasup.Repository.SoporteRepository;
import org.springframework.stereotype.Service;

@Service
public class SoporteService {
    private final SoporteRepository soporteRepository;

    public SoporteService(SoporteRepository soporteRepository) {
        this.soporteRepository = soporteRepository;
    }
    
    public Soporte crearSolicitudSoporte(String tipo, String mensaje) {
        Soporte soporte = new Soporte(tipo, mensaje);
        return soporteRepository.save(soporte);
    }
    
    public Soporte actualizarEstado(String idSoporte, Soporte.Estado nuevoEstado) {
        Soporte soporte = soporteRepository.findById(idSoporte)
            .orElseThrow(() -> new RuntimeException("Soporte no encontrado"));
        
        soporte.setEstado(nuevoEstado);
        return soporteRepository.save(soporte);
    }
}