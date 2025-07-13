package com.sistema.controller;

import com.sistema.dto.RelatorioGeralDTO;
import com.sistema.service.TrabalhoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    private final TrabalhoService trabalhoService;

    public RelatorioController(TrabalhoService trabalhoService) {
        this.trabalhoService = trabalhoService;
    }

    @GetMapping("/geral/disciplina/{idDisciplina}")
    public ResponseEntity<?> gerarRelatorioGeral(@PathVariable int idDisciplina) {
        try {
            RelatorioGeralDTO relatorio = trabalhoService.gerarRelatorioGeral(idDisciplina);
            return ResponseEntity.ok(relatorio);
        } catch (Exception e) {
            e.printStackTrace(); // Para logar a stack trace no console do servidor
            return ResponseEntity.badRequest().body("Erro ao gerar relatório: " + e.getMessage());
        }
    }
}