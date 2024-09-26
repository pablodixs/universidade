package ucb.poo;

import ucb.poo.database.DB;
import ucb.poo.entities.Aluno;

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
        deletarAluno(1);
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

        try {
            conexao = DB.conectar();
            conexao.createStatement();

            Aluno novoAluno = new Aluno();

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
        Statement comando;
        PreparedStatement statement;

        try {
            conexao = DB.conectar();
            comando = conexao.createStatement();

            statement = conexao.prepareStatement("DELETE FROM universidade.alunos WHERE alunos.matricula = ?");

            statement.setInt(1, matricula);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}