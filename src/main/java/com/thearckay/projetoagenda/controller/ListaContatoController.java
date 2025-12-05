package com.thearckay.projetoagenda.controller;

import com.thearckay.projetoagenda.dao.ContatoDAO;
import com.thearckay.projetoagenda.model.Contato;
import com.thearckay.projetoagenda.model.Usuario;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ListaContatoController implements Initializable {

    private Usuario usuarioLogado;
    private DashBoardController dashBoard;
    private final ContatoDAO daoContato = new ContatoDAO();
    @FXML private Label lblTotalContatos;
    @FXML private ListView<Contato> listaContatos;
    @FXML private VBox painelDetalhesContato;

    @FXML private Label lblIniciaisGigantes;
    @FXML private Label lblNomeDetalhe;
    @FXML private Label lblTelefoneDetalhe;
    @FXML private Label lblEmailDetalhe;
    @FXML private Label lblLocalizacaoDetalhe;
    @FXML private Label lblNascimentoDetalhe;

    // Fundo do Label dos detalhes
    @FXML private HBox bgTelefone;
    @FXML private HBox bgEmail;
    @FXML private HBox bgLocalizacao;
    @FXML private HBox bgAniversario;

    private Contato ultimoContatoClicado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        criarCelulas();
        selecionarCelula();
    }

    public void selecionarCelula(){
        listaContatos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contato>() {
            @Override
            public void changed(ObservableValue<? extends Contato> observableValue, Contato oldValue, Contato newValue) {
                System.out.println("Clicado");

                if (newValue != null){
                    //TODO - as cores parecem funcionar quando setamos o estilo diretamente

                    lblNomeDetalhe.setStyle("-fx-text-fill: #5e25d9;");
                    lblTelefoneDetalhe.setStyle("-fx-text-fill: #444444; -fx-font-size: 12px;");
                    lblEmailDetalhe.setStyle("-fx-text-fill: #444444; -fx-font-size: 12px;");
                    lblLocalizacaoDetalhe.setStyle("-fx-text-fill: #444444; -fx-font-size: 12px;");
                    lblNascimentoDetalhe.setStyle("-fx-text-fill: #444444; -fx-font-size: 12px;");

                    String iniciais = "";
                    if (newValue.getNomeCompleto() != null && !newValue.getNomeCompleto().isEmpty()){
                        String[] partes = newValue.getNomeCompleto().trim().split("\\s+");

                        if (partes.length > 0) iniciais += partes[0].substring(0,1);
                        if (partes.length > 1) iniciais += partes[partes.length - 1].substring(0,1);
                    }

                    painelDetalhesContato.setVisible(true);

                    lblIniciaisGigantes.setText(iniciais);
                    lblNomeDetalhe.setText(newValue.getNomeCompleto());
                    lblTelefoneDetalhe.setText(newValue.getNumeroTelefone());
                    lblEmailDetalhe.setText(newValue.getEmail());
                    lblLocalizacaoDetalhe.setText(newValue.getLocalizacao());

                    ultimoContatoClicado = newValue;

                }
            }
        });
    }

    public void criarCelulas(){
        listaContatos.setCellFactory(param -> new ListCell<Contato>(){
            @Override
            protected void updateItem(Contato contato, boolean b) {
                super.updateItem(contato, b);

                if (b || contato == null){
                    setText(null);
                    setGraphic(null);
                } else {
                    Label lblNome = new Label(contato.getNomeCompleto());
                    lblNome.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #1E1E2D;");

                    Label lblTelefone = new Label(contato.getNumeroTelefone());
                    lblTelefone.setStyle("-fx-text-fill: #7D7D8D; -fx-font-size: 12px;");

                    VBox caixaTexto = new VBox(lblNome, lblTelefone);
                    caixaTexto.setSpacing(2);

                    String iniciais = "";
                    if (contato.getNomeCompleto() != null && !contato.getNomeCompleto().isEmpty()){
                        String[] partes = contato.getNomeCompleto().trim().split("\\s+");

                        if (partes.length > 0) iniciais += partes[0].substring(0,1);
                        if (partes.length > 1) iniciais += partes[partes.length - 1].substring(0,1);
                    }

                    Circle fundo = new Circle(20, Color.web("#6A35E8"));

                    Label lblIniciais = new Label(iniciais.toUpperCase());
                    lblIniciais.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

                    StackPane avatar = new StackPane(fundo, lblIniciais);

                    HBox layoutDaLinha = new HBox(avatar, caixaTexto);
                    layoutDaLinha.setSpacing(15);
                    layoutDaLinha.setAlignment(Pos.CENTER_LEFT);
                    layoutDaLinha.setPadding(new Insets(10));

                    setText(null);
                    setGraphic(layoutDaLinha);
                }
            }
        });
    }

    public void abrirFormularioAddContato(ActionEvent actionEvent) {
        try{
            FXMLLoader contatoCadastroLoader = new FXMLLoader(getClass().getResource("/com/thearckay/projetoagenda/fxml/FormularioCadastro.fxml"));
            Parent telaContato = contatoCadastroLoader.load();

            FormularioContatoController controller = contatoCadastroLoader.getController();

            controller.setDashBoardController(this.dashBoard);
            controller.setUsuarioLogado(this.usuarioLogado);
            dashBoard.getdashboardBackground().setCenter(telaContato);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar abrir tela de cadastro de contatos. ", e);
        }
    }

    public void carregardadosNaLista(){
        Integer quantidadeContatos = usuarioLogado.getAgenda().getQuantidade();
        lblTotalContatos.setText("Lista de Contatos ("+quantidadeContatos+")");

        if (quantidadeContatos == 0){
            painelDetalhesContato.setVisible(false);
        }

        if (usuarioLogado.getAgenda() != null){
            ObservableList<Contato> agendaContatosObsl = FXCollections.observableArrayList(usuarioLogado.getAgenda().getAgendaContatos());
            listaContatos.setItems(agendaContatosObsl);
        }
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        if (usuarioLogado != null){
            this.usuarioLogado = usuarioLogado;
            carregardadosNaLista();
        }
    }

    @FXML
    private void excluirContato(){
        if (ultimoContatoClicado !=  null){
            // todo - criar DELETE na agenda - assim fica atualizado
            this.usuarioLogado.getAgenda().removerContato(ultimoContatoClicado, this.usuarioLogado);
            dashBoard.abrirListaContatos();
        }
    }

    public void setDashBoard(DashBoardController dashBoardController){
        this.dashBoard = dashBoardController;
    }
}