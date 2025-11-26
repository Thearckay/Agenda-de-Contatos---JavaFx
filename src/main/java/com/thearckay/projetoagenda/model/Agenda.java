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
}