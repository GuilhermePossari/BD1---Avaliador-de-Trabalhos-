package com.sistema.dao.interfaces;

import com.sistema.model.EstudanteDisciplina;
import java.sql.SQLException;
import java.util.List;

public interface EstudanteDisciplinaDAO {
    void create(EstudanteDisciplina ed) throws SQLException;
    void delete(int idUsuario, int idDisciplina) throws SQLException;
    List<EstudanteDisciplina> findAllByEstudante(int idUsuario);
    List<EstudanteDisciplina> findAllByDisciplina(int idDisciplina);
}
