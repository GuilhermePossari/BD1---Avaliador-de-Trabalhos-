package com.sistema.dao;

import com.sistema.dao.interfaces.TrabalhoCriterioNotaDAO;
import com.sistema.dto.TrabalhoCriterioNotaDTO;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PgTrabalhoCriterioNotaDAO implements TrabalhoCriterioNotaDAO {

    private final Connection connection;

    public PgTrabalhoCriterioNotaDAO(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    @Override
    public void create(int pertAvalCod, int idUsuario, TrabalhoCriterioNotaDTO notaCriterio) throws SQLException {
        String sql = "INSERT INTO TrabalhoCriterioNota (pert_aval_cod, id_usuario, id_criterio, nota, comentario) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pertAvalCod);
            statement.setInt(2, idUsuario);
            statement.setInt(3, notaCriterio.getIdCriterio());
            if (notaCriterio.getNota() != null) {
                statement.setDouble(4, notaCriterio.getNota());
            } else {
                statement.setNull(4, java.sql.Types.DOUBLE);
            }
            statement.setString(5, notaCriterio.getComentario());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(int pertAvalCod, int idUsuario, TrabalhoCriterioNotaDTO notaCriterio) throws SQLException {
        String sql = "UPDATE TrabalhoCriterioNota SET nota = ?, comentario = ? WHERE pert_aval_cod = ? AND id_usuario = ? AND id_criterio = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            if (notaCriterio.getNota() != null) {
                statement.setDouble(1, notaCriterio.getNota());
            } else {
                statement.setNull(1, java.sql.Types.DOUBLE);
            }
            statement.setString(2, notaCriterio.getComentario());
            statement.setInt(3, pertAvalCod);
            statement.setInt(4, idUsuario);
            statement.setInt(5, notaCriterio.getIdCriterio());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int pertAvalCod, int idUsuario, int idCriterio) throws SQLException {
        String sql = "DELETE FROM TrabalhoCriterioNota WHERE pert_aval_cod = ? AND id_usuario = ? AND id_criterio = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pertAvalCod);
            statement.setInt(2, idUsuario);
            statement.setInt(3, idCriterio);
            statement.executeUpdate();
        }
    }

    @Override
    public List<TrabalhoCriterioNotaDTO> readAllByTrabalho(int pertAvalCod, int idUsuario) throws SQLException {
        List<TrabalhoCriterioNotaDTO> notas = new ArrayList<>();
        String sql = "SELECT id_criterio, nota, comentario FROM TrabalhoCriterioNota WHERE pert_aval_cod = ? AND id_usuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pertAvalCod);
            statement.setInt(2, idUsuario);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    TrabalhoCriterioNotaDTO notaCriterio = new TrabalhoCriterioNotaDTO();
                    notaCriterio.setIdCriterio(rs.getInt("id_criterio"));
                    notaCriterio.setNota(rs.getDouble("nota"));
                    if (rs.wasNull()) {
                        notaCriterio.setNota(null);
                    }
                    notaCriterio.setComentario(rs.getString("comentario"));
                    notas.add(notaCriterio);
                }
            }
        }
        return notas;
    }
}
