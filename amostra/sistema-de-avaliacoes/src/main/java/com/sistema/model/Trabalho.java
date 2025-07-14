package com.sistema.model;

import com.sistema.dto.TrabalhoCriterioNotaDTO;

import java.time.LocalDate;
import java.util.List;

public class Trabalho {
    private int idUsuario;
    private int idAvaliacao;
    private int idDisciplina;
    private LocalDate dataEnvio;
    private String arquivoUrl;
    private String status;
    private Double nota;
    private List<TrabalhoCriterioNotaDTO> notasPorCriterio;

    public Trabalho() {}

    public Trabalho(int idUsuario, int idAvaliacao, int idDisciplina, LocalDate dataEnvio, String arquivoUrl, String status, Double nota, List<TrabalhoCriterioNotaDTO> notasPorCriterio) {
        this.idUsuario = idUsuario;
        this.idAvaliacao = idAvaliacao;
        this.idDisciplina = idDisciplina;
        this.dataEnvio = dataEnvio;
        this.arquivoUrl = arquivoUrl;
        this.status = status;
        this.nota = nota;
        this.notasPorCriterio = notasPorCriterio;
    }

    public int getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(int idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(int idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
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

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public List<TrabalhoCriterioNotaDTO> getNotasPorCriterio() {
        return notasPorCriterio;
    }

    public void setNotasPorCriterio(List<TrabalhoCriterioNotaDTO> notasPorCriterio) {
        this.notasPorCriterio = notasPorCriterio;
    }
}