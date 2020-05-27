package com.solicita.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.solicita.R;
import com.solicita.helper.SharedPrefManager;
import com.solicita.network.ApiClient;
import com.solicita.network.ApiInterface;
import com.solicita.network.response.DefaultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RedefinirSenhaActivity extends AppCompatActivity {

    private TextInputEditText textEsqueciSenha;
    ApiInterface apiInterface;
    Button buttonRedefinirSenha, buttonLogout, buttonHome;
    SharedPrefManager sharedPrefManager;
    TextView textNomeUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);

        inicializarComponentes();

        sharedPrefManager = new SharedPrefManager(this);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        buttonRedefinirSenha.setOnClickListener(v -> redefinirSenha());

    }

    public void redefinirSenha(){

        Call<DefaultResponse> responseCall = apiInterface.postEsqueciSenha(textEsqueciSenha.getText().toString());
        responseCall.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse dr = response.body();
                if (response.isSuccessful()){

                    if (response.code()==201){
                        Toast.makeText(RedefinirSenhaActivity.this, dr.getMessage(), Toast.LENGTH_LONG).show();
                          startActivity(new Intent(RedefinirSenhaActivity.this, LoginActivity.class));
                        finish();

                    }else{
                        Toast.makeText(RedefinirSenhaActivity.this, dr.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(RedefinirSenhaActivity.this, "Falha na comunicação com o servidor.", Toast.LENGTH_LONG).show();

                }
            }
            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });

    }

/*    public void logoutApp() {
        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_STATUS_LOGIN, false);
        startActivity(new Intent(RedefinirSenhaActivity.this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }
    public void irHome(){
        startActivity(new Intent(RedefinirSenhaActivity.this, HomeAlunoActivity.class));

    }*/

    public void inicializarComponentes(){

        textEsqueciSenha=findViewById(R.id.textEsqueciSenha);
        buttonRedefinirSenha=findViewById(R.id.buttonRedefinirSenha);
        buttonLogout = findViewById(R.id.buttonLogout);
        buttonHome = findViewById(R.id.buttonHome);
        textNomeUsuario = findViewById(R.id.textNomeUsuario);


    }
}
