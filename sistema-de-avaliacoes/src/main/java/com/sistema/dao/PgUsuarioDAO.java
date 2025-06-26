package com.sistema.dao;

import com.sistema.dao.interfaces.UsuarioDAO;
import com.sistema.model.Usuario;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação do UsuarioDAO para PostgreSQL.
 * Esta classe é gerenciada pelo Spring (@Repository) e recebe a conexão
 * do banco de dados através de injeção de dependência.
 */
@Repository // Anota a classe como um componente de acesso a dados do Spring
public class PgUsuarioDAO implements UsuarioDAO {

    private final Connection connection;

    /**
     * Construtor que recebe o DataSource gerenciado pelo Spring.
     * A injeção de dependência (@Autowired) fornece a conexão configurada
     * no arquivo application.properties.
     *
     * @param dataSource O pool de conexões configurado pelo Spring.
     */
    // @Autowired
    public PgUsuarioDAO(DataSource dataSource) {
        try {
            // Pega uma conexão do pool de conexões do Spring
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            // Se não conseguir conectar, lança uma exceção em tempo de execução
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    /**
     * Cria um novo usuário no banco de dados.
     *
     * @param usuario O objeto Usuario a ser salvo.
     */
    @Override
    public void create(Usuario usuario) {
        // SQL para inserir um novo usuário
        String sql = "INSERT INTO Usuario (id_usuario, pnome, snome, email, senha) VALUES (?, ?, ?, ?, ?)";

        // 'try-with-resources' garante que o PreparedStatement será fechado automaticamente
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, usuario.getId());
            statement.setString(2, usuario.getPnome());
            statement.setString(3, usuario.getSnome());
            statement.setString(4, usuario.getEmail());

            // ATENÇÃO: Armazenar senhas em texto plano é uma falha grave de segurança.
            // O ideal é armazenar um hash da senha (ex: usando BCrypt).
            statement.setString(5, usuario.getSenha());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuário no banco de dados.", e);
        }
    }

    /**
     * Busca um usuário pelo seu ID.
     *
     * @param id O ID do usuário a ser buscado.
     * @return Um objeto Usuario se encontrado, ou null caso contrário.
     */
    @Override
    public Usuario read(int id) {
        String sql = "SELECT * FROM Usuario WHERE id_usuario = ?";
        Usuario usuario = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    usuario = new Usuario();
                    usuario.setId(resultSet.getInt("id_usuario"));
                    usuario.setPnome(resultSet.getString("pnome"));
                    usuario.setSnome(resultSet.getString("snome"));
                    usuario.setEmail(resultSet.getString("email"));
                    usuario.setSenha(resultSet.getString("senha"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por ID.", e);
        }
        return usuario;
    }

    /**
     * Atualiza os dados de um usuário existente.
     *
     * @param usuario O objeto Usuario com os dados atualizados.
     */
    @Override
    public void update(Usuario usuario) {
        String sql = "UPDATE Usuario SET pnome = ?, snome = ?, email = ?, senha = ? WHERE id_usuario = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, usuario.getPnome());
            statement.setString(2, usuario.getSnome());
            statement.setString(3, usuario.getEmail());
            statement.setString(4, usuario.getSenha());
            statement.setInt(5, usuario.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário.", e);
        }
    }

    /**
     * Deleta um usuário do banco de dados.
     *
     * @param id O ID do usuário a ser deletado.
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Usuario WHERE id_usuario = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário.", e);
        }
    }

    /**
     * Retorna uma lista com todos os usuários cadastrados.
     *
     * @return Uma lista de objetos Usuario.
     */
    @Override
    public List<Usuario> all() {
        String sql = "SELECT * FROM Usuario";
        List<Usuario> usuarios = new ArrayList<>();

        // Statement simples, pois não há parâmetros
        try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getInt("id_usuario"));
                usuario.setPnome(resultSet.getString("pnome"));
                usuario.setSnome(resultSet.getString("snome"));
                usuario.setEmail(resultSet.getString("email"));
                usuario.setSenha(resultSet.getString("senha"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os usuários.", e);
        }
        return usuarios;
    }
}