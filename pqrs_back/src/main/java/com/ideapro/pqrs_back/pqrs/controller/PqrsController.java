/**
 * Controlador para las PQRS (Peticiones, Quejas, Reclamos y Sugerencias)
 * pqrs/controller/PqrsController.java
 */
package com.ideapro.pqrs_back.pqrs.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ideapro.pqrs_back.peticionario.model.Peticionario;
import com.ideapro.pqrs_back.pqrs.model.Pqrs;
import com.ideapro.pqrs_back.pqrs.repository.PqrsRepository;
import com.ideapro.pqrs_back.pqrs.service.PqrsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pqrs")
public class PqrsController {

    private static final Logger logger = LoggerFactory.getLogger(PqrsController.class);

    private final PqrsService pqrsService;
    private final PqrsRepository pqrsRepository;

    public PqrsController(PqrsService pqrsService, PqrsRepository pqrsRepository) {
        this.pqrsService = pqrsService;
        this.pqrsRepository = pqrsRepository;
    }

    // Crear PQRS
    @PostMapping
    public ResponseEntity<Pqrs> crearPqrs(@Valid @RequestBody Pqrs pqrs) {
        if (pqrs.getPeticionario() == null) {
            pqrs.setPeticionario(new Peticionario());
        }

        Peticionario pet = pqrs.getPeticionario();
        pet.setApellidos(Optional.ofNullable(pet.getApellidos()).orElse(""));
        pet.setNombres(Optional.ofNullable(pet.getNombres()).orElse(""));
        pet.setTipoDocumento(Optional.ofNullable(pet.getTipoDocumento()).orElse(""));
        pet.setNumeroDocumento(Optional.ofNullable(pet.getNumeroDocumento()).orElse(""));
        pet.setTelefono(Optional.ofNullable(pet.getTelefono()).orElse(""));
        pet.setEmail(Optional.ofNullable(pet.getEmail()).orElse(""));

        Pqrs nuevaPqrs = pqrsService.crearPqrs(pqrs);
        logger.info("PQRS creada con ID {}", nuevaPqrs.getId());

        return ResponseEntity.status(201).body(nuevaPqrs);
    }

    // Listar todas las PQRS
    @GetMapping
    public ResponseEntity<List<Pqrs>> listarPqrs() {
        List<Pqrs> lista = pqrsService.listarPqrs();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    // Obtener una PQRS por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pqrs> obtenerPqrs(@PathVariable Long id) {
        return Optional.ofNullable(pqrsService.obtenerPqrs(id))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar PQRS por número de radicado
    @GetMapping("/buscarPorRadicado/{numeroRadicado}")
    public ResponseEntity<List<Pqrs>> buscarPorNumeroRadicado(@PathVariable String numeroRadicado) {
        List<Pqrs> resultados = pqrsService.buscarPorNumeroRadicado(numeroRadicado);
        return resultados.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(resultados);
    }

    // Buscar PQRS por número de documento
    @GetMapping("/buscarPorDocumento/{numeroDocumento}")
    public ResponseEntity<List<Pqrs>> buscarPorDocumento(@PathVariable String numeroDocumento) {
        List<Pqrs> resultados = pqrsService.buscarPorNumeroDocumento(numeroDocumento);
        return resultados.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(resultados);
    }

    // Eliminar PQRS
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarPqrs(@PathVariable Long id) {
        pqrsService.eliminarPqrs(id);
        return ResponseEntity.noContent().build();
    }

    // Contar total de PQRS
    @GetMapping("/total")
    public ResponseEntity<Long> obtenerTotalPqrs() {
        return ResponseEntity.ok(pqrsService.contarPqrs());
    }

    // Obtener estado de una PQRS por su ID
    @GetMapping("/estado/{id}")
    public ResponseEntity<String> obtenerEstadoPqrs(@PathVariable Long id) {
        return ResponseEntity.ok(pqrsService.obtenerEstadoPqrs(id).getNombre());
    }

    // Obtener estado de una PQRS por número de radicado
    @GetMapping("/estado/radicado/{numeroRadicado}")
    public ResponseEntity<String> obtenerEstadoPorRadicado(@PathVariable String numeroRadicado) {
        return ResponseEntity.ok(pqrsService.obtenerEstadoPorRadicado(numeroRadicado).getNombre());
    }

    // Listar PQRS por estado
    @GetMapping("/porEstado/{estado}")
    public ResponseEntity<List<Pqrs>> listarPqrsPorEstado(@PathVariable String estado) {
        List<Pqrs> lista = pqrsService.listarPqrsPorEstado(estado);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    // Historial de PQRS
    @GetMapping("/historial")
    public ResponseEntity<Map<String, Object>> obtenerHistorialPqrs() {
        return ResponseEntity.ok(pqrsService.obtenerHistorialPqrs());
    }
// ...existing code...

// ...existing code...

// ...existing code...

@GetMapping("/notificaciones")
public Map<String, Object> getNotificaciones() {
    List<Pqrs> abiertos = pqrsRepository.findByEstadoNombreIn(List.of("Pendiente", "En Proceso"));

    int nuevos = 0;
    int porVencer = 0;
    LocalDate hoy = LocalDate.now();

    // Lista para enviar detalles
    List<Map<String, Object>> detalles = new java.util.ArrayList<>();

    for (Pqrs pqrs : abiertos) {
        LocalDate fechaRegistro = pqrs.getFechaRegistro().toLocalDate();
        int diasHabiles = 0;
        LocalDate fecha = fechaRegistro;

        while (fecha.isBefore(hoy)) {
            if (fecha.getDayOfWeek() != DayOfWeek.SATURDAY && fecha.getDayOfWeek() != DayOfWeek.SUNDAY) {
                diasHabiles++;
            }
            fecha = fecha.plusDays(1);
        }

        int diasRestantes = 14 - diasHabiles;
        if (pqrs.getEstado().getNombre().equals("Pendiente")) {
            nuevos++;
        }
        if (diasRestantes <= 2 && diasRestantes > 0) {
            porVencer++;
        }

        // Agrega el detalle de cada PQRS
        detalles.add(Map.of(
            "numeroRadicado", pqrs.getNumeroRadicado(),
            "tipo", pqrs.getTipo(),
            "estado", pqrs.getEstado().getNombre(),
            "fechaRegistro", pqrs.getFechaRegistro().toLocalDate().toString()
        ));
    }

    return Map.of(
        "nuevos", nuevos,
        "porVencer", porVencer,
        "detalles", detalles
    );
}

}
