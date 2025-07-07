package com.sistema.controller;

import com.sistema.dto.LoginRequest;
import com.sistema.dto.LoginResponse;
import com.sistema.model.Usuario;
import com.sistema.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/email")
    public ResponseEntity<Usuario> buscarPorEmail(@RequestParam String email) {
        Usuario usuario = usuarioService.buscarPorEmail(email);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/existe")
    public ResponseEntity<Boolean> emailJaExiste(@RequestParam String email) {
        boolean existe = usuarioService.emailJaExiste(email);
        return ResponseEntity.ok(existe);
    }

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    Usuario usuario = usuarioService.buscarPorEmail(loginRequest.getEmail());

    if (usuario == null || !usuario.getSenha().equals(loginRequest.getSenha())) {
        return ResponseEntity.status(401).body("Email ou senha inválidos");
    }

    // Descobrir o papel do usuário (professor ou estudante)
    String role = usuarioService.obterPapelDoUsuario(usuario.getId());

    LoginResponse response = new LoginResponse(usuario.getId(), usuario.getPnome(), usuario.getSnome(), usuario.getEmail(), role);
    return ResponseEntity.ok(response);
}

}
    