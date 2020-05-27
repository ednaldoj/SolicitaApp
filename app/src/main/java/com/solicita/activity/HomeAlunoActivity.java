package com.solicita.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.solicita.R;
import com.solicita.helper.SharedPrefManager;

public class HomeAlunoActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    TextView textNomeUsuario;
    Button buttonLogout, buttonHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_aluno);

        sharedPrefManager = new SharedPrefManager(this);
        inicializarComponentes();

        textNomeUsuario.setText(sharedPrefManager.getSPNome());

        buttonHome.setOnClickListener(v -> irHome());

        buttonLogout.setOnClickListener(v -> logoutApp());
    }
    public void logoutApp() {
        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_STATUS_LOGIN, false);
        startActivity(new Intent(HomeAlunoActivity.this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }
    public void irHome(){
        startActivity(new Intent(HomeAlunoActivity.this, HomeAlunoActivity.class));

    }


    public void irTelaInformacoesDiscente(View view){
        Intent irTelaInformacoesDiscente = new Intent(getApplicationContext(), InformacoesDiscenteActivity.class);
        startActivity(irTelaInformacoesDiscente);
    }
    public void irTelaListarDocumentosSolicitados(View view){
        Intent irTelaListarDocumentosSolicitados = new Intent(getApplicationContext(), ListarDocumentosSolicitadosActivity.class);
        startActivity(irTelaListarDocumentosSolicitados);
    }
    public void irTelaSolicitarDocumentos(View view){
        Intent irTelaSolicitarDocumentos = new Intent(getApplicationContext(), SolicitarDocumentosActivity.class);
        startActivity(irTelaSolicitarDocumentos);
    }
    public void inicializarComponentes(){
        textNomeUsuario = findViewById(R.id.textNomeUsuario);
        buttonLogout = findViewById(R.id.buttonLogout);
        buttonHome = findViewById(R.id.buttonHome);
    }
}
