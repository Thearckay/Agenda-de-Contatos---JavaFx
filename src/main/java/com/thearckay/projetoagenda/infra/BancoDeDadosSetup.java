package com.thearckay.projetoagenda.infra;

import java.sql.Connection;
import java.sql.Statement;

public class BancoDeDadosSetup {

    public static void inicializar() {
        try (Connection conexao = FabricaConexao.conectar();
             Statement stmt = conexao.createStatement()) {


            String sqlUsuarios = """
                CREATE TABLE IF NOT EXISTS usuarios (
                    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                    nome_completo VARCHAR(100) NOT NULL,
                    numero_telefone VARCHAR(20),
                    localizacao VARCHAR(255),
                    email VARCHAR(255) NOT NULL UNIQUE,
                    senha VARCHAR(255) NOT NULL,
                    data_aniversario DATE
                );
            """;

            String sqlContatos = """
                CREATE TABLE IF NOT EXISTS contatos (
                    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                    usuario_id INT UNSIGNED NOT NULL,
                    nome_completo VARCHAR(255) NOT NULL,
                    numero_telefone VARCHAR(20) NOT NULL,
                    email VARCHAR(255),
                    localizacao VARCHAR(255),
                    favorito BOOLEAN DEFAULT FALSE,
                    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
                );
            """;

            String sqlNotas = """
            CREATE TABLE IF NOT EXISTS notas (
            id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
            contato_id INT UNSIGNED NOT NULL,
            texto TEXT NOT NULL,
            FOREIGN KEY (contato_id) REFERENCES contatos(id) ON DELETE CASCADE
            );
            """;



            stmt.execute(sqlUsuarios);
            stmt.execute(sqlContatos);
            stmt.execute(sqlNotas);

            System.out.println("Banco de dados verificado/criado com sucesso!");

        } catch (Exception e) {

            e.printStackTrace();
            System.exit(1);
        }
    }
}