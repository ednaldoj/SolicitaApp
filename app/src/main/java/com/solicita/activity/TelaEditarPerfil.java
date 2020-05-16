package com.solicita.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.solicita.R;
import com.solicita.helper.SharedPrefManager;
import com.solicita.model.User;
import com.solicita.network.ApiClient;
import com.solicita.network.ApiInterface;
import com.solicita.network.response.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaEditarPerfil extends AppCompatActivity {

    private TextInputEditText editNomePerfil, editEmailPerfil;
    private Button buttonSalvarAlteracoes;
    ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_editar_perfil);

        sharedPrefManager = new SharedPrefManager(this);
        apiInterface= ApiClient.getClient().create(ApiInterface.class);

        inicializarComponentes();
        buscarInfoJSON();

        //Salvar alterações
        buttonSalvarAlteracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarPerfil();
                Toast.makeText(TelaEditarPerfil.this, "Informações atualizadas com sucesso!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(TelaEditarPerfil.this, TelaInformacoesDiscente.class));
                finish();
            }
        });
    }
    private void buscarInfoJSON(){
        Call<UserResponse> userResponseCall = apiInterface.getEdit(sharedPrefManager.getSPToken());
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                User user = response.body().getUser();
                String nome = user.getName();
                String email = user.getEmail();

                editNomePerfil.setText(nome);
                editEmailPerfil.setText(email);
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }
    public void editarPerfil(){

        String name = editNomePerfil.getText().toString();
        String email = editEmailPerfil.getText().toString();

        Call<UserResponse> userResponseCall = apiInterface.postEdit(name, email, sharedPrefManager.getSPToken());
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){

                }else{
                    Toast.makeText(TelaEditarPerfil.this, "Erro ao atualizar informações! Verifique os dados e tente novamente.", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }

    public void inicializarComponentes(){

        editNomePerfil         = findViewById(R.id.editNomePerfil);
        editEmailPerfil        = findViewById(R.id.editEmailPerfil);
        buttonSalvarAlteracoes = findViewById(R.id.buttonSalvarAlteracoes);
        editEmailPerfil.setFocusable(false);
    }

    public void irTelaInformacoesDiscente(View view){
        Intent irTelaInformacoesDiscente = new Intent(getApplicationContext(), TelaInformacoesDiscente.class);
        startActivity(irTelaInformacoesDiscente);
    }
}
