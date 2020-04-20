package com.solicita.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.solicita.helper.MaskCustom;
import com.solicita.helper.ValidacaoCPF;
import com.solicita.R;
import com.google.android.material.textfield.TextInputEditText;
import com.solicita.network.ApiClient;
import com.solicita.network.ApiInterface;
import com.solicita.network.response.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaCadastrarDiscente extends AppCompatActivity {

    TextInputEditText campoNome, campoCPF, campoEmail, campoSenha, campoConfirmarSenha;
    Spinner campoVinculo, campoUnidade, campoCurso;
    Button buttonCadastrar;
    ApiInterface apiInterface;
    Call<UserResponse> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastrar_discente);

        inicializarComponentes();

        buttonCadastrar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });
    }

    public void cadastrar() {

        String name = "Junior";
        String cpf = "00011122233";
        String vinculo = "1";
        String unidade = "1";
        String cursos = "1";
        String email = "junior@yahoo.com";
        String password = "77788899";

        call = apiInterface.postCadastro(name, cpf, vinculo, unidade, cursos, email, password);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TelaCadastrarDiscente.this, "Cadastro realizado com sucesso! Verifique seu e-mail.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(TelaCadastrarDiscente.this, LoginActivity.class));
                    finish();
                } else {

                    Toast.makeText(TelaCadastrarDiscente.this, "Erro ao realizar cadastro! Verifique os dados e tente novamente.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });

        /*

        String name = campoNome.getText().toString();
        String cpf  = campoCPF.getText().toString();

        int vinculo;
        String textoVinculo  = campoVinculo.getSelectedItem().toString();

            if(textoVinculo.equals("Matriculado")){
                vinculo = 1;
            }else if (textoVinculo.equals("Egresso")){
                vinculo = 2;
            }else if (textoVinculo.equals("Especial")){
                vinculo = 3;
            }else if (textoVinculo.equals("REMT - Regime Especial de Movimentação Temporária")){
                vinculo = 4;
            }else if (textoVinculo.equals("Desistente")){
                vinculo = 5;
            }else if (textoVinculo.equals("Matrícula Trancada")){
                vinculo = 6;
            }else{
                vinculo = 7;
            }

            int unidade;
        String textoUnidade  = campoUnidade.getSelectedItem().toString();
            if (textoUnidade.equals("UAG - Unidade Acadêmica de Garanhuns")){
                unidade = 1;
            }else{
                unidade = 1;
            }
            int cursos;
        String textoCurso  = campoCurso.getSelectedItem().toString();
        if(textoCurso.equals("Agronomia")){
            cursos = 1;
        }else if (textoCurso.equals("Ciência da Computação")){
            cursos = 2;
        }else if (textoCurso.equals("Engenharia de Alimentos")){
            cursos = 3;
        }else if (textoCurso.equals("Letras")){
            cursos = 4;
        }else if (textoCurso.equals("Medicina Veterinária")){
            cursos = 5;
        }else if (textoCurso.equals("Pedagogia")){
            cursos = 6;
        }else{
            cursos = 7;
        }

        String email = campoEmail.getText().toString();
        String password = campoSenha.getText().toString();
        String confirm_password = campoConfirmarSenha.getText().toString();

        ValidacaoCPF validacaoCPF = new ValidacaoCPF();

        if (!name.isEmpty()) {//verifica nome
            if (validacaoCPF.isCPF(cpf)) {//verifica cpf valido
                if (!cpf.isEmpty()) {//verifica campo cpf
                    if (!textoVinculo.isEmpty()) {//verifica vinculo
                        if (!textoUnidade.isEmpty()) {//verifica unidade academica
                            if (!textoCurso.isEmpty()) {//verifica cursos
                                if (!email.isEmpty()) {//verifica e-mail
                                    if (!password.isEmpty()) {//verifica senha
                                        if (!confirm_password.isEmpty()) {//verifica confirmacao de senha
                                            if (password.equals(confirm_password)) {

                                                call = apiInterface.postCadastro(name, cpf, vinculo, unidade, cursos, email, password);
                                                call.enqueue(new Callback<UserResponse>() {
                                                    @Override
                                                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                                                        if (response.isSuccessful()) {
                                                            Toast.makeText(TelaCadastrarDiscente.this, "Cadastro realizado com sucesso! Verifique seu e-mail.", Toast.LENGTH_LONG).show();
                                                            startActivity(new Intent(TelaCadastrarDiscente.this, LoginActivity.class));
                                                            finish();
                                                        } else {

                                                            Toast.makeText(TelaCadastrarDiscente.this, "Erro ao realizar cadastro! Verifique os dados e tente novamente.", Toast.LENGTH_LONG).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<UserResponse> call, Throwable t) {

                                                    }
                                                });


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
                                Toast.makeText(TelaCadastrarDiscente.this, "Selecione o cursos", Toast.LENGTH_SHORT).show();
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
    */}

    /*
    public void cadastrarUsuario(final User discente){

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
                        String idUsuario = task.getResult().getUser().getUid();
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
    public void inicializarComponentes(){

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
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
}