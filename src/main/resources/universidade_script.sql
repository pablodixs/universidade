CREATE DATABASE universidade;

USE universidade;

CREATE TABLE cursos
(
    codigo     INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome       VARCHAR(50) NOT NULL,
    codigo_mec INT         NOT NULL UNIQUE
);

CREATE TABLE disciplinas
(
    id           INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome         VARCHAR(50)  NOT NULL,
    professor    VARCHAR(100) NOT NULL,
    codigo_curso INT,

    FOREIGN KEY (codigo_curso) REFERENCES cursos (codigo)
);

CREATE TABLE turmas
(
    codigo            INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sala              INT NOT NULL UNIQUE,
    codigo_disciplina INT,

    FOREIGN KEY (codigo_disciplina) REFERENCES disciplinas (id)
);

CREATE TABLE alunos
(
    cpf             VARCHAR(11)  NOT NULL UNIQUE PRIMARY KEY,
    nome            VARCHAR(128) NOT NULL,
    email           VARCHAR(100) NOT NULL,
    telefone        VARCHAR(11)  NOT NULL,
    data_nascimento DATE
);

CREATE TABLE matriculas
(
    codigo       INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    cpf_aluno    VARCHAR(11)        NOT NULL UNIQUE,
    codigo_curso INT                NOT NULL,
    frequencia   DOUBLE DEFAULT 0,
    nota         DOUBLE DEFAULT 0,

    FOREIGN KEY (codigo_curso) REFERENCES cursos (codigo),
    FOREIGN KEY (cpf_aluno) REFERENCES alunos (cpf)
);

CREATE TABLE aulas
(
    numero_aula     INT PRIMARY KEY AUTO_INCREMENT,
    matricula_aluno INT NOT NULL,
    codigo_turma    INT NOT NULL,

    FOREIGN KEY (matricula_aluno) REFERENCES matriculas (codigo),
    FOREIGN KEY (codigo_turma) REFERENCES turmas (codigo)
);

##

INSERT INTO cursos(nome, codigo_mec)
VALUES ('Engenharia de Software', 2993),
       ('Ciência da Computação', 2822);

INSERT INTO disciplinas(nome, professor, codigo_curso)
VALUES ('Modelagem de Banco de Dados', 'Samuel', 1),
       ('Redes de Computadores', 'Beatriz', 1),
       ('Estrutura de Dados', 'Adam', 1),
       ('Interação Humano-Computador', 'Milton', 2),
       ('Banco de Dados', 'Fabiano', 1),
       ('Matemática Discreta', 'Mario', 1),
       ('Sistemas Computacionais em Nuvem', 'Oscar', 1),
       ('Sistemas Computacionais', 'Marcelo', 1),
       ('Programação Orientada a Objetos', 'Samuel', 1);

INSERT INTO turmas(sala, codigo_disciplina)
VALUES (201, 1),
       (101, 2),
       (210, 3),
       (302, 4),
       (112, 5),
       (227, 6),
       (027, 7),
       (110, 8),
       (214, 9);