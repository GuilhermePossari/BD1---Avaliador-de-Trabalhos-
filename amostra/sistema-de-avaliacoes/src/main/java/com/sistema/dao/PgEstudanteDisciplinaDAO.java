package com.sistema.dao;

import com.sistema.dao.interfaces.EstudanteDisciplinaDAO;
import com.sistema.model.EstudanteDisciplina;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PgEstudanteDisciplinaDAO implements EstudanteDisciplinaDAO {

    private final Connection connection;

    // @Autowired
    public PgEstudanteDisciplinaDAO(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    @Override
    public void create(EstudanteDisciplina relacao) {
        String sql = "INSERT INTO Est_part_discp (id_usuario, id_disciplina) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, relacao.getIdUsuario());
            statement.setInt(2, relacao.getIdDisciplina());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao registrar estudante na disciplina.", e);
        }
    }

    @Override
    public void delete(int idUsuario, int idDisciplina) {
        String sql = "DELETE FROM Est_part_discp WHERE id_usuario = ? AND id_disciplina = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            statement.setInt(2, idDisciplina);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover estudante da disciplina.", e);
        }
    }

    @Override
    public List<EstudanteDisciplina> findAllByEstudante(int idUsuario) {
        String sql = "SELECT * FROM Est_part_discp WHERE id_usuario = ?";
        return find(sql, idUsuario);
    }

    @Override
    public List<EstudanteDisciplina> findAllByDisciplina(int idDisciplina) {
        String sql = "SELECT * FROM Est_part_discp WHERE id_disciplina = ?";
        return find(sql, idDisciplina);
    }

    // Método auxiliar para evitar repetição de código
    private List<EstudanteDisciplina> find(String sql, int id) {
        List<EstudanteDisciplina> relacoes = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    EstudanteDisciplina relacao = new EstudanteDisciplina();
                    relacao.setIdUsuario(rs.getInt("id_usuario"));
                    relacao.setIdDisciplina(rs.getInt("id_disciplina"));
                    relacoes.add(relacao);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar relação estudante-disciplina.", e);
        }
        return relacoes;
    }
}