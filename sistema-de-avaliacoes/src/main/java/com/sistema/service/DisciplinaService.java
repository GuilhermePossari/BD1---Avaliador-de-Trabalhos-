package com.sistema.service;

import com.sistema.model.Disciplina;
import com.sistema.model.Estudante;

import java.util.List;

public interface DisciplinaService {
    Disciplina criar(Disciplina disciplina);
    Disciplina buscarPorId(int id);
    List<Disciplina> listarTodas();
    List<Estudante> listarAlunosMatriculados(int idDisciplina);

}