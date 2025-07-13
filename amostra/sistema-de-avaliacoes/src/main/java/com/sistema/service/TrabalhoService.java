package com.sistema.service;

import com.sistema.model.Trabalho;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public interface TrabalhoService {
    Trabalho enviar(org.springframework.web.multipart.MultipartFile file, int idAvaliacao, int idUsuario, int idDisciplina, java.time.LocalDate dataEnvio) throws java.io.IOException;
    Trabalho avaliar(int idAvaliacao, int idUsuario, String novoStatus, Double nota, java.util.List<com.sistema.dto.TrabalhoCriterioNotaDTO> notasPorCriterio);
    Trabalho buscarPorId(int idAvaliacao, int idUsuario);
    List<Trabalho> listarPorDisciplina(int idDisciplina);
    List<Trabalho> listarPorEstudante(int idUsuario);
    List<Trabalho> listarPorAvaliacao(int idAvaliacao);
    Resource download(int idAvaliacao, int idUsuario) throws IOException;
    com.sistema.dto.RelatorioGeralDTO gerarRelatorioGeral(int idDisciplina);
}