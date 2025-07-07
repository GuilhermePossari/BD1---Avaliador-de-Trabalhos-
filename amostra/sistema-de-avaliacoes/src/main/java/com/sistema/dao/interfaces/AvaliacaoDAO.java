package com.sistema.dao.interfaces;

import com.sistema.model.Avaliacao;
import java.sql.SQLException;
import java.util.List;

public interface AvaliacaoDAO {
    void create(Avaliacao avaliacao) throws SQLException;
    Avaliacao read(int idDisciplina, int codAvaliacao) throws SQLException;
    void update(Avaliacao avaliacao) throws SQLException;
    void delete(int idDisciplina, int codAvaliacao) throws SQLException;
    List<Avaliacao> allByDisciplina(int idDisciplina) throws SQLException;
    List<Avaliacao> all() throws SQLException;
}
