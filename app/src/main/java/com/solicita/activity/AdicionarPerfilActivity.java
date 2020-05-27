package com.solicita.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.solicita.R;
import com.solicita.helper.SharedPrefManager;
import com.solicita.model.Curso;
import com.solicita.model.Unidade;
import com.solicita.network.ApiClient;
import com.solicita.network.ApiInterface;
import com.solicita.network.response.DefaultResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.layout.simple_spinner_item;

public class AdicionarPerfilActivity extends AppCompatActivity {

    private Spinner spinnerVinculo, spinnerUnidade, spinnerCurso;
    private CheckBox checkDefinirPadrao;
    private Button buttonAdicionarPerfil;
    private ApiInterface apiInterface;
    private SharedPrefManager sharedPrefManager;
    TextView textNomeUsuario;


    ArrayList<Curso> cursoArrayList;
    ArrayList<Unidade> unidadeArrayList;

    ArrayList<String> cursos = new ArrayList<>();
    ArrayList<String> unidade = new ArrayList<>();

    private int index;
    String idUnidade;
    String idCurso;

    Call<DefaultResponse> call;
    Context context;

    String vinculo = "", checkDefault = "";

    Button buttonLogout, buttonHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_perfil);

        sharedPrefManager=new SharedPrefManager(this);
        apiInterface= ApiClient.getClient().create(ApiInterface.class);

        inicializarComponentes();

        textNomeUsuario.setText(sharedPrefManager.getSPNome());

        adicionarListenerCheck();
        buscarJSON();

        buttonHome.setOnClickListener(v -> irHome());

        buttonLogout.setOnClickListener(v -> logoutApp());

        buttonAdicionarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarPerfil();
            }
        });
    }
    public void buscarJSON(){

        Call<String> callCurso = apiInterface.getCursoJSONString();
        Call<String> callUnidade = apiInterface.getUnidadeJSONString();

        callCurso.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        String jsonResponse = response.body();
                        spinnerCursoJSON(jsonResponse);
                        // spinnerUnidadeJSON(jsonResponse);

                    }else{
                        Log.i("onEmptyResponse", "Empty");
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("Erro");            }
        });

        callUnidade.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        String jsonResponse = response.body();
                        //  spinnerCursoJSON(jsonResponse);
                        spinnerUnidadeJSON(jsonResponse);

                    }else{
                        Log.i("onEmptyResponse", "Empty");
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("Erro");
            }
        });
    }

    public void spinnerCursoJSON(String response){
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

                //      Log.d("this is my array", "arr: " + Arrays.toString(new ArrayList[]{cursos}));
                //      System.out.println("arr: "+ Arrays.toString(new ArrayList[]{cursos}));
            }

            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(AdicionarPerfilActivity.this, simple_spinner_item, cursos);
            stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCurso.setAdapter(stringArrayAdapter);

            spinnerCurso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    index = parent.getSelectedItemPosition();
                    index++;
                    idCurso = String.valueOf(index);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }catch (JSONException e){
            e.printStackTrace();
        }

    }
    public void spinnerUnidadeJSON(String response){

        try {
            JSONObject object = new JSONObject(response);
            unidadeArrayList = new ArrayList<>();
            JSONArray jsonArray = object.getJSONArray("unidade");

            for(int i=0; i<jsonArray.length();i++){

                Unidade unidade = new Unidade();

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                unidade.setNome(jsonObject.getString("nome"));

                unidadeArrayList.add(unidade);

                //           Log.d("this is my array", "arr: " + Arrays.toString(new ArrayList[]{cursoArrayList}));
                //         System.out.println("arr: "+ Arrays.toString(new ArrayList[]{cursoArrayList}));
            }
            for(int i=0; i<unidadeArrayList.size(); i++){
                unidade.add(unidadeArrayList.get(i).getNome());

                //              Log.d("this is my array", "arr: " + Arrays.toString(new ArrayList[]{unidade}));
                //      System.out.println("arr: "+ Arrays.toString(new ArrayList[]{cursos}));

            }
            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(AdicionarPerfilActivity.this, simple_spinner_item, unidade);
            stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerUnidade.setAdapter(stringArrayAdapter);

            spinnerUnidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    index = parent.getSelectedItemPosition();
                    index++;
                    idUnidade = String.valueOf(index);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }catch (JSONException e){
            e.printStackTrace();
        }

    }
    public void adicionarPerfil(){

        vinculo = spinnerVinculo.getSelectedItem().toString();

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

        spinnerVinculo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        call = apiInterface.postAdicionaPerfil(vinculo, idUnidade, idCurso, checkDefault, sharedPrefManager.getSPToken());
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful()){

                    DefaultResponse dr = response.body();
                    Toast.makeText(AdicionarPerfilActivity.this, dr.getMessage(), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AdicionarPerfilActivity.this, InformacoesDiscenteActivity.class));


                }else{
                    Toast.makeText(AdicionarPerfilActivity.this, "Erro ao adicionar perfil! Verifique os dados e tente novamente.", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });
    }
    public void adicionarListenerCheck(){
        checkDefinirPadrao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkDefinirPadrao.isChecked()){
                    checkDefault = "true";
                    System.out.println("Valor check: " + checkDefault);
                }else{
                    checkDefault = "false";
                    System.out.println("Valor check: " + checkDefault);

                }
            }
        });
    }
    public void logoutApp() {
        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_STATUS_LOGIN, false);
        startActivity(new Intent(AdicionarPerfilActivity.this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }
    public void irHome(){
        startActivity(new Intent(AdicionarPerfilActivity.this, HomeAlunoActivity.class));

    }

    public void abrirPerfilDiscente(View view){
        Intent abrirPerfil = new Intent(getApplicationContext(), InformacoesDiscenteActivity.class);
        startActivity(abrirPerfil);
    }
    public void inicializarComponentes(){

        spinnerVinculo=findViewById(R.id.spinnerVinculo);
        spinnerUnidade=findViewById(R.id.spinnerUnidade);
        spinnerCurso=findViewById(R.id.spinnerCurso);
        checkDefinirPadrao=findViewById(R.id.checkDefinirPadrao);
        buttonAdicionarPerfil=findViewById(R.id.buttonAdicionarPerfil);
        buttonLogout = findViewById(R.id.buttonLogout);
        buttonHome = findViewById(R.id.buttonHome);
        textNomeUsuario = findViewById(R.id.textNomeUsuario);

    }
}
