public class Curso {
    private int id;
    private String nomeCurso;
    private int duracao;

    public Curso() {}

    public Curso(int id, String nomeCurso, int duracao) {
        this.id = id;
        this.nomeCurso = nomeCurso;
        this.duracao = duracao;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNomeCurso() { return nomeCurso; }
    public void setNomeCurso(String nomeCurso) { this.nomeCurso = nomeCurso; }

    public int getDuracao() { return duracao; }
    public void setDuracao(int duracao) { this.duracao = duracao; }
}
