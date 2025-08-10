package com.ideapro.pqrs_back.pqrs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideapro.pqrs_back.pqrs.model.Pqrs;
import com.ideapro.pqrs_back.pqrs.service.PqrsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/pqrs")
public class PqrsController {
    @Autowired
    private PqrsService pqrsService;

    @PostMapping
    public Pqrs crearPqrs(@RequestBody Pqrs pqrs) {
        return pqrsService.crearPqrs(pqrs);
    }

    @GetMapping
    public List<Pqrs> listarPqrs() {
        return pqrsService.listarPqrs();
    }

    @GetMapping("/{id}")
    public Pqrs obtenerPqrs(@PathVariable Long id) {
        return pqrsService.obtenerPqrs(id);
    }

    @GetMapping("/buscarPorRadicado/{numeroRadicado}")
    public List<Pqrs> buscarPorNumeroRadicado(@PathVariable String numeroRadicado) {
        return pqrsService.buscarPorNumeroRadicado(numeroRadicado);
    }

    @PostMapping("/eliminar/{id}")
    public void eliminarPqrs(@PathVariable Long id) {
        pqrsService.eliminarPqrs(id);
    }

}
