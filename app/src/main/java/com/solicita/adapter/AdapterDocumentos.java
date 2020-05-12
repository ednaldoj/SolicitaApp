package com.solicita.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solicita.R;
import com.solicita.model.Solicitacoes;

import java.util.List;


public class AdapterDocumentos extends RecyclerView.Adapter<AdapterDocumentos.MyViewHolder> {

    private List<Solicitacoes> listaSolicitacoes;
    private Context context;


    public AdapterDocumentos(List<Solicitacoes> lista, Context context) {
        this.listaSolicitacoes = lista;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lista_documentos, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Solicitacoes solicitacoes = listaSolicitacoes.get(position);

        holder.textIdAdap.setText(solicitacoes.getId());
        holder.textCursoAdap.setText(solicitacoes.getCurso());
        holder.textDataAdap.setText(solicitacoes.getData_pedido());
        holder.textStatusAdap.setText(solicitacoes.getStatus());
        holder.textSolicitadosAdap.setText(solicitacoes.getDocumentosSolicitados());

    }

    @Override
    public int getItemCount() {
        return listaSolicitacoes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textIdAdap, textCursoAdap, textDataAdap, textStatusAdap, textSolicitadosAdap;

        public MyViewHolder(View itemView){
            super(itemView);

            textIdAdap=itemView.findViewById(R.id.textIdAdap);
            textCursoAdap=itemView.findViewById(R.id.textCursoAdap);
            textDataAdap =itemView.findViewById(R.id.textDataAdap);
            textStatusAdap=itemView.findViewById(R.id.textStatusAdap);
            textSolicitadosAdap=itemView.findViewById(R.id.textSolicitadosAdap);
        }
    }
}
