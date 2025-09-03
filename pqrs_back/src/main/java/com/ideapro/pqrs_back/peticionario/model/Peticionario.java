/**
 * 
 * model/peticionario.java    
 *  
 */

package com.ideapro.pqrs_back.peticionario.model;

import com.ideapro.pqrs_back.pqrs.model.Pqrs;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class Peticionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String tipoDocumento;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String numeroDocumento;

    @NotBlank
    private String nombres;

    @NotBlank
    private String apellidos;

    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    private String telefono;

    @OneToMany(mappedBy = "peticionario", cascade = CascadeType.ALL)
    private List<Pqrs> pqrs;



    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}