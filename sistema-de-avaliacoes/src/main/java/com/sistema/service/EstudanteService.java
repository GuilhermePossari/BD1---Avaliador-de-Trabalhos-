package com.sistema.service;

import com.sistema.model.Estudante;
import java.util.List;

public interface EstudanteService {
    Estudante criar(Estudante estudante);
    Estudante buscarPorId(int id);
    List<Estudante> listarTodos();
    void deletar(int id);
    void matricular(int idUsuario, int idDisciplina);
}
