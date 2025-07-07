package com.sistema.dto;

public class CriterioDTO {
    private String nome;
    private String descricao;
    private double peso; 

    public CriterioDTO() {}

    public CriterioDTO(String nome, String descricao, double peso) {
        this.nome = nome;
        this.descricao = descricao;
        this.peso = peso;
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

    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }
}
