package com.solicita.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.solicita.R;
import com.solicita.helper.SharedPrefManager;

import java.util.ArrayList;

public class TelaConfirmacaoRequisicao extends AppCompatActivity {


    private TextView textProtNome, textProtCurso, textProtVinculo, textProtData, textProtDocumentos;

    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_confirmacao_requisicao);

        sharedPrefManager = new SharedPrefManager(this);

        inicializarComponentes();

        //Recuperar dados enviados
        Bundle dados = getIntent().getExtras();

        String curso = dados.getString("curso");
        String situacao = dados.getString("situacao");

        String data = dados.getString("data");
        String hora = dados.getString("hora");
        ArrayList solicitados = (ArrayList<String>) dados.getSerializable("solicitados");

        String convert = solicitados.toString().replace("[", " ").replace("]", "").replace(",", "\n\n");

        for(int i=0; i<solicitados.size(); i++){
            System.out.println(solicitados.get(i));
        }
        //Configura valores recuperados

        textProtNome.setText(sharedPrefManager.getSPNome());
        textProtData.setText(data + " " + hora);
        textProtDocumentos.setText(convert);
        textProtCurso.setText(curso);
        textProtVinculo.setText(situacao);

    }
    public void inicializarComponentes(){

        textProtNome = findViewById(R.id.textProtNome);
        textProtCurso = findViewById(R.id.textProtCurso);
        textProtVinculo = findViewById(R.id.textProtVinculo);
        textProtData = findViewById(R.id.textProtData);
        textProtDocumentos = findViewById(R.id.textProtDocumentos);

    }public void abrirHome(View view){
        Intent abrirHome = new Intent(getApplicationContext(), TelaHomeAluno.class);
        startActivity(abrirHome);
    }
}