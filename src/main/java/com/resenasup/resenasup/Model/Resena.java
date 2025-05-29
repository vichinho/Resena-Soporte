package com.resenasup.resenasup.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resenas")
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

    @NotBlank(message = "El ID del producto es obligatorio")
    @Column(name = "id_producto", nullable = false)
    private String idProducto;

    protected Resena() {}

    public Resena(String comentario, LocalDateTime fecha, Integer puntuacion, String idProducto) {
        this.comentario = comentario;
        this.fecha = fecha;
        this.puntuacion = puntuacion;
        this.idProducto = idProducto;
    }

    @PrePersist
    protected void onCreate() {
        this.fecha = LocalDateTime.now();
    }

    // Getters and setters
    public Long getIdResena() {
        return idResena;
    }

    public void setIdResena(Long idResena) {
        this.idResena = idResena;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }
}