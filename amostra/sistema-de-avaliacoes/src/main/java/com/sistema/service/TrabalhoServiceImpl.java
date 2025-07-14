package com.sistema.service;

import com.sistema.dao.interfaces.AvaliacaoDAO;
import com.sistema.dao.interfaces.TrabalhoDAO;
import com.sistema.dao.interfaces.TrabalhoCriterioNotaDAO;
import com.sistema.dao.interfaces.DisciplinaDAO;
import com.sistema.dao.interfaces.CriterioDAO;
import com.sistema.dao.interfaces.UsuarioDAO;
import com.sistema.model.Avaliacao;
import com.sistema.model.Trabalho;
import com.sistema.model.Criterio;
import com.sistema.model.Disciplina;
import com.sistema.model.Usuario;
import com.sistema.dto.TrabalhoCriterioNotaDTO;
import com.sistema.dto.RelatorioGeralDTO;
import com.sistema.dto.AlunoRelatorioDTO;
import com.sistema.dto.CriterioEstatisticaDTO;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class TrabalhoServiceImpl implements TrabalhoService {

    private final TrabalhoDAO trabalhoDAO;
    private final AvaliacaoDAO avaliacaoDAO;
    private final TrabalhoCriterioNotaDAO trabalhoCriterioNotaDAO;
    private final DisciplinaDAO disciplinaDAO;
    private final CriterioDAO criterioDAO;
    private final UsuarioDAO usuarioDAO;

    public TrabalhoServiceImpl(TrabalhoDAO trabalhoDAO, AvaliacaoDAO avaliacaoDAO, TrabalhoCriterioNotaDAO trabalhoCriterioNotaDAO, DisciplinaDAO disciplinaDAO, CriterioDAO criterioDAO, UsuarioDAO usuarioDAO) {
        this.trabalhoDAO = trabalhoDAO;
        this.avaliacaoDAO = avaliacaoDAO;
        this.trabalhoCriterioNotaDAO = trabalhoCriterioNotaDAO;
        this.disciplinaDAO = disciplinaDAO;
        this.criterioDAO = criterioDAO;
        this.usuarioDAO = usuarioDAO;
    }

    @Override
    @Transactional
    public Trabalho enviar(org.springframework.web.multipart.MultipartFile file, int idAvaliacao, int idUsuario, int idDisciplina, java.time.LocalDate dataEnvio) throws java.io.IOException {
        String uploadDir = System.getProperty("user.dir") + java.io.File.separator + "uploads" + java.io.File.separator;
        java.io.File dir = new java.io.File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = java.util.UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String filePath = uploadDir + fileName;

        try {
            file.transferTo(new java.io.File(filePath));
        } catch (java.io.IOException e) {
            throw new RuntimeException("Erro ao salvar o arquivo: " + e.getMessage(), e);
        }

        Trabalho trabalho = new Trabalho();
        trabalho.setIdAvaliacao(idAvaliacao);
        trabalho.setIdUsuario(idUsuario);
        trabalho.setIdDisciplina(idDisciplina);
        trabalho.setDataEnvio(dataEnvio);
        trabalho.setArquivoUrl(filePath);
        trabalho.setStatus("Não Avaliado");

        Avaliacao avaliacao;
        try {
            avaliacao = avaliacaoDAO.read(idAvaliacao);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao acessar o banco de dados para buscar a avaliação.", e);
        }
        if (avaliacao == null) {
            throw new RuntimeException("Avaliação não encontrada para o envio do trabalho.");
        }

        LocalDate prazo = new java.sql.Date(avaliacao.getPrazo().getTime()).toLocalDate();

        if (LocalDate.now().isAfter(prazo)) {
            throw new RuntimeException("Não é possível enviar o trabalho: o prazo já encerrou.");
        }

        try {
            trabalhoDAO.create(trabalho);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao acessar o banco de dados para criar o trabalho.", e);
        }
        return trabalho;
    }
    @Override
    public List<Trabalho> listarPorAvaliacao(int idAvaliacao) {
        try {
            return trabalhoDAO.listarPorAvaliacao(idAvaliacao);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar trabalhos da avaliação", e);
        }
    }

    @Override
    @Transactional
    public Trabalho avaliar(int idAvaliacao, int idUsuario, String novoStatus, Double nota, List<TrabalhoCriterioNotaDTO> notasPorCriterio) {
        Trabalho trabalho;
        try {
            trabalho = trabalhoDAO.read(idAvaliacao, idUsuario);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao acessar o banco de dados para buscar o trabalho.", e);
        }
        if (trabalho == null) {
            throw new RuntimeException("Trabalho não encontrado para avaliação.");
        }

        if (!Objects.equals(novoStatus, "Avaliado")) {
            throw new IllegalArgumentException("Status de avaliação inválido.");
        }

        trabalho.setStatus(novoStatus);
        trabalho.setNota(nota);

        try {
            trabalhoDAO.update(trabalho);

            if (notasPorCriterio != null) {
                for (TrabalhoCriterioNotaDTO notaCriterio : notasPorCriterio) {
                    try {
                        trabalhoCriterioNotaDAO.update(idAvaliacao, idUsuario, notaCriterio);
                    } catch (java.sql.SQLException e) {
                        trabalhoCriterioNotaDAO.create(idAvaliacao, idUsuario, notaCriterio);
                    }
                }
            }
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao acessar o banco de dados para atualizar o trabalho ou notas por critério.", e);
        }
        return trabalho;
    }

    @Override
    public Trabalho buscarPorId(int idAvaliacao, int idUsuario) {
        try {
            Trabalho trabalho = trabalhoDAO.read(idAvaliacao, idUsuario);
            if (trabalho != null) {
                List<TrabalhoCriterioNotaDTO> notas = trabalhoCriterioNotaDAO.readAllByTrabalho(idAvaliacao, idUsuario);
                trabalho.setNotasPorCriterio(notas);
            }
            return trabalho;
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao acessar o banco de dados para buscar o trabalho.", e);
        }
    }

    @Override
    public List<Trabalho> listarPorDisciplina(int idDisciplina) {
        try {
            return trabalhoDAO.allByDisciplina(idDisciplina);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao acessar o banco de dados para listar trabalhos por disciplina.", e);
        }
    }

    @Override
    public List<Trabalho> listarPorEstudante(int idUsuario) {
        try {
            return trabalhoDAO.allByEstudante(idUsuario);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao acessar o banco de dados para listar trabalhos por estudante.", e);
        }
    }

    @Override
    public Resource download(int idAvaliacao, int idUsuario) throws IOException {
        try {
            Trabalho trabalho = trabalhoDAO.read(idAvaliacao, idUsuario);
            if (trabalho == null || trabalho.getArquivoUrl() == null) {
                throw new RuntimeException("Trabalho ou URL do arquivo não encontrado.");
            }

            java.nio.file.Path filePath = java.nio.file.Paths.get(trabalho.getArquivoUrl()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Arquivo não encontrado ou não legível: " + trabalho.getArquivoUrl());
            }
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao acessar o banco de dados para buscar o trabalho.", e);
        }
    }

    @Override
    public RelatorioGeralDTO gerarRelatorioGeral(int idDisciplina) {
        RelatorioGeralDTO relatorio = new RelatorioGeralDTO();
        try {
            Disciplina disciplina = disciplinaDAO.read(idDisciplina);
            if (disciplina == null) {
                throw new RuntimeException("Disciplina não encontrada.");
            }
            relatorio.setIdDisciplina(disciplina.getId());
            relatorio.setNomeDisciplina(disciplina.getNome());

            List<Trabalho> trabalhosAvaliados = trabalhoDAO.allByDisciplina(idDisciplina).stream()
                    .filter(t -> Objects.equals(t.getStatus(), "Avaliado") && t.getNota() != null)
                    .collect(Collectors.toList());

            List<AlunoRelatorioDTO> alunosAvaliados = new ArrayList<>();
            for (Trabalho t : trabalhosAvaliados) {
                Usuario aluno = usuarioDAO.read(t.getIdUsuario());
                String nomeAluno = (aluno != null) ? aluno.getPnome() + " " + aluno.getSnome() : "Aluno não encontrado";
                alunosAvaliados.add(new AlunoRelatorioDTO(t.getIdUsuario(), nomeAluno, t.getNota()));
            }
            relatorio.setAlunosAvaliados(alunosAvaliados);

            Map<Integer, CriterioEstatisticaDTO> estatisticasPorCriterio = new HashMap<>();
            List<Criterio> criteriosDaDisciplina = criterioDAO.listarPorDisciplina(idDisciplina);

            for (Criterio criterio : criteriosDaDisciplina) {
                List<Double> notasDoCriterio = new ArrayList<>();
                for (Trabalho trabalho : trabalhosAvaliados) {
                    trabalho.getNotasPorCriterio().stream()
                            .filter(n -> n.getIdCriterio() == criterio.getIdCriterio() && n.getNota() != null)
                            .map(TrabalhoCriterioNotaDTO::getNota)
                            .forEach(notasDoCriterio::add);
                }

                if (!notasDoCriterio.isEmpty()) {
                    double media = notasDoCriterio.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
                    double desvioPadrao = calcularDesvioPadrao(notasDoCriterio, media);

                    CriterioEstatisticaDTO estatistica = new CriterioEstatisticaDTO();
                    estatistica.setIdCriterio(criterio.getIdCriterio());
                    estatistica.setNomeCriterio(criterio.getNome());
                    estatistica.setMediaNotas(media);
                    estatistica.setDesvioPadrao(desvioPadrao);
                    estatistica.setQuantidadeAvaliacoes(notasDoCriterio.size());
                    estatisticasPorCriterio.put(criterio.getIdCriterio(), estatistica);
                }
            }
            relatorio.setEstatisticasPorCriterio(estatisticasPorCriterio);

            double notaMediaFinalTurma = trabalhosAvaliados.stream()
                    .mapToDouble(Trabalho::getNota)
                    .average().orElse(0.0);
            relatorio.setNotaMediaFinalTurma(notaMediaFinalTurma);

            relatorio.setDistribuicaoNotasFinais(trabalhosAvaliados.stream()
                    .map(Trabalho::getNota)
                    .collect(Collectors.toList()));

        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao gerar relatório geral: " + e.getMessage(), e);
        }
        return relatorio;
    }

    private double calcularDesvioPadrao(List<Double> notas, double media) {
        if (notas == null || notas.isEmpty()) {
            return 0.0;
        }
        double sumOfSquares = notas.stream()
                .mapToDouble(nota -> Math.pow(nota - media, 2))
                .sum();
        return Math.sqrt(sumOfSquares / notas.size());
    }
}