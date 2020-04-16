package com.solicita.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.solicita.R;
import com.solicita.config.ConfiguracaoFirebase;
import com.solicita.config.UsuarioFirebase;
import com.solicita.model.User;

import java.util.ArrayList;

public class TelaConfirmacaoRequisicao extends AppCompatActivity {


    private TextView textProtNome, textProtCurso, textProtVinculo, textProtData, textProtDocumentos;
    private DatabaseReference discentRef;
    private String idUsuarioLogado;
    private User usuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_confirmacao_requisicao);

        //Configuracoes iniciais
        usuarioLogado = new UsuarioFirebase().getDadosUsuarioLogado();
        discentRef = ConfiguracaoFirebase.getFirebaseDatabase();//
        idUsuarioLogado=UsuarioFirebase.getIdUsuario();

        discentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("FIREBASE", dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        inicializarComponentes();

        //Recuperar dados enviados
        Bundle dados = getIntent().getExtras();
        String data = dados.getString("data");
        ArrayList documentos = dados.getStringArrayList("documentos");

        //Configura valores recuperados
        textProtData.setText(data);
        textProtDocumentos.setText(String.valueOf(documentos));

        recuperarDados();


    }
    private void recuperarDados(){
        DatabaseReference disRef = discentRef.child("discentes").child(idUsuarioLogado);
        disRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    User discente = dataSnapshot.getValue(User.class);
                    textProtNome.setText(discente.getNome());
                    textProtCurso.setText(discente.getTipoCurso());
                    textProtVinculo.setText(discente.getTipoVinculo());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void inicializarComponentes(){

        textProtNome = findViewById(R.id.textProtNome);
        textProtCurso = findViewById(R.id.textProtCurso);
        textProtVinculo = findViewById(R.id.textProtVinculo);
        textProtData = findViewById(R.id.textProtData);
        textProtDocumentos = findViewById(R.id.textProtDocumentos);

    }public void abrirHome(View view){
        Intent abrirHome = new Intent(getApplicationContext(), TelaHomeAluno.class);
        startActivity(abrirHome);
    }
}
