package com.sistema.controller;

import com.sistema.model.Avaliacao;
import com.sistema.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    @Autowired
    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping
    public ResponseEntity<Avaliacao> criarAvaliacao(@RequestBody Avaliacao avaliacao) {
        Avaliacao criada = avaliacaoService.criar(avaliacao);
        return ResponseEntity.ok(criada);
    }

    @GetMapping("/{idDisciplina}/{codAvaliacao}")
    public ResponseEntity<Avaliacao> buscarPorId(@PathVariable int idDisciplina,
                                                 @PathVariable int codAvaliacao) {
        Avaliacao avaliacao = avaliacaoService.buscarPorId(idDisciplina, codAvaliacao);
        if (avaliacao != null) {
            return ResponseEntity.ok(avaliacao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/disciplina/{idDisciplina}")
    public ResponseEntity<List<Avaliacao>> listarPorDisciplina(@PathVariable int idDisciplina) {
        List<Avaliacao> avaliacoes = avaliacaoService.listarPorDisciplina(idDisciplina);
        return ResponseEntity.ok(avaliacoes);
    }
}
