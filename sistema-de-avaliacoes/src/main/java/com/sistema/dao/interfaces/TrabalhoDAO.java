package com.sistema.dao.interfaces;

import com.sistema.model.Trabalho;
import java.sql.SQLException;
import java.util.List;

public interface TrabalhoDAO {
    void create(Trabalho trabalho) throws SQLException;
    Trabalho read(int idUsuario, int codAvaliacao) throws SQLException;
    void update(Trabalho trabalho) throws SQLException;
    void delete(int idUsuario, int codAvaliacao) throws SQLException;
    List<Trabalho> allByDisciplina(int idDisciplina) throws SQLException;
    List<Trabalho> all() throws SQLException;
}
