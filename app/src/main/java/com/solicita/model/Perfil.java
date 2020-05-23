package com.solicita.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Perfil {

    @Expose
    @SerializedName("id") String id;

    @Expose
    @SerializedName("default") String curso;

    @Expose
    @SerializedName("situacao") String situacao;
    private int aluno_id;
    private String unidade_id;
    private int curso_id;

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public int getAluno_id() {
        return aluno_id;
    }

    public void setAluno_id(int aluno_id) {
        this.aluno_id = aluno_id;
    }

    public String getUnidade_id() {
        return unidade_id;
    }

    public void setUnidade_id(String unidade_id) {
        this.unidade_id = unidade_id;
    }

    public int getCurso_id() {
        return curso_id;
    }

    public void setCurso_id(int curso_id) {
        this.curso_id = curso_id;
    }
}
