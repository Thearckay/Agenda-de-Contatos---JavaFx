package com.thearckay.projetoagenda.controller;

import com.thearckay.projetoagenda.dao.ContatoDAO;
import com.thearckay.projetoagenda.enums.StatusContato;
import com.thearckay.projetoagenda.model.Contato;
import com.thearckay.projetoagenda.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.awt.*;
import java.time.LocalDate;

public class EditarContatoController {

    @FXML private TextField txtNome;
    @FXML private javafx.scene.control.TextField txtTelefone;
    @FXML private javafx.scene.control.TextField txtEmail;
    @FXML private javafx.scene.control.TextField txtLocalizacao;
    @FXML private DatePicker dtAniversario;
    @FXML private CheckBox cbFavorito;

    private ContatoDAO contatoDAO = new ContatoDAO();
    private Integer idUsuarioLogado;
    private Integer idContatoAtual;



    private DashBoardController dashBoardController;

    public void cancelarCadastro(ActionEvent actionEvent) {
        dashBoardController.abrirListaContatos();
    }

    public StatusContato salvarContato() {
        Integer usuarioId = dashBoardController.getUsuarioLogado().getId();
        String nome = txtNome.getText();
        String telefone = txtTelefone.getText();
        String email = txtEmail.getText();
        String localizacao = txtLocalizacao.getText();
        LocalDate aniversario = dtAniversario.getValue();
        Boolean favorito = cbFavorito.isSelected();

        Contato contatoAtualizado = new Contato(idContatoAtual,usuarioId, nome, telefone, email, localizacao, favorito, aniversario);
        StatusContato status = contatoDAO.atualizarContato(contatoAtualizado, idUsuarioLogado);

        if (status == StatusContato.SUCESSO) {
            Usuario usuario = dashBoardController.getUsuarioLogado();
            usuario.getAgenda().atualizarContato(contatoAtualizado);
        }

        dashBoardController.abrirListaContatos();
        return status;

    }

    public void informarContatoParaEdicao(Contato contatoAEditar){
        txtNome.setText(contatoAEditar.getNomeCompleto());
        txtTelefone.setText(contatoAEditar.getNumeroTelefone());
        txtEmail.setText(contatoAEditar.getEmail());
        txtLocalizacao.setText(contatoAEditar.getLocalizacao());
        dtAniversario.setValue(contatoAEditar.getNascimento());
        cbFavorito.setSelected(contatoAEditar.getFavorito());

        this.idContatoAtual = contatoAEditar.getId();
        this.idUsuarioLogado = contatoAEditar.getUsuarioId();
    }

    public void setDashBoardController(DashBoardController dashBoardController){
        this.dashBoardController = dashBoardController;
    }
}
