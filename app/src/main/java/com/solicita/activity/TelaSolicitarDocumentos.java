package com.solicita.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.solicita.R;
import com.solicita.helper.DateCustom;
import com.solicita.helper.SharedPrefManager;
import com.solicita.model.Documento;
import com.solicita.model.Perfil;
import com.solicita.model.Requisicao;
import com.solicita.model.User;
import com.solicita.network.ApiClient;
import com.solicita.network.ApiInterface;
import com.solicita.network.response.SolicitacaoResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.layout.simple_spinner_item;

public class TelaSolicitarDocumentos extends AppCompatActivity {

    private Spinner spinnerPerfil;

    private Button buttonSolicitar;
    ApiInterface apiInterface;

    ArrayList<Perfil> perfilArrayList;
    ArrayList<String> perfil = new ArrayList<>();

    ArrayList<Documento> documentoArrayList;
    ArrayList<Documento> documentoDetalhesArrayList;

    ArrayList<String> documento = new ArrayList<>();
    ArrayList<String> documentoDetalhes = new ArrayList<>();

    LinearLayout linearLayout;
    CheckBox checkBox;

    SharedPrefManager sharedPrefManager;

    TextView textNomeUsuario;

    private int index;

    private int idPerfil;

    Context context;

    String cursoP, situacaoP, dataP, horaP;

