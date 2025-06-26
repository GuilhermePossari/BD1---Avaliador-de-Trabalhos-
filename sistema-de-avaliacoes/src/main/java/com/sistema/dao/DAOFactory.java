package com.sistema.dao;

import com.sistema.util.Conexao;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class DAOFactory implements AutoCloseable {

    protected Connection connection;

    public static DAOFactory getInstance() throws IOException, SQLException, ClassNotFoundException {
        Connection connection = Conexao.getConnection(); // adaptado para seu projeto
        DAOFactory factory;

        // Você pode trocar isso por leitura de config se quiser
        factory = new PgDAOFactory(connection); 

        return factory;
    }

    public void beginTransaction() throws SQLException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new SQLException("Erro ao iniciar transação", e);
        }
    }

    public void commitTransaction() throws SQLException {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new SQLException("Erro ao fazer commit", e);
        }
    }

    public void rollbackTransaction() throws SQLException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new SQLException("Erro ao fazer rollback", e);
        }
    }

    public void endTransaction() throws SQLException {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new SQLException("Erro ao encerrar transação", e);
        }
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public abstract UsuarioDAO getUsuarioDAO();
    public abstract ProfessorDAO getProfessorDAO();
    public abstract EstudanteDAO getEstudanteDAO();
    // outros DAOs...

    @Override
    public void close() throws SQLException {
        closeConnection();
    }
}
