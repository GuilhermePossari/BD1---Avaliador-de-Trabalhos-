package com.sistema.controller;

import com.sistema.model.Trabalho;
import com.sistema.service.TrabalhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trabalhos")
public class TrabalhoController {

    private final TrabalhoService trabalhoService;

    @Autowired
    public TrabalhoController(TrabalhoService trabalhoService) {
        this.trabalhoService = trabalhoService;
    }

    @PostMapping
    public Trabalho enviarTrabalho(@RequestBody Trabalho trabalho) {
        return trabalhoService.enviar(trabalho);
    }

    @PutMapping("/{pertAvalCod}/{idUsuario}/avaliar")
    public Trabalho avaliarTrabalho(
        @PathVariable int pertAvalCod,
        @PathVariable int idUsuario,
        @RequestParam String status
    ) {
        return trabalhoService.avaliar(pertAvalCod, idUsuario, status);
    }

    @GetMapping("/{pertAvalCod}/{idUsuario}")
    public Trabalho buscarTrabalhoPorId(
        @PathVariable int pertAvalCod,
        @PathVariable int idUsuario
    ) {
        return trabalhoService.buscarPorId(pertAvalCod, idUsuario);
    }

    @GetMapping("/disciplina/{idDisciplina}")
    public List<Trabalho> listarPorDisciplina(@PathVariable int idDisciplina) {
        return trabalhoService.listarPorDisciplina(idDisciplina);
    }

    @GetMapping("/estudante/{idUsuario}")
    public List<Trabalho> listarPorEstudante(@PathVariable int idUsuario) {
        return trabalhoService.listarPorEstudante(idUsuario); // cuidado: esse método precisa ser implementado no DAO
    }
}
