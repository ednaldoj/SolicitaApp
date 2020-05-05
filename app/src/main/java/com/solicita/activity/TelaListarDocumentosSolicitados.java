package com.solicita.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.solicita.config.ConfiguracaoFirebase;
import com.solicita.config.UsuarioFirebase;
import com.solicita.model.Requisicao;
import com.solicita.R;
import com.solicita.adapter.AdapterDocumentos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TelaListarDocumentosSolicitados extends AppCompatActivity {

    public RecyclerView recyclerRequisicoes;
    public List<Requisicao> requisicoes = new ArrayList<>();
    public AdapterDocumentos adapterDocumentos;

    private DatabaseReference documentosUsuarioRef;
    private String idUsuarioLogado;
    private TextView textSolicitados;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_listar_documetos_solicitados);

        inicializarComponentes();


        //Configurar RecyclerView
        recyclerRequisicoes.setLayoutManager(new LinearLayoutManager(this));
        recyclerRequisicoes.setHasFixedSize(true);
        adapterDocumentos=new AdapterDocumentos(requisicoes, this);
        recyclerRequisicoes.setAdapter(adapterDocumentos);

    }
    public void irTelaHomeAluno(View view){
        Intent irTelaHomeAluno = new Intent(getApplicationContext(), TelaHomeAluno.class);
        startActivity(irTelaHomeAluno);
    }

    public void inicializarComponentes(){
        recyclerRequisicoes=findViewById(R.id.recyclerRequisicoes);

    }

}









