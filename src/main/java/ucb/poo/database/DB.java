package ucb.poo.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {
    private static Connection conexao = null;

    public static Connection conectar() {
        if(conexao == null) {
            try {
                Properties props = carregarPropiedades();
                String url = props.getProperty("jdbc.url");

                conexao = DriverManager.getConnection(url, props);
            } catch (Exception e) {
                throw new DbExcecao(e.getMessage());
            }
        }

        return conexao;
    }

    public static void desconectar() {
        if(conexao != null) {
            try {
                conexao.close();
            } catch (Exception e) {
                throw new DbExcecao(e.getMessage());
            }
        }
    }

    private static Properties carregarPropiedades() {
        try(FileInputStream fs = new FileInputStream("src/main/resources/db.properties")) {
            Properties prop = new Properties();
            prop.load(fs);

            return prop;
        } catch (IOException e) {
            throw new DbExcecao(e.getMessage());
        }
    }

    public static void fecharComando(Statement stmt) {
        if(stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new DbExcecao(e.getMessage());
            }
        }
    }

    public static void fecharResultado(ResultSet rslt) {
        if(rslt != null) {
            try {
                rslt.close();
            } catch (SQLException e) {
                throw new DbExcecao(e.getMessage());
            }
        }
    }
}
