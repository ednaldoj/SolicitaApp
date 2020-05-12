package com.solicita.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
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

    ArrayList<Solicitacoes> listarRequisicoesArrayList;
    ArrayList<Solicitacoes> listarSolicitadosArrayList;
    ArrayList<Solicitacoes> listarPerfisArrayList;
    ArrayList<Solicitacoes> listarDocumentosArrayList;

    ArrayList<Solicitacoes> listarSolDocsArrayList;

    ArrayList<String> listarId = new ArrayList<>();
    ArrayList<String> listarData = new ArrayList<>();
    ArrayList<String> listarHora = new ArrayList<>();

    ArrayList<String> listarStatus = new ArrayList<>();
    ArrayList<String> listarDocumentoId = new ArrayList<>();
    ArrayList<String> listarPerfilId = new ArrayList<>();
    ArrayList<String> listarRequisicaoId = new ArrayList<>();

    ArrayList<String> listarCursoPerfil = new ArrayList<>();
    ArrayList<String> listarIdPerfil = new ArrayList<>();

    ArrayList<String> listarDocumentos = new ArrayList<>();
    ArrayList<String> listarIdDocumentos = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_listar_documetos_solicitados);

        sharedPrefManager = new SharedPrefManager(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        inicializarComponentes();
        buscarJSON();

        //Listagem de solicitações
        this.criarSolicitacoes();

        //Configurar RecyclerView
        recyclerRequisicoes.setLayoutManager(new LinearLayoutManager(this));
        recyclerRequisicoes.setHasFixedSize(true);
        adapterDocumentos = new AdapterDocumentos(listaSolicitacoes, this);
        recyclerRequisicoes.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerRequisicoes.setAdapter(adapterDocumentos);

    }

    public void criarSolicitacoes() {
        Solicitacoes solicitacoes = new Solicitacoes("1", "Letras", "08/05/2020", "04h24", "Histórico", "Em andamento");
        listaSolicitacoes.add(solicitacoes);
        solicitacoes = new Solicitacoes("2", "Zootecnia", "07/05/2020", "12h24", "Declaração de Vínculo", "Em andamento");
        listaSolicitacoes.add(solicitacoes);
    }

    private void buscarJSON() {
        Call<String> getRequisicoesJSONString = apiInterface.getRequisicoesJSONString(sharedPrefManager.getSPToken());
        getRequisicoesJSONString.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    // System.out.println("Okk");


                    String jsonResponse = response.body();
                    listarSolicitacoes(jsonResponse);


                } else {
                    System.out.println("Falhou ou token expirado");

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void retornarLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
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

                listarSolicitadosArrayList.add(solicitados);
            }
            for (int i = 0; i < listarSolicitadosArrayList.size(); i++) {
                listarStatus.add(listarSolicitadosArrayList.get(i).getStatus());
                listarDocumentoId.add(listarSolicitadosArrayList.get(i).getDocumentoId());
                listarRequisicaoId.add(listarSolicitadosArrayList.get(i).getRequisicaoId());
            }
            System.out.println("Status: " + listarStatus + " ID Documento: " + listarDocumentoId + " ID Requisicao: " + listarRequisicaoId);

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
            System.out.println("ID: " + listarIdPerfil + "Curso: " + listarCursoPerfil);

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

            for (int i = 0; i < jsonArraySolicitados.length() - 1; i++) {
                for (int j = 0; j < jsonArraySolicitados.length(); j++) {
                    for (int k = 0; k < jsonArrayDocumentos.length(); k++) {
                        for (int l = 0; l < jsonArrayPerfis.length(); l++) {

                            if (listarRequisicoesArrayList.get(i).getId().equals(listarSolicitadosArrayList.get(j).getRequisicaoId())) {
                                if (listarSolicitadosArrayList.get(j).getDocumentoId().equals(listarDocumentosArrayList.get(k).getIdDocumento())) {
                                    if (listarRequisicoesArrayList.get(i).getPerfilId().equals(listarPerfisArrayList.get(l).getIdPerfil())) {

                                        System.out.println("\n" + listarRequisicoesArrayList.get(i).getId() + " " + listarRequisicoesArrayList.get(i).getData_pedido() + " " +
                                                listarRequisicoesArrayList.get(i).getHora_pedido() + " " + listarSolicitadosArrayList.get(j).getDocumentoId() + " " +
                                                listarSolicitadosArrayList.get(j).getStatus() + " " + listarRequisicoesArrayList.get(i).getPerfilId() + " " +
                                                listarDocumentosArrayList.get(k).getDocumento() + " " + listarPerfisArrayList.get(l).getCurso());

                                    }
                                }
                            }
                        }
                    }
                }
            }

            for (int k = 0; k < jsonArraySolicitados.length(); k++) {
                for (int l = 0; l < jsonArrayDocumentos.length(); l++) {
                    if (listarSolicitadosArrayList.get(k).getDocumentoId().equals(listarDocumentosArrayList.get(l).getIdDocumento())) {
                        //      System.out.println("Docs: "+ listarDocumentosArrayList.get(l).getDocumento());
                    }
                }
            }// System.out.println("Documentos solicitados: "+ listarDocumentosArrayList.get(l).getDocumento());
            for (int m = 0; m < jsonArrayRequisicoes.length(); m++) {
                for (int n = 0; n < jsonArrayPerfis.length(); n++) {
                    if (listarRequisicoesArrayList.get(m).getPerfilId().equals(listarPerfisArrayList.get(n).getIdPerfil())) {
                        // System.out.println("Curso do perfil: " + listarPerfisArrayList.get(n).getCurso());
                    }
                }
            }



/*            for (int i=0; i< jsonArrayRequisicoes.length();i++){

                System.out.println(listarRequisicoesArrayList.get(i).getId() + " " + listarRequisicoesArrayList.get(i).getData_pedido() + " " + listarRequisicoesArrayList.get(i).getHora_pedido()
                + " " );
            }

 */

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void irTelaHomeAluno(View view) {
        Intent irTelaHomeAluno = new Intent(getApplicationContext(), TelaHomeAluno.class);
        startActivity(irTelaHomeAluno);
    }

    public void inicializarComponentes() {
        recyclerRequisicoes = findViewById(R.id.recyclerRequisicoes);

    }
}