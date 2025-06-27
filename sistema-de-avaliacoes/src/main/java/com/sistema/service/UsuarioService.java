package com.sistema.service;

import com.sistema.model.Usuario;

public interface UsuarioService {
    /**
     * Verifica se um e-mail já está cadastrado no sistema.
     * @param email O e-mail a ser verificado.
     * @return true se o e-mail já existe, false caso contrário.
     */
    boolean emailJaExiste(String email);

    /**
     * Busca um usuário pelo seu e-mail.
     * @param email O e-mail do usuário.
     * @return O usuário encontrado ou null.
     */
    Usuario buscarPorEmail(String email);
}