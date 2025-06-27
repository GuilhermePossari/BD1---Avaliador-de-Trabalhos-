package com.sistema.service;

import com.sistema.dao.interfaces.AvaliacaoDAO;
import com.sistema.dao.interfaces.TrabalhoDAO;
import com.sistema.model.Avaliacao;
import com.sistema.model.Trabalho;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class TrabalhoServiceImpl implements TrabalhoService {

    private final TrabalhoDAO trabalhoDAO;
    private final AvaliacaoDAO avaliacaoDAO; // Dependência adicional para validar o prazo

    public TrabalhoServiceImpl(TrabalhoDAO trabalhoDAO, AvaliacaoDAO avaliacaoDAO) {
        this.trabalhoDAO = trabalhoDAO;
        this.avaliacaoDAO = avaliacaoDAO;
    }

    @Override
    @Transactional
    public Trabalho enviar(Trabalho trabalho) {
        // Verificar se o envio tá dentro do prazo.
        Avaliacao avaliacao;
        try {
            avaliacao = avaliacaoDAO.read(trabalho.getIdDisciplina(), trabalho.getPertAvalCod());
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao acessar o banco de dados para buscar a avaliação.", e);
        }
        if (avaliacao == null) {
            throw new RuntimeException("Avaliação não encontrada para o envio do trabalho.");
        }

        // Convertendo java.util.Date para java.time.LocalDate para comparação
        LocalDate prazo = new java.sql.Date(avaliacao.getPrazo().getTime()).toLocalDate();

        if (LocalDate.now().isAfter(prazo)) {
            throw new RuntimeException("Não é possível enviar o trabalho: o prazo já encerrou.");
        }

        // Define o status inicial e a data de envio
        trabalho.setStatus("Não Avaliado");
        trabalho.setDataEnvio(LocalDate.now());

        try {
            trabalhoDAO.create(trabalho);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao acessar o banco de dados para criar o trabalho.", e);
        }
        return trabalho;
    }

    @Override
    @Transactional
    public Trabalho avaliar(int pertAvalCod, int idUsuario, String novoStatus) {
        Trabalho trabalho;
        try {
            trabalho = trabalhoDAO.read(pertAvalCod, idUsuario);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao acessar o banco de dados para buscar o trabalho.", e);
        }
        if (trabalho == null) {
            throw new RuntimeException("Trabalho não encontrado para avaliação.");
        }

        // Validação simples do status
        if (!Objects.equals(novoStatus, "Avaliado")) {
           // Poderia adicionar outros status como "Pendente de Revisão", etc.
            throw new IllegalArgumentException("Status de avaliação inválido.");
        }

        trabalho.setStatus(novoStatus);
        try {
            trabalhoDAO.update(trabalho);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao acessar o banco de dados para atualizar o trabalho.", e);
        }
        return trabalho;
    }

    @Override
    public Trabalho buscarPorId(int pertAvalCod, int idUsuario) {
        try {
            return trabalhoDAO.read(pertAvalCod, idUsuario);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao acessar o banco de dados para buscar o trabalho.", e);
        }
    }

    @Override
    public List<Trabalho> listarPorDisciplina(int idDisciplina) {
        try {
            return trabalhoDAO.allByDisciplina(idDisciplina);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao acessar o banco de dados para listar trabalhos por disciplina.", e);
        }
    }

    @Override
    public List<Trabalho> listarPorEstudante(int idUsuario) {
        // Para este método funcionar, é preciso adicioná-lo ao TrabalhoDAO
        // return trabalhoDAO.allByEstudante(idUsuario);
        throw new UnsupportedOperationException("Método não implementado no DAO ainda.");
    }
}