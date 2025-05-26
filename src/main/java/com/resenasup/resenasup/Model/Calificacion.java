package com.resenasup.resenasup.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "calificaciones")
@Getter
@Setter
public class Calificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Integer puntuacion;
    
    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resena_id", nullable = false)
    private Resena resena;

    public Calificacion() {}

    public Calificacion(Integer puntuacion, Resena resena) {
        this.puntuacion = puntuacion;
        this.resena = resena;
    }
}