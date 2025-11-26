package com.thearckay.projetoagenda.model;

public class Nota {
    private Integer id;
    private Integer contatoId;
    private String texto;

    public Nota() {}

    public Nota(Integer contatoId, String texto) {
        this.contatoId = contatoId;
        this.texto = texto;
    }

    // getters
    public Integer getId() {
        return id;
    }

    public Integer getContatoId() {
        return contatoId;
    }

    public String getTexto() {
        return texto;
    }

    // setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setContatoId(Integer contatoId) {
        this.contatoId = contatoId;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return texto;
    }
}