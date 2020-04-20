package com.solicita.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.solicita.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.solicita.config.ConfiguracaoFirebase;
import com.solicita.config.UsuarioFirebase;
import com.solicita.model.User;

public class TelaInformacoesDiscente extends AppCompatActivity {

    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    public TextView textInfoNome, textInfoCPF, textInfoVinculo, textInfoUnidadeAcademica, textInfoCurso, textInfoEmail;
    public User usuarioLogado;
    private DatabaseReference discentRef;
    private String idUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_informacoes_discente);

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

        //inicializar componentes
        inicializarComponentes();

        //Recuperar dados do usuário
        FirebaseUser usuarioPerfil = UsuarioFirebase.getUsuarioAtual();

        recuperarDados();

        textInfoNome.setText( usuarioPerfil.getDisplayName() );
        textInfoEmail.setText(usuarioPerfil.getEmail());


    }
    private void recuperarDados(){
        DatabaseReference disRef = discentRef.child("discentes").child(idUsuarioLogado);
        disRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    User discente = dataSnapshot.getValue(User.class);
                    textInfoCPF.setText(discente.getCpf());
                    textInfoVinculo.setText(discente.getVinculo());
                    textInfoUnidadeAcademica.setText(discente.getUnidade());
                    textInfoCurso.setText(discente.getCursos());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void inicializarComponentes(){

        textInfoNome         = findViewById(R.id.textProtNome);
        textInfoCPF         = findViewById(R.id.textInfoCPF);
        textInfoVinculo         = findViewById(R.id.textProtVinculo);
        textInfoUnidadeAcademica         = findViewById(R.id.textInfoUnidadeAcademica);
        textInfoCurso         = findViewById(R.id.textAdapCurso);
        textInfoEmail         = findViewById(R.id.textInfoEmail);


    }


    public void irTelaEditarPerfil(View view){
        Intent irTelaEditarPerfil = new Intent(getApplicationContext(), TelaEditarPerfil.class);
        startActivity(irTelaEditarPerfil);
    }
    public void irTelaAlterarSenha(View view){
        Intent irTelaAlterarSenha = new Intent(getApplicationContext(), TelaAlterarSenha.class);
        startActivity(irTelaAlterarSenha);
    }
    public void irTelaAdicionarPerfil(View view){
        Intent irTelaAdicionarPerfil = new Intent(getApplicationContext(), TelaAdicionarPerfil.class);
        startActivity(irTelaAdicionarPerfil);
    }

    public void abrirExcluirPerfil(View view){
        AlertDialog.Builder dialogExluirPerfil = new AlertDialog.Builder(this);

        dialogExluirPerfil.setTitle("Exclusão de Perfil Acadêmico");
        dialogExluirPerfil.setMessage("Deseja realmente excluir o perfil selecionado?");

        dialogExluirPerfil.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Perfil exluído com sucesso", Toast.LENGTH_LONG).show();
            }
        });
        dialogExluirPerfil.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogExluirPerfil.create();
        dialogExluirPerfil.show();

    }

}






















