package com.sistema.model;

import java.time.LocalDate;

public class Trabalho {
    private int idUsuario;
    private int idDisciplina;
    private int codAvaliacao;
    private LocalDate dataEnvio;
    private String arquivoUrl;
    private String status;
    private int pertAvalCod;

    
    public Trabalho() {}
    
    public Trabalho(int idUsuario, int idDisciplina, int codAvaliacao, LocalDate dataEnvio, String arquivoUrl, String status) {
        this.idUsuario = idUsuario;
        this.idDisciplina = idDisciplina;
        this.codAvaliacao = codAvaliacao;
        this.dataEnvio = dataEnvio;
        this.arquivoUrl = arquivoUrl;
        this.status = status;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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

    public LocalDate getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDate dataEnvio) {
        this.dataEnvio = dataEnvio;
    }
    
    public String getArquivoUrl() {
        return arquivoUrl;
    }

    public void setArquivoUrl(String arquivoUrl) {
        this.arquivoUrl = arquivoUrl;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    public int getPertAvalCod() {
        return pertAvalCod;
    }
    
    public void setPertAvalCod(int pertAvalCod) {
        this.pertAvalCod = pertAvalCod;
    }
}
