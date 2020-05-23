package com.solicita.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solicita.R;

public class AdapterPerfis extends RecyclerView.Adapter<AdapterPerfis.MyViewHolder> {
    @NonNull
    @Override
    public AdapterPerfis.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPerfis.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
