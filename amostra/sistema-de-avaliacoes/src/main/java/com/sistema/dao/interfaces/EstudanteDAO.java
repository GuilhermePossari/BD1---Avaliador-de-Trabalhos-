package com.sistema.dao.interfaces;

import com.sistema.model.Estudante;
import com.sistema.model.Disciplina;
import java.sql.SQLException;
import java.util.List;

public interface EstudanteDAO {
    void create(Estudante estudante) throws SQLException;
    Estudante read(int id) throws SQLException;
    void update(Estudante estudante) throws SQLException;
    void delete(int id) throws SQLException;
    List<Estudante> all() throws SQLException;
    void matricular(int idUsuario, int idDisciplina) throws SQLException;
    List<Disciplina> listarDisciplinasPorEstudante(int idEstudante) throws SQLException;
}
