package com.sistema.service;

import com.sistema.model.Criterio;
import java.util.List;

public interface CriterioService {
    Criterio criar(Criterio criterio);
    List<Criterio> listarPorAvaliacao(int idDisciplina, int codAvaliacao);
    void deletar(int idDisciplina, int codAvaliacao, int idProfessor, int idCriterio);
}