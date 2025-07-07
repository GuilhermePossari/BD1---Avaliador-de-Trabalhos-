package com.sistema.service;

import com.sistema.model.Professor;
import java.util.List;

public interface ProfessorService {
    Professor criar(Professor professor);
    Professor buscarPorId(int id);
    List<Professor> listarTodos();
    void deletar(int id);
}