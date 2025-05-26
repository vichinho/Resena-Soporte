package com.resenasup.resenasup.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resenas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resena", updatable = false, nullable = false)
    private Long idResena;

    @NotBlank(message = "El comentario no puede estar vacío")
    @Size(max = 500, message = "El comentario no puede exceder 500 caracteres")
    private String comentario;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @NotNull(message = "La puntuación es obligatoria")
    @Min(value = 1, message = "La puntuación debe ser al menos 1")
    @Max(value = 5, message = "La puntuación no puede exceder 5")
    private Integer puntuacion;

    @PrePersist
    protected void onCreate() {
        this.fecha = LocalDateTime.now();
    }
}