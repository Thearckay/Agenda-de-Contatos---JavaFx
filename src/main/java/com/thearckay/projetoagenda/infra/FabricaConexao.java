package com.thearckay.projetoagenda.infra;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class FabricaConexao {

    private static String url = carregarPropriedades().getProperty("db.url");
    private static String usuario = carregarPropriedades().getProperty("db.user");
    private static String senha = carregarPropriedades().getProperty("db.password");

    public static Connection conectar(){
        try {
            Connection conexao = DriverManager.getConnection(url,usuario,senha);
            return conexao;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao tentar se conectar ao banco de dados: "+e);
        }
    }

    // todo - mudar o diretorio do arquivo
    private static Properties carregarPropriedades(){
        try(FileInputStream fs = new FileInputStream("src/main/java/db.properties")){

            Properties props = new Properties();
            props.load(fs);
            return props;

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Arquivo de propriedades não encontrado: "+e);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar as propriedades para conexão: "+e);
        }
    }

}
