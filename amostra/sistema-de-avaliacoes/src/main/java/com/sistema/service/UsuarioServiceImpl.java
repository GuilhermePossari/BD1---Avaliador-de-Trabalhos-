package com.sistema.service;

import com.sistema.dao.interfaces.EstudanteDAO;
import com.sistema.dao.interfaces.ProfessorDAO;
import com.sistema.dao.interfaces.UsuarioDAO;
import com.sistema.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDAO usuarioDAO;
    private final ProfessorDAO professorDAO;
    private final EstudanteDAO estudanteDAO;

    @Autowired
    public UsuarioServiceImpl(
            UsuarioDAO usuarioDAO,
            ProfessorDAO professorDAO,
            EstudanteDAO estudanteDAO
    ) {
        this.usuarioDAO = usuarioDAO;
        this.professorDAO = professorDAO;
        this.estudanteDAO = estudanteDAO;
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

    @Override
    public String obterPapelDoUsuario(int idUsuario) {
        try {
            if (professorDAO.read(idUsuario) != null) {
                return "professor";
            } else if (estudanteDAO.read(idUsuario) != null) {
                return "estudante";
            } else {
                return "desconhecido";
            }
        } catch (Exception e) {
            return "desconhecido";
        }
    }
}
