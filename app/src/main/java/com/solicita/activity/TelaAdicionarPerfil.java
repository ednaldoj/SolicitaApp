package com.solicita.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.solicita.R;

public class TelaAdicionarPerfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_adicionar_perfil);
    }

    public void abrirPerfilDiscente(View view){
        Intent abrirPerfil = new Intent(getApplicationContext(), TelaInformacoesDiscente.class);
        startActivity(abrirPerfil);
    }
}
