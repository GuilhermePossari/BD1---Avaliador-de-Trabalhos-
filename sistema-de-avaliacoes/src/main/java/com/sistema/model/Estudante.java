package com.sistema.model;

public class Estudante extends Usuario {
    public Estudante() {
        super();
    }

    public Estudante(int id, String pnome, String snome, String email, String senha) {
        super(id, pnome, snome, email, senha, "estudante");
    }
}