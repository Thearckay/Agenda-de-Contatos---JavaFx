package com.thearckay.projetoagenda;

import com.thearckay.projetoagenda.infra.BancoDeDadosSetup;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        BancoDeDadosSetup.inicializar();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/thearckay/projetoagenda/fxml/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        primaryStage.setTitle("Login - Agenda de Contatos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
