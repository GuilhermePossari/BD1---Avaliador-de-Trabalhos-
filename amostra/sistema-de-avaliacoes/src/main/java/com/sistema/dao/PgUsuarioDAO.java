package com.sistema.dao;

import com.sistema.dao.interfaces.UsuarioDAO;
import com.sistema.model.Usuario;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PgUsuarioDAO implements UsuarioDAO {

    private final DataSource dataSource;

    public PgUsuarioDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO Usuario (id_usuario, pnome, snome, email, senha) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, usuario.getId());
            statement.setString(2, usuario.getPnome());
            statement.setString(3, usuario.getSnome());
            statement.setString(4, usuario.getEmail());
            statement.setString(5, usuario.getSenha());
            statement.executeUpdate();
        }
    }

    @Override
    public Usuario read(int id) throws SQLException {
        String sql = "SELECT * FROM Usuario WHERE id_usuario = ?";
        Usuario usuario = null;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId(rs.getInt("id_usuario"));
                    usuario.setPnome(rs.getString("pnome"));
                    usuario.setSnome(rs.getString("snome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha"));
                }
            }
        }
        return usuario;
    }

    @Override
    public void update(Usuario usuario) throws SQLException {
        String sql = "UPDATE Usuario SET pnome = ?, snome = ?, email = ?, senha = ? WHERE id_usuario = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, usuario.getPnome());
            statement.setString(2, usuario.getSnome());
            statement.setString(3, usuario.getEmail());
            statement.setString(4, usuario.getSenha());
            statement.setInt(5, usuario.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Usuario WHERE id_usuario = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public List<Usuario> all() throws SQLException {
        String sql = "SELECT * FROM Usuario";
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id_usuario"));
                usuario.setPnome(rs.getString("pnome"));
                usuario.setSnome(rs.getString("snome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    @Override
    public Usuario buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Usuario WHERE email = ?";
        Usuario usuario = null;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId(rs.getInt("id_usuario"));
                    usuario.setPnome(rs.getString("pnome"));
                    usuario.setSnome(rs.getString("snome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha"));
                }
            }
        }
        return usuario;
    }
}
