package com.solicita.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solicita.R;
import com.solicita.adapter.AdapterDocumentos;
import com.solicita.helper.SharedPrefManager;
import com.solicita.model.Solicitacoes;
import com.solicita.network.ApiClient;
import com.solicita.network.ApiInterface;
import com.solicita.network.response.DefaultResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaListarDocumentosSolicitados extends AppCompatActivity {

    //RecyclerView
    public RecyclerView recyclerRequisicoes;
    public List<Solicitacoes> listaSolicitacoes = new ArrayList<>();
    public AdapterDocumentos adapterDocumentos;
    SharedPrefManager sharedPrefManager;
    ApiInterface apiInterface;
    Context context;

    ArrayList<Solicitacoes> listarRequisicoesArrayList;
    ArrayList<Solicitacoes> listarSolicitadosArrayList;
    ArrayList<Solicitacoes> listarPerfisArrayList;
    ArrayList<Solicitacoes> listarDocumentosArrayList;

    ArrayList<String> listarId = new ArrayList<>();
    ArrayList<String> listarData = new ArrayList<>();
    ArrayList<String> listarHora = new ArrayList<>();

    ArrayList<String> listarStatus = new ArrayList<>();
    ArrayList<String> listarDocumentoId = new ArrayList<>();
    ArrayList<String> listarPerfilId = new ArrayList<>();
    ArrayList<String> listarRequisicaoId = new ArrayList<>();
    ArrayList<String> listarDetalhes = new ArrayList<>();

    ArrayList<String> listarCursoPerfil = new ArrayList<>();
    ArrayList<String> listarIdPerfil = new ArrayList<>();

    ArrayList<String> listarDocumentos = new ArrayList<>();
    ArrayList<String> listarIdDocumentos = new ArrayList<>();

    String idRequisicao = "";


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_listar_documetos_solicitados);

        sharedPrefManager = new SharedPrefManager(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        inicializarComponentes();

        this.buscarJSON();

    }

    public void configurarRecycler() {

        recyclerRequisicoes.setLayoutManager(new LinearLayoutManager(this));
        recyclerRequisicoes.setHasFixedSize(true);
        adapterDocumentos = new AdapterDocumentos(listaSolicitacoes, this);
        recyclerRequisicoes.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerRequisicoes.setAdapter(adapterDocumentos);
    }

    private void buscarJSON() {
        Call<String> getRequisicoesJSONString = apiInterface.getRequisicoesJSONString(sharedPrefManager.getSPToken());
        getRequisicoesJSONString.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    String jsonResponse = response.body();
                    listarSolicitacoes(jsonResponse);
                    configurarRecycler();


                } else {

                    Toast.makeText(getApplicationContext(), "Falha na comunicação com o servidor.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(TelaListarDocumentosSolicitados.this, LoginActivity.class));

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void listarSolicitacoes(String response) {
        try {
            JSONObject object = new JSONObject(response);
            listarRequisicoesArrayList = new ArrayList<>();
            listarSolicitadosArrayList = new ArrayList<>();

            listarPerfisArrayList = new ArrayList<>();
            listarDocumentosArrayList = new ArrayList<>();

            JSONArray jsonArrayRequisicoes = object.getJSONArray("requisicoes");
            JSONArray jsonArraySolicitados = object.getJSONArray("solicitados");
            JSONArray jsonArrayPerfis = object.getJSONArray("perfil");
            JSONArray jsonArrayDocumentos = object.getJSONArray("documentos");

            for (int i = 0; i < jsonArrayRequisicoes.length(); i++) {
                Solicitacoes requisicoes = new Solicitacoes();
                JSONObject jsonObject = jsonArrayRequisicoes.getJSONObject(i);
                requisicoes.setId(jsonObject.getString("id"));
                requisicoes.setData_pedido(jsonObject.getString("data_pedido"));
                requisicoes.setHora_pedido(jsonObject.getString("hora_pedido"));
                requisicoes.setPerfilId(jsonObject.getString("perfil_id"));

                listarRequisicoesArrayList.add(requisicoes);
            }
            for (int i = 0; i < listarRequisicoesArrayList.size(); i++) {
                listarId.add(listarRequisicoesArrayList.get(i).getId());
                listarData.add(listarRequisicoesArrayList.get(i).getData_pedido());
                listarHora.add(listarRequisicoesArrayList.get(i).getHora_pedido());
                listarPerfilId.add(listarRequisicoesArrayList.get(i).getPerfilId());
            }
            System.out.println("ID: " + listarId + " Data: " + listarData + " Hora: " + listarHora + " ID Perfil: " + listarPerfilId);

            for (int i = 0; i < jsonArraySolicitados.length(); i++) {
                Solicitacoes solicitados = new Solicitacoes();
                JSONObject jsonObject = jsonArraySolicitados.getJSONObject(i);
                solicitados.setStatus(jsonObject.getString("status"));
                solicitados.setDocumentoId(jsonObject.getString("documento_id"));
                solicitados.setRequisicaoId(jsonObject.getString("requisicao_id"));
                solicitados.setDetalhes(jsonObject.getString("detalhes"));

                listarSolicitadosArrayList.add(solicitados);
            }
            for (int i = 0; i < listarSolicitadosArrayList.size(); i++) {
                listarStatus.add(listarSolicitadosArrayList.get(i).getStatus());
                listarDocumentoId.add(listarSolicitadosArrayList.get(i).getDocumentoId());
                listarRequisicaoId.add(listarSolicitadosArrayList.get(i).getRequisicaoId());
                listarDetalhes.add(listarSolicitadosArrayList.get(i).getDetalhes());
            }
            System.out.println("Status: " + listarStatus + " ID Documento: " + listarDocumentoId + " ID Requisicao: " + listarRequisicaoId
                    + " Detalhes: " + listarDetalhes);

            for (int i = 0; i < jsonArrayPerfis.length(); i++) {
                Solicitacoes perfis = new Solicitacoes();
                JSONObject jsonObject = jsonArrayPerfis.getJSONObject(i);
                perfis.setCurso(jsonObject.getString("default"));
                perfis.setIdPerfil(jsonObject.getString("id"));

                listarPerfisArrayList.add(perfis);
            }

            for (int i = 0; i < listarPerfisArrayList.size(); i++) {
                listarCursoPerfil.add(listarPerfisArrayList.get(i).getCurso());
                listarIdPerfil.add(listarPerfisArrayList.get(i).getIdPerfil());
            }
            System.out.println("ID: " + listarIdPerfil + " Curso: " + listarCursoPerfil);

            for (int i = 0; i < jsonArrayDocumentos.length(); i++) {
                Solicitacoes documentos = new Solicitacoes();
                JSONObject jsonObject = jsonArrayDocumentos.getJSONObject(i);
                documentos.setDocumento(jsonObject.getString("tipo"));
                documentos.setIdDocumento(jsonObject.getString("id"));

                listarDocumentosArrayList.add(documentos);

            }
            for (int i = 0; i < listarDocumentosArrayList.size(); i++) {
                listarDocumentos.add(listarDocumentosArrayList.get(i).getDocumento());
                listarIdDocumentos.add(listarDocumentosArrayList.get(i).getIdDocumento());
            }
            System.out.println("ID: " + listarIdDocumentos + "Documentos: " + listarDocumentos);

            for (int i = 0; i < jsonArrayRequisicoes.length(); i++) {
                for (int j = 0; j <jsonArraySolicitados.length(); j++) {
                    for (int k = 0; k < jsonArrayDocumentos.length(); k++) {
                        for (int l = 0; l < jsonArrayPerfis.length(); l++) {

                            if (listarRequisicoesArrayList.get(i).getId().equals(listarSolicitadosArrayList.get(j).getRequisicaoId())) {
                                if (listarSolicitadosArrayList.get(j).getDocumentoId().equals(listarDocumentosArrayList.get(k).getIdDocumento())) {
                                    if (listarRequisicoesArrayList.get(i).getPerfilId().equals(listarPerfisArrayList.get(l).getIdPerfil())) {

                                        Solicitacoes solicitacoes = new Solicitacoes(listarRequisicoesArrayList.get(i).getId(), listarPerfisArrayList.get(l).getCurso(),
                                                listarRequisicoesArrayList.get(i).getData_pedido()+ " "+listarRequisicoesArrayList.get(i).getHora_pedido(), listarRequisicoesArrayList.get(i).getHora_pedido(),
                                                listarDocumentosArrayList.get(k).getDocumento(), listarSolicitadosArrayList.get(j).getStatus());
                                        listaSolicitacoes.add(solicitacoes);

                                        System.out.println("\n" + listarRequisicoesArrayList.get(i).getId() + " " + listarPerfisArrayList.get(l).getCurso() + " " +
                                                listarRequisicoesArrayList.get(i).getData_pedido() + " " + listarRequisicoesArrayList.get(i).getHora_pedido() + " " +
                                                listarDocumentosArrayList.get(k).getDocumento() + " " + listarSolicitadosArrayList.get(j).getStatus());


                                    }
                                }
                            }

                        }
                    }
                }
            }

 /*           for (int i = 0; i < jsonArrayRequisicoes.length(); i++) {
                for (int j = 0; j < jsonArraySolicitados.length(); j++) {
                    if (listarRequisicoesArrayList.get(i).getId().equals(listarSolicitadosArrayList.get(j).getRequisicaoId())) {

                        Solicitacoes solicitacoes = new Solicitacoes(listarRequisicoesArrayList.get(i).getId(), listarSolicitadosArrayList.get(j).getDocumentoId(), "", "", "", "");
                        listaSolicitacoes.add(solicitacoes);

       /*                 if (listarSolicitadosArrayList.contains(listarSolicitadosArrayList.get(j).getRequisicaoId())){
                            System.out.println("Mesma requisição");
                        }else{
                            System.out.println("Nova requisição");
                        }

        */
 /*                       System.out.println("\n" + listarRequisicoesArrayList.get(i).getId() + " " + listarRequisicoesArrayList.get(i).getPerfilId() + " " +
                                listarRequisicoesArrayList.get(i).getData_pedido() + ", " + listarRequisicoesArrayList.get(i).getHora_pedido() + " " +
                                listarSolicitadosArrayList.get(j).getDocumentoId() + " ");

                    }
                }
            }*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

   /* public static void excluirRequisicao() {

            AlertDialog.Builder dialogExluirPerfil = new AlertDialog.Builder(this);

            dialogExluirPerfil.setTitle("Exclusão de Requisição");
            dialogExluirPerfil.setMessage("Deseja realmente excluir a requisição?");

            dialogExluirPerfil.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Call<DefaultResponse> callExcluir = apiInterface.postExcluirPerfil(idRequisicao, sharedPrefManager.getSPToken());
                    callExcluir.enqueue(new Callback<DefaultResponse>() {
                        @Override
                        public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                            if (response.code() == 200) {

                                DefaultResponse dr = response.body();
                                Toast.makeText(getApplicationContext(), dr.getMessage(), Toast.LENGTH_LONG).show();

                            } else {

                            }
                        }

                        @Override
                        public void onFailure(Call<DefaultResponse> call, Throwable t) {

                        }
                    });
                }
            });
            dialogExluirPerfil.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialogExluirPerfil.create();
            dialogExluirPerfil.show();

    } */

    public void excluirRequisicao() {

        Call<DefaultResponse> callExcluir = apiInterface.postExcluirRequisicao(idRequisicao, sharedPrefManager.getSPToken());
        callExcluir.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.code() == 200) {

                    DefaultResponse dr = response.body();
                    Toast.makeText(getApplicationContext(), dr.getMessage(), Toast.LENGTH_LONG).show();

                } else {

                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });
    }

    public void irTelaHomeAluno(View view) {
        Intent irTelaHomeAluno = new Intent(getApplicationContext(), TelaHomeAluno.class);
        startActivity(irTelaHomeAluno);
    }

    public void inicializarComponentes() {
        recyclerRequisicoes = findViewById(R.id.recyclerRequisicoes);

    }
}