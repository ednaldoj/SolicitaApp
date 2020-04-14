package com.solicita.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solicita.model.Aluno;

public class UserResponse extends BaseResponse {

    @Expose
    @SerializedName("status")
    String status;
    @Expose
    @SerializedName("token")
    String token;
    @Expose
    @SerializedName("expires_in") int expires_in;
    @Expose
    @SerializedName("aluno")
    Aluno aluno;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
}
