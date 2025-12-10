package com.thearckay.projetoagenda.model;

import com.thearckay.projetoagenda.dao.ContatoDAO;
import com.thearckay.projetoagenda.enums.StatusContato;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        contatoARemover.setDeletado(true);
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
        return (int) this.agendaContatos.stream()
                .filter(contato -> !Boolean.TRUE.equals(contato.getDeletado()))
                .count()
        ;
    }

    public void restaurarContato(Contato contato) {
        contato.setDeletado(false);
        contatoDAO.atualizarContato(contato, this.usuarioDaAgenda.getId());
    }

    public StatusContato excluirDefinitivamente(Contato contato) {
        StatusContato status = contatoDAO.deletarContatoDoBancoDeDados(contato, usuarioDaAgenda);
        if (status == StatusContato.SUCESSO) {
            this.agendaContatos.remove(contato);
        }

        return status;
    }

    public StatusContato adicionarNovoContato(Contato novoContato) {
        if (novoContato != null){
            agendaContatos.add(novoContato);
            return StatusContato.SUCESSO;
        }

        return null;
    }

    public void atualizarContato(Contato contatoAAtualizar){
        contatoDAO.atualizarContato(contatoAAtualizar, this.usuarioDaAgenda.getId());
        for (Contato c : this.usuarioDaAgenda.getAgenda().getAgendaContatos()) {
            if (c.getId().equals(contatoAAtualizar.getId())) {
                c.setNomeCompleto(contatoAAtualizar.getNomeCompleto());
                c.setNumeroTelefone(contatoAAtualizar.getNumeroTelefone());
                c.setEmail(contatoAAtualizar.getEmail());
                c.setLocalizacao(contatoAAtualizar.getLocalizacao());
                c.setFavorito(contatoAAtualizar.getFavorito());
                c.setNascimento(contatoAAtualizar.getNascimento());
                break;
            }
        }
    }

    public List<Contato> getContatosFavoritos(){
        return this.agendaContatos.stream().filter(contato -> contato.getFavorito() && contato.getDeletado() == false).collect(Collectors.toList());
    }

    public List<Contato> getContatosDeletados(){
        return this.agendaContatos.stream()
                .filter(contato -> Boolean.TRUE.equals(contato.getDeletado())) // Só TRUE passa
                .collect(Collectors.toList());
    }

    public List<Contato> getTodosContatos() {
        return this.agendaContatos.stream()
                .filter(contato -> !Boolean.TRUE.equals(contato.getDeletado()))
                .collect(Collectors.toList())
        ;
    }

    public void apagarContatosDeletadosPermanentemente(Usuario usuarioLogado){

        List<Contato> paraExcluir = this.agendaContatos.stream()
                .filter(c -> Boolean.TRUE.equals(c.getDeletado()))
                .collect(Collectors.toList())
        ;
        paraExcluir.stream().forEach(contato -> contatoDAO.deletarContatoDoBancoDeDados(contato, usuarioLogado));
        agendaContatos.removeAll(paraExcluir);
    }

    public List<Contato> getContatosExcluidos(){
        List<Contato> contatosExluidos = new ArrayList<>();
        contatosExluidos.addAll(getAgendaContatos().stream().filter(contato -> contato.getDeletado() == true).collect(Collectors.toList()));
        return contatosExluidos;
    }

}