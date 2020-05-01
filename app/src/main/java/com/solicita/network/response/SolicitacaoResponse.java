package com.solicita.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solicita.model.User;

public class SolicitacaoResponse extends BaseResponse {

    @Expose
    @SerializedName("id")
    int id;
    @Expose
    @SerializedName("data_pedido")
    String data_pedido;
    @Expose
    @SerializedName("hora_pedido")
    String hora_pedido;
    @Expose
    @SerializedName("perfil_id")
    int perfil_id;
    @Expose
    @SerializedName("aluno_id")
    int aluno_id;
    @Expose
    @SerializedName("update_at")
    String update_at;
    @Expose
    @SerializedName("created_at")
    int created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getPerfil_id() {
        return perfil_id;
    }

    public void setPerfil_id(int perfil_id) {
        this.perfil_id = perfil_id;
    }

    public int getAluno_id() {
        return aluno_id;
    }

    public void setAluno_id(int aluno_id) {
        this.aluno_id = aluno_id;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }
}
