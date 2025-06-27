package com.sistema.service;

import com.sistema.dao.interfaces.UsuarioDAO;
import com.sistema.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDAO usuarioDAO;

    @Autowired
    public UsuarioServiceImpl(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }
    
    @Override
    public boolean emailJaExiste(String email) {
        try {
            return usuarioDAO.buscarPorEmail(email) != null;
        } catch (java.sql.SQLException e) {
            
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        try {
            return usuarioDAO.buscarPorEmail(email);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}