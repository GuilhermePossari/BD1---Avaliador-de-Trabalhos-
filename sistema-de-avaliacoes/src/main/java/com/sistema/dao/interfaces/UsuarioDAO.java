package com.sistema.dao.interfaces;

import com.sistema.model.Usuario;
import java.sql.SQLException;
import java.util.List;

public interface UsuarioDAO {
    void create(Usuario usuario) throws SQLException;
    Usuario read(int id) throws SQLException;
    void update(Usuario usuario) throws SQLException;
    void delete(int id) throws SQLException;
    List<Usuario> all() throws SQLException;
}
