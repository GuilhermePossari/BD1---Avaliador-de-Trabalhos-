package com.sistema.dto;

public class CriterioEstatisticaDTO {
    private int idCriterio;
    private String nomeCriterio;
    private Double mediaNotas;
    private Double desvioPadrao;
    private int quantidadeAvaliacoes;

    public CriterioEstatisticaDTO() {}

    public CriterioEstatisticaDTO(int idCriterio, String nomeCriterio, Double mediaNotas, Double desvioPadrao, int quantidadeAvaliacoes) {
        this.idCriterio = idCriterio;
        this.nomeCriterio = nomeCriterio;
        this.mediaNotas = mediaNotas;
        this.desvioPadrao = desvioPadrao;
        this.quantidadeAvaliacoes = quantidadeAvaliacoes;
    }

    // Getters e Setters
    public int getIdCriterio() {
        return idCriterio;
    }

    public void setIdCriterio(int idCriterio) {
        this.idCriterio = idCriterio;
    }

    public String getNomeCriterio() {
        return nomeCriterio;
    }

    public void setNomeCriterio(String nomeCriterio) {
        this.nomeCriterio = nomeCriterio;
    }

    public Double getMediaNotas() {
        return mediaNotas;
    }

    public void setMediaNotas(Double mediaNotas) {
        this.mediaNotas = mediaNotas;
    }

    public Double getDesvioPadrao() {
        return desvioPadrao;
    }

    public void setDesvioPadrao(Double desvioPadrao) {
        this.desvioPadrao = desvioPadrao;
    }

    public int getQuantidadeAvaliacoes() {
        return quantidadeAvaliacoes;
    }

    public void setQuantidadeAvaliacoes(int quantidadeAvaliacoes) {
        this.quantidadeAvaliacoes = quantidadeAvaliacoes;
    }
}
