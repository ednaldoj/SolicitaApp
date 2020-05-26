package com.solicita.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.solicita.R;
import com.google.android.material.textfield.TextInputEditText;
import com.solicita.helper.SharedPrefManager;
import com.solicita.model.User;
import com.solicita.network.ApiClient;
import com.solicita.network.ApiInterface;
import com.solicita.network.response.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public TextInputEditText editLoginEmail, editLoginSenha;
    public Button buttonLogin, buttonCadastrar;

    Context context;
    ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

        inicializarComponentes();

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TelaCadastrarDiscente.class);
                startActivity(intent);
            }
        });


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validado = true;
                if (editLoginEmail.getText().length()==0){
                    Toast.makeText(getApplicationContext(), "Preecha o campo e-mail.", Toast.LENGTH_LONG).show();
                    validado=false;
                }
                if (editLoginSenha.getText().length()==0){
                    Toast.makeText(getApplicationContext(), "Preencha o campo senha.", Toast.LENGTH_LONG).show();
                    validado=false;
                }
                if (validado){
                    loginApp();
                }
            }
        });

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sharedPrefManager = new SharedPrefManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando...");
        progressDialog.setCancelable(false);


        if (sharedPrefManager.getSPStatusLogin()){
            startActivity(new Intent(LoginActivity.this, TelaHomeAluno.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }

    public void loginApp(){

        progressDialog.show();
        Call<UserResponse> postLogin = apiInterface.postLogin(editLoginEmail.getText().toString(),
                editLoginSenha.getText().toString());
        postLogin.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                progressDialog.dismiss();

                if (response.code() == 200) {
                    User user = response.body().getUser();
                    String primeiroNome = user.getName();
                    String[] s = primeiroNome.trim().split(" ");
                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NOME, s[0]);
                   // sharedPrefManager.saveSPString(SharedPrefManager.SP_NOME, user.getName());
                    sharedPrefManager.saveSPString(SharedPrefManager.SP_TOKEN, "Bearer " +response.body().getToken());
                    System.out.println(response.body().getToken());
                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_STATUS_LOGIN, true);
                    startActivity(new Intent(context, TelaHomeAluno.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                } else {
                    Toast.makeText(context, "Email ou senha incorretos.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
    public void abrirCadastro(View view){
        Intent intent = new Intent(getApplicationContext(), TelaCadastrarDiscente.class);
        startActivity(intent);
    }
    public void recuperarSenha(View view){
        Intent intent = new Intent(getApplicationContext(), TelaRedefinirSenha.class);
        startActivity(intent);
    }

    public void inicializarComponentes(){
        editLoginEmail = findViewById(R.id.editLoginEmail);
        editLoginSenha = findViewById(R.id.editLoginSenha);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonCadastrar = findViewById(R.id.buttonAdicionarPerfil);
    }
}
