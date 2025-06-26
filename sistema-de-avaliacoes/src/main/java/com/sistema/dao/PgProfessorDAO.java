package com.sistema.dao;

import com.sistema.dao.interfaces.ProfessorDAO;
import com.sistema.model.Professor;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PgProfessorDAO implements ProfessorDAO {

    private final Connection connection;

    // @Autowired
    public PgProfessorDAO(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    @Override
    public void create(Professor professor) {
        // Primeiro, insere na tabela Usuario, pois Professor é uma especialização
        new PgUsuarioDAO(null).create(professor);

        String sql = "INSERT INTO Professor (id_usuario, cria_aval_cod) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, professor.getId());
            statement.setInt(2, professor.getCriaAvalCod());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir professor.", e);
        }
    }

    @Override
    public Professor read(int id) {
        String sql = "SELECT u.*, p.cria_aval_cod FROM Usuario u " +
                    "JOIN Professor p ON u.id_usuario = p.id_usuario WHERE u.id_usuario = ?";
        Professor professor = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    professor = new Professor();
                    professor.setId(rs.getInt("id_usuario"));
                    professor.setPnome(rs.getString("pnome"));
                    professor.setSnome(rs.getString("snome"));
                    professor.setEmail(rs.getString("email"));
                    professor.setSenha(rs.getString("senha"));
                    professor.setCriaAvalCod(rs.getInt("cria_aval_cod"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar professor.", e);
        }
        return professor;
    }

    @Override
    public void update(Professor professor) {
        // Atualiza a parte do Usuario
        new PgUsuarioDAO(null).update(professor);

        String sql = "UPDATE Professor SET cria_aval_cod = ? WHERE id_usuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, professor.getCriaAvalCod());
            statement.setInt(2, professor.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar professor.", e);
        }
    }

    @Override
    public void delete(int id) {
        String sqlProf = "DELETE FROM Professor WHERE id_usuario = ?";
        String sqlUser = "DELETE FROM Usuario WHERE id_usuario = ?";

        try (PreparedStatement stmtProf = connection.prepareStatement(sqlProf);
            PreparedStatement stmtUser = connection.prepareStatement(sqlUser)) {

            // A ordem importa devido às chaves estrangeiras
            stmtProf.setInt(1, id);
            stmtProf.executeUpdate();

            stmtUser.setInt(1, id);
            stmtUser.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar professor.", e);
        }
    }

    @Override
    public List<Professor> all() {
        String sql = "SELECT u.*, p.cria_aval_cod FROM Usuario u JOIN Professor p ON u.id_usuario = p.id_usuario";
        List<Professor> professores = new ArrayList<>();

        try (Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                Professor professor = new Professor();
                professor.setId(rs.getInt("id_usuario"));
                professor.setPnome(rs.getString("pnome"));
                professor.setSnome(rs.getString("snome"));
                professor.setEmail(rs.getString("email"));
                professor.setSenha(rs.getString("senha"));
                professor.setCriaAvalCod(rs.getInt("cria_aval_cod"));
                professores.add(professor);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os professores.", e);
        }
        return professores;
    }
}