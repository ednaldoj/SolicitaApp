package com.solicita.model;

public class Unidade {

    private int id;
    private String nome, instituicao_id;


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

    public String getInstituicao_id() {
        return instituicao_id;
    }

    public void setInstituicao_id(String instituicao_id) {
        this.instituicao_id = instituicao_id;
    }
}
