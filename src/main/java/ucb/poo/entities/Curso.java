package ucb.poo.entities;

public class Curso {
    private int id;
    private String nome;
    private int codigo_mec;

    public Curso(int id, String nome, int codigo_mec) {
        this.id = id;
        this.nome = nome;
        this.codigo_mec = codigo_mec;
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

    public int getCodigo_mec() {
        return codigo_mec;
    }

    public void setCodigo_mec(int codigo_mec) {
        this.codigo_mec = codigo_mec;
    }
}
