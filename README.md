# Avaliador de Trabalhos Acadêmicos

Sistema de avaliação de trabalhos acadêmicos, desenvolvido como projeto da disciplina de Banco de Dados I (BD1) - UEL.

## Sobre o Projeto

Este sistema tem como objetivo principal auxiliar professores no processo de avaliação de trabalhos acadêmicos. O foco é permitir o cadastro de informações relevantes, a realização de avaliações segundo critérios definidos e a geração de relatórios estatísticos detalhados sobre o desempenho dos avaliados.

**Usuários:** Apenas professores possuem acesso ao sistema para realizar todas as operações, alunos receberão esses relatórios individuais como feedback.

## Funcionalidades Principais

- Cadastro de entidades essenciais (ex: pessoas avaliadas, trabalhos, critérios de avaliação).
- Realização de avaliações de trabalhos com base em critérios personalizados.
- Edição, exclusão e consulta dos registros cadastrados.
- Geração de relatórios e visualizações estatísticas sobre o desempenho dos avaliados.

## Estrutura do Projeto

- `docs/`: Documentação técnica, requisitos e diagramas do sistema.
- `sql/`: Scripts SQL de criação do banco, inserção de dados e exemplos de consultas.
- `src/`: Código-fonte do backend e frontend (quando aplicável).
- `README.md`: Este arquivo de apresentação do projeto.

## Tecnologias Utilizadas

- **Backend:** Spring Boot
- **Banco de Dados:** PostgreSQL
- **Frontend:** React
- **Documentação:** Google Docs/Overleaf

## Como Executar

1. Clone este repositório:
    ```
    git clone https://github.com/GuilhermePossari/BD1---Avaliador-de-Trabalhos-/.git
    ```
2. Importe o script SQL de criação do banco disponível na pasta `sql/` para um banco PostgreSQL.
3. Insira dados de teste com os scripts fornecidos ou pela interface.
4. Siga as instruções do `src/` para executar a aplicação backend e frontend.

## Relatórios e Consultas

O sistema disponibiliza relatórios variados, incluindo:
- Média de avaliações por trabalho.
- Desempenho individual por avaliado.
- Exportação dos resultados.

## Colaboradores

- **Guilherme Possari**
- **Tomás Pagani Pires**

## Licença

Este projeto é acadêmico e não possui licença de uso comercial.

---


