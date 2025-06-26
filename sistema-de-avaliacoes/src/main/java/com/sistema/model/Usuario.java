package com.sistema.model;

public class Usuario {
    private int id;
    private String pnome;
    private String snome;
    private String email;
    private String senha;

    public Usuario(){

    }
    
    public Usuario(int id, String pnome, String snome, String email, String senha) {
        this.id = id;
        this.pnome = pnome;
        this.snome = snome;
        this.email = email;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getPnome() {
        return pnome;
    }

    public void setPnome(String pnome) {
        this.pnome = pnome;
    }

    public String getSnome() {
        return snome;
    }

    public void setSnome(String snome) {
        this.snome = snome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
}
