package com.ideapro.pqrs_back.config;

import com.ideapro.pqrs_back.user.model.User;
import com.ideapro.pqrs_back.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner initSuperAdmin(UserRepository userRepository) {
        return args -> {
            String email = "superadmin@correo.com";

            // Si no existe, lo crea
            if (userRepository.findByEmail(email).isEmpty()) {
                User superAdmin = new User();
                superAdmin.setNombre("Super");
                superAdmin.setApellido("Admin");
                superAdmin.setCredencial("0000");
                superAdmin.setEmail(email);
                superAdmin.setContrasena(new BCryptPasswordEncoder().encode("123456")); // contraseña encriptada
                superAdmin.setRol("ADMIN");

                userRepository.save(superAdmin);
                System.out.println("✅ SuperAdmin creado -> email: " + email + " / contraseña: 123456");
            } else {
                System.out.println("ℹ️ SuperAdmin ya existe, no se creó otro.");
            }
        };
    }
}
