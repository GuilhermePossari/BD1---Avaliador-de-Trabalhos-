package com.sistema.model;

public class Disciplina {

    private int id;
    private String nome;

    private int idProfessor;
    
    public Disciplina() {
    }

    public Disciplina(int id, String nome, int idProfessor) {
        this.id = id;
        this.nome = nome;
        this.idProfessor = idProfessor;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    
    public int getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }

    @Override
    public String toString() {
        return "Disciplina{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idProfessor=" + idProfessor +
                '}';
    }
}