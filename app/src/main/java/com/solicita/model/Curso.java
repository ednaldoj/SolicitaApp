package com.solicita.model;

public class Curso {

    private int id;
    private String nome, unidade_id, abreviatura;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUnidade_id() {
        return unidade_id;
    }

    public void setUnidade_id(String unidade_id) {
        this.unidade_id = unidade_id;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }
}
