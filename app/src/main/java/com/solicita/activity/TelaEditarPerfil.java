package com.solicita.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;
import com.solicita.R;
import com.solicita.config.UsuarioFirebase;
import com.solicita.model.User;

public class TelaEditarPerfil extends AppCompatActivity {

    private TextInputEditText editNomePerfil, editEmailPerfil;
    private Button buttonSalvarAlteracoes;
    private User usuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_editar_perfil);


        //Configuracoes iniciais
        usuarioLogado = new UsuarioFirebase().getDadosUsuarioLogado();

        //inicializar componentes
        inicializarComponentes();

        //Recuperar dados do usuário
        FirebaseUser usuarioPerfil = UsuarioFirebase.getUsuarioAtual();
        editNomePerfil.setText( usuarioPerfil.getDisplayName() );
        editEmailPerfil.setText( usuarioPerfil.getEmail() );

        //Salvar alterações
        buttonSalvarAlteracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeAtualizado = editNomePerfil.getText().toString();

                //Atualizar nome no perfil
                UsuarioFirebase.atualizarNomeUsuario(nomeAtualizado);

                //Atualizar nome no banco de dados
                usuarioLogado.setNome(nomeAtualizado);
             //   usuarioLogado.atualizar();

                Toast.makeText(TelaEditarPerfil.this, "Dados alterados com sucesso!", Toast.LENGTH_LONG).show();
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
