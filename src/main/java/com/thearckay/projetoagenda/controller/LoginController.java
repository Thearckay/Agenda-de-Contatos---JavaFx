package com.thearckay.projetoagenda.controller;

import com.thearckay.projetoagenda.dao.UsuarioDAO;
import com.thearckay.projetoagenda.enums.StatusUsuario;
import com.thearckay.projetoagenda.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class LoginController {
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtSenha;
    @FXML private Hyperlink linkCriarConta;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void onBtnEntrarClick() throws IOException {
        System.out.println("click");
        String email = txtEmail.getText();
        String senha = txtSenha.getText();

        System.out.println("o email é: "+email+". e a senha é: "+senha);

        Usuario usuarioLogado = usuarioDAO.pegarUsuario(email, senha);

        if (email.isBlank() && senha.isBlank()){
            enviarNotificacaoDesktop("Informe as credenciais", "Preencha os campos antes de prosseguir");
        } else if (email.isBlank() || senha.isBlank()){
            enviarNotificacaoDesktop("Credencial inválida", "Uma das credenciais é inválida!");
        }


        if(usuarioLogado != null){
            try {
                enviarNotificacaoDesktop("Credenciais válidas", "Logado com Sucesso!");
                FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("/com/thearckay/projetoagenda/fxml/DashBoard.fxml"));
                Parent root = dashboardLoader.load();

                Stage janelaAtual = (Stage) txtEmail.getScene().getWindow();
                Scene cenaDashboard = new Scene(root);
                janelaAtual.setScene(cenaDashboard);
                janelaAtual.centerOnScreen();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            return;
        }

    }

    public void btnCriarConta() throws IOException {
        try{
            Stage janela = (Stage) txtEmail.getScene().getWindow();
            FXMLLoader fxmlLoaderCadastro = new FXMLLoader(getClass().getResource("/com/thearckay/projetoagenda/fxml/Cadastro.fxml"));
            Parent root = fxmlLoaderCadastro.load();
            Scene telaCadastro = new Scene(root);

            janela.setScene(telaCadastro);
            janela.centerOnScreen();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao mudar de janela (login -> cadastro) "+e);
        }

    }



    private void enviarNotificacaoDesktop(String titulo, String mensagem) {
        if (!SystemTray.isSupported()) {
            System.out.println("Sistema não suporta TrayIcon.");
            return;
        }

        try {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().createImage("");

            TrayIcon trayIcon = new TrayIcon(image, "Agenda contatos");
            //trayIcon.setImageAutoSize(true);
            tray.add(trayIcon);

            trayIcon.displayMessage(titulo, mensagem, TrayIcon.MessageType.INFO);
            tray.remove(trayIcon);

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }


}
