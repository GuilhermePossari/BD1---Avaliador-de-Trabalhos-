package com.sistema.service;

import com.sistema.dao.interfaces.ProfessorDAO;
import com.sistema.dao.interfaces.UsuarioDAO;
import com.sistema.model.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    private final UsuarioDAO usuarioDAO;
    private final ProfessorDAO professorDAO;
    private final UsuarioService usuarioService;

    @Autowired
    public ProfessorServiceImpl(UsuarioDAO usuarioDAO, ProfessorDAO professorDAO, UsuarioService usuarioService) {
        this.usuarioDAO = usuarioDAO;
        this.professorDAO = professorDAO;
        this.usuarioService = usuarioService;
    }

    @Override
    @Transactional // Garante que ou tudo funciona, ou nada é salvo no banco
    public Professor criar(Professor professor) {
        if (usuarioService.emailJaExiste(professor.getEmail())) {
            throw new RuntimeException("Erro ao criar professor: E-mail já cadastrado.");
        }
        // lógica para hash da senha antes de salvar
        try {
            usuarioDAO.create(professor);
            professorDAO.create(professor);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao criar professor: " + e.getMessage(), e);
        }
        return professor;
    }

    @Override
    public Professor buscarPorId(int id) {
        try {
            return professorDAO.read(id);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao buscar professor por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Professor> listarTodos() {
        try {
            return professorDAO.all();
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao listar todos os professores: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void deletar(int id) {
        try {
            professorDAO.delete(id);
            // O delete no DAO já deve remover da tabela Usuario também (CASCADE)
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao deletar professor: " + e.getMessage(), e);
        }
    }
}