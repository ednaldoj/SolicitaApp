package com.solicita.model;

import java.util.ArrayList;

public class Solicitacoes {

    private String id, curso, data_pedido, hora_pedido, documentosSolicitados, status, detalhes, documentoId, perfilId, requisicaoId, documento, idDocumento, idPerfil, abreviatura, idCurso, cursoId;
   // private String id, curso, data_pedido, hora_pedido, status, documentoId, perfilId, requisicaoId, documento, idDocumento, idPerfil;
   // private ArrayList documentosSolicitados;
    private ArrayList arrayDocumentos, arrayStatus, arrayDetalhes;

    public Solicitacoes() {
    }

    public Solicitacoes(String id, String curso, String abreviatura, String data_pedido, String hora_pedido, String documentosSolicitados, String detalhes, String status, ArrayList arrayDocumentos, ArrayList arrayStatus, ArrayList arrayDetalhes) {
        this.id = id;
        this.curso = curso;
        this.abreviatura = abreviatura;
        this.data_pedido = data_pedido;
        this.hora_pedido = hora_pedido;
        this.documentosSolicitados = documentosSolicitados;
        this.detalhes=detalhes;
        this.status = status;
        this.arrayDocumentos = arrayDocumentos;
        this.arrayStatus = arrayStatus;
        this.arrayDetalhes = arrayDetalhes;

    }

    public Solicitacoes(String id, String curso, String documentosSolicitados, String status) {
        this.id = id;
        this.curso = curso;
        this.documentosSolicitados = documentosSolicitados;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData_pedido() {
        return data_pedido;
    }

    public void setData_pedido(String data_pedido) {
        this.data_pedido = data_pedido;
    }

    public String getHora_pedido() {
        return hora_pedido;
    }

    public void setHora_pedido(String hora_pedido) {
        this.hora_pedido = hora_pedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getDocumentosSolicitados() {
        return documentosSolicitados;
    }

    public void setDocumentosSolicitados(String documentosSolicitados) {
        this.documentosSolicitados = documentosSolicitados;
    }
    public String getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(String documentoId) {
        this.documentoId = documentoId;
    }

    public String getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(String perfilId) {
        this.perfilId = perfilId;
    }

    public String getRequisicaoId() {
        return requisicaoId;
    }

    public void setRequisicaoId(String requisicaoId) {
        this.requisicaoId = requisicaoId;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(String idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(String idCurso) {
        this.idCurso = idCurso;
    }

    public String getCursoId() {
        return cursoId;
    }

    public void setCursoId(String cursoId) {
        this.cursoId = cursoId;
    }

    public ArrayList getArrayDocumentos() {
        return arrayDocumentos;
    }

    public void setArrayDocumentos(ArrayList arrayDocumentos) {
        this.arrayDocumentos = arrayDocumentos;
    }

    public ArrayList getArrayStatus() {
        return arrayStatus;
    }

    public void setArrayStatus(ArrayList arrayStatus) {
        this.arrayStatus = arrayStatus;
    }

    public ArrayList getArrayDetalhes() {
        return arrayDetalhes;
    }

    public void setArrayDetalhes(ArrayList arrayDetalhes) {
        this.arrayDetalhes = arrayDetalhes;
    }
}
