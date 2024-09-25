CREATE DATABASE universidade;

USE universidade;

CREATE TABLE alunos(
    matricula INT NOT NULL PRIMARY KEY,
	cpf VARCHAR(11) NOT NULL UNIQUE,
    nome VARCHAR(128) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefone VARCHAR(11) NOT NULL,
    data_nascimento DATE NOT NULL
);

CREATE TABLE cursos(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    codigo_mec INT NOT NULL
);

CREATE TABLE disciplinas(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    frequencia DOUBLE DEFAULT 0,
    nota DOUBLE DEFAULT 0,
    professor VARCHAR(100) NOT NULL
);

CREATE TABLE turmas(
	codigo INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sala INT NOT NULL,
    id_disciplina INT,
    id_curso INT,
    matricula_aluno INT,
	
    FOREIGN KEY (id_disciplina) REFERENCES disciplinas(id),
    FOREIGN KEY (id_curso) REFERENCES cursos(id),
    FOREIGN KEY (matricula_aluno) REFERENCES  alunos(matricula)
);