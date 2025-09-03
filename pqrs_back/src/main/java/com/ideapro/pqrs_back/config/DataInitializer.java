package com.ideapro.pqrs_back.config;



import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ideapro.pqrs_back.pqrs.model.PqrsEstado;

import com.ideapro.pqrs_back.pqrs.repository.PqrsEstadoRepository;

import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(PqrsEstadoRepository estadoRepository) {
        return args -> {
            // Inicializar estados de PQRS
            Arrays.asList("Pendiente", "En Proceso", "Resuelto", "Cancelado").forEach(nombre -> {
                estadoRepository.findByNombre(nombre).orElseGet(() -> {
                    PqrsEstado estado = new PqrsEstado();
                    estado.setNombre(nombre);
                    return estadoRepository.save(estado);
                });
            });

            // Si tu TipoPqrs es Enum, no hace falta inicializar en BD, pero si lo quieres en tabla:
            // Arrays.asList("PETICION", "QUEJA", "RECLAMO", "SUGERENCIA")...
        };
    }
}