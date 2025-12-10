package com.thearckay.projetoagenda.controller;

import com.thearckay.projetoagenda.model.Contato;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.net.URL;
import java.util.ResourceBundle;

public class ContatosFavoritosController implements Initializable {

    private DashBoardController dashBoardController;
    @FXML private ListView listaFavoritos;
    @FXML private TextField txtPesquisarFavoritos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cofigurarCelulas();
    }

    public void cofigurarCelulas(){
        listaFavoritos.setCellFactory(listView -> new ListCell<Contato>() {
            @Override
            protected void updateItem(Contato contato, boolean empty) {
                super.updateItem(contato, empty);

                if (empty || contato == null) {
                    setGraphic(null);
                    return;
                }

                String iniciais = "";
                if (contato.getNomeCompleto() != null && !contato.getNomeCompleto().isEmpty()) {
                    String[] partes = contato.getNomeCompleto().trim().split("\\s+");

                    if (partes.length > 0) iniciais += partes[0].substring(0, 1);
                    if (partes.length > 1) iniciais += partes[partes.length - 1].substring(0, 1);
                }

                Circle fundo = new Circle(20, Color.web("#6A35E8"));

                Label lblIniciais = new Label(iniciais.toUpperCase());
                lblIniciais.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

                StackPane avatar = new StackPane(fundo, lblIniciais);
                avatar.setMinWidth(40);
                avatar.setMaxWidth(40);

                HBox linha = new HBox(20);
                linha.setStyle("-fx-padding: 10 20; -fx-alignment: center-left;");

                Label lblNome = new Label(contato.getNomeCompleto());
                lblNome.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #1E1E2D;");
                HBox.setHgrow(lblNome, javafx.scene.layout.Priority.ALWAYS);
                lblNome.setMaxWidth(Double.MAX_VALUE);

                Label lblTelefone = new Label(contato.getNumeroTelefone());
                lblTelefone.setStyle("-fx-text-fill: #555;");
                HBox.setHgrow(lblTelefone, javafx.scene.layout.Priority.ALWAYS);
                lblTelefone.setMaxWidth(Double.MAX_VALUE);

                Label lblEmail = new Label(contato.getEmail());
                lblEmail.setStyle("-fx-text-fill: #555;");
                HBox.setHgrow(lblEmail, javafx.scene.layout.Priority.ALWAYS);
                lblEmail.setMaxWidth(Double.MAX_VALUE);

                // ============================
                // 3. Montar a linha completa
                // ============================

                linha.getChildren().addAll(
                        avatar,
                        lblNome,
                        lblTelefone,
                        lblEmail
                );

                setGraphic(linha);
            }
        });
    }

    public void carregarDados(){
        ObservableList<Contato> listaVivaDeContato = FXCollections.observableArrayList(this.dashBoardController.getUsuarioLogado().getAgenda().getContatosFavoritos());
        FilteredList<Contato> listaFiltada  = new FilteredList<>(listaVivaDeContato, contato -> true);
        txtPesquisarFavoritos.textProperty().addListener((observable, oldValue, contatoDigitado) -> {
            listaFiltada.setPredicate(contato -> {
                if (contatoDigitado == null || contatoDigitado.isEmpty()){
                    return true;
                }

                String textoDigitado = contatoDigitado.toLowerCase();

                if (contato.getNomeCompleto().toLowerCase().contains(textoDigitado)){
                    return true;
                } else if (contato.getNumeroTelefone().contains(textoDigitado)) {
                    return true;
                } else if (contato.getEmail().toLowerCase().contains(textoDigitado)) {
                    return true;
                }

                return false;
            });

        });
        listaFavoritos.setItems(listaFiltada);
    }


    public void setDashBoardController(DashBoardController dashBoardController){
        this.dashBoardController = dashBoardController;
        carregarDados();
    }



}
