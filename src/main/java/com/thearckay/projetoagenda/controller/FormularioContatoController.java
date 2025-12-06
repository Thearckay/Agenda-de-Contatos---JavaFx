package com.thearckay.projetoagenda.controller;

import com.thearckay.projetoagenda.dao.ContatoDAO;
import com.thearckay.projetoagenda.enums.StatusContato;
import com.thearckay.projetoagenda.enums.StatusUsuario;
import com.thearckay.projetoagenda.model.Contato;
import com.thearckay.projetoagenda.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.LocalDate;

public class FormularioContatoController {

    @FXML private TextField txtNome;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEmail;
    @FXML private TextField txtLocalizacao;
    @FXML private DatePicker dtAniversario;
    @FXML private CheckBox cbFavorito;

    @FXML private Button btnCancelar;
    @FXML private Button btnSalvar;

    private Usuario usuarioLogado;
    private BorderPane painelPrincipal;
    private DashBoardController dashBoardController;
    private ContatoDAO daoContato = new ContatoDAO();

    // --- MÉTODOS DE CONFIGURAÇÃO (Chamados pelo DashboardController) ---

    public void setDashBoardController(DashBoardController dashBoardController){
        this.dashBoardController = dashBoardController;
    }

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    public void setPainelPrincipal(BorderPane painel) {
        this.painelPrincipal = painel;
    }


    @FXML
    public void salvarContato() {
        if (txtNome.getText().isEmpty() || txtTelefone.getText().isEmpty()) {
            mostrarAlerta("Erro", "Nome e Telefone são obrigatórios!");
            return;
        }

        int idUsuario = usuarioLogado.getId();
        String nomeContato = txtNome.getText();
        String numeroTelefone = txtTelefone.getText();
        String email = txtEmail.getText().isBlank()? "null": txtEmail.getText();
        String localizacao = txtLocalizacao.getText().isBlank()? "null": txtLocalizacao.getText();
        LocalDate nascimento = dtAniversario.getValue();
        boolean favorito = cbFavorito.isSelected();

        Contato novoContato = new Contato(idUsuario, nomeContato, numeroTelefone, email, localizacao, favorito, nascimento);
        StatusContato status = usuarioLogado.getAgenda().adicionarContato(novoContato);

        if (status == StatusContato.SUCESSO) {
            System.out.println("Salvo com sucesso!");
            voltarParaLista();
        } else {
            mostrarAlerta("Erro ao Salvar", "Não foi possível salvar o contato.");
        }

    }

    @FXML
    public void cancelarCadastro() {
        voltarParaLista();
    }

    // --- MÉTODOS AUXILIARES ---


    private void voltarParaLista() {
        if (dashBoardController != null){
            dashBoardController.abrirListaContatos();

        }
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}