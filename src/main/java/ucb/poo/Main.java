package ucb.poo;

import ucb.poo.database.DB;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        Connection conexao = null;
        Statement comando = null;
        ResultSet resultado = null;

        try {
            conexao = DB.conectar();

            comando = conexao.createStatement();
            resultado = comando.executeQuery("SELECT * FROM universidade.alunos");

            while (resultado.next()) {
                System.out.println(resultado.getString("nome"));
            }

        } catch (SQLException erro) {
            erro.printStackTrace();
        } finally {
            DB.fecharResultado(resultado);
            DB.fecharComando(comando);
            DB.desconectar();
        }

        PreparedStatement statement = null;

        try {
            conexao = DB.conectar();

            statement = conexao.prepareStatement(
                    "INSERT INTO universidade.alunos(cpf, nome, telefone, email, codigo_matricula) VALUES (?, ?, ?, ?, ?)"
            );

            statement.setString(1, "828128812");
            statement.setString(2, "Pablo");

        } catch (SQLException erro) {
            erro.printStackTrace();
        }
    }
}