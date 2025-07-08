package com.sistema.service;

import com.sistema.dto.AvaliacaoComCriteriosDTO;
import com.sistema.model.Avaliacao;

import java.util.List;

public interface AvaliacaoService {
    Avaliacao criar(Avaliacao avaliacao);
    Avaliacao criarComCriterios(AvaliacaoComCriteriosDTO dto);
    Avaliacao buscarPorId(int idDisciplina, int codAvaliacao);
    List<Avaliacao> listarPorDisciplina(int idDisciplina);
}
