package ucb.poo;

import ucb.poo.database.DB;

import java.sql.*;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        Connection conexao = null;
        Statement comando = null;
        ResultSet resultado = null;

        Date data = new Date(System.currentTimeMillis());

        PreparedStatement statement = null;

        try {
            conexao = DB.conectar();

            statement = conexao.prepareStatement(
                    "INSERT INTO universidade.alunos(cpf, nome, telefone, email, data_nascimento) VALUES (?, ?, ?, ?, ?)"
            );

            statement.setString(1, "828128812");
            statement.setString(2, "Pablo");
            statement.setString(3, "619999999");
            statement.setString(4, "@");
            statement.setDate(5, data);

            statement.executeUpdate();

        } catch (SQLException erro) {
            erro.printStackTrace();
        }

        try {

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
    }
}