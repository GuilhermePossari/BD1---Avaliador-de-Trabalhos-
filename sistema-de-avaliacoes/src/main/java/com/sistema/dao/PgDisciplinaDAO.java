package com.sistema.dao;

import com.sistema.dao.interfaces.DisciplinaDAO;
import com.sistema.model.Disciplina;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PgDisciplinaDAO implements DisciplinaDAO {

    private final Connection connection;

    // @Autowired
    public PgDisciplinaDAO(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    @Override
    public void create(Disciplina disciplina) {
        String sql = "INSERT INTO Disciplina (id_disciplina, nome, adm_prof_id) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, disciplina.getId());
            statement.setString(2, disciplina.getNome());
            statement.setInt(3, disciplina.getIdProfessor());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir disciplina.", e);
        }
    }

    @Override
    public Disciplina read(int id) {
        String sql = "SELECT * FROM Disciplina WHERE id_disciplina = ?";
        Disciplina disciplina = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    disciplina = new Disciplina();
                    disciplina.setId(rs.getInt("id_disciplina"));
                    disciplina.setNome(rs.getString("nome"));
                    disciplina.setIdProfessor(rs.getInt("adm_prof_id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar disciplina.", e);
        }
        return disciplina;
    }

    @Override
    public void update(Disciplina disciplina) {
        String sql = "UPDATE Disciplina SET nome = ?, adm_prof_id = ? WHERE id_disciplina = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, disciplina.getNome());
            statement.setInt(2, disciplina.getIdProfessor());
            statement.setInt(3, disciplina.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar disciplina.", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Disciplina WHERE id_disciplina = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar disciplina.", e);
        }
    }

    @Override
    public List<Disciplina> all() {
        String sql = "SELECT * FROM Disciplina";
        List<Disciplina> disciplinas = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Disciplina disciplina = new Disciplina();
                disciplina.setId(rs.getInt("id_disciplina"));
                disciplina.setNome(rs.getString("nome"));
                disciplina.setIdProfessor(rs.getInt("adm_prof_id"));
                disciplinas.add(disciplina);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todas as disciplinas.", e);
        }
        return disciplinas;
    }
}