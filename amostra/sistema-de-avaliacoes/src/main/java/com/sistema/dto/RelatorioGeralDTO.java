package com.sistema.dto;

import java.util.List;
import java.util.Map;

public class RelatorioGeralDTO {
    private int idDisciplina;
    private String nomeDisciplina;
    private List<AlunoRelatorioDTO> alunosAvaliados;
    private Map<Integer, CriterioEstatisticaDTO> estatisticasPorCriterio;
    private Double notaMediaFinalTurma;
    private List<Double> distribuicaoNotasFinais;

    public RelatorioGeralDTO() {}

    // Getters e Setters
    public int getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(int idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public List<AlunoRelatorioDTO> getAlunosAvaliados() {
        return alunosAvaliados;
    }

    public void setAlunosAvaliados(List<AlunoRelatorioDTO> alunosAvaliados) {
        this.alunosAvaliados = alunosAvaliados;
    }

    public Map<Integer, CriterioEstatisticaDTO> getEstatisticasPorCriterio() {
        return estatisticasPorCriterio;
    }

    public void setEstatisticasPorCriterio(Map<Integer, CriterioEstatisticaDTO> estatisticasPorCriterio) {
        this.estatisticasPorCriterio = estatisticasPorCriterio;
    }

    public Double getNotaMediaFinalTurma() {
        return notaMediaFinalTurma;
    }

    public void setNotaMediaFinalTurma(Double notaMediaFinalTurma) {
        this.notaMediaFinalTurma = notaMediaFinalTurma;
    }

    public List<Double> getDistribuicaoNotasFinais() {
        return distribuicaoNotasFinais;
    }

    public void setDistribuicaoNotasFinais(List<Double> distribuicaoNotasFinais) {
        this.distribuicaoNotasFinais = distribuicaoNotasFinais;
    }
}
