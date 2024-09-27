package ucb.poo;

import ucb.poo.database.DB;
import ucb.poo.entities.Aluno;
import ucb.poo.entities.Matricula;
import ucb.poo.entities.Turma;

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
        cadastrarTurma();
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
                aluno.setMatricula(resultado.getInt("matricula"));
                aluno.setCpf(resultado.getString("cpf"));
                aluno.setNome(resultado.getString("nome"));
                aluno.setEmail(resultado.getString("email"));
                aluno.setTelefone(resultado.getString("telefone"));
                alunos.add(aluno);
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
        } catch (SQLException erro) {
            erro.printStackTrace();
        }
    }

    public static Optional<Aluno> buscarAluno(int matricula) {
        Connection conexao;
        ResultSet resultado;
        Statement comando;
        Aluno aluno = null;

        try {
            conexao = DB.conectar();
            comando = conexao.createStatement();

            resultado = comando.executeQuery(
                    "SELECT matricula, " +
                            "nome, " +
                            "cpf, " +
                            "email, " +
                            "telefone " +
                            "FROM universidade.alunos WHERE universidade.alunos.matricula = " + matricula
            );

            while(resultado.next()) {
                aluno = new Aluno();
                aluno.setMatricula(resultado.getInt("matricula"));
                aluno.setNome(resultado.getString("nome"));
                aluno.setCpf(resultado.getString("cpf"));
                aluno.setEmail(resultado.getString("email"));
                aluno.setTelefone(resultado.getString("telefone"));
            }

            if(aluno == null) {
                System.out.println("Aluno não encontrado");
                return Optional.empty();
            }

            System.out.println("Aluno encontrado");
            System.out.println(aluno.getNome());
            System.out.println(aluno.getMatricula());
            System.out.println(aluno.getEmail());

            return Optional.of(aluno);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.desconectar();
        }

        return Optional.empty();
    }

    public static void deletarAluno(int matricula) {
        Connection conexao;
        PreparedStatement statement;

        try {
            conexao = DB.conectar();
            conexao.createStatement();

            statement = conexao.prepareStatement("DELETE FROM universidade.alunos WHERE alunos.matricula = ?");

            statement.setInt(1, matricula);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void cadastrarTurma() {
        Connection conexao;

        PreparedStatement statement;
        Scanner sc = new Scanner(System.in);

        try {
            conexao = DB.conectar();
            conexao.createStatement();

            Turma turma = new Turma();

            System.out.print("Insira o número da sala: ");
            turma.setSala(sc.nextInt());

            System.out.print("Insira o ID da Disciplina: ");
            turma.setId_disciplina(sc.nextInt());

            System.out.print("Insira o ID do Curso: ");
            turma.setId_curso(sc.nextInt());

            statement = conexao.prepareStatement(
                    "INSERT INTO universidade.turmas(sala, id_disciplina, id_curso) VALUES (?, ?, ?)"
            );

            statement.setInt(1, turma.getSala());
            statement.setInt(2, turma.getId_disciplina());
            statement.setInt(3, turma.getId_curso());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar Turma");
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
            Optional<Aluno> aluno = buscarAluno(sc.nextInt());

            if(aluno.isEmpty()) {
                System.err.println("Aluno não encontrado!");
            }

            System.out.println("Alterando frequencia de: " + aluno.get().getNome());
            System.out.print("Insira a frequência do aluno: ");
            double frequencia = sc.nextDouble();

            if(frequencia <= 0) {
                System.err.println("Insira uma frequência acima de 0.");
            }

//            statement = conexao.prepareStatement(
//                    "UPDATE universidade.disciplinas SET frequencia = ? WHERE "
//            );

        } catch (SQLException erro) {
            erro.printStackTrace();
        }
    }
}