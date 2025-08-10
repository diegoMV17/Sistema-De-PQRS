package com.ideapro.pqrs_back.pqrs.model;

import java.time.LocalDateTime;

import com.ideapro.pqrs_back.peticionario.model.Peticionario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "pqrs")
@Data
public class Pqrs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private String descripcion;
    private LocalDateTime fechaRegistro;
    private String numeroRadicado;

    @ManyToOne
    @JoinColumn(name = "peticionario_id")
    private Peticionario peticionario;
}
