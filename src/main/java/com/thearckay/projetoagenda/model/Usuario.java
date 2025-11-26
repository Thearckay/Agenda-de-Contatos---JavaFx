package com.thearckay.projetoagenda.model;

import java.time.LocalDate;

public class Usuario {

    private Integer id;
    private String nomeCompleto;
    private String numeroTelefone;
    private String localizacao;
    private String email;
    private String senha;
    private LocalDate dataAniversario;
    private Agenda agenda;

    // constructors
    public Usuario(){}
    public Usuario(String nomeCompleto, String numeroTelefone, String localizacao, String email, String senha, LocalDate dataAniversario, Agenda agenda) {
        this.nomeCompleto = nomeCompleto;
        this.numeroTelefone = numeroTelefone;
        this.localizacao = localizacao;
        this.email = email;
        this.senha = senha;
        this.dataAniversario = dataAniversario;
        this.agenda = agenda;
        agenda.setUsuarioDaAgenda(this);
    }

    // getters
    public Integer getId() {
        return id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public LocalDate getDataAniversario() {
        return dataAniversario;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    // setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setDataAniversario(LocalDate dataAniversario) {
        this.dataAniversario = dataAniversario;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }
}
