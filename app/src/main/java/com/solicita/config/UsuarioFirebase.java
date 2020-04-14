package com.solicita.config;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.solicita.model.Aluno;

/**
 * Created by jamiltondamasceno
 */

public class UsuarioFirebase {

    public static FirebaseUser getUsuarioAtual(){

        FirebaseAuth discente = ConfiguracaoFirebase.getFirebaseAutenticacao();
        return discente.getCurrentUser();

    }
    public static String getIdUsuario(){
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        return autenticacao.getCurrentUser().getUid();
    }

    public static void atualizarNomeUsuario(String nome){

        try {

            //Usuario logado no App
            FirebaseUser usuarioLogado = getUsuarioAtual();

            //Configurar objeto para alteração do perfil
            UserProfileChangeRequest profile = new UserProfileChangeRequest
                    .Builder()
                    .setDisplayName( nome )
                    .build();
            usuarioLogado.updateProfile( profile ).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if( !task.isSuccessful() ){
                        Log.d("Perfil","Erro ao atualizar nome de perfil." );
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static Aluno getDadosUsuarioLogado(){

        FirebaseUser firebaseUser = getUsuarioAtual();

        Aluno discente = new Aluno();
        discente.setEmail(firebaseUser.getEmail());
        discente.setCPF(discente.getCPF());
        discente.setNome(firebaseUser.getDisplayName());
      //  discente.setId(firebaseUser.getUid());

        return discente;

    }
}

















