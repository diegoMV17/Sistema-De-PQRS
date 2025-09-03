package com.ideapro.pqrs_back.pqrs.model;

import java.time.LocalDateTime;
import com.ideapro.pqrs_back.peticionario.model.Peticionario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "pqrs")
public class Pqrs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) // 🔹 Guardará el nombre del enum en la BD (PETICION, QUEJA, etc.)
    @Column(nullable = false, length = 50)
    private TipoPqrs tipo;

    @NotBlank
    @Column(length = 2000, nullable = false)
    private String descripcion;

    @ManyToOne // 🔹 relación con peticionario
    @JoinColumn(name = "peticionario_id", nullable = false)
    private Peticionario peticionario;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @Column(unique = true, nullable = false, length = 50)
    private String numeroRadicado;

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    private PqrsEstado estado;

    // --- Auto-set antes de guardar ---
    @PrePersist
    public void prePersist() {
        fechaRegistro = LocalDateTime.now();
        if (numeroRadicado == null || numeroRadicado.isEmpty()) {
            numeroRadicado = "RAD-" + System.currentTimeMillis();
        }
    }

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TipoPqrs getTipo() { return tipo; }
    public void setTipo(TipoPqrs tipo) { this.tipo = tipo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Peticionario getPeticionario() { return peticionario; }
    public void setPeticionario(Peticionario peticionario) { this.peticionario = peticionario; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getNumeroRadicado() { return numeroRadicado; }
    public void setNumeroRadicado(String numeroRadicado) { this.numeroRadicado = numeroRadicado; }

    public PqrsEstado getEstado() { return estado; }
    public void setEstado(PqrsEstado estado) { this.estado = estado; }
}
