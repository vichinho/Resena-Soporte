package com.resenasup.resenasup.controller;

import com.resenasup.resenasup.model.Resena;
import com.resenasup.resenasup.service.ResenaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.validation.ConstraintViolationException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResenaControllerTest {

    @Mock
    private ResenaService resenaService;

    @InjectMocks
    private ResenaController resenaController;

    private Resena resena;

    @BeforeEach
    void setUp() {
        resena = new Resena("Excelente producto", LocalDateTime.now(), 5, "PROD1");
        resena.setIdResena(1L); // Establecemos el ID manualmente para simular una reseña persistida
    }

    @Test
    void testCreateResena_ConDatosValidos_RetornaResenaCreada() {
        // Arrange
        when(resenaService.crearResena(any(Resena.class))).thenReturn(resena);

        // Act
        ResponseEntity<Resena> response = resenaController.createResena(resena);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "El estado debe ser 201 CREATED");
        assertEquals(resena, response.getBody(), "La reseña devuelta debe coincidir con la creada");
        verify(resenaService, times(1)).crearResena(resena);
    }

    @Test
    void testCreateResena_ConComentarioVacio_LanzaExcepcion() {
        // Arrange
        Resena resenaInvalida = new Resena("", LocalDateTime.now(), 5, "PROD1");
        when(resenaService.crearResena(any(Resena.class)))
            .thenThrow(new ConstraintViolationException("El comentario no puede estar vacío", null));

        // Act & Assert
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> {
            resenaController.createResena(resenaInvalida);
        }, "Debe lanzar excepción para comentario vacío");
        assertEquals("El comentario no puede estar vacío", exception.getMessage());
        verify(resenaService, times(1)).crearResena(resenaInvalida);
    }

    @Test
    void testGetResenaById_ConIdExistente_RetornaResena() {
        // Arrange
        when(resenaService.obtenerResenaPorId(1L)).thenReturn(resena);

        // Act
        ResponseEntity<Resena> response = resenaController.getResenaById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "El estado debe ser 200 OK");
        assertEquals(resena, response.getBody(), "La reseña devuelta debe coincidir con la solicitada");
        verify(resenaService, times(1)).obtenerResenaPorId(1L);
    }

    @Test
    void testGetResenaById_ConIdInexistente_LanzaExcepcion() {
        // Arrange
        when(resenaService.obtenerResenaPorId(1L)).thenThrow(new IllegalArgumentException("Reseña no encontrada"));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            resenaController.getResenaById(1L);
        }, "Debe lanzar excepción para ID inexistente");
        assertEquals("Reseña no encontrada", exception.getMessage());
        verify(resenaService, times(1)).obtenerResenaPorId(1L);
    }

    @Test
    void testGetTodasResenas_ConResenasExistentes_RetornaLista() {
        // Arrange
        List<Resena> resenas = List.of(resena);
        when(resenaService.obtenerTodasResenas()).thenReturn(resenas);

        // Act
        ResponseEntity<List<Resena>> response = resenaController.getTodasResenas();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "El estado debe ser 200 OK");
        assertEquals(resenas, response.getBody(), "La lista de reseñas devuelta debe coincidir");
        verify(resenaService, times(1)).obtenerTodasResenas();
    }

    @Test
    void testGetTodasResenas_SinResenas_RetornaListaVacia() {
        // Arrange
        when(resenaService.obtenerTodasResenas()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<Resena>> response = resenaController.getTodasResenas();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "El estado debe ser 200 OK");
        assertTrue(response.getBody().isEmpty(), "La lista de reseñas debe estar vacía");
        verify(resenaService, times(1)).obtenerTodasResenas();
    }

    @Test
    void testDeleteResena_ConIdExistente_EliminaCorrectamente() {
        // Arrange
        doNothing().when(resenaService).eliminarResena(1L);

        // Act
        ResponseEntity<Void> response = resenaController.deleteResena(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode(), "El estado debe ser 204 NO CONTENT");
        verify(resenaService, times(1)).eliminarResena(1L);
    }

    @Test
    void testDeleteResena_ConIdInexistente_LanzaExcepcion() {
        // Arrange
        doThrow(new IllegalArgumentException("Reseña no encontrada")).when(resenaService).eliminarResena(1L);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            resenaController.deleteResena(1L);
        }, "Debe lanzar excepción para ID inexistente");
        assertEquals("Reseña no encontrada", exception.getMessage());
        verify(resenaService, times(1)).eliminarResena(1L);
    }
}
