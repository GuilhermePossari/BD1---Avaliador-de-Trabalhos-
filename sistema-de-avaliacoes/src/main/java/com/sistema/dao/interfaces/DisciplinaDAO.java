package com.sistema.dao.interfaces;

import com.sistema.model.Disciplina;
import java.sql.SQLException;
import java.util.List;

public interface DisciplinaDAO {
    void create(Disciplina disciplina) throws SQLException;
    Disciplina read(int id) throws SQLException;
    void update(Disciplina disciplina) throws SQLException;
    void delete(int id) throws SQLException;
    List<Disciplina> all() throws SQLException;
}
