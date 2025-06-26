package com.sistema.dao;

import com.sistema.model.Usuario;
import com.sistema.util.Conexao;


import java.sql.*;

public class UsuarioDAO {

    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO usuario (pnome, snome, email, senha) VALUES (?, ?, ?, ?) RETURNING id_usuario";

        try (Connection conn = Conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getPnome());
            stmt.setString(2, usuario.getSnome());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getSenha());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int idGerado = rs.getInt("id_usuario");
                usuario.setId(idGerado);
                System.out.println("Usuário inserido com sucesso. ID: " + idGerado);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir usuário: " + e.getMessage());
        }

        }
    }
