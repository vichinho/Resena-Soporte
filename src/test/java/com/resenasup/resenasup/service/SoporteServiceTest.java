package com.resenasup.resenasup.service;

import com.resenasup.resenasup.model.Soporte;
import com.resenasup.resenasup.repository.SoporteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SoporteServiceTest {

    @Mock
    private SoporteRepository soporteRepository;

    @InjectMocks
    private SoporteService soporteService;

    @Test
    void crearSoporte_DeberiaInicializarValoresPorDefectoYGuardar() {
        // Arrange
        Soporte soporteParam = Soporte.builder()
                .mensaje("Problema con mi cuenta")
                .idUsuario(1L)
                .build();
        
        Soporte soporteGuardado = Soporte.builder()
                .idSoporte(1L)
                .mensaje("Problema con mi cuenta")
                .idUsuario(1L)
                .estado("PENDIENTE")
                .tipo("POR_DEFINIR")
                .fecha(LocalDateTime.now())
                .build();
        
        when(soporteRepository.save(any(Soporte.class))).thenReturn(soporteGuardado);

        // Act
        Soporte resultado = soporteService.crearSoporte(soporteParam);

        // Assert
        assertNotNull(resultado);
        assertEquals("PENDIENTE", resultado.getEstado());
        assertEquals("POR_DEFINIR", resultado.getTipo());
        assertNotNull(resultado.getFecha());
        verify(soporteRepository, times(1)).save(any(Soporte.class));
    }

    @Test
    void actualizarEstado_DeberiaActualizarCorrectamente() {
        // Arrange
        Long id = 1L;
        Soporte soporteExistente = Soporte.builder()
                .idSoporte(id)
                .estado("PENDIENTE")
                .build();
        
        when(soporteRepository.findById(id)).thenReturn(Optional.of(soporteExistente));
        when(soporteRepository.save(any(Soporte.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Soporte resultado = soporteService.actualizarEstado(id, "RESUELTO");

        // Assert
        assertEquals("RESUELTO", resultado.getEstado());
        verify(soporteRepository, times(1)).findById(id);
        verify(soporteRepository, times(1)).save(soporteExistente);
    }

    @Test
    void actualizarEstado_ConIdInvalido_DeberiaLanzarExcepcion() {
        // Arrange
        Long id = 99L;
        when(soporteRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            soporteService.actualizarEstado(id, "RESUELTO");
        });
    }

    @Test
    void actualizarTipo_DeberiaActualizarCorrectamente() {
        // Arrange
        Long id = 1L;
        Soporte soporteExistente = Soporte.builder()
                .idSoporte(id)
                .tipo("POR_DEFINIR")
                .build();
        
        when(soporteRepository.findById(id)).thenReturn(Optional.of(soporteExistente));
        when(soporteRepository.save(any(Soporte.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Soporte resultado = soporteService.actualizarTipo(id, "TECNICO");

        // Assert
        assertEquals("TECNICO", resultado.getTipo());
        verify(soporteRepository, times(1)).findById(id);
        verify(soporteRepository, times(1)).save(soporteExistente);
    }

    @Test
    void obtenerSoportesPorUsuario_DeberiaRetornarLista() {
        // Arrange
        Long idUsuario = 1L;
        List<Soporte> soportes = Arrays.asList(
                Soporte.builder().idUsuario(idUsuario).build(),
                Soporte.builder().idUsuario(idUsuario).build()
        );
        
        when(soporteRepository.findByIdUsuario(idUsuario)).thenReturn(soportes);

        // Act
        List<Soporte> resultado = soporteService.obtenerSoportesPorUsuario(idUsuario);

        // Assert
        assertEquals(2, resultado.size());
        verify(soporteRepository, times(1)).findByIdUsuario(idUsuario);
    }

    @Test
    void obtenerTodosSoportes_DeberiaRetornarTodosLosRegistros() {
        // Arrange
        List<Soporte> soportes = Arrays.asList(
                Soporte.builder().build(),
                Soporte.builder().build()
        );
        
        when(soporteRepository.findAll()).thenReturn(soportes);

        // Act
        List<Soporte> resultado = soporteService.obtenerTodosSoportes();

        // Assert
        assertEquals(2, resultado.size());
        verify(soporteRepository, times(1)).findAll();
    }
}