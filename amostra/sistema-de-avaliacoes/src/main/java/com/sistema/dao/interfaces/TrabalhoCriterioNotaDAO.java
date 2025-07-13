package com.sistema.dao.interfaces;

import com.sistema.dto.TrabalhoCriterioNotaDTO;
import java.sql.SQLException;
import java.util.List;

public interface TrabalhoCriterioNotaDAO {
    void create(int pertAvalCod, int idUsuario, TrabalhoCriterioNotaDTO notaCriterio) throws SQLException;
    void update(int pertAvalCod, int idUsuario, TrabalhoCriterioNotaDTO notaCriterio) throws SQLException;
    void delete(int pertAvalCod, int idUsuario, int idCriterio) throws SQLException;
    List<TrabalhoCriterioNotaDTO> readAllByTrabalho(int pertAvalCod, int idUsuario) throws SQLException;
}
