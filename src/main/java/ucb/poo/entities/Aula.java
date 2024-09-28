package ucb.poo.entities;

public class Aula {
    private int numero_aula;
    private int matricula_aluno;
    private int codigo_turma;

    public Aula() {}

    public int getNumero_aula() {
        return numero_aula;
    }

    public void setNumero_aula(int numero_aula) {
        this.numero_aula = numero_aula;
    }

    public int getMatricula_aluno() {
        return matricula_aluno;
    }

    public void setMatricula_aluno(int matricula_aluno) {
        this.matricula_aluno = matricula_aluno;
    }

    public int getCodigo_turma() {
        return codigo_turma;
    }

    public void setCodigo_turma(int codigo_turma) {
        this.codigo_turma = codigo_turma;
    }
}
