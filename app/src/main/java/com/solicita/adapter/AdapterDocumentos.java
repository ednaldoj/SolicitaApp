package com.solicita.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.solicita.R;
import com.solicita.helper.SharedPrefManager;
import com.solicita.model.Solicitacoes;
import com.solicita.network.ApiClient;
import com.solicita.network.ApiInterface;
import com.solicita.network.response.DefaultResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdapterDocumentos extends RecyclerView.Adapter<AdapterDocumentos.MyViewHolder> {

    private List<Solicitacoes> listaSolicitacoes;
    private Context context;
    SharedPrefManager sharedPrefManager;
    ApiInterface apiInterface;


    public AdapterDocumentos(List<Solicitacoes> lista, Context context) {

        sharedPrefManager = new SharedPrefManager(context);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);


        this.listaSolicitacoes = lista;
        this.context = context;

    }

    public void removerItem(int position){
        listaSolicitacoes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listaSolicitacoes.size());

    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lista_documentos, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Solicitacoes solicitacoes = listaSolicitacoes.get(position);

    //    holder.textIdAdap.setText(solicitacoes.getId());
        holder.textCursoAdap.setText(solicitacoes.getAbreviatura());
    //    holder.textDataAdap.setText(solicitacoes.getData_pedido());
        holder.textStatusAdap.setText(solicitacoes.getStatus());
        holder.textSolicitadosAdap.setText(solicitacoes.getDocumentosSolicitados());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   removerItem(position);

                AlertDialog.Builder dialogExluirRequisicao = new AlertDialog.Builder(context);

                dialogExluirRequisicao.setTitle("Exclusão de Requisição");
                dialogExluirRequisicao.setMessage("Deseja realmente excluir a requisição?");

                dialogExluirRequisicao.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        removerItem(position);
                        String idRequisicao = solicitacoes.getId();
                        System.out.println("Valor do ID: "+ idRequisicao);

                        Call<DefaultResponse> defaultResponseCall = apiInterface.postExcluirRequisicao(idRequisicao, sharedPrefManager.getSPToken());
                        defaultResponseCall.enqueue(new Callback<DefaultResponse>() {
                            @Override
                            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                if (response.code()==200){
                                    DefaultResponse dr = response.body();
                                    Toast.makeText(context.getApplicationContext(), dr.getMessage(), Toast.LENGTH_LONG).show();

                                }else{

                                }
                            }

                            @Override
                            public void onFailure(Call<DefaultResponse> call, Throwable t) {

                            }
                        });



                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialogExluirRequisicao.create();
                dialogExluirRequisicao.show();
            }

        });
    }

    @Override
    public int getItemCount() {
        return listaSolicitacoes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textIdAdap, textCursoAdap, textDataAdap, textStatusAdap, textSolicitadosAdap;
        public Button deleteButton;

        public MyViewHolder(View itemView){
            super(itemView);

       //     textIdAdap=itemView.findViewById(R.id.textIdAdap);
            textCursoAdap=itemView.findViewById(R.id.textCursoAdap);
         //   textDataAdap =itemView.findViewById(R.id.textDataAdap);
            textStatusAdap=itemView.findViewById(R.id.textStatusAdap);
            textSolicitadosAdap=itemView.findViewById(R.id.textSolicitadosAdap);
            deleteButton = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
