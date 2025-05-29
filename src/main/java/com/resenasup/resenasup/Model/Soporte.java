package com.resenasup.resenasup.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "soportes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Soporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_soporte", updatable = false, nullable = false)
    private Long idSoporte;

    private String tipo;

    @NotBlank(message = "El mensaje no puede estar vacío")
    @Size(max = 1000, message = "El mensaje no puede exceder 1000 caracteres")
    private String mensaje;

    private String estado;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @PrePersist
    protected void onCreate() {
        this.fecha = LocalDateTime.now();
        this.tipo = "POR_DEFINIR";
        this.estado = "PENDIENTE";
    }

    public void actualizarEstado(String nuevoEstado) {
        if (nuevoEstado != null && !nuevoEstado.isBlank()) {
            this.estado = nuevoEstado;
        } else {
            throw new IllegalArgumentException("El nuevo estado no puede estar vacío");
        }
    }

    public void actualizarTipo(String nuevoTipo) {
        if (nuevoTipo != null && !nuevoTipo.isBlank()) {
            this.tipo = nuevoTipo;
        } else {
            throw new IllegalArgumentException("El nuevo tipo no puede estar vacío");
        }
    }
}