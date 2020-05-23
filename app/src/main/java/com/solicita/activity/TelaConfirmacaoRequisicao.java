package com.solicita.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.solicita.R;
import com.solicita.helper.SharedPrefManager;

import java.util.ArrayList;

public class TelaConfirmacaoRequisicao extends AppCompatActivity {


    private TextView textProtNome, textProtCurso, textProtVinculo, textProtData, textProtDocumentos, textNomeUsuario;

    private SharedPrefManager sharedPrefManager;

    Button buttonLogout, buttonHome, buttonVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_confirmacao_requisicao);

        sharedPrefManager = new SharedPrefManager(this);

        inicializarComponentes();

        textNomeUsuario.setText(sharedPrefManager.getSPNome());

        buttonHome.setOnClickListener(v -> irHome());

        buttonLogout.setOnClickListener(v -> logoutApp());

        buttonVoltar.setOnClickListener(v -> irHome());

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
        buttonLogout = findViewById(R.id.buttonLogout);
        buttonHome = findViewById(R.id.buttonHome);
        buttonVoltar = findViewById(R.id.buttonVoltar);
        textNomeUsuario = findViewById(R.id.textNomeUsuario);


    }
    public void logoutApp() {
        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_STATUS_LOGIN, false);
        startActivity(new Intent(TelaConfirmacaoRequisicao.this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }
    public void irHome(){
        startActivity(new Intent(TelaConfirmacaoRequisicao.this, TelaHomeAluno.class));

    }
}