package com.sistema.dto;

public class AlunoRelatorioDTO {
    private int idUsuario;
    private String nomeCompleto;
    private Double notaFinal;

    public AlunoRelatorioDTO() {}

    public AlunoRelatorioDTO(int idUsuario, String nomeCompleto, Double notaFinal) {
        this.idUsuario = idUsuario;
        this.nomeCompleto = nomeCompleto;
        this.notaFinal = notaFinal;
    }

    // Getters e Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public Double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(Double notaFinal) {
        this.notaFinal = notaFinal;
    }
}
