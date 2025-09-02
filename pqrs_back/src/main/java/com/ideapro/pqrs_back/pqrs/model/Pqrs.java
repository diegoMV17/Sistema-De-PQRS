package com.ideapro.pqrs_back.pqrs.model;

import java.time.LocalDateTime;

import com.ideapro.pqrs_back.peticionario.model.Peticionario;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Pqrs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String tipo;

    @NotBlank
    @Column(length = 2000)
    private String descripcion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "peticionario_id", referencedColumnName = "id")
    @Valid
    private Peticionario peticionario; // <-- Variable faltante

    private LocalDateTime fechaRegistro;
    private String numeroRadicado;
    private String estado;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Peticionario getPeticionario() { return peticionario; }
    public void setPeticionario(Peticionario peticionario) { this.peticionario = peticionario; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getNumeroRadicado() { return numeroRadicado; }
    public void setNumeroRadicado(String numeroRadicado) { this.numeroRadicado = numeroRadicado; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}