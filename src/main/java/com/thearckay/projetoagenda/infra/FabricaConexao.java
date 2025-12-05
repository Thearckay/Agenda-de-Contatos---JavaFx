package com.thearckay.projetoagenda.infra;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class FabricaConexao {

    private static final String url = carregarPropriedades().getProperty("db.url");
    private static final String usuario = carregarPropriedades().getProperty("db.user");
    private static final String senha = carregarPropriedades().getProperty("db.password");

    public static Connection conectar(){
        try {
            return DriverManager.getConnection(url,usuario,senha);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao tentar se conectar ao banco de dados: "+e);
        }
    }

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
