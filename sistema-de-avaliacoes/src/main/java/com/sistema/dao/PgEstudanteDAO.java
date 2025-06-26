package com.sistema.dao;

import com.sistema.dao.interfaces.EstudanteDAO;
import com.sistema.model.Estudante;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PgEstudanteDAO implements EstudanteDAO {

    private final Connection connection;

    // @Autowired
    public PgEstudanteDAO(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    @Override
    public void create(Estudante estudante) {
        // Primeiro, insere na tabela Usuario
        new PgUsuarioDAO(null).create(estudante);

        String sql = "INSERT INTO Estudante (id_usuario) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, estudante.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir estudante.", e);
        }
    }

    @Override
    public Estudante read(int id) {
        String sql = "SELECT u.* FROM Usuario u JOIN Estudante e ON u.id_usuario = e.id_usuario WHERE u.id_usuario = ?";
        Estudante estudante = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    estudante = new Estudante();
                    estudante.setId(rs.getInt("id_usuario"));
                    estudante.setPnome(rs.getString("pnome"));
                    estudante.setSnome(rs.getString("snome"));
                    estudante.setEmail(rs.getString("email"));
                    estudante.setSenha(rs.getString("senha"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar estudante.", e);
        }
        return estudante;
    }

    @Override
    public void update(Estudante estudante) {
        // Estudante não tem campos próprios, apenas atualiza a parte do Usuario
        new PgUsuarioDAO(null).update(estudante);
    }

    @Override
    public void delete(int id) {
        String sqlEst = "DELETE FROM Estudante WHERE id_usuario = ?";
        String sqlUser = "DELETE FROM Usuario WHERE id_usuario = ?";
        try (PreparedStatement stmtEst = connection.prepareStatement(sqlEst);
             PreparedStatement stmtUser = connection.prepareStatement(sqlUser)) {
            stmtEst.setInt(1, id);
            stmtEst.executeUpdate();
            stmtUser.setInt(1, id);
            stmtUser.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar estudante.", e);
        }
    }

    @Override
    public List<Estudante> all() {
        String sql = "SELECT u.* FROM Usuario u JOIN Estudante e ON u.id_usuario = e.id_usuario";
        List<Estudante> estudantes = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Estudante estudante = new Estudante();
                estudante.setId(rs.getInt("id_usuario"));
                estudante.setPnome(rs.getString("pnome"));
                estudante.setSnome(rs.getString("snome"));
                estudante.setEmail(rs.getString("email"));
                estudante.setSenha(rs.getString("senha"));
                estudantes.add(estudante);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os estudantes.", e);
        }
        return estudantes;
    }
}