package com.ideapro.pqrs_back.pqrs.dto;

import lombok.Data;

@Data
public class CrearPqrsDTO {
    private String tipo; // PETICION, QUEJA, RECLAMO, SUGERENCIA
    private String descripcion;

    // Datos del Peticionario
    private String tipoPeticionario; // PERSONA_NATURAL, PERSONA_JURIDICA
    private String nombre;
    private String apellido;
    private String tipoDocumento; // CC, CE, NIT, etc.
    private String documento;
    private String telefono;
    private String email;
    private String direccion;
}
