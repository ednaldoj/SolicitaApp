package com.solicita.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.solicita.R;
import com.solicita.helper.SharedPrefManager;

public class TelaHomeAluno extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    TextView textNomeUsuario;
    Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_home_aluno);

        sharedPrefManager = new SharedPrefManager(this);
        inicializarComponentes();

        textNomeUsuario.setText(sharedPrefManager.getSPNome());

        buttonLogout.setOnClickListener(v -> logoutApp());
    }
    public void logoutApp() {
        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_STATUS_LOGIN, false);
        startActivity(new Intent(TelaHomeAluno.this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }


    public void irTelaInformacoesDiscente(View view){
        Intent irTelaInformacoesDiscente = new Intent(getApplicationContext(), TelaInformacoesDiscente.class);
        startActivity(irTelaInformacoesDiscente);
    }
    public void irTelaListarDocumentosSolicitados(View view){
        Intent irTelaListarDocumentosSolicitados = new Intent(getApplicationContext(), TelaListarDocumentosSolicitados.class);
        startActivity(irTelaListarDocumentosSolicitados);
    }
    public void irTelaSolicitarDocumentos(View view){
        Intent irTelaSolicitarDocumentos = new Intent(getApplicationContext(), TelaSolicitarDocumentos.class);
        startActivity(irTelaSolicitarDocumentos);
    }
    public void inicializarComponentes(){
        textNomeUsuario = findViewById(R.id.textNomeUsuario);
        buttonLogout = findViewById(R.id.buttonLogout);
    }
}
