package com.sistema.dao.interfaces;

import com.sistema.model.Professor;
import java.sql.SQLException;
import java.util.List;

public interface ProfessorDAO {
    void create(Professor professor) throws SQLException;
    Professor read(int id) throws SQLException;
    void update(Professor professor) throws SQLException;
    void delete(int id) throws SQLException;
    List<Professor> all() throws SQLException;
}