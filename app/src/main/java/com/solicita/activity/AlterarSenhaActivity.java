package com.solicita.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class AlterarSenhaActivity extends AppCompatActivity {

    private TextInputEditText campoConfNovaSenha, campoSenhaAtual, campoNovaSenha;
    private Button buttonAlterarSenha, buttonLogout, buttonHome;
    ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;
    TextView textNomeUsuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_senha);

        sharedPrefManager = new SharedPrefManager(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);



        inicializarComponentes();

        textNomeUsuario.setText(sharedPrefManager.getSPNome());

        buttonHome.setOnClickListener(v -> irHome());

        buttonLogout.setOnClickListener(v -> logoutApp());

        buttonAlterarSenha.setOnClickListener(v -> alterarSenha());
    }
    public void alterarSenha(){
        String atual = campoSenhaAtual.getText().toString();
        String novaSenha = campoNovaSenha.getText().toString();
        String confNovaSenha = campoConfNovaSenha.getText().toString();

        Call<DefaultResponse> userResponseCall = apiInterface.postEditSenha(atual, novaSenha, confNovaSenha, sharedPrefManager.getSPToken());
        userResponseCall.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse dr = response.body();
                Toast.makeText(AlterarSenhaActivity.this, dr.getMessage(), Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()){
                    if(response.code()==201){
                        Toast.makeText(AlterarSenhaActivity.this, dr.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AlterarSenhaActivity.this, InformacoesDiscenteActivity.class));

                    }
                }else {

                    Toast.makeText(getApplicationContext(), "Falha na comunicação com o servidor.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AlterarSenhaActivity.this, LoginActivity.class));

                }
            }
            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });

    }

 /*   public void atualizarSenha() {
        usuario = FirebaseAuth.getInstance().getCurrentUser();
        String email = usuario.getEmail();

        final String senhaAtual = campoSenhaAtual.getText().toString();

        System.out.println(senhaAtual);

        final AuthCredential credencial = EmailAuthProvider.getCredential(email, senhaAtual);

        usuario.reauthenticate(credencial).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(AlterarSenhaActivity.this, "Falha na autenticação.", Toast.LENGTH_SHORT).show();

                } else {
                    final String novaSenha = campoNovaSenha.getText().toString();
                    final String confNovaSenha = campoConfNovaSenha.getText().toString();

                    if (novaSenha.equals(confNovaSenha)) {
                        usuario.updatePassword(novaSenha).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(AlterarSenhaActivity.this,
                                            "Senha atualizada com sucesso!",
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(AlterarSenhaActivity.this,
                                            "Falha ao atualizar senha!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(AlterarSenhaActivity.this,
                                "A nova senha deve ser igual a de confirmação!",
                                Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }*/
    public void inicializarComponentes() {

        campoSenhaAtual = findViewById(R.id.editSenhaAtual);
        campoNovaSenha = findViewById(R.id.editNovaSenha);
        campoConfNovaSenha = findViewById(R.id.editConfNovaSenha);
        buttonAlterarSenha = findViewById(R.id.buttonAlterarSenha);
        buttonLogout = findViewById(R.id.buttonLogout);
        buttonHome = findViewById(R.id.buttonHome);
        textNomeUsuario = findViewById(R.id.textNomeUsuario);


    }

    public void logoutApp() {
        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_STATUS_LOGIN, false);
        startActivity(new Intent(AlterarSenhaActivity.this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }
    public void irHome(){
        startActivity(new Intent(AlterarSenhaActivity.this, HomeAlunoActivity.class));

    }
    public void irTelaInformacoesDiscente(View view) {
        Intent irTelaInformacoesDiscente = new Intent(getApplicationContext(), InformacoesDiscenteActivity.class);
        startActivity(irTelaInformacoesDiscente);
    }
}
