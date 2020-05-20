package com.solicita.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.solicita.R;
import com.solicita.network.ApiClient;
import com.solicita.network.ApiInterface;
import com.solicita.network.response.DefaultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaRedefinirSenha extends AppCompatActivity {

    private TextInputEditText textEsqueciSenha;
    ApiInterface apiInterface;
    Button buttonRedefinirSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_redefinir_senha);

        inicializarComponentes();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        buttonRedefinirSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redefinirSenha();

            }
        });

    }

    public void redefinirSenha(){

        Call<DefaultResponse> responseCall = apiInterface.postEsqueciSenha(textEsqueciSenha.getText().toString());
        responseCall.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse dr = response.body();
                if (response.isSuccessful()){

                    if (response.code()==201){
                        Toast.makeText(TelaRedefinirSenha.this, dr.getMessage(), Toast.LENGTH_LONG).show();
                          startActivity(new Intent(TelaRedefinirSenha.this, LoginActivity.class));
                        finish();

                    }else{
                        Toast.makeText(TelaRedefinirSenha.this, dr.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(TelaRedefinirSenha.this, dr.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });

    }

    public void inicializarComponentes(){

        textEsqueciSenha=findViewById(R.id.textEsqueciSenha);
        buttonRedefinirSenha=findViewById(R.id.buttonRedefinirSenha);

    }
}
