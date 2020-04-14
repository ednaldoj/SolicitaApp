package com.solicita.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.solicita.helper.MaskCustom;
import com.solicita.helper.ValidacaoCPF;
import com.solicita.model.Aluno;
import com.solicita.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class TelaCadastrarDiscente extends AppCompatActivity {

    public TextInputEditText campoNome, campoCPF, campoEmail, campoSenha, campoConfirmarSenha;
    public Spinner campoVinculo, campoUnidade, campoCurso;
    public FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastrar_discente);


        //Inicializar componentes
        campoNome = findViewById(R.id.textProtNome);
        campoCPF = findViewById(R.id.textInfoCPF);
        campoCPF.addTextChangedListener(MaskCustom.insert(MaskCustom.CPF_MASK, campoCPF));
        campoVinculo=(findViewById(R.id.spinnerVinculo));
        campoUnidade = findViewById(R.id.spinnerUnidadeAcademica);
        campoCurso = findViewById(R.id.spinnerCurso);
        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        campoConfirmarSenha = findViewById(R.id.editConfirmarSenha);

    }

    public void validarCadastroUsuario(View view) {

        //Recuperar textos dos campos
        String textoNome = campoNome.getText().toString();
        String textoCPF  = campoCPF.getText().toString();

        String textoVinculo  = campoVinculo.getSelectedItem().toString();
        String textoUnidade  = campoUnidade.getSelectedItem().toString();
        String textoCurso  = campoCurso.getSelectedItem().toString();
        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();
        String textoConfirmarSenha = campoConfirmarSenha.getText().toString();

        ValidacaoCPF validacaoCPF = new ValidacaoCPF();

        if (!textoNome.isEmpty()) {//verifica nome
            if (validacaoCPF.isCPF(textoCPF)) {//verifica cpf valido
                if (!textoCPF.isEmpty()) {//verifica campo cpf
                    if (!textoVinculo.isEmpty()) {//verifica vinculo
                        if (!textoUnidade.isEmpty()) {//verifica unidade academica
                            if (!textoCurso.isEmpty()) {//verifica curso
                                if (!textoEmail.isEmpty()) {//verifica e-mail
                                    if (!textoSenha.isEmpty()) {//verifica senha
                                        if (!textoConfirmarSenha.isEmpty()) {//verifica confirmacao de senha
                                            if (textoSenha.equals(textoConfirmarSenha)) {

                                                Aluno discente = new Aluno();

                                                discente.setNome(textoNome);
                                                discente.setCPF(textoCPF);
                                                discente.setTipoVinculo(textoVinculo);
                                                discente.setUnidadeAcademica(textoUnidade);
                                                discente.setTipoCurso(textoCurso);
                                                discente.setEmail(textoEmail);
                                                discente.setSenha(textoSenha);
                                                discente.setConfirmarSenha(textoConfirmarSenha);

                                                //cadastrarUsuario(discente);

                                            } else {
                                                Toast.makeText(TelaCadastrarDiscente.this, "As senhas devem ser iguais", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(TelaCadastrarDiscente.this, "Confirme a senha", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(TelaCadastrarDiscente.this, "Informe a senha", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(TelaCadastrarDiscente.this, "Preencha o campo e-mail", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(TelaCadastrarDiscente.this, "Selecione o curso", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(TelaCadastrarDiscente.this, "Selecione a unidade acadêmica", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(TelaCadastrarDiscente.this, "Selecione o tipo de vínculo", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TelaCadastrarDiscente.this, "Preecha o campo CPF.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(TelaCadastrarDiscente.this, "O CPF informado é inválido", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(TelaCadastrarDiscente.this, "Preencha o campo nome", Toast.LENGTH_SHORT).show();
        }
    }/*
    public void cadastrarUsuario(final Aluno discente){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                discente.getEmail(),
                discente.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ){
                    try {

                        //Salvar dados no firebase
                        String idUsuario = task.getResult().getAluno().getUid();
                        discente.setId( idUsuario );
                        discente.salvar();

                        //Salvar dados no profile do Firebase
                        UsuarioFirebase.atualizarNomeUsuario( discente.getNome() );

                        Toast.makeText(TelaCadastrarDiscente.this,
                                "Cadastro com sucesso",
                                Toast.LENGTH_SHORT).show();

                        startActivity( new Intent(getApplicationContext(), LoginActivity.class));
                        finish();

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else {

                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha maior!";
                    }catch ( FirebaseAuthInvalidCredentialsException e){
                        excecao= "Por favor, digite um e-mail válido";
                    }catch ( FirebaseAuthUserCollisionException e){
                        excecao = "Este conta já foi cadastrada";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: "  + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(TelaCadastrarDiscente.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();

                }

            }
        });

    }*/
    public void abrirTelaLogin(View view){
        Intent abrirTelaLogin = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(abrirTelaLogin);
    }

}