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
        Statement comando = null;
        Connection conexao = null;
        Scanner sc = new Scanner(System.in);

        int opc = 0;

        System.out.println("Insira a matrícula: ");
        int matricula = sc.nextInt();

        try {
            conexao = DB.conectar();
            comando = conexao.createStatement();

            Optional<Aluno> aluno = buscarAluno(matricula, comando);

            if(aluno.isPresent()) {
                System.out.println("Aluno encontrado");
                System.out.println(aluno.get().getNome());
                System.out.println(aluno.get().getMatricula());
                System.out.println(aluno.get().getEmail());
            } else {
                System.out.println("Aluno nao encontrado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Aluno> listarAlunos(Statement comando) {
        Connection conexao = null;
        ResultSet resultado = null;
        List<Aluno> alunos = new ArrayList<>();

        try {
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

    public static void adicionarAluno(Connection conexao) {
        PreparedStatement statement = null;
        Scanner sc = new Scanner(System.in);

        try {

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
                statement.setDate(5, java.sql.Date.valueOf(novoAluno.getData_nascimento().toLocalDate()));
            } else {
                statement.setNull(5, java.sql.Types.DATE);
            }

            statement.executeUpdate();
        } catch (SQLException erro) {
            erro.printStackTrace();
        }
    }

    public static Optional<Aluno> buscarAluno(int matricula, Statement comando) {
        Connection conexao = null;
        ResultSet resultado = null;
        Aluno aluno = null;


        try {
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

            return Optional.of(aluno);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}