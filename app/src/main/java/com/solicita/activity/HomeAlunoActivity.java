package com.solicita.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.solicita.R;
import com.solicita.helper.SharedPrefManager;
import com.solicita.network.ApiClient;
import com.solicita.network.ApiInterface;
import com.solicita.network.response.DefaultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeAlunoActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    TextView textNomeUsuario;
    Button buttonLogout, buttonHome;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_aluno);

        sharedPrefManager = new SharedPrefManager(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        inicializarComponentes();

        textNomeUsuario.setText(sharedPrefManager.getSPNome());

        buttonLogout.setOnClickListener(v -> logoutApp());
    }
    public void logoutApp() {

        Call<DefaultResponse> responseCall = apiInterface.postLogout(sharedPrefManager.getSPToken());

        responseCall.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse dr = response.body();
                Toast.makeText(HomeAlunoActivity.this, dr.getMessage(), Toast.LENGTH_SHORT).show();
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_STATUS_LOGIN, false);
                startActivity(new Intent(HomeAlunoActivity.this, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });

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
