package com.solicita.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.solicita.R;
import com.solicita.helper.DateCustom;
import com.solicita.model.Documento;
import com.solicita.model.Perfil;
import com.solicita.model.Requisicao;
import com.solicita.network.ApiClient;
import com.solicita.network.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.layout.simple_spinner_item;

public class TelaSolicitarDocumentos extends AppCompatActivity {

    public EditText editCampoProgDisciplina, editCampoOutros;
    private Spinner spinnerPerfil;

    private Button buttonSolicitar;
    ApiInterface apiInterface;

    ArrayList<Perfil> perfilArrayList;
    ArrayList<String> perfil = new ArrayList<>();

    ArrayList<Documento> documentoArrayList;
    ArrayList<String> documento = new ArrayList<>();

    LinearLayout linearLayout;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_solicitar_documentos);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        inicializarComponentes();
        buscarJSON();
    }

    private void buscarJSON(){

        Call<String> callPerfil = apiInterface.getPerfilJSONString();
        Call<String> callDocumento = apiInterface.getDocumentoJSONString();

        callPerfil.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()){
                    String jsonResponse=response.body();
                    spinnerPerfilJSON(jsonResponse);
                }else{
                    Log.i("onEmptyResponse", "Empty");
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("Erro");            }
        });

        callDocumento.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    String jsonResponse = response.body();
                    checkboxDocumentos(jsonResponse);
                }else{
                    Log.i("onEmptyResponse", "Empty");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("Erro");
            }
        });

    }
    public void checkboxDocumentos(String response){
        try {
            JSONObject object = new JSONObject(response);
            documentoArrayList = new ArrayList<>();
            JSONArray jsonArray = object.getJSONArray("documentos");

            for(int i=0; i<jsonArray.length(); i++){

                Documento documento = new Documento();
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                documento.setTipo(jsonObject.getString("tipo"));
                documentoArrayList.add(documento);

            }
            for(int i=0; i<documentoArrayList.size(); i++){
                documento.add(documentoArrayList.get(i).getTipo());
            }
            for (int i=0; i<documento.size();i++){
                checkBox = new CheckBox(this);
                checkBox.setId(i);
                checkBox.setText(documento.get(i));
                checkBox.setOnClickListener(getOnClickDoSomething(checkBox));
                linearLayout.addView(checkBox);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    View.OnClickListener getOnClickDoSomething(final Button button){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  Log.d("ON_CLICK", "Checkbox ID: "+ button.getId() + " Text: "+button.getText().toString());
                int valor = button.getId();
                System.out.println("Valor do documento: " + ++valor);
            }
        };
    }


    public void spinnerPerfilJSON(String response){
        try {
            JSONObject object = new JSONObject(response);
            perfilArrayList = new ArrayList<>();
            JSONArray jsonArray = object.getJSONArray("perfil");

            for(int i=0; i<jsonArray.length(); i++){
                Perfil perfil = new Perfil();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                perfil.setCurso(jsonObject.getString("default"));
                perfil.setSituacao(jsonObject.getString("situacao"));

                perfilArrayList.add(perfil);
            }
            for (int i=0; i<perfilArrayList.size(); i++){
                perfil.add(perfilArrayList.get(i).getCurso() + " - " + perfilArrayList.get(i).getSituacao());

            }
            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(TelaSolicitarDocumentos.this, simple_spinner_item, perfil );
            stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPerfil.setAdapter(stringArrayAdapter);


        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    /*
        //Listener para monitorar checkbox do programa de disciplina
        public void adicionarListenerCheckProg(){
            checkProgramaDisciplina.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    editCampoProgDisciplina = findViewById(R.id.editCampoProgDisciplina);
                    if(checkProgramaDisciplina.isChecked()){
                        editCampoProgDisciplina.setVisibility(View.VISIBLE);
                    }else{
                        editCampoProgDisciplina.setVisibility(View.GONE);
                    }
                }
            });
        }

        //Listener para monitorar checkbox de outros documentos
        public void adicionarListenerCheckOutros(){
            checkOutros.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    editCampoOutros = findViewById(R.id.editCampoOutros);
                    if(checkOutros.isChecked()){
                        editCampoOutros.setVisibility(View.VISIBLE);
                    }else{
                        editCampoOutros.setVisibility(View.GONE);
                    }
                }
            });
        }

        public void validarRequisicao(View view){

            String textoPerfil = spinnerPerfil.getSelectedItem().toString();

            ArrayList<String> documentos = new ArrayList<String>();

            if(checkDeclaracaoVinculo.isChecked()){
                documentos.add("Declaração de vínculo");
            }
            if(checkComprovanteMatricula.isChecked()){
                documentos.add("Comprovante de Matrícula");
            }
            if(checkHistorico.isChecked()){
                documentos.add("Histórico");
            }
            if(checkProgramaDisciplina.isChecked()){
                documentos.add("Programa de Disciplina");
                editCampoProgDisciplina.setFocusable(true);
            }
            if(checkOutros.isChecked()){
                documentos.add("Outros");
                editCampoOutros.setFocusable(true);
            }

            if (checkDeclaracaoVinculo.isChecked()==false && checkComprovanteMatricula.isChecked()==false
                    && checkHistorico.isChecked()==false && checkProgramaDisciplina.isChecked()==false && checkOutros.isChecked()==false){
                Toast.makeText(TelaSolicitarDocumentos.this, "Selecione pelo menos um documento.", Toast.LENGTH_SHORT).show();

            }else {

                Requisicao requisicao = new Requisicao();
                requisicao.setVinculo(textoPerfil);
                requisicao.setCurso("Ciência da Computação");
                requisicao.setDataRequisicao(DateCustom.dataAtual());
                requisicao.setStatus("Em andamento");
                requisicao.setDocumentosSolicitados(documentos);
                requisicao.salvar();

                //passar dados da requisição para tela de confirmação


                Intent abrirConfirmacao = new Intent(getApplicationContext(), TelaConfirmacaoRequisicao.class);
                abrirConfirmacao.putExtra("data", DateCustom.dataAtual());
                abrirConfirmacao.putExtra("documentos", documentos);
                startActivity(abrirConfirmacao);
            }

        }
        public void abrirHome(View view){
            Intent abrirHome = new Intent(getApplicationContext(), TelaHomeAluno.class);
            startActivity(abrirHome);
        }*/
    public void inicializarComponentes(){

        spinnerPerfil = findViewById(R.id.spinnerPerfil);
        linearLayout = findViewById(R.id.linear_docs);
        buttonSolicitar = findViewById(R.id.buttonSolicitar);

    }
}





















