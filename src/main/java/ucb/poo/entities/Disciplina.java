package ucb.poo.entities;

public class Disciplina {
    private int id;
    private String nome;
    private double frequencia;
    private double nota;
    private String professor;

    public Disciplina(int id, String nome, double frequencia, double nota, String professor) {
        this.id = id;
        this.nome = nome;
        this.frequencia = frequencia;
        this.nota = nota;
        this.professor = professor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }
}
