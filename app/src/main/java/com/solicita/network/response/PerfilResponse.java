package com.solicita.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solicita.model.Aluno;
import com.solicita.model.Perfil;
import com.solicita.model.User;

public class PerfilResponse extends BaseResponse{

    @Expose
    @SerializedName("perfil")
    Perfil perfil;

    @Expose
    @SerializedName("user")
    User user;

    @Expose
    @SerializedName("aluno")
    Aluno aluno;

 /*   @Expose
    @SerializedName("unidades")
    Unidade unidade;*/

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

/*    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }*/
}
