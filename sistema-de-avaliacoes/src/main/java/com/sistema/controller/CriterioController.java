package com.sistema.controller;

import com.sistema.model.Criterio;
import com.sistema.service.CriterioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/criterios")
public class CriterioController {

    private final CriterioService criterioService;

    @Autowired
    public CriterioController(CriterioService criterioService) {
        this.criterioService = criterioService;
    }

    @PostMapping
    public ResponseEntity<Criterio> criarCriterio(@RequestBody Criterio criterio) {
        Criterio criado = criterioService.criar(criterio);
        return ResponseEntity.ok(criado);
    }

    @GetMapping("/avaliacao/{idDisciplina}/{codAvaliacao}")
    public ResponseEntity<List<Criterio>> listarPorAvaliacao(@PathVariable int idDisciplina,
                                                             @PathVariable int codAvaliacao) {
        List<Criterio> criterios = criterioService.listarPorAvaliacao(idDisciplina, codAvaliacao);
        return ResponseEntity.ok(criterios);
    }

    @DeleteMapping("/{idDisciplina}/{codAvaliacao}/{idProfessor}/{idCriterio}")
    public ResponseEntity<Void> deletar(@PathVariable int idDisciplina,
                                        @PathVariable int codAvaliacao,
                                        @PathVariable int idProfessor,
                                        @PathVariable int idCriterio) {
        criterioService.deletar(idDisciplina, codAvaliacao, idProfessor, idCriterio);
        return ResponseEntity.noContent().build();
    }
}
