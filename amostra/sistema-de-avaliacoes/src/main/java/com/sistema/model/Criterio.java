package com.sistema.model;

import java.math.BigDecimal;

public class Criterio {
    private int idDisciplina;
    private int codAvaliacao;
    private int idProfessor;
    private int idCriterio;
    private String nome;
    private String descricao;
    private BigDecimal peso;

    public Criterio() {}

    public Criterio(int idDisciplina, int codAvaliacao, int idProfessor, int idCriterio, String nome, String descricao, BigDecimal peso) {
        this.idDisciplina = idDisciplina;
        this.codAvaliacao = codAvaliacao;
        this.idProfessor = idProfessor;
        this.idCriterio = idCriterio;
        this.nome = nome;
        this.descricao = descricao;
        this.peso = peso;
    }

    public int getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(int idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public int getCodAvaliacao() {
        return codAvaliacao;
    }

    public void setCodAvaliacao(int codAvaliacao) {
        this.codAvaliacao = codAvaliacao;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }

    public int getIdCriterio() {
        return idCriterio;
    }

    public void setIdCriterio(int idCriterio) {
        this.idCriterio = idCriterio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }
}