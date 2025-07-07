package com.sistema.controller;

import com.sistema.model.Estudante;
import com.sistema.service.EstudanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudantes")
public class EstudanteController {

    private final EstudanteService estudanteService;

    @Autowired
    public EstudanteController(EstudanteService estudanteService) {
        this.estudanteService = estudanteService;
    }

    @PostMapping
    public ResponseEntity<Estudante> criar(@RequestBody Estudante estudante) {
        Estudante novo = estudanteService.criar(estudante);
        return ResponseEntity.ok(novo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudante> buscarPorId(@PathVariable int id) {
        Estudante estudante = estudanteService.buscarPorId(id);
        if (estudante == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(estudante);
    }

    @GetMapping
    public ResponseEntity<List<Estudante>> listarTodos() {
        return ResponseEntity.ok(estudanteService.listarTodos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        estudanteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/matricular")
    public ResponseEntity<?> matricular(
        @RequestParam int idUsuario,
        @RequestParam int idDisciplina
    ) {
        try {
            estudanteService.matricular(idUsuario, idDisciplina);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao matricular: " + e.getMessage());
        }
    }
}
