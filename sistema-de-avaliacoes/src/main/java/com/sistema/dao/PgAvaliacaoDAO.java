package com.sistema.dao;

import com.sistema.dao.interfaces.AvaliacaoDAO;
import com.sistema.model.Avaliacao;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PgAvaliacaoDAO implements AvaliacaoDAO {

    private final Connection connection;

    // @Autowired
    public PgAvaliacaoDAO(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    @Override
    public void create(Avaliacao avaliacao) {
        String sql = "INSERT INTO Avaliacao (id_disciplina, cod_avaliacao, cria_aval_cod, data_publicacao, prazo) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, avaliacao.getIdDisciplina());
            statement.setInt(2, avaliacao.getCodAvaliacao());
            statement.setInt(3, avaliacao.getCriaAvalCod());
            statement.setDate(4, new java.sql.Date(avaliacao.getDataPublicacao().getTime()));
            statement.setDate(5, new java.sql.Date(avaliacao.getPrazo().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir avaliação.", e);
        }
    }

    @Override
    public Avaliacao read(int idDisciplina, int codAvaliacao) {
        String sql = "SELECT * FROM Avaliacao WHERE id_disciplina = ? AND cod_avaliacao = ?";
        Avaliacao avaliacao = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idDisciplina);
            statement.setInt(2, codAvaliacao);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    avaliacao = new Avaliacao();
                    avaliacao.setIdDisciplina(rs.getInt("id_disciplina"));
                    avaliacao.setCodAvaliacao(rs.getInt("cod_avaliacao"));
                    avaliacao.setCriaAvalCod(rs.getInt("cria_aval_cod"));
                    avaliacao.setDataPublicacao(rs.getDate("data_publicacao"));
                    avaliacao.setPrazo(rs.getDate("prazo"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar avaliação.", e);
        }
        return avaliacao;
    }

    @Override
    public void update(Avaliacao avaliacao) {
        String sql = "UPDATE Avaliacao SET cria_aval_cod = ?, data_publicacao = ?, prazo = ? WHERE id_disciplina = ? AND cod_avaliacao = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, avaliacao.getCriaAvalCod());
            statement.setDate(2, new java.sql.Date(avaliacao.getDataPublicacao().getTime()));
            statement.setDate(3, new java.sql.Date(avaliacao.getPrazo().getTime()));
            statement.setInt(4, avaliacao.getIdDisciplina());
            statement.setInt(5, avaliacao.getCodAvaliacao());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar avaliação.", e);
        }
    }

    @Override
    public void delete(int idDisciplina, int codAvaliacao) {
        String sql = "DELETE FROM Avaliacao WHERE id_disciplina = ? AND cod_avaliacao = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idDisciplina);
            statement.setInt(2, codAvaliacao);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar avaliação.", e);
        }
    }

    @Override
    public List<Avaliacao> all() {
        String sql = "SELECT * FROM Avaliacao";
        List<Avaliacao> avaliacoes = new ArrayList<>();
        try (Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setIdDisciplina(rs.getInt("id_disciplina"));
                avaliacao.setCodAvaliacao(rs.getInt("cod_avaliacao"));
                avaliacao.setCriaAvalCod(rs.getInt("cria_aval_cod"));
                avaliacao.setDataPublicacao(rs.getDate("data_publicacao"));
                avaliacao.setPrazo(rs.getDate("prazo"));
                avaliacoes.add(avaliacao);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todas as avaliações.", e);
        }
        return avaliacoes;
    }

    @Override
    public List<Avaliacao> allByDisciplina(int idDisciplina) {
        String sql = "SELECT * FROM Avaliaco WHERE id_disciplina = ?";
        List<Avaliacao> avaliacoes = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idDisciplina);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Avaliacao avaliacao = new Avaliacao();
                    avaliacao.setIdDisciplina(rs.getInt("id_disciplina"));
                    avaliacao.setCodAvaliacao(rs.getInt("cod_avaliacao"));
                    avaliacao.setCriaAvalCod(rs.getInt("cria_aval_cod"));
                    avaliacao.setDataPublicacao(rs.getDate("data_publicacao"));
                    avaliacao.setPrazo(rs.getDate("prazo"));
                    avaliacoes.add(avaliacao);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar avaliações por disciplina.", e);
        }
        return avaliacoes;
    }
}