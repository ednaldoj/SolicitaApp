package com.solicita.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.solicita.R;
import com.solicita.config.ConfiguracaoFirebase;
import com.solicita.config.UsuarioFirebase;
import com.solicita.helper.SharedPrefManager;
import com.solicita.model.User;
import com.solicita.network.ApiClient;
import com.solicita.network.ApiInterface;
import com.solicita.network.response.DefaultResponse;
import com.solicita.network.response.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaAlterarSenha extends AppCompatActivity {

    private TextInputEditText campoConfNovaSenha, campoSenhaAtual, campoNovaSenha;
    private Button buttonAlterarSenha, buttonLogout, buttonHome;
    ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;
    TextView textNomeUsuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_alterar_senha);

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
                Toast.makeText(TelaAlterarSenha.this, dr.getMessage(), Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()){
                    if(response.code()==201){
                        Toast.makeText(TelaAlterarSenha.this, dr.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(TelaAlterarSenha.this, TelaInformacoesDiscente.class));

                    }
                }else{
                    Toast.makeText(TelaAlterarSenha.this, "Falha ao atualizar senha!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(TelaAlterarSenha.this, "Falha na autenticação.", Toast.LENGTH_SHORT).show();

                } else {
                    final String novaSenha = campoNovaSenha.getText().toString();
                    final String confNovaSenha = campoConfNovaSenha.getText().toString();

                    if (novaSenha.equals(confNovaSenha)) {
                        usuario.updatePassword(novaSenha).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(TelaAlterarSenha.this,
                                            "Senha atualizada com sucesso!",
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(TelaAlterarSenha.this,
                                            "Falha ao atualizar senha!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(TelaAlterarSenha.this,
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
        startActivity(new Intent(TelaAlterarSenha.this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }
    public void irHome(){
        startActivity(new Intent(TelaAlterarSenha.this, TelaHomeAluno.class));

    }
    public void irTelaInformacoesDiscente(View view) {
        Intent irTelaInformacoesDiscente = new Intent(getApplicationContext(), TelaInformacoesDiscente.class);
        startActivity(irTelaInformacoesDiscente);
    }
}
