package com.thearckay.projetoagenda.controller;

import com.thearckay.projetoagenda.dao.ContatoDAO;
import com.thearckay.projetoagenda.model.Contato;
import com.thearckay.projetoagenda.model.Usuario;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ContatosExcluidosController implements Initializable {

    @FXML private ListView<Contato> listaExcluidos;
    @FXML private TextField txtPesquisarLixeira;

    private Usuario usuarioLogado;
    private DashBoardController dashBoard;
    private final ContatoDAO contatoDAO = new ContatoDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarCelulas();
    }

    private void configurarCelulas() {
        listaExcluidos.setCellFactory(param -> new ListCell<Contato>() {
            @Override
            protected void updateItem(Contato contato, boolean empty) {
                super.updateItem(contato, empty);

                if (empty || contato == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("-fx-background-color: transparent;");
                } else {
                    Label lblNome = new Label(contato.getNomeCompleto());
                    lblNome.setStyle("-fx-font-weight: bold; -fx-text-fill: #1E1E2D; -fx-font-size: 14px;");

                    Circle avatar = new Circle(15, Color.web("#FFCDD2"));
                    Label inicial = new Label(contato.getNomeCompleto().substring(0, 1));
                    inicial.setStyle("-fx-text-fill: #D32F2F; -fx-font-weight: bold;");
                    javafx.scene.layout.StackPane icon = new javafx.scene.layout.StackPane(avatar, inicial);

                    HBox colNome = new HBox(10, icon, lblNome);
                    colNome.setAlignment(Pos.CENTER_LEFT);
                    HBox.setHgrow(colNome, Priority.ALWAYS);
                    colNome.setMaxWidth(Double.MAX_VALUE);

                    Label lblTel = new Label(contato.getNumeroTelefone());
                    lblTel.setStyle("-fx-text-fill: #7D7D8D;");
                    HBox colTel = new HBox(lblTel);
                    colTel.setAlignment(Pos.CENTER_LEFT);
                    HBox.setHgrow(colTel, Priority.ALWAYS);
                    colTel.setMaxWidth(Double.MAX_VALUE);

                    Label lblEmail = new Label(contato.getEmail());
                    lblEmail.setStyle("-fx-text-fill: #7D7D8D;");
                    HBox colEmail = new HBox(lblEmail);
                    colEmail.setAlignment(Pos.CENTER_LEFT);
                    HBox.setHgrow(colEmail, Priority.ALWAYS);
                    colEmail.setMaxWidth(Double.MAX_VALUE);

                    Button btnRestaurar = new Button("Restaurar");
                    btnRestaurar.setStyle("-fx-background-color: transparent; -fx-text-fill: #6A35E8; -fx-font-weight: bold; -fx-cursor: hand;");
                    btnRestaurar.setOnAction(event -> restaurarContato(contato));

                    Button btnExcluir = new Button("Excluir");
                    btnExcluir.setStyle("-fx-background-color: transparent; -fx-text-fill: #FF5555; -fx-font-weight: bold; -fx-cursor: hand;");
                    btnExcluir.setOnAction(event -> deletarDefinitivamente(contato));

                    HBox colAcoes = new HBox(5, btnRestaurar, btnExcluir);
                    colAcoes.setAlignment(Pos.CENTER_RIGHT);
                    colAcoes.setMinWidth(160);

                    HBox linha = new HBox(10, colNome, colTel, colEmail, colAcoes);
                    linha.setAlignment(Pos.CENTER_LEFT);
                    linha.setPadding(new Insets(10, 20, 10, 20));
                    linha.setStyle("-fx-border-color: transparent transparent #F0F0F0 transparent; -fx-border-width: 1;");

                    setGraphic(linha);
                }
            }
        });
    }

    @FXML
    private void btnEsvaziarLixeira(){
        usuarioLogado.getAgenda().apagarContatosDeletadosPermanentemente(usuarioLogado);
        carregarContatosExcluidos();
    }

    private void restaurarContato(Contato contato) {
        if (usuarioLogado != null && usuarioLogado.getAgenda() != null) {
            usuarioLogado.getAgenda().restaurarContato(contato);
            listaExcluidos.getItems().remove(contato);
        }
    }

    private void deletarDefinitivamente(Contato contato) {
        if (usuarioLogado != null && usuarioLogado.getAgenda() != null) {
            usuarioLogado.getAgenda().excluirDefinitivamente(contato);
            listaExcluidos.getItems().remove(contato);
        }
    }

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
        carregarContatosExcluidos();
    }

    public void setDashBoard(DashBoardController dash) {
        this.dashBoard = dash;
    }

    private void carregarContatosExcluidos() {
        if (usuarioLogado != null) {
            List<Contato> excluidos = usuarioLogado.getAgenda().getContatosDeletados();
            listaExcluidos.setItems(FXCollections.observableArrayList(excluidos));
        }
    }
}