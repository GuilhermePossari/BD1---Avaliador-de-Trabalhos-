package com.sistema.controller;

import com.sistema.dao.interfaces.UsuarioDAO;
import com.sistema.dao.PgDAOFactory;
import com.sistema.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioDAO usuarioDAO;

    @Autowired
    public UsuarioController(PgDAOFactory daoFactory) {
        this.usuarioDAO = daoFactory.getUsuarioDAO();
    }

    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        usuarioDAO.inserir(usuario);
        return ResponseEntity.ok(usuario);
    }
}