package com.sistema.model;

public class EstudanteDisciplina {
    private int idUsuario;
    private int idDisciplina;

    public EstudanteDisciplina() {}

    public EstudanteDisciplina(int idUsuario, int idDisciplina) {
        this.idUsuario = idUsuario;
        this.idDisciplina = idDisciplina;
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
}
