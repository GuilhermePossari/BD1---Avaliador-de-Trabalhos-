package com.sistema.service;

import com.sistema.model.Estudante;
import java.util.List;
import com.sistema.model.Disciplina;

public interface EstudanteService {
    Estudante criar(Estudante estudante);
    Estudante buscarPorId(int id);
    List<Estudante> listarTodos();
    void deletar(int id);
    void matricular(int idUsuario, int idDisciplina);
    List<Disciplina> listarDisciplinasPorEstudante(int idEstudante);
}
