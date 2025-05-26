package com.resenasup.resenasup.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "resenas")
@Getter
@Setter
public class Resena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 1000)
    private String comentario;
    
    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();
    
    @OneToOne(mappedBy = "resena", cascade = CascadeType.ALL, orphanRemoval = true)
    private Calificacion calificacion;

    public Resena() {}

    public Resena(String comentario) {
        this.comentario = comentario;
    }
}