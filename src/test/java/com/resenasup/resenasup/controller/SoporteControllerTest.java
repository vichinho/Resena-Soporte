package com.resenasup.resenasup.controller;

import com.resenasup.resenasup.model.Soporte;
import com.resenasup.resenasup.service.SoporteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.validation.ConstraintViolationException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SoporteControllerTest {

    @Mock
    private SoporteService soporteService;

    @InjectMocks
    private SoporteController soporteController;

    private Soporte soporte;

    @BeforeEach
    void setUp() {
        soporte = Soporte.builder()
                .idSoporte(1L)
                .mensaje("Este es un mensaje de soporte.")
                .idUsuario(123L)
                .build();
    }

    @Test
    void testCrearSoporte_ConDatosValidos_RetornaSoporteCreado() {
        // Arrange
        when(soporteService.crearSoporte(any(Soporte.class))).thenReturn(soporte);

        // Act
        ResponseEntity<Soporte> response = soporteController.crearSoporte(soporte);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "El estado debe ser 201 CREATED");
        assertEquals(soporte, response.getBody(), "El soporte devuelto debe coincidir con el creado");
        verify(soporteService, times(1)).crearSoporte(soporte);
    }

    @Test
    void testCrearSoporte_ConMensajeVacio_LanzaExcepcion() {
        // Arrange
        Soporte soporteInvalido = Soporte.builder()
                .mensaje("") // Mensaje vacío
                .idUsuario(123L)
                .build();
        when(soporteService.crearSoporte(any(Soporte.class)))
            .thenThrow(new ConstraintViolationException("El mensaje no puede estar vacío", null));

        // Act & Assert
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> {
            soporteController.crearSoporte(soporteInvalido);
        }, "Debe lanzar excepción para mensaje vacío");
        assertEquals("El mensaje no puede estar vacío", exception.getMessage());
        verify(soporteService, times(1)).crearSoporte(soporteInvalido);
    }

    @Test
    void testCrearSoporte_ConMensajeDemasiadoLargo_LanzaExcepcion() {
        // Arrange
        String mensajeLargo = "x".repeat(1001); // Mensaje de más de 1000 caracteres
        Soporte soporteInvalido = Soporte.builder()
                .mensaje(mensajeLargo)
                .idUsuario(123L)
                .build();
        when(soporteService.crearSoporte(any(Soporte.class)))
            .thenThrow(new ConstraintViolationException("El mensaje no puede exceder 1000 caracteres", null));

        // Act & Assert
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> {
            soporteController.crearSoporte(soporteInvalido);
        }, "Debe lanzar excepción para mensaje demasiado largo");
        assertEquals("El mensaje no puede exceder 1000 caracteres", exception.getMessage());
        verify(soporteService, times(1)).crearSoporte(soporteInvalido);
    }

    @Test
    void testActualizarEstado_ConIdExistente_RetornaSoporteActualizado() {
        // Arrange
        String nuevoEstado = "Resuelto";
        when(soporteService.actualizarEstado(1L, nuevoEstado)).thenReturn(soporte);

        // Act
        ResponseEntity<Soporte> response = soporteController.actualizarEstado(1L, nuevoEstado);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "El estado debe ser 200 OK");
        assertEquals(soporte, response.getBody(), "El soporte devuelto debe coincidir con el actualizado");
        verify(soporteService, times(1)).actualizarEstado(1L, nuevoEstado);
    }

    @Test
    void testActualizarTipo_ConIdExistente_RetornaSoporteActualizado() {
        // Arrange
        String nuevoTipo = "Consulta";
        when(soporteService.actualizarTipo(1L, nuevoTipo)).thenReturn(soporte);

        // Act
        ResponseEntity<Soporte> response = soporteController.actualizarTipo(1L, nuevoTipo);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "El estado debe ser 200 OK");
        assertEquals(soporte, response.getBody(), "El soporte devuelto debe coincidir con el actualizado");
        verify(soporteService, times(1)).actualizarTipo(1L, nuevoTipo);
    }

    @Test
    void testGetTodosSoportes_ConSoportesExistentes_RetornaLista() {
        // Arrange
        List<Soporte> soportes = List.of(soporte);
        when(soporteService.obtenerTodosSoportes()).thenReturn(soportes);

        // Act
        ResponseEntity<List<Soporte>> response = soporteController.getTodosSoportes();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "El estado debe ser 200 OK");
        assertEquals(soportes, response.getBody(), "La lista de soportes devuelta debe coincidir");
        verify(soporteService, times(1)).obtenerTodosSoportes();
    }

    @Test
    void testGetTodosSoportes_SinSoportes_RetornaListaVacia() {
        // Arrange
        when(soporteService.obtenerTodosSoportes()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<Soporte>> response = soporteController.getTodosSoportes();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "El estado debe ser 200 OK");
        assertTrue(response.getBody().isEmpty(), "La lista de soportes debe estar vacía");
        verify(soporteService, times(1)).obtenerTodosSoportes();
    }
}
