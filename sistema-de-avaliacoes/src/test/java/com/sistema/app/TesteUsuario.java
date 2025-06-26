package com.sistema.app;

import com.sistema.dao.UsuarioDAO;
import com.sistema.model.Usuario;

public class TesteUsuario {
    public static void main(String[] args) {
        Usuario usuario = new Usuario(1, "João", "Silva", "joao@email.com", "123456", "estudante");

        UsuarioDAO dao = new UsuarioDAO();
        dao.inserir(usuario);
    }
}
