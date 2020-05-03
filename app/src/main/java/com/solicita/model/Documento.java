package com.solicita.model;

public class Documento {

    private int id, detalhes;
    private String tipo, anotacoes;
    private boolean selecionado;

    public String getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(String anotacoes) {
        this.anotacoes = anotacoes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(int detalhes) {
        this.detalhes = detalhes;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }
}
