package com.sistema.service;

import com.sistema.dao.interfaces.EstudanteDAO;
import com.sistema.model.Estudante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstudanteServiceImpl implements EstudanteService {

    private final EstudanteDAO estudanteDAO;

    @Autowired
    public EstudanteServiceImpl(EstudanteDAO estudanteDAO) {
        this.estudanteDAO = estudanteDAO;
    }

    @Override
    public void matricular(int idUsuario, int idDisciplina) {
        estudanteDAO.matricular(idUsuario, idDisciplina);
    }

    @Override
    @Transactional
    public Estudante criar(Estudante estudante) {
        try {
            estudanteDAO.create(estudante);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar estudante: " + e.getMessage(), e);
        }
        return estudante;
    }

    @Override
    public Estudante buscarPorId(int id) {
        try {
            return estudanteDAO.read(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar estudante por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Estudante> listarTodos() {
        try {
            return estudanteDAO.all();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar todos os estudantes: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void deletar(int id) {
        try {
            estudanteDAO.delete(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar estudante: " + e.getMessage(), e);
        }
    }
}
