package com.resenasup.resenasup.service;

import com.resenasup.resenasup.model.Resena;
import com.resenasup.resenasup.repository.ResenaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.validation.ConstraintViolationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResenaServiceTest {

    @Mock
    private ResenaRepository resenaRepository;

    @InjectMocks
    private ResenaService resenaService;

    private Resena resena;

    @BeforeEach
    void setUp() {
        resena = new Resena("Excelente producto", LocalDateTime.now(), 5, "PROD1");
        resena.setIdResena(1L); // Establecemos el ID manualmente para simular una reseña persistida
    }

    @Test
    void testCrearResena_ConDatosValidos_RetornaResena() {
        // Arrange
        when(resenaRepository.save(any(Resena.class))).thenReturn(resena);

        // Act
        Resena resultado = resenaService.crearResena(resena);

        // Assert
        assertNotNull(resultado, "La reseña no debe ser nula");
        assertEquals(1L, resultado.getIdResena(), "El ID debe ser 1");
        assertEquals("PROD1", resultado.getIdProducto(), "El ID del producto debe coincidir");
        assertEquals("Excelente producto", resultado.getComentario(), "El comentario debe coincidir");
        assertEquals(5, resultado.getPuntuacion(), "La puntuación debe ser 5");
        assertNotNull(resultado.getFecha(), "La fecha no debe ser nula");
        verify(resenaRepository, times(1)).save(resena);
    }

    @Test
    void testCrearResena_ConIdProductoNuloOLargo_LanzaExcepcion() {
        // Arrange
        Resena resenaInvalida = new Resena("Excelente producto", LocalDateTime.now(), 5, null);
        when(resenaRepository.save(any(Resena.class)))
            .thenThrow(new ConstraintViolationException("El ID del producto es obligatorio", null));

        // Act & Assert
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> {
            resenaService.crearResena(resenaInvalida);
        }, "Debe lanzar excepción para ID de producto nulo");
        assertEquals("El ID del producto es obligatorio", exception.getMessage());
        verify(resenaRepository, times(1)).save(resenaInvalida);
    }

    @Test
    void testCrearResena_ConPuntuacionFueraDeRango_LanzaExcepcion() {
        // Arrange: Puntuación menor a 1
        Resena resenaInvalidaBaja = new Resena("Excelente producto", LocalDateTime.now(), 0, "PROD1");
        // Arrange: Puntuación mayor a 5
        Resena resenaInvalidaAlta = new Resena("Excelente producto", LocalDateTime.now(), 6, "PROD1");

        when(resenaRepository.save(any(Resena.class)))
            .thenThrow(new ConstraintViolationException("La puntuación debe ser al menos 1", null))
            .thenThrow(new ConstraintViolationException("La puntuación no puede exceder 5", null));

        // Act & Assert
        ConstraintViolationException exceptionBaja = assertThrows(ConstraintViolationException.class, () -> {
            resenaService.crearResena(resenaInvalidaBaja);
        }, "Debe lanzar excepción para puntuación menor que 1");
        assertEquals("La puntuación debe ser al menos 1", exceptionBaja.getMessage());

        ConstraintViolationException exceptionAlta = assertThrows(ConstraintViolationException.class, () -> {
            resenaService.crearResena(resenaInvalidaAlta);
        }, "Debe lanzar excepción para puntuación mayor que 5");
        assertEquals("La puntuación no puede exceder 5", exceptionAlta.getMessage());

        verify(resenaRepository, times(2)).save(any(Resena.class));
    }

    @Test
    void testCrearResena_ConComentarioInvalido_LanzaExcepcion() {
        // Arrange: Comentario vacío
        Resena resenaComentarioVacio = new Resena("", LocalDateTime.now(), 5, "PROD1");
        // Arrange: Comentario largo (más de 500 caracteres)
        String comentarioLargo = "x".repeat(501);
        Resena resenaComentarioLargo = new Resena(comentarioLargo, LocalDateTime.now(), 5, "PROD1");

        when(resenaRepository.save(any(Resena.class)))
            .thenThrow(new ConstraintViolationException("El comentario no puede estar vacío", null))
            .thenThrow(new ConstraintViolationException("El comentario no puede exceder 500 caracteres", null));

        // Act & Assert
        ConstraintViolationException exceptionVacio = assertThrows(ConstraintViolationException.class, () -> {
            resenaService.crearResena(resenaComentarioVacio);
        }, "Debe lanzar excepción para comentario vacío");
        assertEquals("El comentario no puede estar vacío", exceptionVacio.getMessage());

        ConstraintViolationException exceptionLargo = assertThrows(ConstraintViolationException.class, () -> {
            resenaService.crearResena(resenaComentarioLargo);
        }, "Debe lanzar excepción para comentario demasiado largo");
        assertEquals("El comentario no puede exceder 500 caracteres", exceptionLargo.getMessage());

        verify(resenaRepository, times(2)).save(any(Resena.class));
    }

    @Test
    void testObtenerResenaPorId_ConIdExistente_RetornaResena() {
        // Arrange
        when(resenaRepository.findById(1L)).thenReturn(Optional.of(resena));

        // Act
        Resena resultado = resenaService.obtenerResenaPorId(1L);

        // Assert
        assertNotNull(resultado, "La reseña no debe ser nula");
        assertEquals(1L, resultado.getIdResena(), "El ID debe ser 1");
        assertEquals("PROD1", resultado.getIdProducto(), "El ID del producto debe coincidir");
        assertEquals("Excelente producto", resultado.getComentario(), "El comentario debe coincidir");
        verify(resenaRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerResenaPorId_ConIdInexistente_LanzaExcepcion() {
        // Arrange
        when(resenaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            resenaService.obtenerResenaPorId(1L);
        }, "Debe lanzar excepción para ID inexistente");
        assertEquals("Reseña no encontrada", exception.getMessage(), "El mensaje de la excepción debe coincidir");
        verify(resenaRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerResenasPorProducto_ConIdProductoValido_RetornaLista() {
        // Arrange
        List<Resena> resenas = List.of(resena);
        when(resenaRepository.findByIdProducto("PROD1")).thenReturn(resenas);

        // Act
        List<Resena> resultado = resenaService.obtenerResenasPorProducto("PROD1");

        // Assert
        assertNotNull(resultado, "La lista no debe ser nula");
        assertEquals(1, resultado.size(), "La lista debe contener una reseña");
        assertEquals("PROD1", resultado.get(0).getIdProducto(), "El ID del producto debe coincidir");
        verify(resenaRepository, times(1)).findByIdProducto("PROD1");
    }

    @Test
    void testObtenerResenasPorProducto_ConIdProductoInvalido_LanzaExcepcion() {
        // Act & Assert
        IllegalArgumentException exceptionVacio = assertThrows(IllegalArgumentException.class, () -> {
            resenaService.obtenerResenasPorProducto("");
        }, "Debe lanzar excepción para ID de producto vacío");
        assertEquals("El ID del producto no puede estar vacío", exceptionVacio.getMessage(), "El mensaje de la excepción debe coincidir");

        IllegalArgumentException exceptionNulo = assertThrows(IllegalArgumentException.class, () -> {
            resenaService.obtenerResenasPorProducto(null);
        }, "Debe lanzar excepción para ID de producto nulo");
        assertEquals("El ID del producto no puede estar vacío", exceptionNulo.getMessage(), "El mensaje de la excepción debe coincidir");

        verify(resenaRepository, never()).findByIdProducto(anyString());
    }

    @Test
    void testEliminarResena_ConIdExistente_EliminaCorrectamente() {
        // Arrange
        when(resenaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(resenaRepository).deleteById(1L);

        // Act
        resenaService.eliminarResena(1L);

        // Assert
        verify(resenaRepository, times(1)).existsById(1L);
        verify(resenaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testEliminarResena_ConIdInexistente_LanzaExcepcion() {
        // Arrange
        when(resenaRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            resenaService.eliminarResena(1L);
        }, "Debe lanzar excepción para ID inexistente");
        assertEquals("Reseña no encontrada", exception.getMessage(), "El mensaje de la excepción debe coincidir");
        verify(resenaRepository, times(1)).existsById(1L);
        verify(resenaRepository, never()).deleteById(anyLong());
    }
}
