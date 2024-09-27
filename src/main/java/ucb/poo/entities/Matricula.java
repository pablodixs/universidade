package ucb.poo.entities;

public class Matricula {
    private int codigo;
    private String cpf_aluno;
    private int codigo_turma;
    private int codigo_curso;
    private double frequencia;
    private double nota;

    public Matricula() {}

    public Matricula(int codigo, String cpr_aluno, int codigo_turma, int codigo_aluno, double frequencia, double nota) {
        this.codigo = codigo;
        this.cpf_aluno = cpr_aluno;
        this.codigo_turma = codigo_turma;
        this.codigo_curso = codigo_aluno;
        this.frequencia = frequencia;
        this.nota = nota;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getCpf_aluno() {
        return cpf_aluno;
    }

    public void setCpf_aluno(String cpf_aluno) {
        this.cpf_aluno = cpf_aluno;
    }

    public int getCodigo_turma() {
        return codigo_turma;
    }

    public void setCodigo_turma(int codigo_turma) {
        this.codigo_turma = codigo_turma;
    }

    public int getCodigo_curso() {
        return codigo_curso;
    }

    public void setCodigo_curso(int codigo_curso) {
        this.codigo_curso = codigo_curso;
    }

    public double getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(double frequencia) {
        this.frequencia = frequencia;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
}
