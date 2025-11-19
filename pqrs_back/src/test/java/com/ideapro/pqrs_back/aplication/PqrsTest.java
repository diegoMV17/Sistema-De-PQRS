package com.ideapro.pqrs_back.aplication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ideapro.pqrs_back.peticionario.model.Peticionario;
import com.ideapro.pqrs_back.peticionario.repository.PeticionarioRepository;
import com.ideapro.pqrs_back.pqrs.model.Pqrs;
import com.ideapro.pqrs_back.pqrs.model.PqrsEstado;
import com.ideapro.pqrs_back.pqrs.model.TipoPqrs;
import com.ideapro.pqrs_back.pqrs.repository.PqrsEstadoRepository;
import com.ideapro.pqrs_back.pqrs.repository.PqrsRepository;
import com.ideapro.pqrs_back.pqrs.service.PqrsService;
import com.ideapro.pqrs_back.service.EmailService;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class PqrsTest {
    
    @Mock
    private PqrsRepository pqrsRepository;
    
    @Mock
    private PqrsEstadoRepository estadoRepository;
    
    @Mock
    private PeticionarioRepository peticionarioRepository;
    
    @Mock
    private EmailService emailService;
    
    @InjectMocks
    private PqrsService pqrsService;
    
    private Pqrs testPqrs;
    private Peticionario testPeticionario;
    private PqrsEstado estadoPendiente;
    private PqrsEstado estadoEnProceso;
    private PqrsEstado estadoResuelta;
    
    @BeforeEach
    void setUp() {
        // Configurar estados
        estadoPendiente = new PqrsEstado();
        estadoPendiente.setId(1L);
        estadoPendiente.setNombre("Pendiente");
        
        estadoEnProceso = new PqrsEstado();
        estadoEnProceso.setId(2L);
        estadoEnProceso.setNombre("En Proceso");
        
        estadoResuelta = new PqrsEstado();
        estadoResuelta.setId(3L);
        estadoResuelta.setNombre("Resuelta");
        
        // Configurar peticionario
        testPeticionario = new Peticionario();
        testPeticionario.setId(1L);
        testPeticionario.setNombres("Juan");
        testPeticionario.setApellidos("Pérez");
        testPeticionario.setTipoDocumento("CC");
        testPeticionario.setNumeroDocumento("12345678");
        testPeticionario.setEmail("juan.perez@example.com");
        testPeticionario.setTelefono("3001234567");
        
        // Configurar PQRS
        testPqrs = new Pqrs();
        testPqrs.setId(1L);
        testPqrs.setTipo(TipoPqrs.PETICION);
        testPqrs.setDescripcion("Esta es una petición de prueba");
        testPqrs.setPeticionario(testPeticionario);
        testPqrs.setFechaRegistro(LocalDateTime.now());
        testPqrs.setNumeroRadicado("PQRS-NUM-0000001-2025");
        testPqrs.setEstado(estadoPendiente);
    }
    
    @Test
    void crearPqrs_Success() {
        // Arrange
        when(estadoRepository.findByNombre("Pendiente")).thenReturn(Optional.of(estadoPendiente));
        when(peticionarioRepository.findByNumeroDocumentoOrEmail(anyString(), anyString()))
            .thenReturn(Optional.of(testPeticionario));
        when(pqrsRepository.save(any(Pqrs.class))).thenReturn(testPqrs);
        doNothing().when(emailService).enviarConfirmacionPQRS(anyString(), anyString());
        
        // Act
        Pqrs result = pqrsService.crearPqrs(testPqrs);
        
        // Assert
        assertNotNull(result);
        assertNotNull(result.getFechaRegistro());
        assertNotNull(result.getNumeroRadicado());
        assertEquals(estadoPendiente, result.getEstado());
        verify(pqrsRepository).save(any(Pqrs.class));
    }
    
    @Test
    void crearPqrs_NuevoPeticionario() {
        // Arrange
        when(estadoRepository.findByNombre("Pendiente")).thenReturn(Optional.of(estadoPendiente));
        when(peticionarioRepository.findByNumeroDocumentoOrEmail(anyString(), anyString()))
            .thenReturn(Optional.empty());
        when(peticionarioRepository.save(any(Peticionario.class))).thenReturn(testPeticionario);
        when(pqrsRepository.save(any(Pqrs.class))).thenReturn(testPqrs);
        doNothing().when(emailService).enviarConfirmacionPQRS(anyString(), anyString());
        
        // Act
        Pqrs result = pqrsService.crearPqrs(testPqrs);
        
        // Assert
        assertNotNull(result);
        verify(peticionarioRepository).save(any(Peticionario.class));
        verify(pqrsRepository).save(any(Pqrs.class));
    }
    
    @Test
    void listarPqrs_Success() {
        // Arrange
        List<Pqrs> listaPqrs = Arrays.asList(testPqrs);
        when(pqrsRepository.findAll()).thenReturn(listaPqrs);
        
        // Act
        List<Pqrs> result = pqrsService.listarPqrs();
        
        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(pqrsRepository).findAll();
    }
    
    @Test
    void listarPqrs_ListaVacia() {
        // Arrange
        when(pqrsRepository.findAll()).thenReturn(Collections.emptyList());
        
        // Act
        List<Pqrs> result = pqrsService.listarPqrs();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    void obtenerPqrs_Success() {
        // Arrange
        when(pqrsRepository.findById(1L)).thenReturn(Optional.of(testPqrs));
        
        // Act
        Pqrs result = pqrsService.obtenerPqrs(1L);
        
        // Assert
        assertNotNull(result);
        assertEquals(testPqrs.getId(), result.getId());
        assertEquals(testPqrs.getNumeroRadicado(), result.getNumeroRadicado());
        verify(pqrsRepository).findById(1L);
    }
    
    @Test
    void obtenerPqrs_NotFound() {
        // Arrange
        when(pqrsRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            pqrsService.obtenerPqrs(999L);
        });
        
        assertEquals("PQRS no encontrada con id: 999", exception.getMessage());
    }
    
    @Test
    void eliminarPqrs_Success() {
        // Arrange
        when(pqrsRepository.existsById(1L)).thenReturn(true);
        doNothing().when(pqrsRepository).deleteById(1L);
        
        // Act
        pqrsService.eliminarPqrs(1L);
        
        // Assert
        verify(pqrsRepository).existsById(1L);
        verify(pqrsRepository).deleteById(1L);
    }
    
    @Test
    void eliminarPqrs_NotFound() {
        // Arrange
        when(pqrsRepository.existsById(999L)).thenReturn(false);
        
        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            pqrsService.eliminarPqrs(999L);
        });
        
        assertEquals("No se puede eliminar. PQRS no encontrada con id: 999", exception.getMessage());
    }
    
    @Test
    void buscarPorNumeroRadicado_Success() {
        // Arrange
        List<Pqrs> listaPqrs = Arrays.asList(testPqrs);
        when(pqrsRepository.findByNumeroRadicado("PQRS-NUM-0000001-2025"))
            .thenReturn(listaPqrs);
        
        // Act
        List<Pqrs> result = pqrsService.buscarPorNumeroRadicado("PQRS-NUM-0000001-2025");
        
        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(pqrsRepository).findByNumeroRadicado("PQRS-NUM-0000001-2025");
    }
    
    @Test
    void buscarPorNumeroDocumento_Success() {
        // Arrange
        List<Pqrs> listaPqrs = Arrays.asList(testPqrs);
        when(pqrsRepository.findByPeticionario_NumeroDocumento("12345678"))
            .thenReturn(listaPqrs);
        
        // Act
        List<Pqrs> result = pqrsService.buscarPorNumeroDocumento("12345678");
        
        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
    
    @Test
    void contarPqrs_Success() {
        // Arrange
        when(pqrsRepository.count()).thenReturn(10L);
        
        // Act
        long result = pqrsService.contarPqrs();
        
        // Assert
        assertEquals(10L, result);
        verify(pqrsRepository).count();
    }
    
    @Test
    void obtenerEstadoPqrs_Success() {
        // Arrange
        when(pqrsRepository.findEstadoById(1L)).thenReturn(Optional.of(estadoPendiente));
        
        // Act
        PqrsEstado result = pqrsService.obtenerEstadoPqrs(1L);
        
        // Assert
        assertNotNull(result);
        assertEquals("Pendiente", result.getNombre());
    }
    
    @Test
    void listarPqrsPorEstado_Success() {
        // Arrange
        List<Pqrs> listaPqrs = Arrays.asList(testPqrs);
        when(pqrsRepository.findByEstadoNombre("Pendiente")).thenReturn(listaPqrs);
        
        // Act
        List<Pqrs> result = pqrsService.listarPqrsPorEstado("Pendiente");
        
        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
    
    @Test
    void obtenerEstadoPorRadicado_Success() {
        // Arrange
        when(pqrsRepository.findEstadoByNumeroRadicado("PQRS-NUM-0000001-2025"))
            .thenReturn(Optional.of(estadoPendiente));
        
        // Act
        PqrsEstado result = pqrsService.obtenerEstadoPorRadicado("PQRS-NUM-0000001-2025");
        
        // Assert
        assertNotNull(result);
        assertEquals("Pendiente", result.getNombre());
    }
    
    @Test
    void obtenerHistorialPqrs_Success() {
        // Arrange
        List<Map<String, Object>> estadisticas = Arrays.asList(
            Map.of("estado", "Pendiente", "cantidad", 1L, "estadoId", 1L),
            Map.of("estado", "En Proceso", "cantidad", 1L, "estadoId", 2L)
        );
        
        when(pqrsRepository.countPqrsByEstado()).thenReturn(estadisticas);
        when(pqrsRepository.findByEstadoNombreOrderByFechaRegistroDesc("Pendiente"))
            .thenReturn(Arrays.asList(testPqrs));
        when(pqrsRepository.findByEstadoNombreOrderByFechaRegistroDesc("En Proceso"))
            .thenReturn(Collections.emptyList());
        when(pqrsRepository.findByEstadoNombreOrderByFechaRegistroDesc("Resuelta"))
            .thenReturn(Collections.emptyList());
        when(pqrsRepository.findByEstadoNombreOrderByFechaRegistroDesc("Cancelada"))
            .thenReturn(Collections.emptyList());
        
        // Act
        Map<String, Object> result = pqrsService.obtenerHistorialPqrs();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.containsKey("estadisticas"));
        assertTrue(result.containsKey("pendientes"));
        assertTrue(result.containsKey("enProceso"));
        assertTrue(result.containsKey("resueltas"));
        assertTrue(result.containsKey("canceladas"));
    }
}