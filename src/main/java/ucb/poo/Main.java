package ucb.poo;

import ucb.poo.database.DB;
import ucb.poo.entities.Aluno;
import ucb.poo.entities.Aula;
import ucb.poo.entities.Matricula;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while (option != 8) {
            System.out.println("############### UNIVERSIDADE ###############");
            System.out.println("1. Cadastrar aluno");
            System.out.println("2. Cadastrar aluno em turma");
            System.out.println("3. Atualizar frequência");
            System.out.println("4. Atualizar nota");
            System.out.println("5. Deletar aluno");
            System.out.println("6. Buscar aluno");
            System.out.println("7. Listar todos os alunos");
            System.out.println("8. Sair");
            System.out.print("Escolha uma opção: ");

            option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println();
                    System.out.println("############### ADICIONAR ALUNO ###############");
                    adicionarAluno();
                    break;
                case 2:
                    System.out.println();
                    adicionarAlunoNaTurma();
                    break;
                case 3:
                    System.out.println();
                    System.out.println("############### ALTERAR FREQUÊNCIA ###############");
                    alterarFrequencia();
                    break;
                case 4:
                    System.out.println();
                    System.out.println("############### ALTERAR NOTA ###############");
                    alterarNota();
                    break;
                case 5:
                    System.out.println();
                    System.out.println("############### DELETAR ALUNO ###############");
                    System.out.println("Insira o CPF do aluno");
                    String cpf = scanner.next();
                    deletarAluno(cpf);
                    break;
                case 6:
                    System.out.println();
                    System.out.println("############### BUSCAR ALUNO ###############");
                    System.out.println("Insira o CPF do aluno");
                    String cpfA = scanner.next();
                    buscarAluno(cpfA);
                    break;
                case 7:
                    System.out.println();
                    System.out.println("############### TODOS OS ALUNOS ###############");
                    listarAlunos();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            System.out.println();
        }

        scanner.close();
    }

    public static List<Aluno> listarAlunos() {
        Connection conexao;
        Statement comando;
        ResultSet resultado;

        List<Aluno> alunos = new ArrayList<>();

        try {
            conexao = DB.conectar();
            comando = conexao.createStatement();

            resultado = comando.executeQuery("SELECT * FROM universidade.alunos");

            while (resultado.next()) {
                Aluno aluno = new Aluno();
                aluno.setCpf(resultado.getString("cpf"));
                aluno.setNome(resultado.getString("nome"));
                aluno.setEmail(resultado.getString("email"));
                aluno.setTelefone(resultado.getString("telefone"));
                alunos.add(aluno);
            }

            for (Aluno aluno : alunos) {
                System.out.println("Nome: " + aluno.getNome());
                System.out.println("CPF: " + aluno.getCpf());
                System.out.println("E-mail: " + aluno.getEmail());
                System.out.println("Telefone: " + aluno.getTelefone());
                System.out.println();
            }

            return alunos;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alunos;
    }

    public static void adicionarAluno() {
        Connection conexao;

        PreparedStatement statement;
        PreparedStatement statement_matricula;
        Scanner sc = new Scanner(System.in);

        Aluno novoAluno = new Aluno();

        System.out.println("######### INFORMAÇÕES PESSOAIS #########");

        System.out.println("Insira o nome do aluno");
        novoAluno.setNome(sc.nextLine());

        System.out.println("Insira o CPF do aluno");
        novoAluno.setCpf(sc.nextLine());

        System.out.println("Insira o e-mail do aluno");
        novoAluno.setEmail(sc.nextLine());

        System.out.println("Insira o telefone do aluno");
        novoAluno.setTelefone(sc.nextLine());

        System.out.println("Insira a data de nascimento do aluno (dd/mm/aaaa");
        String dataString = sc.nextLine();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        try {
            java.util.Date utilDate = formatter.parse(dataString);

            LocalDateTime localDateTime = utilDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            novoAluno.setData_nascimento(localDateTime);
        } catch (ParseException e) {
            System.out.println("Formato de data inválido!");
        }

        System.out.println("######### INFORMAÇÕES ACADÊMICAS #########");

        Matricula matricula = new Matricula();

        System.out.print("Insira o código do curso: ");
        matricula.setCodigo_curso(sc.nextInt());
        matricula.setCpf_aluno(novoAluno.getCpf());
        matricula.setCodigo_turma(1);

        try {
            conexao = DB.conectar();
            conexao.createStatement();

            statement = conexao.prepareStatement(
                    "INSERT INTO universidade.alunos(cpf, nome, email, telefone, data_nascimento) VALUES (?, ?, ?, ?, ?)"
            );

            statement.setString(1, novoAluno.getCpf());
            statement.setString(2, novoAluno.getNome());
            statement.setString(3, novoAluno.getEmail());
            statement.setString(4, novoAluno.getTelefone());

            if (novoAluno.getData_nascimento() != null) {
                statement.setDate(5, Date.valueOf(novoAluno.getData_nascimento().toLocalDate()));
            } else {
                statement.setNull(5, Types.DATE);
            }

            statement.executeUpdate();

            statement_matricula = conexao.prepareStatement(
                    "INSERT INTO universidade.matriculas(cpf_aluno, codigo_curso) VALUES (?, ?)"
            );

            statement_matricula.setString(1, novoAluno.getCpf());
            statement_matricula.setInt(2, matricula.getCodigo_curso());

            statement_matricula.executeUpdate();

            System.out.println("Aluno adicionado com sucesso!");
        } catch (SQLException erro) {
            erro.printStackTrace();
        }
    }

    public static Optional<Aluno> buscarAluno(String cpf) {
        Connection conexao;
        ResultSet resultado, resultado2;
        Statement comando, comando2;
        Aluno aluno = null;
        Matricula matricula = null;

        try {
            conexao = DB.conectar();
            comando = conexao.createStatement();
            comando2 = conexao.createStatement();

            resultado = comando.executeQuery(
                    "SELECT nome, " +
                            "cpf, " +
                            "email, " +
                            "telefone " +
                            "FROM universidade.alunos WHERE universidade.alunos.cpf = " + cpf
            );

            resultado2 = comando2.executeQuery(
                    "SELECT codigo, frequencia, nota, codigo_curso FROM universidade.matriculas"
            );

            while (resultado.next()) {
                aluno = new Aluno();
                aluno.setNome(resultado.getString("nome"));
                aluno.setCpf(resultado.getString("cpf"));
                aluno.setEmail(resultado.getString("email"));
                aluno.setTelefone(resultado.getString("telefone"));
            }

            while (resultado2.next()) {
                matricula = new Matricula();
                matricula.setCodigo(resultado2.getInt("codigo"));
                matricula.setCodigo_curso(resultado2.getInt("codigo_curso"));
                matricula.setNota(resultado2.getInt("nota"));
                matricula.setFrequencia(resultado2.getInt("frequencia"));
            }

            if (aluno == null) {
                System.out.println("Aluno não encontrado");
                return Optional.empty();
            }

            if (matricula == null) {
                System.out.println("Matricula não encontrada");
                return Optional.empty();
            }

            System.out.println("Aluno encontrado");
            System.out.println();
            System.out.println("Matricula: " + matricula.getCodigo());
            System.out.println("Nome: " + aluno.getNome());
            System.out.println("E-mail: " + aluno.getEmail());
            System.out.println("Telefone: " + aluno.getTelefone());
            System.out.println("Nota: " + matricula.getNota());
            System.out.println("Frequencia: " + matricula.getFrequencia());
            System.out.println("Codigo curso: " + matricula.getCodigo_curso());

            return Optional.of(aluno);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static void deletarAluno(String cpf) {
        Connection conexao;
        PreparedStatement statement;

        try {
            conexao = DB.conectar();
            conexao.createStatement();

            statement = conexao.prepareStatement("DELETE FROM universidade.alunos WHERE alunos.cpf = ?");

            statement.setString(1, cpf);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void alterarFrequencia() {
        Connection conexao;
        Statement comando;
        PreparedStatement statement;
        Scanner sc = new Scanner(System.in);

        try {
            conexao = DB.conectar();
            conexao.createStatement();

            System.out.print("Insira a matrícula do aluno: ");
            int matricula = sc.nextInt();

            System.out.print("Insira a frequência do aluno: ");
            double frequencia = sc.nextDouble();

            if (frequencia <= 0) {
                System.err.println("Insira uma frequência acima de 0.");
            }

            statement = conexao.prepareStatement(
                    "UPDATE universidade.matriculas SET frequencia = ? WHERE matriculas.codigo = ?"
            );

            statement.setDouble(1, frequencia);
            statement.setInt(2, matricula);

            statement.executeUpdate();
            System.out.println("Frequência atualizada com sucesso!");
        } catch (SQLException erro) {
            erro.printStackTrace();
        }
    }

    public static void alterarNota() {
        Connection conexao;
        Statement comando;
        PreparedStatement statement;
        Scanner sc = new Scanner(System.in);

        try {
            conexao = DB.conectar();
            conexao.createStatement();

            System.out.print("Insira a matrícula do aluno: ");
            int matricula = sc.nextInt();

            System.out.print("Insira a nota do aluno: ");
            double nota = sc.nextDouble();

            if (nota <= 0) {
                System.err.println("Insira uma nota acima de 0.");
            }

            statement = conexao.prepareStatement(
                    "UPDATE universidade.matriculas SET nota = ? WHERE matriculas.codigo = ?"
            );

            statement.setDouble(1, nota);
            statement.setInt(2, matricula);

            statement.executeUpdate();
            System.out.println("Nota atualizada com sucesso!");
        } catch (SQLException erro) {
            erro.printStackTrace();
        }
    }

    public static void adicionarAlunoNaTurma() {
        Connection conexao;
        Statement comando;
        PreparedStatement statement;
        Scanner sc = new Scanner(System.in);

        Aula aula = new Aula();

        System.out.println("######## ADICIONAR ALUNO EM TURMA ########");
        System.out.print("Insira a matrícula do aluno: ");
        aula.setMatricula_aluno(sc.nextInt());
        System.out.print("Insira o código da turma do aluno: ");
        aula.setCodigo_turma(sc.nextInt());

        try {
            conexao = DB.conectar();
            conexao.createStatement();

            statement = conexao.prepareStatement(
                    "INSERT INTO universidade.aulas(matricula_aluno, codigo_turma) VALUES (?, ?)"
            );

            statement.setInt(1, aula.getMatricula_aluno());
            statement.setInt(2, aula.getCodigo_turma());

            statement.executeUpdate();

            System.out.println("Aluno adicionado a turma " + aula.getCodigo_turma() + " com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}