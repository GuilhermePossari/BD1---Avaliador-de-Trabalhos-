package com.sistema.controller;

import com.sistema.model.Trabalho;
import com.sistema.service.TrabalhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.sistema.dto.TrabalhoCriterioNotaDTO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/trabalhos")
public class TrabalhoController {

    private final TrabalhoService trabalhoService;

    @Autowired
    public TrabalhoController(TrabalhoService trabalhoService) {
        this.trabalhoService = trabalhoService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> enviarTrabalho(
            @RequestParam("file") MultipartFile file,
            @RequestParam("idAvaliacao") int idAvaliacao,
            @RequestParam("idUsuario") int idUsuario,
            @RequestParam("idDisciplina") int idDisciplina,
            @RequestParam("dataEnvio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataEnvio
    ) {
        try {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || (!originalFilename.toLowerCase().endsWith(".pdf") && !originalFilename.toLowerCase().endsWith(".docx"))) {
                return ResponseEntity.badRequest().body("Erro: Apenas arquivos PDF e DOCX são permitidos.");
            }

            Trabalho trabalho = trabalhoService.enviar(file, idAvaliacao, idUsuario, idDisciplina, dataEnvio);
            return ResponseEntity.ok(trabalho);
        } catch (Exception e) { // Captura qualquer exceção
            e.printStackTrace(); // Loga a stack trace no console do servidor
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao enviar trabalho: " + e.getMessage());
        }
    }

    @GetMapping("/avaliacao/{idAvaliacao}")
        public List<Trabalho> listarPorAvaliacao(
            @PathVariable int idAvaliacao
        ) {
            return trabalhoService.listarPorAvaliacao(idAvaliacao);
        }


    @PutMapping("/{idAvaliacao}/{idUsuario}/avaliar")
    public ResponseEntity<Trabalho> avaliarTrabalho(
        @PathVariable int idAvaliacao,
        @PathVariable int idUsuario,
        @RequestParam String status,
        @RequestParam(required = false) Double nota,
        @RequestBody(required = false) List<TrabalhoCriterioNotaDTO> notasPorCriterio
    ) {
        try {
            Trabalho trabalhoAtualizado = trabalhoService.avaliar(idAvaliacao, idUsuario, status, nota, notasPorCriterio);
            return ResponseEntity.ok(trabalhoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{idAvaliacao}/{idUsuario}/download")
    public ResponseEntity<Resource> downloadTrabalho(
            @PathVariable int idAvaliacao,
            @PathVariable int idUsuario) {
        try {
            Resource resource = trabalhoService.download(idAvaliacao, idUsuario);
            String contentType = "application/octet-stream";
            String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                    .body(resource);
        } catch (RuntimeException | IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{idAvaliacao}/{idUsuario}")
    public ResponseEntity<?> buscarTrabalhoPorId(
        @PathVariable int idAvaliacao,
        @PathVariable int idUsuario
    ) {
        Trabalho trabalho = trabalhoService.buscarPorId(idAvaliacao, idUsuario);
        if (trabalho != null) {
            return ResponseEntity.ok(trabalho);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/disciplina/{idDisciplina}")
    public List<Trabalho> listarPorDisciplina(@PathVariable int idDisciplina) {
        return trabalhoService.listarPorDisciplina(idDisciplina);
    }

    @GetMapping("/estudante/{idUsuario}")
    public List<Trabalho> listarPorEstudante(@PathVariable int idUsuario) {
        return trabalhoService.listarPorEstudante(idUsuario);
    }
}
