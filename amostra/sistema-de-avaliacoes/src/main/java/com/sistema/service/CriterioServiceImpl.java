package com.sistema.service;

import com.sistema.dao.interfaces.CriterioDAO;
import com.sistema.model.Criterio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CriterioServiceImpl implements CriterioService {

    private final CriterioDAO criterioDAO;

    public CriterioServiceImpl(CriterioDAO criterioDAO) {
        this.criterioDAO = criterioDAO;
    }

    @Override
    @Transactional
    public Criterio criar(Criterio criterio) {
        // A validação do peso (entre 0 e 10) já é feita pelo CHECK do banco,
        // validar aqui também pra fornecer erros mais claros.
        if (criterio.getPeso().compareTo(BigDecimal.ZERO) < 0 || criterio.getPeso().compareTo(new BigDecimal("10")) > 0) {
            throw new RuntimeException("O peso do critério deve estar entre 0 e 10.");
        }

        // Poderíamos verificar se a avaliação (idDisciplina, codAvaliacao) existe.

        try {
            criterioDAO.create(criterio);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao criar critério: " + e.getMessage(), e);
        }
        return criterio;
    }

    @Override
    public List<Criterio> listarPorAvaliacao(int idDisciplina, int codAvaliacao) {
        try {
            return criterioDAO.allByAvaliacao(idDisciplina, codAvaliacao);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao listar critérios por avaliação: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void deletar(int idDisciplina, int codAvaliacao, int idProfessor, int idCriterio) {
        try {
            criterioDAO.delete(idDisciplina, codAvaliacao, idProfessor, idCriterio);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao deletar critério: " + e.getMessage(), e);
        }
    }
}