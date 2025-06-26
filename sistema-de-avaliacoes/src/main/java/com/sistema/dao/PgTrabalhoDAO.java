package com.sistema.dao;

import com.sistema.dao.interfaces.TrabalhoDAO;
import com.sistema.model.Trabalho;
// import org.springframework.beans.factory.annotation.Autowired;
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

    // @Autowired
    public PgTrabalhoDAO(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    @Override
    public void create(Trabalho trabalho) {
        String sql = "INSERT INTO Trabalho (pert_aval_cod, id_usuario, id_disciplina, data_envio, arquivo_url, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, trabalho.getPertAvalCod());
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
    public Trabalho read(int pertAvalCod, int idUsuario) {
        String sql = "SELECT * FROM Trabalho WHERE pert_aval_cod = ? AND id_usuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pertAvalCod);
            statement.setInt(2, idUsuario);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapRowToTrabalho(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar trabalho.", e);
        }
        return null;
    }

    @Override
    public void update(Trabalho trabalho) {
        String sql = "UPDATE Trabalho SET id_disciplina = ?, data_envio = ?, arquivo_url = ?, status = ? " +
                    "WHERE pert_aval_cod = ? AND id_usuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, trabalho.getIdDisciplina());
            statement.setDate(2, Date.valueOf(trabalho.getDataEnvio())); // Conversão
            statement.setString(3, trabalho.getArquivoUrl());
            statement.setString(4, trabalho.getStatus());
            statement.setInt(5, trabalho.getPertAvalCod());
            statement.setInt(6, trabalho.getIdUsuario());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar trabalho.", e);
        }
    }

    @Override
    public void delete(int pertAvalCod, int idUsuario) {
        String sql = "DELETE FROM Trabalho WHERE pert_aval_cod = ? AND id_usuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pertAvalCod);
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
        String sql = "SELECT * FROM Trabalho WHERE id_disciplina = ?";
        List<Trabalho> trabalhos = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idDisciplina);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    trabalhos.add(mapRowToTrabalho(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar trabalhos por disciplina.", e);
        }
        return trabalhos;
    }

    // Método auxiliar para não repetir código de mapeamento
    private Trabalho mapRowToTrabalho(ResultSet rs) throws SQLException {
        Trabalho trabalho = new Trabalho();
        trabalho.setPertAvalCod(rs.getInt("pert_aval_cod"));
        trabalho.setIdUsuario(rs.getInt("id_usuario"));
        trabalho.setIdDisciplina(rs.getInt("id_disciplina"));
        // Conversão CORRETA de sql.Date para LocalDate
        trabalho.setDataEnvio(rs.getDate("data_envio").toLocalDate());
        trabalho.setArquivoUrl(rs.getString("arquivo_url"));
        trabalho.setStatus(rs.getString("status"));
        return trabalho;
    }
}