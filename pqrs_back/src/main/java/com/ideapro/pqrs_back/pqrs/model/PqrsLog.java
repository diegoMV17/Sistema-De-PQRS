package com.ideapro.pqrs_back.pqrs.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pqrs_log")
public class PqrsLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pqrs_id", nullable = false)
    private Pqrs pqrs;

    private String usuario;
    private String accion;

    @Column(length = 1000)
    private String detalle;

    private LocalDateTime fecha = LocalDateTime.now();

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Pqrs getPqrs() { return pqrs; }
    public void setPqrs(Pqrs pqrs) { this.pqrs = pqrs; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }

    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
