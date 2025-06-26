package com.sistema.dao;

import com.sistema.model.Estudante;
import com.sistema.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudanteDAO {

    private final Connection conn;

    public EstudanteDAO(Connection conn) {
        this.conn = conn;
    }

    public void inserir(Estudante estudante) throws SQLException {
        String sql = "INSERT INTO estudante (id_usuario) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, estudante.getId());
            stmt.executeUpdate();
        }
    }

    public Estudante buscarPorId(int id) throws SQLException {
        String sql = "SELECT u.* FROM estudante e JOIN usuario u ON e.id_usuario = u.id_usuario WHERE e.id_usuario = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Estudante(
                        rs.getInt("id_usuario"),
                        rs.getString("pnome"),
                        rs.getString("snome"),
                        rs.getString("email"),
                        rs.getString("senha")
                    );
                }
            }
        }
        return null;
    }

    public List<Estudante> listarTodos() throws SQLException {
        String sql = "SELECT u.* FROM estudante e JOIN usuario u ON e.id_usuario = u.id_usuario";
        List<Estudante> lista = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Estudante est = new Estudante(
                    rs.getInt("id_usuario"),
                    rs.getString("pnome"),
                    rs.getString("snome"),
                    rs.getString("email"),
                    rs.getString("senha")
                );
                lista.add(est);
            }
        }
        return lista;
    }
}
