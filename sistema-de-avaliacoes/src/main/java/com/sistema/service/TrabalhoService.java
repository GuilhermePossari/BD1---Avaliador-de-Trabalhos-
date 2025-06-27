package com.sistema.service;

import com.sistema.model.Trabalho;
import java.util.List;

public interface TrabalhoService {
    Trabalho enviar(Trabalho trabalho);
    Trabalho avaliar(int pertAvalCod, int idUsuario, String novoStatus);
    Trabalho buscarPorId(int pertAvalCod, int idUsuario);
    List<Trabalho> listarPorDisciplina(int idDisciplina);
    List<Trabalho> listarPorEstudante(int idUsuario);
}