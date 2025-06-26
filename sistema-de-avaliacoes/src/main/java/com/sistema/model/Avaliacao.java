package com.sistema.model;

import java.util.Date;

public class Avaliacao {

    private int idDisciplina;
    private int codAvaliacao;
    private int criaAvalCod; // Atributo que estava faltando
    private Date dataPublicacao;
    private Date prazo;

    // --- GETTERS E SETTERS ---

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

    // Método GET que estava faltando
    public int getCriaAvalCod() {
        return criaAvalCod;
    }

    // Método SET que estava faltando
    public void setCriaAvalCod(int criaAvalCod) {
        this.criaAvalCod = criaAvalCod;
    }

    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public Date getPrazo() {
        return prazo;
    }

    public void setPrazo(Date prazo) {
        this.prazo = prazo;
    }
}