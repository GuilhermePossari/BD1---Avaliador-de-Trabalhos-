package com.sistema.service;

import com.sistema.dao.interfaces.AvaliacaoDAO;
import com.sistema.dao.interfaces.CriterioDAO;
import com.sistema.dto.AvaliacaoComCriteriosDTO;
import com.sistema.dto.CriterioDTO;
import com.sistema.model.Avaliacao;
import com.sistema.model.Criterio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AvaliacaoServiceImpl implements AvaliacaoService {

    private final AvaliacaoDAO avaliacaoDAO;
    private final CriterioDAO criterioDAO;

    @Autowired
    public AvaliacaoServiceImpl(AvaliacaoDAO avaliacaoDAO, CriterioDAO criterioDAO) {
        this.avaliacaoDAO = avaliacaoDAO;
        this.criterioDAO = criterioDAO;
    }

    @Override
    @Transactional
    public Avaliacao criar(Avaliacao avaliacao) {
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
@Transactional
public Avaliacao criarComCriterios(AvaliacaoComCriteriosDTO dto) {
    if (dto.getPrazo().before(dto.getDataPublicacao())) {
        throw new RuntimeException("A data de prazo não pode ser anterior à data de publicação.");
    }

    Avaliacao avaliacao = new Avaliacao();
    avaliacao.setIdDisciplina(dto.getIdDisciplina());
    Integer maxCodAvaliacao = avaliacaoDAO.findMaxCodAvaliacao(dto.getIdDisciplina());
    int nextCodAvaliacao = (maxCodAvaliacao != null) ? maxCodAvaliacao + 1 : 1; // Start from 1 if no existing evaluations for this discipline
    avaliacao.setCodAvaliacao(nextCodAvaliacao);
    avaliacao.setCriaAvalCod(dto.getCriaAvalCod());
    avaliacao.setDataPublicacao(dto.getDataPublicacao());
    avaliacao.setPrazo(dto.getPrazo());

    try {
        avaliacaoDAO.create(avaliacao);

        List<CriterioDTO> criteriosDto = dto.getCriterios();

        if (criteriosDto != null) {
            Integer maxIdCriterio = criterioDAO.findMaxIdCriterio(dto.getIdDisciplina(), dto.getCodAvaliacao(), dto.getCriaAvalCod());
            int nextIdCriterio = (maxIdCriterio != null) ? maxIdCriterio + 1 : 0;
            for (CriterioDTO cDto : criteriosDto) {
                Criterio c = new Criterio();
                c.setIdDisciplina(dto.getIdDisciplina());
                c.setCodAvaliacao(dto.getCodAvaliacao());
                c.setIdProfessor(dto.getCriaAvalCod()); // O professor criador
                c.setIdCriterio(nextIdCriterio++); // Cada critério recebe um id único dentro da avaliação
                c.setNome(cDto.getNome());
                c.setDescricao(cDto.getDescricao());
                c.setPeso(java.math.BigDecimal.valueOf(cDto.getPeso()));

                criterioDAO.create(c);
            }
        }

    } catch (java.sql.SQLException e) {
        throw new RuntimeException("Erro ao criar avaliação com critérios: " + e.getMessage(), e);
    }

    return avaliacao;
}


    @Override
    public Avaliacao buscarPorId(int codAvaliacao) {
        try {
            return avaliacaoDAO.read(codAvaliacao);
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

    @Override
    public Avaliacao buscarPorDisciplinaECodigo(int idDisciplina, int codAvaliacao) {
        try {
            return avaliacaoDAO.readByDisciplinaAndCodigo(idDisciplina, codAvaliacao);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao buscar avaliação por disciplina e código: " + e.getMessage(), e);
        }
    }
}
