package com.solicita.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solicita.R;
import com.solicita.model.Requisicao;

import java.util.List;

public class AdapterDocumentos extends RecyclerView.Adapter<AdapterDocumentos.MyViewHolder> {

    private List<Requisicao> requisicoes;
    private Context context;

    public AdapterDocumentos(List<Requisicao> requisicoes, Context context){
        this.requisicoes = requisicoes;
        this.context=context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lista_documentos, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Requisicao requisicao = requisicoes.get(position);

//        holder.vinculo.setText(requisicao.getVinculo());
 //       holder.curso.setText(requisicao.getCurso());
   //     holder.dataRequisicao.setText(requisicao.getDataRequisicao());
     //   holder.status.setText(requisicao.getStatus());
       // holder.documentosSolicitados.setText(String.valueOf(requisicao.getDocumentosSolicitados()));

    }

    @Override
    public int getItemCount() {
        return requisicoes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView vinculo, curso, dataRequisicao, status, documentosSolicitados;

        public MyViewHolder(View itemView){
            super(itemView);

//            vinculo=itemView.findViewById(R.id.textInfoVinculo);
  //          System.out.println(vinculo);
            curso=itemView.findViewById(R.id.textCursoAdap);
            System.out.println(curso);
            dataRequisicao=itemView.findViewById(R.id.textDataAdap);
            System.out.println(dataRequisicao);
            status=itemView.findViewById(R.id.textStatusAdap);
            System.out.println(status);
            documentosSolicitados=itemView.findViewById(R.id.textSolicitadosAdap);
            System.out.println(documentosSolicitados);
        }
    }
}
