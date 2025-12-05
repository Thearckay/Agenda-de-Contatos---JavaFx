package com.thearckay.projetoagenda.model;

import com.thearckay.projetoagenda.dao.ContatoDAO;
import com.thearckay.projetoagenda.enums.StatusContato;

import java.util.ArrayList;
import java.util.List;

public class Agenda {

    private Usuario usuarioDaAgenda;
    private List<Contato> agendaContatos = new ArrayList<>();
    private ContatoDAO contatoDAO = new ContatoDAO();

    public Agenda(Usuario usuario) {
        this.usuarioDaAgenda = usuario;
    }
    public Agenda() {}

    public StatusContato adicionarContato(Contato contato) {

        contato.setUsuarioId(this.usuarioDaAgenda.getId());
        StatusContato status = contatoDAO.salvarContatoCompleto(contato);

        if (status == StatusContato.SUCESSO) {
            agendaContatos.add(contato);
        }

        return status;
    }

    public StatusContato removerContato(Contato contatoARemover, Usuario usuarioLogado){
        StatusContato status = contatoDAO.deletarContato(contatoARemover, usuarioLogado);
        agendaContatos.remove(contatoARemover);

        return status;
    }

    public void carregarContatoDoBanco(Contato contato) {
        if (this.agendaContatos == null) {
            this.agendaContatos = new ArrayList<>();
        }
        this.agendaContatos.add(contato);
    }

    // Getters e Setters
    public Usuario getUsuarioDaAgenda() {
        return usuarioDaAgenda;
    }

    public void setUsuarioDaAgenda(Usuario usuarioDaAgenda) {
        this.usuarioDaAgenda = usuarioDaAgenda;
    }

    public List<Contato> getAgendaContatos() {
        return agendaContatos;
    }

    public void setAgendaContatos(List<Contato> agendaContatos) {
        this.agendaContatos = agendaContatos;
    }

    public Integer getQuantidade(){
        return agendaContatos.size();
    }

    public StatusContato adicionarNovoContato(Contato novoContato) {
        if (novoContato != null){
            agendaContatos.add(novoContato);
            return StatusContato.SUCESSO;
        }

        return null;
    }
}