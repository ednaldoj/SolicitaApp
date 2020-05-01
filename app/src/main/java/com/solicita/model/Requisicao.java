package com.solicita.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.solicita.config.ConfiguracaoFirebase;

import java.util.ArrayList;

public class Requisicao {

    private String idRequisicao;
    private String vinculo;
    private String curso;
    private String dataRequisicao;
    private String status;
    private ArrayList<String> documentosSolicitados;

    public Requisicao() {

    }

    @Exclude
    public String getIdRequisicao() {
        return idRequisicao;
    }

    public void setIdRequisicao(String idRequisicao) {
        this.idRequisicao = idRequisicao;
    }

    public String getVinculo() {
        return vinculo;
    }

    public void setVinculo(String vinculo) {
        this.vinculo = vinculo;
    }
    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getDataRequisicao() {
        return dataRequisicao;
    }

    public void setDataRequisicao(String dataRequisicao) {
        this.dataRequisicao = dataRequisicao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public CharSequence getDocumentosSolicitados() {
  //      return (CharSequence) documentosSolicitados;
    //}


    public ArrayList<String> getDocumentosSolicitados() {
        return documentosSolicitados;
    }

    public void setDocumentosSolicitados(ArrayList<String> documentosSolicitados) {
        this.documentosSolicitados = documentosSolicitados;
    }
}
