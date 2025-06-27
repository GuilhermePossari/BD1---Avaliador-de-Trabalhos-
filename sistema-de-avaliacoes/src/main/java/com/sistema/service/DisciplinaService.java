package com.sistema.service;

import com.sistema.model.Disciplina;
import java.util.List;

public interface DisciplinaService {
    Disciplina criar(Disciplina disciplina);
    Disciplina buscarPorId(int id);
    List<Disciplina> listarTodas();
    // Adicionar outros métodos conforme a necessidade
}