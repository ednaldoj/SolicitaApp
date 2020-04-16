package com.solicita.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.solicita.model.User;

public class TelaAlterarSenha extends AppCompatActivity {

    private TextInputEditText campoConfNovaSenha, campoSenhaAtual, campoNovaSenha;
    private Button buttonAlterarSenha;
    private User usuarioLogado;
    private FirebaseAuth autenticacao;
    private FirebaseUser usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_alterar_senha);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        //Configuracoes iniciais
        usuarioLogado = new UsuarioFirebase().getDadosUsuarioLogado();

        //inicializar componentes
        inicializarComponentes();

        buttonAlterarSenha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                atualizarSenha();

            }
        });
    }

    public void atualizarSenha() {
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
    }
    public void inicializarComponentes() {

        campoSenhaAtual = findViewById(R.id.editSenhaAtual);
        campoNovaSenha = findViewById(R.id.editNovaSenha);
        campoConfNovaSenha = findViewById(R.id.editConfNovaSenha);
        buttonAlterarSenha = findViewById(R.id.buttonAlterarSenha);

    }
    public void irTelaInformacoesDiscente(View view) {
        Intent irTelaInformacoesDiscente = new Intent(getApplicationContext(), TelaInformacoesDiscente.class);
        startActivity(irTelaInformacoesDiscente);
    }
}
