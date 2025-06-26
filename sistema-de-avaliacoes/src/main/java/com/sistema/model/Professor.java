package com.sistema.model;

public class Professor extends Usuario {

    /**
     * Atributo específico do Professor.
     * Conforme o script SQL, representa 'cria_aval_cod'.
     */
    private int criaAvalCod;

    public Professor() {
        super();
    }

    public Professor(int id, String pnome, String snome, String email, String senha, int criaAvalCod) {
        super(id, pnome, snome, email, senha);
        this.criaAvalCod = criaAvalCod;
    }

    /**
     * Retorna o código de avaliação associado à criação pelo professor.
     * @return int
     */
    public int getCriaAvalCod() {
        return criaAvalCod;
    }

    public void setCriaAvalCod(int criaAvalCod) {
        this.criaAvalCod = criaAvalCod;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "id=" + getId() +
                ", nome='" + getPnome() + " " + getSnome() + '\'' +
                ", criaAvalCod=" + criaAvalCod +
                '}';
    }
}