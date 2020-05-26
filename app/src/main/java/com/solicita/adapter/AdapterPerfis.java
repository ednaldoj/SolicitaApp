package com.solicita.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solicita.R;
import com.solicita.helper.SharedPrefManager;
import com.solicita.model.Perfil;
import com.solicita.network.ApiInterface;

import java.util.List;

public class AdapterPerfis extends RecyclerView.Adapter<AdapterPerfis.MyViewHolder> {

    private List<Perfil> listaPerfis;
    private Context context;
    SharedPrefManager sharedPrefManager;
    ApiInterface apiInterface;

    @NonNull
    @Override
    public AdapterPerfis.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lista_perfis, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPerfis.MyViewHolder holder, int position) {
        Perfil perfil = listaPerfis.get(position);

    }

    @Override
    public int getItemCount() {
        return listaPerfis.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        RadioGroup radioGroupPerfis;
        Button buttonPerfil;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            radioGroupPerfis = itemView.findViewById(R.id.radioGroupPerfis);
            buttonPerfil = itemView.findViewById(R.id.buttonPerfil);

        }
    }
}
