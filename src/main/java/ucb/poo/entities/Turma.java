package ucb.poo.entities;

public class Turma {
    private int codigo_turma;
    private int sala;
    private int id_disciplina;
    private int id_curso;
    private int matricula_aluno;

    public Turma() {}

    public Turma(int codigo_turma, int sala, int id_disciplina, int id_curso, int matricula_aluno) {
        this.codigo_turma = codigo_turma;
        this.sala = sala;
        this.id_disciplina = id_disciplina;
        this.id_curso = id_curso;
        this.matricula_aluno = matricula_aluno;
    }

    public int getCodigo_turma() {
        return codigo_turma;
    }

    public void setCodigo_turma(int codigo_turma) {
        this.codigo_turma = codigo_turma;
    }

    public int getSala() {
        return sala;
    }

    public void setSala(int sala) {
        this.sala = sala;
    }

    public int getId_disciplina() {
        return id_disciplina;
    }

    public void setId_disciplina(int id_disciplina) {
        this.id_disciplina = id_disciplina;
    }

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }

    public int getMatricula_aluno() {
        return matricula_aluno;
    }

    public void setMatricula_aluno(int matricula_aluno) {
        this.matricula_aluno = matricula_aluno;
    }
}
