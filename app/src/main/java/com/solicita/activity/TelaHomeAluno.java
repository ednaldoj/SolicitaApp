package com.solicita.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.solicita.R;
import com.google.firebase.auth.FirebaseAuth;
import com.solicita.config.UsuarioFirebase;
import com.solicita.model.Aluno;

public class TelaHomeAluno extends AppCompatActivity {

    private FirebaseAuth discente = FirebaseAuth.getInstance();
    TextView textNomeUsuario;
    private Aluno usuarioLogado;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_home_aluno);

        textNomeUsuario=findViewById(R.id.textNomeUsuario);

        //Configuracoes iniciais
        usuarioLogado = new UsuarioFirebase().getDadosUsuarioLogado();


        //Recuperar dados do usuário
        FirebaseUser usuarioPerfil = UsuarioFirebase.getUsuarioAtual();
        textNomeUsuario.setText( usuarioPerfil.getDisplayName() );

    }
    public void irTelaInformacoesDiscente(View view){
        Intent irTelaInformacoesDiscente = new Intent(getApplicationContext(), TelaInformacoesDiscente.class);
        startActivity(irTelaInformacoesDiscente);
    }
    public void irTelaListarDocumentosSolicitados(View view){
        Intent irTelaListarDocumentosSolicitados = new Intent(getApplicationContext(), TelaListarDocumentosSolicitados.class);
        startActivity(irTelaListarDocumentosSolicitados);
    }
    public void irTelaSolicitarDocumentos(View view){
        Intent irTelaSolicitarDocumentos = new Intent(getApplicationContext(), TelaSolicitarDocumentos.class);
        startActivity(irTelaSolicitarDocumentos);
    }
    public void logoutDiscente(View view){
        discente.signOut();
        Intent intent = new Intent(TelaHomeAluno.this, LoginActivity.class);
        startActivity(intent);

    }

}
