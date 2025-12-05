package com.thearckay.projetoagenda.controller;

import com.thearckay.projetoagenda.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {

    @FXML private BorderPane dashboardBackground;
    @FXML private StackPane dialogoLogout;
    @FXML private  Button btnContatos;
    @FXML private Button btnFavoritos;
    @FXML private Button btnSair;
    @FXML private Button btnLixeira;

    @FXML private ListaContatoController listaContatosController;

    private Usuario usuarioLogado;


    @FXML
    public void abrirListaContatos() {
        selecionarBotao(btnContatos);
        listaContatosController.carregardadosNaLista();
        try {
            FXMLLoader loaderLista = new FXMLLoader(getClass().getResource("/com/thearckay/projetoagenda/fxml/ListaContatos.fxml"));
            Parent rootLista = loaderLista.load();

            ListaContatoController controller = loaderLista.getController();
            controller.setDashBoard(this);
            if (this.usuarioLogado != null){
                controller.setUsuarioLogado(this.usuarioLogado);
            }

            dashboardBackground.setCenter(rootLista);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar mudar o módulo do dashboard, do atual para a da lista de contatos",e);
        }
    }

    @FXML
    private void abrirContatosFavoritos(){
        selecionarBotao(btnFavoritos);
        try {
            FXMLLoader loaderFavoritos = new FXMLLoader(getClass().getResource("/com/thearckay/projetoagenda/fxml/ContatosFavoritos.fxml"));
            Parent moduloFavoritos = loaderFavoritos.load();

            dashboardBackground.setCenter(moduloFavoritos);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar mudar o módulo do dashboard para os contatos favoritos",e);
        }

    }

    @FXML
    private void abrirContatosExluidos(){
        selecionarBotao(btnLixeira);
    }

    private void selecionarBotao(Button botao){

        btnContatos.getStyleClass().remove("botaoSelecionado");
        btnContatos.getStyleClass().add("botaoMenu");

        btnFavoritos.getStyleClass().remove("botaoSelecionado");
        btnFavoritos.getStyleClass().add("botaoMenu");

        btnLixeira.getStyleClass().remove("botaoSelecionado");
        btnLixeira.getStyleClass().add("botaoMenu");

        // tirar seleção
        botao.getStyleClass().add("botaoSelecionado");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selecionarBotao(btnContatos);
    }

    @FXML
    public void sair() {
        dialogoLogout.setVisible(true);
        dashboardBackground.setEffect(new GaussianBlur(10));
    }



    @FXML
    public void fecharDialogoLogout() {
        dialogoLogout.setVisible(false);
        dashboardBackground.setEffect(new GaussianBlur(0));
    }

    @FXML
    public void confirmarLogout() throws IOException {
        System.out.println("Click?");
        FXMLLoader telaLoginFxml = new FXMLLoader(getClass().getResource("/com/thearckay/projetoagenda/fxml/Login.fxml"));
        Parent rootLogin = telaLoginFxml.load();
        Scene cenaLogin = new Scene(rootLogin);
        Stage janela = (Stage) btnSair.getScene().getWindow();
        janela.setScene(cenaLogin);
        janela.setMaximized(false);
        janela.setResizable(false);
        janela.sizeToScene();
        janela.centerOnScreen();

    }

    public void setUsuarioLogado(Usuario usuarioLogado){
        if (usuarioLogado != null){
            System.out.println("O usuário não é nulo e seu nome é: "+usuarioLogado.getNomeCompleto());
            this.usuarioLogado = usuarioLogado;
            if (listaContatosController != null) {
                listaContatosController.setUsuarioLogado(usuarioLogado);
                listaContatosController.setDashBoard(this);
            }
        } else {
            System.out.println("O usuário é nulo");
        }
    }

    public ListaContatoController getModuloListaContatos(){
        return this.listaContatosController;
    }

    public BorderPane getdashboardBackground(){
        return this.dashboardBackground;
    }


}