package com.ideapro.pqrs_back.peticionario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "peticionario")
@Data
public class Peticionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; 
    private String nombre;
    private String apellido;
    private String tipoDocumento;
    private String documento;
    private String telefono;
    private String email;
    private String direccion;
}
