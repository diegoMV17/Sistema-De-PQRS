package com.ideapro.pqrs_back.aplication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.ideapro.pqrs_back.peticionario.model.Peticionario;
import com.ideapro.pqrs_back.peticionario.repository.PeticionarioRepository;
import com.ideapro.pqrs_back.peticionario.service.PeticionarioService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class PeticionarioTest {
    

    @Mock
    private PeticionarioRepository peticionarioRepository;
    
    @Autowired
    private PeticionarioService peticionarioService;
    
    private Peticionario testPeticionario;
    private Peticionario testPeticionario2;
    
    @BeforeEach
    void setUp() {
        // Peticionario 1
        testPeticionario = new Peticionario();
        testPeticionario.setId(1L);
        testPeticionario.setNombres("Juan");
        testPeticionario.setApellidos("Pérez García");
        testPeticionario.setTipoDocumento("CC");
        testPeticionario.setNumeroDocumento("12345678");
        testPeticionario.setEmail("juan.perez@example.com");
        testPeticionario.setTelefono("3001234567");
        
        // Peticionario 2
        testPeticionario2 = new Peticionario();
        testPeticionario2.setId(2L);
        testPeticionario2.setNombres("María");
        testPeticionario2.setApellidos("González López");
        testPeticionario2.setTipoDocumento("CC");
        testPeticionario2.setNumeroDocumento("87654321");
        testPeticionario2.setEmail("maria.gonzalez@example.com");
        testPeticionario2.setTelefono("3009876543");
    }
    
    @Test
    void crearPeticionario_Success() {
        // Arrange
        when(peticionarioRepository.save(any(Peticionario.class))).thenReturn(testPeticionario);
        
        // Act
        Peticionario result = peticionarioService.crearPeticionario(testPeticionario);
        
        // Assert
        assertNotNull(result);
        assertEquals(testPeticionario.getId(), result.getId());
        assertEquals(testPeticionario.getNombres(), result.getNombres());
        assertEquals(testPeticionario.getNumeroDocumento(), result.getNumeroDocumento());
        verify(peticionarioRepository).save(any(Peticionario.class));
    }
    
    @Test
    void listarPeticionario_Success() {
        // Arrange
        List<Peticionario> listaPeticionarios = Arrays.asList(testPeticionario, testPeticionario2);
        when(peticionarioRepository.findAll()).thenReturn(listaPeticionarios);
        
        // Act
        List<Peticionario> result = peticionarioService.listarPeticionario();
        
        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        verify(peticionarioRepository).findAll();
    }
    
    @Test
    void listarPeticionario_ListaVacia() {
        // Arrange
        when(peticionarioRepository.findAll()).thenReturn(Collections.emptyList());
        
        // Act
        List<Peticionario> result = peticionarioService.listarPeticionario();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(peticionarioRepository).findAll();
    }
    
    @Test
    void obtenerPeticionario_Success() {
        // Arrange
        when(peticionarioRepository.findById(1L)).thenReturn(Optional.of(testPeticionario));
        
        // Act
        Peticionario result = peticionarioService.obtenerPeticionario(1L);
        
        // Assert
        assertNotNull(result);
        assertEquals(testPeticionario.getId(), result.getId());
        assertEquals(testPeticionario.getNombres(), result.getNombres());
        assertEquals(testPeticionario.getEmail(), result.getEmail());
        verify(peticionarioRepository).findById(1L);
    }
    
    @Test
    void obtenerPeticionario_NotFound() {
        // Arrange
        when(peticionarioRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Act
        Peticionario result = peticionarioService.obtenerPeticionario(999L);
        
        // Assert
        assertNull(result);
        verify(peticionarioRepository).findById(999L);
    }
    
    @Test
    void eliminarPeticionario_Success() {
        // Arrange
        doNothing().when(peticionarioRepository).deleteById(1L);
        
        // Act
        peticionarioService.eliminarPeticionario(1L);
        
        // Assert
        verify(peticionarioRepository).deleteById(1L);
    }
    
    @Test
    void actualizarPeticionario_Success() {
        // Arrange
        Peticionario peticionarioActualizado = new Peticionario();
        peticionarioActualizado.setNombres("Juan Carlos");
        peticionarioActualizado.setApellidos("Pérez García");
        peticionarioActualizado.setTipoDocumento("CC");
        peticionarioActualizado.setNumeroDocumento("12345678");
        peticionarioActualizado.setEmail("juancarlos.perez@example.com");
        peticionarioActualizado.setTelefono("3001234567");
        
        when(peticionarioRepository.existsById(1L)).thenReturn(true);
        when(peticionarioRepository.save(any(Peticionario.class))).thenReturn(peticionarioActualizado);
        
        // Act
        Peticionario result = peticionarioService.actualizarPeticionario(1L, peticionarioActualizado);
        
        // Assert
        assertNotNull(result);
        assertEquals("Juan Carlos", result.getNombres());
        verify(peticionarioRepository).existsById(1L);
        verify(peticionarioRepository).save(any(Peticionario.class));
    }
    
    @Test
    void actualizarPeticionario_NotFound() {
        // Arrange
        when(peticionarioRepository.existsById(999L)).thenReturn(false);
        
        // Act
        Peticionario result = peticionarioService.actualizarPeticionario(999L, testPeticionario);
        
        // Assert
        assertNull(result);
        verify(peticionarioRepository).existsById(999L);
        verify(peticionarioRepository, never()).save(any(Peticionario.class));
    }
    
    @Test
    void buscarPorDocumento_Success() {
        // Arrange
        when(peticionarioRepository.findByNumeroDocumento("12345678"))
            .thenReturn(Optional.of(testPeticionario));
        
        // Act
        Peticionario result = peticionarioService.buscarPorDocumento("12345678");
        
        // Assert
        assertNotNull(result);
        assertEquals("12345678", result.getNumeroDocumento());
        assertEquals("Juan", result.getNombres());
        verify(peticionarioRepository).findByNumeroDocumento("12345678");
    }
    
    @Test
    void buscarPorDocumento_NotFound() {
        // Arrange
        when(peticionarioRepository.findByNumeroDocumento("99999999"))
            .thenReturn(Optional.empty());
        
        // Act
        Peticionario result = peticionarioService.buscarPorDocumento("99999999");
        
        // Assert
        assertNull(result);
        verify(peticionarioRepository).findByNumeroDocumento("99999999");
    }
    
    @Test
    void buscarPorNombreOApellido_Success() {
        // Arrange
        List<Peticionario> listaPeticionarios = Arrays.asList(testPeticionario);
        when(peticionarioRepository.findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase("Juan", "Juan"))
            .thenReturn(listaPeticionarios);
        
        // Act
        List<Peticionario> result = peticionarioService.buscarPorNombreOApellido("Juan");
        
        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Juan", result.get(0).getNombres());
        verify(peticionarioRepository).findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase("Juan", "Juan");
    }
    
    @Test
    void buscarPorNombreOApellido_PorApellido() {
        // Arrange
        List<Peticionario> listaPeticionarios = Arrays.asList(testPeticionario);
        when(peticionarioRepository.findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase("Pérez", "Pérez"))
            .thenReturn(listaPeticionarios);
        
        // Act
        List<Peticionario> result = peticionarioService.buscarPorNombreOApellido("Pérez");
        
        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertTrue(result.get(0).getApellidos().contains("Pérez"));
    }
    
    @Test
    void buscarPorNombreOApellido_NotFound() {
        // Arrange
        when(peticionarioRepository.findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase("NoExiste", "NoExiste"))
            .thenReturn(Collections.emptyList());
        
        // Act
        List<Peticionario> result = peticionarioService.buscarPorNombreOApellido("NoExiste");
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    void buscarPorNombreOApellido_CaseInsensitive() {
        // Arrange
        List<Peticionario> listaPeticionarios = Arrays.asList(testPeticionario);
        when(peticionarioRepository.findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase("juan", "juan"))
            .thenReturn(listaPeticionarios);
        
        // Act
        List<Peticionario> result = peticionarioService.buscarPorNombreOApellido("juan");
        
        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Juan", result.get(0).getNombres());
    }
}