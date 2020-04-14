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
import com.solicita.model.Aluno;
import com.solicita.network.ApiClient;
import com.solicita.network.ApiInterface;
import com.solicita.network.response.UserResponse;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText editLoginEmail, editLoginSenha;
    public Button buttonLogin;

    Context context;
    ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        ButterKnife.bind(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sharedPrefManager = new SharedPrefManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);


        if (sharedPrefManager.getSPSudahLogin()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }

    @OnClick(R.id.buttonLogin) void login() {
        progressDialog.show();
        Call<UserResponse> postLogin = apiInterface.postLogin(editLoginEmail.getText().toString(),
                editLoginSenha.getText().toString());
        postLogin.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                progressDialog.dismiss();

                if (response.code() == 200) {
                    Aluno aluno = response.body().getAluno();
                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, aluno.getNome());
                    sharedPrefManager.saveSPString(SharedPrefManager.SP_TOKEN, "Bearer " +response.body().getToken());
                    System.out.println(response.body().getToken());
                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                    startActivity(new Intent(context, TelaHomeAluno.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                } else {
                    Toast.makeText(context, "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public void abrirTelaCadastro(View view){
        Intent intent = new Intent(LoginActivity.this, TelaCadastrarDiscente.class);
        startActivity(intent);

    }

    public void recuperarSenha(View view){
        Intent intent =new Intent(LoginActivity.this, TelaRedefinirSenha.class);
        startActivity(intent);
    }

}
