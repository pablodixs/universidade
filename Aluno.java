public class Aluno {
    private int id;
    private String nome;
    private String email;
    private String matricula;
    private int cursoId;  // Chave estrangeira para Curso

    public Aluno() {}

    public Aluno(int id, String nome, String email, String matricula, int cursoId) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.matricula = matricula;
        this.cursoId = cursoId;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public int getCursoId() { return cursoId; }
    public void setCursoId(int cursoId) { this.cursoId = cursoId; }
}
