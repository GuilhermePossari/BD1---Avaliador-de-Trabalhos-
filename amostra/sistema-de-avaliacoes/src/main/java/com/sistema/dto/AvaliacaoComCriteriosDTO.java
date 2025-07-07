package com.sistema.dto;

import java.util.Date;
import java.util.List;

public class AvaliacaoComCriteriosDTO {
    private int idDisciplina;
    private int codAvaliacao;
    private int criaAvalCod;
    private Date dataPublicacao;
    private Date prazo;
    private List<CriterioDTO> criterios;

    public AvaliacaoComCriteriosDTO() {}

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

    public int getCriaAvalCod() {
        return criaAvalCod;
    }
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

    public List<CriterioDTO> getCriterios() {
        return criterios;
    }
    public void setCriterios(List<CriterioDTO> criterios) {
        this.criterios = criterios;
    }
}
