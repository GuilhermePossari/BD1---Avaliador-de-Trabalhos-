package com.sistema.service;

import com.sistema.dao.interfaces.DisciplinaDAO;
import com.sistema.model.Disciplina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DisciplinaServiceImpl implements DisciplinaService {

    private final DisciplinaDAO disciplinaDAO;

    @Autowired
    public DisciplinaServiceImpl(DisciplinaDAO disciplinaDAO) {
        this.disciplinaDAO = disciplinaDAO;
    }

    @Override
    @Transactional
    public Disciplina criar(Disciplina disciplina) {
        // Adicionar validações
        try {
            disciplinaDAO.create(disciplina);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao criar disciplina", e);
        }
        return disciplina;
    }

    @Override
    public Disciplina buscarPorId(int id) {
        try {
            return disciplinaDAO.read(id);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao buscar disciplina por ID", e);
        }
    }

    @Override
    public List<Disciplina> listarTodas() {
        try {
            return disciplinaDAO.all();
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao listar todas as disciplinas", e);
        }
    }
}