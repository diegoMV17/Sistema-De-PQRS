package com.ideapro.pqrs_back.aplication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ideapro.pqrs_back.user.model.User;
import com.ideapro.pqrs_back.user.repository.UserRepository;
import com.ideapro.pqrs_back.user.service.UserService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    
    @InjectMocks
    private UserService userService;
    
    private User testUser;
    
    @BeforeEach
    void setUp() {
        // Inyectar el mock del passwordEncoder en el servicio
        ReflectionTestUtils.setField(userService, "passwordEncoder", passwordEncoder);
        
        testUser = new User();
        testUser.setId(1L);
        testUser.setNombre("Juan");
        testUser.setApellido("Pérez");
        testUser.setEmail("juan.perez@example.com");
        testUser.setCredencial("12345678");
        testUser.setContrasena("password123");
        testUser.setRol("ADMIN");
    }
    
    @Test
    void createUser_Success() {
        // Arrange
        String encodedPassword = "encodedPassword123";
        when(passwordEncoder.encode(testUser.getContrasena())).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        
        // Act
        User savedUser = userService.crearUser(testUser);
        
        // Assert
        assertNotNull(savedUser);
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
        assertEquals(testUser.getId(), savedUser.getId());
    }
    
    @Test
    void getAllUsers_Success() {
        // Arrange
        List<User> userList = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(userList);
        
        // Act
        List<User> foundUsers = userService.listarUser();
        
        // Assert
        assertNotNull(foundUsers);
        assertFalse(foundUsers.isEmpty());
        assertEquals(1, foundUsers.size());
        verify(userRepository).findAll();
    }
    
    @Test
    void obtenerUser_Success() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        
        // Act
        User foundUser = userService.obtenerUser(1L);
        
        // Assert
        assertNotNull(foundUser);
        assertEquals(testUser.getId(), foundUser.getId());
        assertEquals(testUser.getEmail(), foundUser.getEmail());
        verify(userRepository).findById(1L);
    }
    
    @Test
    void obtenerUser_NotFound() {
        // Arrange
        when(userRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Act
        User foundUser = userService.obtenerUser(999L);
        
        // Assert
        assertNull(foundUser);
        verify(userRepository).findById(999L);
    }
    
    @Test
    void eliminarUser_Success() {
        // Arrange
        doNothing().when(userRepository).deleteById(1L);
        
        // Act
        userService.eliminarUser(1L);
        
        // Assert
        verify(userRepository).deleteById(1L);
    }
    
    @Test
    void buscarEmail_Success() {
        // Arrange
        List<User> users = Arrays.asList(testUser);
        when(userRepository.findByEmail("juan.perez@example.com")).thenReturn(users);
        
        // Act
        List<User> foundUsers = userService.buscarEmail("juan.perez@example.com");
        
        // Assert
        assertNotNull(foundUsers);
        assertFalse(foundUsers.isEmpty());
        assertEquals(1, foundUsers.size());
        assertEquals(testUser.getEmail(), foundUsers.get(0).getEmail());
        verify(userRepository).findByEmail("juan.perez@example.com");
    }
    
    @Test
    void register_Success() {
        // Arrange
        String encodedPassword = "encodedPassword123";
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Collections.emptyList());
        when(userRepository.findByCredencial(testUser.getCredencial())).thenReturn(null);
        when(passwordEncoder.encode(testUser.getContrasena())).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        
        // Act
        User registeredUser = userService.register(testUser);
        
        // Assert
        assertNotNull(registeredUser);
        verify(userRepository).findByEmail(testUser.getEmail());
        verify(userRepository).findByCredencial(testUser.getCredencial());
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
    }
    
    @Test
    void register_EmailAlreadyExists() {
        // Arrange
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Arrays.asList(testUser));
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.register(testUser);
        });
        
        assertEquals("El email ya está registrado.", exception.getMessage());
        verify(userRepository).findByEmail(testUser.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }
    
    @Test
    void register_CredencialAlreadyExists() {
        // Arrange
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Collections.emptyList());
        when(userRepository.findByCredencial(testUser.getCredencial())).thenReturn(testUser);
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.register(testUser);
        });
        
        assertEquals("La credencial ya está registrada.", exception.getMessage());
        verify(userRepository).findByCredencial(testUser.getCredencial());
        verify(userRepository, never()).save(any(User.class));
    }
    
    @Test
    void login_Success() {
        // Arrange
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword123";
        testUser.setContrasena(encodedPassword);
        
        when(userRepository.findByCredencial(testUser.getCredencial())).thenReturn(testUser);
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
        
        // Act
        User loggedUser = userService.login(testUser.getCredencial(), rawPassword);
        
        // Assert
        assertNotNull(loggedUser);
        assertEquals(testUser.getId(), loggedUser.getId());
        assertEquals(testUser.getCredencial(), loggedUser.getCredencial());
        verify(userRepository).findByCredencial(testUser.getCredencial());
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
    }
    
    @Test
    void login_UserNotFound() {
        // Arrange
        when(userRepository.findByCredencial(anyString())).thenReturn(null);
        
        // Act
        User loggedUser = userService.login("wrongCredencial", "password123");
        
        // Assert
        assertNull(loggedUser);
        verify(userRepository).findByCredencial("wrongCredencial");
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }
    
    @Test
    void login_WrongPassword() {
        // Arrange
        String rawPassword = "wrongPassword";
        String encodedPassword = "encodedPassword123";
        testUser.setContrasena(encodedPassword);
        
        when(userRepository.findByCredencial(testUser.getCredencial())).thenReturn(testUser);
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);
        
        // Act
        User loggedUser = userService.login(testUser.getCredencial(), rawPassword);
        
        // Assert
        assertNull(loggedUser);
        verify(userRepository).findByCredencial(testUser.getCredencial());
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
    }
}