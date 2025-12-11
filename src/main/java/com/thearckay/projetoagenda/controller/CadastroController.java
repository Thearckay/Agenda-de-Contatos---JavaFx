package com.thearckay.projetoagenda.controller;

import com.thearckay.projetoagenda.dao.UsuarioDAO;
import com.thearckay.projetoagenda.enums.StatusUsuario;
import com.thearckay.projetoagenda.model.Agenda;
import com.thearckay.projetoagenda.model.Usuario;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;

public class CadastroController {

    @FXML private Hyperlink btnLogarSe;
    @FXML private TextField txtNomeCompleto;
    @FXML private TextField txtNumeroTelefone;
    @FXML private TextField txtCEP;
    @FXML private DatePicker txtDataNascimento;
    @FXML private TextField txtCriarEmail;
    @FXML private TextField txtCriarSenha;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void voltarTelaLogin(){
        try{
            Stage janela = (Stage) btnLogarSe.getScene().getWindow();
            FXMLLoader fxmlTelaLogin = new FXMLLoader(getClass().getResource("/com/thearckay/projetoagenda/fxml/Login.fxml"));
            Parent root = fxmlTelaLogin.load();

            Scene telaLogin = new Scene(root);
            janela.setScene(telaLogin);
            janela.centerOnScreen();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void btnCriarConta() throws IOException {
        String nomeCompleto = txtNomeCompleto.getText();
        String numeroTelefone = txtNumeroTelefone.getText();
        String localizacao = txtCEP.getText();
        LocalDate dataNascimento = txtDataNascimento.getValue();
        String email = txtCriarEmail.getText();
        String senha = txtCriarSenha.getText();

        if (nomeCompleto.isBlank() || numeroTelefone.isBlank() || localizacao.isBlank() || email.isBlank() || senha.isBlank()){
            enviarNotificacaoDesktop("Não foi possível Cadastrar", "Preencha todos os campos para prosseguir com o cadastro");
            return;
        }

        if (nomeCompleto.trim().contains(" ")){
            System.out.println("Contém sobrenome");

        } else {
            enviarNotificacaoDesktop("Nome completo","Informe o nome e o sobrenome para o cadastro.");
            return;
        }

        int numeroSemEspaco = numeroTelefone.trim().replace(" ", "").length();

        if ( numeroSemEspaco != 14){
            enviarNotificacaoDesktop("Numero de telefone","O número de telefone precisa obedecer o padrão: +00 00 000000000");
            return;
        } else {
            System.out.println("O número tem 14 digitos");
        }


        String localizacaoSemVirgula = localizacao.trim().replace(",", "");
        if (localizacaoSemVirgula.isBlank()){
            enviarNotificacaoDesktop("Informe sua localização", "Informe a localização seguindo o padrão: Cidade, Estado");
            return;
        }

        // Validação data Nascimento
        if (dataNascimento == null){
            enviarNotificacaoDesktop("Informe sua data de nascimento", "Preencha o campo de Data com sua data de nascimento.");
            return;
        } else {
            System.out.println("Campo data preenchido");
        }

        if (!email.trim().contains("@")){
            enviarNotificacaoDesktop("Email inválido", "Informe um email válido para prosseguir com o cadastro");
            return;

        } else if (usuarioDAO.verificarDisponibilidadeEmail(email) == StatusUsuario.EMAIL_DISPONIVEL) {
            System.out.println("O email contém um @");

        } else {
            enviarNotificacaoDesktop("Email indisponivel", "O email digitado já está em uso! Tente outro e-mail");
            return;
        }


        if (senha.trim().length() < 8){
            enviarNotificacaoDesktop("Senha inválida", "A senha tem que conter ao menos 8 caracteres");
            return;

        } else {
            System.out.println("A senha tem pelo menos 8 caracteres");
        }

        Agenda novaAgenda = new Agenda();
        Usuario novoUsuario = new Usuario(nomeCompleto, numeroTelefone, localizacao, email, senha, dataNascimento, novaAgenda);
        usuarioDAO.salvarNovoUsuario(novoUsuario);

        enviarNotificacaoDesktop("Seja bem-vindo", "Usuário Criado com sucesso!");

        Stage janelaPrincipal = (Stage) txtCriarEmail.getScene().getWindow();
        FXMLLoader dashboardFxml = new FXMLLoader(getClass().getResource("/com/thearckay/projetoagenda/fxml/Dashboard.fxml"));
        Parent root = dashboardFxml.load();
        Scene cenaDashboard = new Scene(root);

        janelaPrincipal.setScene(cenaDashboard);
        janelaPrincipal.show();
        janelaPrincipal.setResizable(true);
        janelaPrincipal.setMaximized(true);


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

            PauseTransition delay = new PauseTransition(Duration.seconds(2));

            trayIcon.displayMessage(titulo, mensagem, TrayIcon.MessageType.INFO);
            delay.setOnFinished(_ -> tray.remove(trayIcon));

            delay.play();

        } catch (AWTException e) {
            throw new RuntimeException("Erro ao tentar exibir notificação"+e);
        }
    }


}
