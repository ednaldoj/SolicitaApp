package com.solicita.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.solicita.R;
import com.solicita.helper.SharedPrefManager;
import com.solicita.model.Aluno;
import com.solicita.model.Perfil;
import com.solicita.model.Solicitacoes;
import com.solicita.model.Unidade;
import com.solicita.model.User;
import com.solicita.network.ApiClient;
import com.solicita.network.ApiInterface;
import com.solicita.network.response.PerfilResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaInformacoesDiscente extends AppCompatActivity {

    public TextView textInfoNome, textInfoCPF, textInfoVinculo, textInfoUnidadeAcademica, textInfoCurso, textInfoEmail;

    ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;
    Context context;

    ArrayList<Perfil> listarPerfilArrayList;
    ArrayList<Unidade> listarUnidadesArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_informacoes_discente);

        sharedPrefManager = new SharedPrefManager(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        context = this;

        //inicializar componentes
        inicializarComponentes();
        exibirInfo();


    }
    public void exibirInfo(){

        Call<PerfilResponse> perfilResponseCall = apiInterface.getPerfilInfoJSONString(sharedPrefManager.getSPToken());

        perfilResponseCall.enqueue(new Callback<PerfilResponse>() {
            @Override
            public void onResponse(Call<PerfilResponse> call, Response<PerfilResponse> response) {
                if (response.code()==200){

                    Perfil perfil = response.body().getPerfil();
                    Aluno aluno = response.body().getAluno();
                    User user = response.body().getUser();
                   // Unidade unidade = response.body().getUnidade();

                    String nome = user.getName();
                    textInfoNome.setText(nome);
                    String cpf = aluno.getCpf();
                    textInfoCPF.setText(cpf);
                    String vinculo = perfil.getSituacao();
                    textInfoVinculo.setText(vinculo);
                    String curso = perfil.getCurso();
                    textInfoCurso.setText(curso);
                    String email = user.getEmail();
                    textInfoEmail.setText(email);

                  //  String unidades = unidade.getInstituicao_id();
                    //textInfoUnidadeAcademica.setText(unidades);


                }else{

                }
            }

            @Override
            public void onFailure(Call<PerfilResponse> call, Throwable t) {

            }
        });
    }
    public void buscarUnidade(String response){
        try{
            JSONObject object = new JSONObject(response);

            listarPerfilArrayList = new ArrayList<>();
            listarUnidadesArrayList = new ArrayList<>();

            JSONArray jsonArrayPerfil = object.getJSONArray("perfil");
            JSONArray jsonArrayUnidades = object.getJSONArray("unidades");

            for (int i=0; i< jsonArrayPerfil.length();i++){
                Perfil perfil = new Perfil();
                JSONObject jsonObject = jsonArrayPerfil.getJSONObject(i);
                perfil.setUnidade_id(jsonObject.getString("unidade_id"));

                listarPerfilArrayList.add(perfil);

            }
            for (int i=0; i< jsonArrayUnidades.length();i++){
                Unidade unidade = new Unidade();
                JSONObject jsonObject = jsonArrayUnidades.getJSONObject(i);
                unidade.setNome(jsonObject.getString("nome"));
                unidade.setInstituicao_id(jsonObject.getString("instituicao_id"));

                listarUnidadesArrayList.add(unidade);
            }
            for (int i=0; i<jsonArrayPerfil.length();i++){
                for (int j=0; j<jsonArrayUnidades.length(); j++){
                    if (listarPerfilArrayList.get(i).getUnidade_id().equals(listarUnidadesArrayList.get(j).getInstituicao_id())){
                        System.out.println(listarUnidadesArrayList.get(j).getNome());
                    }
                }
            }


        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void inicializarComponentes(){

        textInfoNome         = findViewById(R.id.textProtNome);
        textInfoCPF         = findViewById(R.id.textInfoCPF);
        textInfoVinculo         = findViewById(R.id.textProtVinculo);
        textInfoUnidadeAcademica         = findViewById(R.id.textInfoUnidadeAcademica);
        textInfoCurso         = findViewById(R.id.textCursoAdap);
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






