    String declaracaoVinculo = "", comprovanteMatricula = "", historico = "", programaDisciplina = "", outros = "";
    String requisicaoPrograma = "", requisicaoOutros = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_solicitar_documentos);

        sharedPrefManager = new SharedPrefManager(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        context = this;

        inicializarComponentes();

        textNomeUsuario.setText(sharedPrefManager.getSPNama());

        buscarJSON();

        buttonSolicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizarSolicitacao();

            }
        });
    }

    public void finalizarSolicitacao(){

        int defaultt=idPerfil;

        Call<SolicitacaoResponse> solicitacaoResponseCall = apiInterface.postSolicitacao(defaultt, declaracaoVinculo, comprovanteMatricula, historico, programaDisciplina, outros, requisicaoPrograma, requisicaoOutros, sharedPrefManager.getSPToken());
        solicitacaoResponseCall.enqueue(new Callback<SolicitacaoResponse>() {
            @Override
            public void onResponse(Call<SolicitacaoResponse> call, Response<SolicitacaoResponse> response) {
                if(response.code()==200){

                    Perfil perfil = response.body().getPerfil();
                    Requisicao requisicao = response.body().getRequisicao();
                    cursoP = perfil.getCurso();
//                    System.out.println("Curso recuperado: " + cursoP);
                    situacaoP = perfil.getSituacao();
                    dataP = requisicao.getData_pedido();
                    horaP = requisicao.getHora_pedido();

                    Intent abrirProtocolo = new Intent(getApplicationContext(), TelaConfirmacaoRequisicao.class);
                    abrirProtocolo.putExtra("curso", cursoP);
                    abrirProtocolo.putExtra("situacao", situacaoP);
                    abrirProtocolo.putExtra("data", dataP);
                    abrirProtocolo.putExtra("hora", horaP);
                    startActivity(abrirProtocolo);

                }else{
                }
            }
            @Override
            public void onFailure(Call<SolicitacaoResponse> call, Throwable t) {

            }
        });
    }

    private void buscarJSON() {

        Call<String> getUserPerfil = apiInterface.getUserPerfil(sharedPrefManager.getSPToken());
        getUserPerfil.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {

                    String jsonResponse = response.body();
                    spinnerPerfilJSON(jsonResponse);

                    Call<String> callDocumento = apiInterface.getDocumentoJSONString();
                    callDocumento.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()) {
                                String jsonResponse = response.body();
                                checkboxDocumentos(jsonResponse);
                            } else {
                                Log.i("onEmptyResponse", "Empty");
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });

                } else {
                    System.out.println("Failure code=!200");
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("Erro");
            }

        });
    }

    public void checkboxDocumentos(String response) {
        try {
            JSONObject object = new JSONObject(response);
            documentoArrayList = new ArrayList<>();
            documentoDetalhesArrayList = new ArrayList<>();
            JSONArray jsonArray = object.getJSONArray("documentos");

            for (int i = 0; i < jsonArray.length(); i++) {

                Documento documento = new Documento();
                Documento documentoDetalhes = new Documento();
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                documento.setTipo(jsonObject.getString("tipo"));
                // documento.setDetalhes(jsonObject.getInt("detalhes"));
                documentoDetalhes.setDetalhes(jsonObject.getString("detalhes"));
                documentoArrayList.add(documento);
                documentoDetalhesArrayList.add(documentoDetalhes);
            }
            for (int i = 0; i < documentoArrayList.size(); i++) {
                documento.add(documentoArrayList.get(i).getTipo());
               // System.out.println(documento);
            }
            for (int i = 0; i < documentoDetalhesArrayList.size(); i++) {
                documentoDetalhes.add(documentoDetalhesArrayList.get(i).getDetalhes());
               // System.out.println(documentoDetalhes);
            }
            for (int i = 0; i < documento.size(); i++) {
                checkBox = new CheckBox(this);
                checkBox.setId(i);
                checkBox.setText(documento.get(i));

              //  System.out.println(documentoArrayList.get(i).getTipo() +" " +documentoDetalhesArrayList.get(i).getDetalhes());
                //checkBox.setOnClickListener(getOnClickDoSomething(checkBox));

                linearLayout.addView(checkBox);

                EditText editTextPrograma = new EditText(context);
                EditText editTextOutros = new EditText(context);

                if(documentoDetalhesArrayList.get(i).getDetalhes().equals("1")){
                    if(i==3){
                        editTextPrograma.setTextSize(18);
                        editTextPrograma.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        editTextPrograma.setVisibility(View.GONE);
                        linearLayout.addView(editTextPrograma);

                    }else if (i==4){
                        editTextOutros.setTextSize(18);
                        editTextOutros.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        editTextOutros.setVisibility(View.GONE);
                        linearLayout.addView(editTextOutros);
                    }
                }
                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {

                    int valor = buttonView.getId();
                    if(valor==0){
                        declaracaoVinculo="1";
                    }
                    else if(valor==1){
                        comprovanteMatricula="1";
                    }
                    else if(valor==2){
                        historico="1";
                    }
                    else if(valor==3){
                        programaDisciplina="1";
                    }
                    else if(valor==4){
                        outros="1";
                    }else{
                        declaracaoVinculo = "";
                        comprovanteMatricula = "";
                        historico = "";
                        programaDisciplina = "";
                        outros = "";
                    }
                    if (isChecked && documentoDetalhesArrayList.get(3).getDetalhes().equals("1")){
                        editTextPrograma.setVisibility(View.VISIBLE);

                       // requisicaoPrograma = "Ingles";
                    }else{
                        editTextPrograma.setVisibility(View.GONE);
                        requisicaoPrograma="";
                    }if (isChecked && documentoDetalhesArrayList.get(4).getDetalhes().equals("1")){
                        editTextOutros.setVisibility(View.VISIBLE);
                    }else {
                        editTextOutros.setVisibility(View.GONE);
                        requisicaoOutros="";
                    }

/*                        int valor = buttonView.getId();

                        if(valor==0){
                            declaracaoVinculo = "1";
                            Toast.makeText(TelaSolicitarDocumentos.this, "Campo " + buttonView.getText() + " selecionado.", Toast.LENGTH_LONG).show();
                        }else if (valor==1){
                            comprovanteMatricula = "1";
                            Toast.makeText(TelaSolicitarDocumentos.this, "Campo " + buttonView.getText() + " selecionado.", Toast.LENGTH_LONG).show();
                        }
                        else if (valor==2){
                            historico = "1";
                            Toast.makeText(TelaSolicitarDocumentos.this, "Campo " + buttonView.getText() + " selecionado.", Toast.LENGTH_LONG).show();
                        }
                        else if (valor==3){
                            programaDisciplina = "1";
                            Toast.makeText(TelaSolicitarDocumentos.this, "Campo " + buttonView.getText() + " selecionado.", Toast.LENGTH_LONG).show();
                        }
                        else if (valor==4){
                            outros = "1";
                            Toast.makeText(TelaSolicitarDocumentos.this, "Campo " + buttonView.getText() + " selecionado.", Toast.LENGTH_LONG).show();
                        }

                       // System.out.println("Valor do documento: " + buttonView.getId());

                    }else{

                        declaracaoVinculo = "";
                        comprovanteMatricula = "";
                        historico = "";
                        programaDisciplina = "";
                        outros = "";
                    }*/
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    View.OnClickListener getOnClickDoSomething(final Button button) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Log.d("ON_CLICK", "Checkbox ID: "+ button.getId() + " Text: "+button.getText().toString());

                int valor = button.getId();

                if(valor==0){
                    declaracaoVinculo = "1";
                }else if (valor==1){
                    comprovanteMatricula = "1";
                }
                else if (valor==2){
                    historico = "1";
                }
                else if (valor==3){
                    programaDisciplina = "1";
                }
                else if (valor==4){
                    outros = "1";
                }

           //     System.out.println("Valor do documento: " + ++valor);
            }
        };
    }

    public void spinnerPerfilJSON(String response) {

        try {
            JSONObject object = new JSONObject(response);
            perfilArrayList = new ArrayList<>();
            JSONArray jsonArray = object.getJSONArray("perfil");

            for (int i = 0; i < jsonArray.length(); i++) {
                Perfil perfil = new Perfil();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                perfil.setCurso(jsonObject.getString("default"));
                perfil.setSituacao(jsonObject.getString("situacao"));

                perfilArrayList.add(perfil);
            }
            for (int i = 0; i < perfilArrayList.size(); i++) {
                perfil.add(perfilArrayList.get(i).getCurso() + " - " + perfilArrayList.get(i).getSituacao());

            }
            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(TelaSolicitarDocumentos.this, simple_spinner_item, perfil);
            stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPerfil.setAdapter(stringArrayAdapter);

            spinnerPerfil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    index = parent.getSelectedItemPosition();
                    index++;
                    idPerfil = index;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        } catch (JSONException e) {
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
    public void inicializarComponentes() {

        spinnerPerfil = findViewById(R.id.spinnerPerfil);
        linearLayout = findViewById(R.id.linear_docs);
        buttonSolicitar = findViewById(R.id.buttonSolicitar);
        textNomeUsuario = findViewById(R.id.textNomeUsuario);

    }
}





















