package com.sistema.dao;

import com.sistema.dao.interfaces.CriterioDAO;
import com.sistema.model.Criterio;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PgCriterioDAO implements CriterioDAO {

    private final Connection connection;

    // @Autowired
    public PgCriterioDAO(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    @Override
    public void create(Criterio criterio) {
        String sql = "INSERT INTO Criterio (id_disciplina, cod_avaliacao, define_id_prof, id_criterio, nome, descricao, peso) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, criterio.getIdDisciplina());
            statement.setInt(2, criterio.getCodAvaliacao());
            statement.setInt(3, criterio.getIdProfessor());
            statement.setInt(4, criterio.getIdCriterio());
            statement.setString(5, criterio.getNome());
            statement.setString(6, criterio.getDescricao());
            statement.setBigDecimal(7, criterio.getPeso());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir critério.", e);
        }
    }

    @Override
    public Criterio read(int idDisciplina, int codAvaliacao, int idProfessor, int idCriterio) {
        String sql = "SELECT * FROM Criterio WHERE id_disciplina = ? AND cod_avaliacao = ? AND define_id_prof = ? AND id_criterio = ?";
        Criterio criterio = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idDisciplina);
            statement.setInt(2, codAvaliacao);
            statement.setInt(3, idProfessor);
            statement.setInt(4, idCriterio);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    criterio = new Criterio();
                    criterio.setIdDisciplina(rs.getInt("id_disciplina"));
                    criterio.setCodAvaliacao(rs.getInt("cod_avaliacao"));
                    criterio.setIdProfessor(rs.getInt("define_id_prof"));
                    criterio.setIdCriterio(rs.getInt("id_criterio"));
                    criterio.setNome(rs.getString("nome"));
                    criterio.setDescricao(rs.getString("descricao"));
                    criterio.setPeso(rs.getBigDecimal("peso"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar critério.", e);
        }
        return criterio;
    }

    @Override
    public void update(Criterio criterio) {
        String sql = "UPDATE Criterio SET nome = ?, descricao = ?, peso = ? " +
                    "WHERE id_disciplina = ? AND cod_avaliacao = ? AND define_id_prof = ? AND id_criterio = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, criterio.getNome());
            statement.setString(2, criterio.getDescricao());
            statement.setBigDecimal(3, criterio.getPeso());
            statement.setInt(4, criterio.getIdDisciplina());
            statement.setInt(5, criterio.getCodAvaliacao());
            statement.setInt(6, criterio.getIdProfessor());
            statement.setInt(7, criterio.getIdCriterio());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar critério.", e);
        }
    }

    @Override
    public void delete(int idDisciplina, int codAvaliacao, int idProfessor, int idCriterio) {
        String sql = "DELETE FROM Criterio WHERE id_disciplina = ? AND cod_avaliacao = ? AND define_id_prof = ? AND id_criterio = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idDisciplina);
            statement.setInt(2, codAvaliacao);
            statement.setInt(3, idProfessor);
            statement.setInt(4, idCriterio);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar critério.", e);
        }
    }

    @Override
    public List<Criterio> all() {
        String sql = "SELECT * FROM Criterio";
        List<Criterio> criterios = new ArrayList<>();
        try (Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Criterio criterio = new Criterio();
                criterio.setIdDisciplina(rs.getInt("id_disciplina"));
                criterio.setCodAvaliacao(rs.getInt("cod_avaliacao"));
                criterio.setIdProfessor(rs.getInt("define_id_prof"));
                criterio.setIdCriterio(rs.getInt("id_criterio"));
                criterio.setNome(rs.getString("nome"));
                criterio.setDescricao(rs.getString("descricao"));
                criterio.setPeso(rs.getBigDecimal("peso"));
                criterios.add(criterio);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os critérios.", e);
        }
        return criterios;
    }

    @Override
    public List<Criterio> allByAvaliacao(int idDisciplina, int codAvaliacao) {
        String sql = "SELECT * FROM Criterio WHERE id_disciplina = ? AND cod_avaliacao = ?";
        List<Criterio> criterios = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idDisciplina);
            statement.setInt(2, codAvaliacao);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Criterio criterio = new Criterio();
                    criterio.setIdDisciplina(rs.getInt("id_disciplina"));
                    criterio.setCodAvaliacao(rs.getInt("cod_avaliacao"));
                    criterio.setIdProfessor(rs.getInt("define_id_prof"));
                    criterio.setIdCriterio(rs.getInt("id_criterio"));
                    criterio.setNome(rs.getString("nome"));
                    criterio.setDescricao(rs.getString("descricao"));
                    criterio.setPeso(rs.getBigDecimal("peso"));
                    criterios.add(criterio);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar critérios por avaliação.", e);
        }
        return criterios;
    }
}