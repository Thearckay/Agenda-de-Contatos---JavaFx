package com.thearckay.projetoagenda.model;

import java.util.ArrayList;
import java.util.List;

public class Contato {

    private Integer id;
    private Integer usuarioId;
    private String nomeCompleto;
    private String numeroTelefone;
    private String email;
    private String localizacao;
    private Boolean favorito;
    private List<Nota> notas = new ArrayList<>();

    // construtores
    public Contato(){}

    public Contato(Integer usuarioId, String nomeCompleto, String numeroTelefone) {
        setUsuarioId(usuarioId);
        setNomeCompleto(nomeCompleto);
        setNumeroTelefone(numeroTelefone);
    }

    public Contato(Integer usuarioId, String nomeCompleto, String numeroTelefone, String email){
        setUsuarioId(usuarioId);
        setNomeCompleto(nomeCompleto);
        setNumeroTelefone(numeroTelefone);
        setEmail(email);
    }

    public Contato(Integer usuarioId, String nomeCompleto, String numeroTelefone, String email, String localizacao, Boolean favorito) {
        setUsuarioId(usuarioId);
        setNomeCompleto(nomeCompleto);
        setNumeroTelefone(numeroTelefone);
        setEmail(email);
        setLocalizacao(localizacao);
        setFavorito(favorito);
    }

    public Contato(Integer id, Integer usuarioId, String nomeCompleto, String numeroTelefone, String email, String localizacao, Boolean favorito) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.nomeCompleto = nomeCompleto;
        this.numeroTelefone = numeroTelefone;
        this.email = email;
        this.localizacao = localizacao;
        this.favorito = favorito;
    }

    // getters
    public Integer getId() {
        return id;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public String getEmail() {
        return email;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public Boolean getFavorito() {
        return favorito;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    // setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public void adicionarNota(Nota nota) {
        this.notas.add(nota);
    }

    @Override
    public String toString() {
        return "Contato{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", nomeCompleto='" + nomeCompleto + '\'' +
                ", numeroTelefone='" + numeroTelefone + '\'' +
                ", email='" + email + '\'' +
                ", localizacao='" + localizacao + '\'' +
                ", favorito=" + favorito +
                '}';
    }
}
