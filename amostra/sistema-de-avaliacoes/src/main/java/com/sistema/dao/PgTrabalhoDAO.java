package com.sistema.dao;

import com.sistema.dao.interfaces.TrabalhoDAO;
import com.sistema.dao.interfaces.TrabalhoCriterioNotaDAO;
import com.sistema.model.Trabalho;
import com.sistema.dto.TrabalhoCriterioNotaDTO;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PgTrabalhoDAO implements TrabalhoDAO {

    private final Connection connection;
    private final TrabalhoCriterioNotaDAO trabalhoCriterioNotaDAO;

    public PgTrabalhoDAO(DataSource dataSource, TrabalhoCriterioNotaDAO trabalhoCriterioNotaDAO) {
        try {
            this.connection = dataSource.getConnection();
            this.trabalhoCriterioNotaDAO = trabalhoCriterioNotaDAO;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    @Override
    public void create(Trabalho trabalho) {
        String sql = "INSERT INTO Trabalho (pert_aval_cod, id_usuario, data_envio, arquivo_url, status) " +
                    "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, trabalho.getIdAvaliacao());
            statement.setInt(2, trabalho.getIdUsuario());
            statement.setInt(3, trabalho.getIdDisciplina());
            statement.setDate(4, Date.valueOf(trabalho.getDataEnvio()));
            statement.setString(5, trabalho.getArquivoUrl());
            statement.setString(6, trabalho.getStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir trabalho.", e);
        }
    }

     @Override
    public List<Trabalho> listarPorAvaliacao(int idAvaliacao) {
        String sql = "SELECT * FROM Trabalho WHERE id_avaliacao = ?";
        List<Trabalho> trabalhos = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idAvaliacao);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    trabalhos.add(mapRowToTrabalho(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar trabalhos por avaliação.", e);
        }
        return trabalhos;
    }

    @Override
    public Trabalho read(int idAvaliacao, int idUsuario) {
        String sql = "SELECT * FROM Trabalho WHERE id_avaliacao = ? AND id_usuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idAvaliacao);
            statement.setInt(2, idUsuario);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Trabalho trabalho = mapRowToTrabalho(rs);
                    List<TrabalhoCriterioNotaDTO> notas = trabalhoCriterioNotaDAO.readAllByTrabalho(idAvaliacao, idUsuario);
                    trabalho.setNotasPorCriterio(notas);
                    return trabalho;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar trabalho.", e);
        }
        return null;
    }

    @Override
    public void update(Trabalho trabalho) {
        String sql = "UPDATE Trabalho SET data_envio = ?, arquivo_url = ?, status = ?, nota = ? " +
                    "WHERE id_avaliacao = ? AND id_usuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(trabalho.getDataEnvio()));
            statement.setString(2, trabalho.getArquivoUrl());
            statement.setString(3, trabalho.getStatus());
            if (trabalho.getNota() != null) {
                statement.setDouble(4, trabalho.getNota());
            } else {
                statement.setNull(4, java.sql.Types.DOUBLE);
            }
            statement.setInt(5, trabalho.getIdAvaliacao());
            statement.setInt(6, trabalho.getIdUsuario());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar trabalho.", e);
        }
    }

    @Override
    public void delete(int idAvaliacao, int idUsuario) {
        String sql = "DELETE FROM Trabalho WHERE id_avaliacao = ? AND id_usuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idAvaliacao);
            statement.setInt(2, idUsuario);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar trabalho.", e);
        }
    }

    @Override
    public List<Trabalho> all() {
        String sql = "SELECT * FROM Trabalho";
        List<Trabalho> trabalhos = new ArrayList<>();
        try (Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                trabalhos.add(mapRowToTrabalho(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os trabalhos.", e);
        }
        return trabalhos;
    }

    @Override
    public List<Trabalho> allByDisciplina(int idDisciplina) {
        String sql = "SELECT t.* FROM Trabalho t JOIN Avaliacao a ON t.pert_aval_cod = a.cod_avaliacao WHERE a.id_disciplina = ?";
        List<Trabalho> trabalhos = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idDisciplina);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Trabalho trabalho = mapRowToTrabalho(rs);
                    try {
                        List<TrabalhoCriterioNotaDTO> notas = trabalhoCriterioNotaDAO.readAllByTrabalho(trabalho.getIdAvaliacao(), trabalho.getIdUsuario());
                        trabalho.setNotasPorCriterio(notas);
                    } catch (SQLException e) {
                        // Logar o erro ou tratar de outra forma, mas não impedir a execução
                        System.err.println("Erro ao carregar notas por critério para o trabalho " + trabalho.getIdAvaliacao() + "/" + trabalho.getIdUsuario() + ": " + e.getMessage());
                        trabalho.setNotasPorCriterio(new ArrayList<>()); // Define uma lista vazia para evitar NullPointerException
                    }
                    trabalhos.add(trabalho);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar trabalhos por disciplina.", e);
        }
        return trabalhos;
    }

    private Trabalho mapRowToTrabalho(ResultSet rs) throws SQLException {
        Trabalho trabalho = new Trabalho();
        trabalho.setIdAvaliacao(rs.getInt("pert_aval_cod"));
        trabalho.setIdUsuario(rs.getInt("id_usuario"));
        trabalho.setDataEnvio(rs.getDate("data_envio").toLocalDate());
        trabalho.setArquivoUrl(rs.getString("arquivo_url"));
        trabalho.setStatus(rs.getString("status"));
        trabalho.setNota(rs.getDouble("nota"));
        if (rs.wasNull()) {
            trabalho.setNota(null);
        }
        return trabalho;
    }

    @Override
    public List<Trabalho> allByEstudante(int idUsuario) throws SQLException {
        String sql = "SELECT * FROM Trabalho WHERE id_usuario = ?";
        List<Trabalho> trabalhos = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    trabalhos.add(mapRowToTrabalho(rs));
                }
            }
        }
        return trabalhos;
    }
}