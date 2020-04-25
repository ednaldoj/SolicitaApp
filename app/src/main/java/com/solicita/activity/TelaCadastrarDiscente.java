package com.solicita.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.solicita.helper.MaskCustom;
import com.solicita.helper.ValidacaoCPF;
import com.solicita.R;
import com.google.android.material.textfield.TextInputEditText;
import com.solicita.model.Curso;
import com.solicita.network.ApiClient;
import com.solicita.network.ApiInterface;
import com.solicita.network.response.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.layout.simple_spinner_item;

public class TelaCadastrarDiscente extends AppCompatActivity {

    TextInputEditText campoNome, campoCPF, campoEmail, campoSenha, campoConfirmarSenha;
    Spinner spinnerVinculo, spinnerUnidade, spinnerCurso;
    Button buttonCadastro;
    ApiInterface apiInterface;
    Call<UserResponse> call;
    Context mContext;
    ArrayList<Curso> cursoArrayList;
    ArrayList<String> cursos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastrar_discente);

        mContext=this;
      //  apiInterface = ApiClient.getClient().create(ApiInterface.class);

        inicializarComponentes();
        buscarJSON();

       // buttonCadastro.setOnClickListener(v -> cadastrar());
    }
    private void buscarJSON(){

        Call<String> stringCall = apiInterface.getJSONString();

        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> stringCall, Response<String> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        String jsonResponse = response.body();
                        spinnerJSON(jsonResponse);

                    }
                }
            }
            @Override
            public void onFailure(Call<String> stringCall, Throwable t) {
            }
        });
    }
    public void spinnerJSON(String response){
        try {
            JSONObject object = new JSONObject(response);
            cursoArrayList = new ArrayList<>();
            JSONArray jsonArray = object.getJSONArray("cursos");

            for(int i=0; i<jsonArray.length();i++){

                Curso curso = new Curso();

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                curso.setNome(jsonObject.getString("nome"));

                cursoArrayList.add(curso);
            }
            for(int i=0; i<cursoArrayList.size(); i++){
                cursos.add(cursoArrayList.get(i).getNome());
            }
            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(TelaCadastrarDiscente.this, simple_spinner_item, cursos);
            stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            spinnerCurso.setAdapter(stringArrayAdapter);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

/*
    public void preencherSpinner(){
        String url = "http://192.168.0.104/SolicitaWeb/public/api/cursos/";
        asyncHttpClient.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode ==200){
                    carregarSpinner(new String(responseBody));
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
    public void carregarSpinner(String response){
        ArrayList<Curso> lista = new ArrayList<Curso>();
        try {

            JSONArray jsonArray = new JSONArray(response);
            for(int i=0; i<jsonArray.length(); i++){
                Curso curso = new Curso();
                curso.setNome(jsonArray.getJSONObject(i).getString("nome"));
                lista.add(curso);
            }
            ArrayAdapter<Curso> arrayAdapter = new ArrayAdapter<Curso>(this, android.R.layout.simple_dropdown_item_1line, lista);
            spinnerVinculo.setAdapter(arrayAdapter);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
*/

/*
    public void cadastrar() {

        String name = campoNome.getText().toString();
        String cpf  = campoCPF.getText().toString();
        String vinculo  = spinnerVinculo.getSelectedItem().toString();

            if(vinculo.equals("Matriculado")){
                vinculo = "1";
            }else if (vinculo.equals("Egresso")){
                vinculo = "2";
            }else if (vinculo.equals("Especial")){
                vinculo = "3";
            }else if (vinculo.equals("REMT - Regime Especial de Movimentação Temporária")){
                vinculo = "4";
            }else if (vinculo.equals("Desistente")){
                vinculo = "5";
            }else if (vinculo.equals("Matrícula Trancada")){
                vinculo = "6";
            }else{
                vinculo = "7";
            }

        String unidade  = spinnerUnidade.getSelectedItem().toString();
            if (unidade.equals("UAG - Unidade Acadêmica de Garanhuns")){
                unidade = "1";
            }else{
                unidade = "1";
            }
        String cursos  = spinnerCurso.getSelectedItem().toString();
        if(cursos.equals("Agronomia")){
            cursos = "1";
        }else if (cursos.equals("Ciência da Computação")){
            cursos = "2";
        }else if (cursos.equals("Engenharia de Alimentos")){
            cursos = "3";
        }else if (cursos.equals("Letras")){
            cursos = "4";
        }else if (cursos.equals("Medicina Veterinária")){
            cursos = "5";
        }else if (cursos.equals("Pedagogia")){
            cursos = "6";
        }else{
            cursos = "7";
        }

        String email = campoEmail.getText().toString();
        String password = campoSenha.getText().toString();
        String confirm_password = campoConfirmarSenha.getText().toString();

        ValidacaoCPF validacaoCPF = new ValidacaoCPF();

        if (!name.isEmpty()) {//verifica nome
            if (validacaoCPF.isCPF(cpf)) {//verifica cpf valido
                if (!cpf.isEmpty()) {//verifica campo cpf
                    if (!vinculo.isEmpty()) {//verifica vinculo
                        if (!unidade.isEmpty()) {//verifica unidade academica
                            if (!cursos.isEmpty()) {//verifica cursos
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
    }
*/
    public void abrirTelaLogin(View view){
        Intent abrirTelaLogin = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(abrirTelaLogin);
    }
    public void inicializarComponentes(){

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        campoNome = findViewById(R.id.textProtNome);
        campoCPF = findViewById(R.id.textInfoCPF);
        campoCPF.addTextChangedListener(MaskCustom.insert(MaskCustom.CPF_MASK, campoCPF));
        spinnerVinculo =(findViewById(R.id.spinner));
        spinnerUnidade = findViewById(R.id.spinnerUnidadeAcademica);
        spinnerCurso = findViewById(R.id.spinnerCurso);
        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        campoConfirmarSenha = findViewById(R.id.editConfirmarSenha);
        buttonCadastro = findViewById(R.id.buttonCadastro);
    }
}