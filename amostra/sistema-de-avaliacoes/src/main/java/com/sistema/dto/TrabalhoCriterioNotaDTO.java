package com.sistema.dto;

public class TrabalhoCriterioNotaDTO {
    private int idCriterio;
    private Double nota;
    private String comentario;

    public TrabalhoCriterioNotaDTO() {}

    public TrabalhoCriterioNotaDTO(int idCriterio, Double nota, String comentario) {
        this.idCriterio = idCriterio;
        this.nota = nota;
        this.comentario = comentario;
    }

    public int getIdCriterio() {
        return idCriterio;
    }

    public void setIdCriterio(int idCriterio) {
        this.idCriterio = idCriterio;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
