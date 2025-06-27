package com.sistema.service;

import com.sistema.dao.interfaces.AvaliacaoDAO;
import com.sistema.model.Avaliacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AvaliacaoServiceImpl implements AvaliacaoService {

    private final AvaliacaoDAO avaliacaoDAO;

    @Autowired
    public AvaliacaoServiceImpl(AvaliacaoDAO avaliacaoDAO) {
        this.avaliacaoDAO = avaliacaoDAO;
    }

    @Override
    @Transactional
    public Avaliacao criar(Avaliacao avaliacao) {
        // Aqui poderíamos adicionar validações?:
        // 1. Verificar se a data de prazo é posterior à data de publicação.
        // 2. Verificar se a disciplina (avaliacao.getIdDisciplina()) existe.
        // 3. Verificar se o professor (avaliacao.getCriaAvalCod()) existe.

        if (avaliacao.getPrazo().before(avaliacao.getDataPublicacao())) {
            throw new RuntimeException("A data de prazo não pode ser anterior à data de publicação.");
        }

        try {
            avaliacaoDAO.create(avaliacao);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao criar avaliação: " + e.getMessage(), e);
        }
        return avaliacao;
    }

    @Override
    public Avaliacao buscarPorId(int idDisciplina, int codAvaliacao) {
        try {
            return avaliacaoDAO.read(idDisciplina, codAvaliacao);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao buscar avaliação: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Avaliacao> listarPorDisciplina(int idDisciplina) {
        try {
            return avaliacaoDAO.allByDisciplina(idDisciplina);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao listar avaliações por disciplina: " + e.getMessage(), e);
        }
    }
}