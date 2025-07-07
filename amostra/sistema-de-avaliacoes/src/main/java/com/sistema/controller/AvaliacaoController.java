package com.sistema.controller;

import com.sistema.dto.AvaliacaoComCriteriosDTO;
import com.sistema.model.Avaliacao;
import com.sistema.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    @Autowired
    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping("/com-criterios")
    public ResponseEntity<?> criarAvaliacaoComCriterios(@RequestBody AvaliacaoComCriteriosDTO dto) {
        try {
            Avaliacao criada = avaliacaoService.criarComCriterios(dto);
            return ResponseEntity.ok(criada);
        } catch (Exception e) {
            // Log do erro (para debug no console do backend)
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao criar avaliação: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> criarAvaliacao(@RequestBody Avaliacao avaliacao) {
        try {
            Avaliacao criada = avaliacaoService.criar(avaliacao);
            return ResponseEntity.ok(criada);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao criar avaliação: " + e.getMessage());
        }
    }

    @GetMapping("/{idDisciplina}/{codAvaliacao}")
    public ResponseEntity<?> buscarPorId(@PathVariable int idDisciplina,
                                         @PathVariable int codAvaliacao) {
        try {
            Avaliacao avaliacao = avaliacaoService.buscarPorId(idDisciplina, codAvaliacao);
            if (avaliacao != null) {
                return ResponseEntity.ok(avaliacao);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao buscar avaliação: " + e.getMessage());
        }
    }

    @GetMapping("/disciplina/{idDisciplina}")
    public ResponseEntity<?> listarPorDisciplina(@PathVariable int idDisciplina) {
        try {
            List<Avaliacao> avaliacoes = avaliacaoService.listarPorDisciplina(idDisciplina);
            return ResponseEntity.ok(avaliacoes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao listar avaliações: " + e.getMessage());
        }
    }
}
