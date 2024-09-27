CREATE DATABASE universidade;

USE universidade;

CREATE TABLE cursos
(
    codigo     INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome       VARCHAR(50) NOT NULL,
    codigo_mec INT         NOT NULL
);

CREATE TABLE disciplinas
(
    id         INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome       VARCHAR(50)  NOT NULL,
    professor  VARCHAR(100) NOT NULL
);

CREATE TABLE turmas
(
    codigo        INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sala          INT NOT NULL,
    id_disciplina INT,
    id_curso      INT,

    FOREIGN KEY (id_disciplina) REFERENCES disciplinas (id),
    FOREIGN KEY (id_curso) REFERENCES cursos (codigo)
);

CREATE TABLE alunos
(
    cpf             VARCHAR(11)  NOT NULL UNIQUE PRIMARY KEY,
    nome            VARCHAR(128) NOT NULL,
    email           VARCHAR(100) NOT NULL,
    telefone        VARCHAR(11)  NOT NULL,
    matricula       INT          NOT NULL,
    data_nascimento DATE
);

CREATE TABLE matriculas
(
    codigo       INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    cpf_aluno    VARCHAR(11)        NOT NULL UNIQUE,
    codigo_turma INT                NOT NULL,
    codigo_curso INT                NOT NULL,
    frequencia   DOUBLE DEFAULT 0,
    nota         DOUBLE DEFAULT 0,

    FOREIGN KEY (codigo_turma) REFERENCES turmas (codigo),
    FOREIGN KEY (codigo_curso) REFERENCES cursos (codigo)
);

ALTER TABLE alunos
    ADD CONSTRAINT fk_alunos_matriculas FOREIGN KEY (matricula) REFERENCES matriculas (codigo);

ALTER TABLE matriculas
    ADD CONSTRAINT fk_matriculas_alunos FOREIGN KEY (cpf_aluno) REFERENCES alunos (cpf);

##

INSERT INTO disciplinas(nome, professor)
VALUES ("Modelagem de Banco de Dados", "Samuel"),
       ("Redes de Computadores", "Beatriz"),
       ("Estrutura de Dados", "Adam"),
       ("Interação Humano-Computador", "Milton"),
       ("Banco de Dados", "Fabiano"),
       ("Matemática Discreta", "Mario"),
       ("Sistemas Computacionais em Nuvem", "Oscar"),
       ("Sistemas Computacionais", "Marcelo"),
       ("Programação Orientada a Objetos", "Samuel");

INSERT INTO cursos(nome, codigo_mec)
VALUES ("Engenharia de Software", 2993),
       ("Ciência da Computação", 2822);

INSERT INTO turmas(sala, id_disciplina, id_curso) VALUES ()