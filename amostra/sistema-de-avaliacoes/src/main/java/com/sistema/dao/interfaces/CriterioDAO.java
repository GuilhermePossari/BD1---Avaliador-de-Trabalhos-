package com.sistema.dao.interfaces;

import com.sistema.model.Criterio;
import java.sql.SQLException;
import java.util.List;

public interface CriterioDAO {
    void create(Criterio criterio) throws SQLException;
    Criterio read(int idDisciplina, int codAvaliacao, int idProfessor, int idCriterio) throws SQLException;
    void update(Criterio criterio) throws SQLException;
    void delete(int idDisciplina, int codAvaliacao, int idProfessor, int idCriterio) throws SQLException;
    List<Criterio> allByAvaliacao(int idDisciplina, int codAvaliacao) throws SQLException;
    public List<Criterio> all() throws SQLException;
}
