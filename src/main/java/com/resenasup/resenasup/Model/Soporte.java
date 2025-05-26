package com.resenasup.resenasup.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Soporte {
    public enum Estado {
        PENDIENTE, EN_PROCESO, RESUELTO, CERRADO
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idSoporte;
    
    @Column(nullable = false)
    private String tipo;
    
    @Column(nullable = false, length = 2000)
    private String mensaje;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado = Estado.PENDIENTE;
    
    @Column(nullable = false)
    private LocalDateTime fecha;

    public Soporte() {
        this.fecha = LocalDateTime.now();
    }
    
    public Soporte(String tipo, String mensaje) {
        this();
        this.tipo = tipo;
        this.mensaje = mensaje;
    }
    
    // Getters y Setters
    public String getIdSoporte() { return idSoporte; }
    public String getTipo() { return tipo; }
    public String getMensaje() { return mensaje; }
    public Estado getEstado() { return estado; }
    public LocalDateTime getFecha() { return fecha; }
    
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public void setEstado(Estado estado) { this.estado = estado; }
}