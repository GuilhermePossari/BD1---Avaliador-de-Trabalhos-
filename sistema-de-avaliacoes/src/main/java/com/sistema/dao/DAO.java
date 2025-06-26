package com.sistema.dao;
import java.sql.SQLException;
import java.util.List;

/**
 * Interface genérica DAO que define operações básicas de persistência.
 * @param <T> Tipo da entidade manipulada (por exemplo, Usuario, Professor, etc.)
 * O <T> indica que é genérica, ou seja, serve para qualquer tio de entidade.
 */

public interface DAO<T> {

    /**
     * Cria (insere) um novo objeto no banco de dados.
     * @param t Objeto a ser inserido.
     * @throws SQLException Em caso de erro na inserção.
     */
    public void create(T t) throws SQLException;
    
    /**
     * Lê (busca) um objeto pelo ID.
     * @param id Identificador único do objeto.
     * @return Objeto encontrado, ou null se não existir.
     * @throws SQLException Em caso de erro na leitura.
     */
    public T read(Integer id) throws SQLException;

    /**
     * Atualiza os dados de um objeto existente.
     * @param t Objeto com os dados atualizados.
     * @throws SQLException Em caso de erro na atualização.
     */
    public void update(T t) throws SQLException;

    
    /**
     * Remove um objeto do banco pelo seu ID.
     * @param id Identificador do objeto a ser removido.
     * @throws SQLException Em caso de erro na remoção.
     */
    public void delete(Integer id) throws SQLException;

    /**
     * Lista todos os objetos da entidade.
     * @return Lista com todos os objetos.
     * @throws SQLException Em caso de erro na consulta.
     */
    public List<T> all() throws SQLException;  
}
