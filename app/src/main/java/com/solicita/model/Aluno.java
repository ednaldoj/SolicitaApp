package com.solicita.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Aluno {

    String id;

    @Expose
    @SerializedName("cpf") String cpf;

    @Expose
    @SerializedName("user_id") String user_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
