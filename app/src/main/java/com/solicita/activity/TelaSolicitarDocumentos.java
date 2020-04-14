package com.solicita.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.solicita.R;
import com.solicita.helper.DateCustom;
import com.solicita.model.Requisicao;

import java.util.ArrayList;

public class TelaSolicitarDocumentos extends AppCompatActivity {

    public EditText editCampoProgDisciplina, editCampoOutros;
    private Spinner campoPerfil;
    private CheckBox checkDeclaracaoVinculo, checkComprovanteMatricula, checkHistorico, checkProgramaDisciplina, checkOutros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_solicitar_documentos);

        //Inicializar componentes
        campoPerfil = findViewById(R.id.spinnerPerfil);
        checkDeclaracaoVinculo = findViewById(R.id.checkDeclaracaoVinculo);
        checkComprovanteMatricula = findViewById(R.id.checkComprovanteMatricula);
        checkHistorico = findViewById(R.id.checkHistorico);
        checkProgramaDisciplina = findViewById(R.id.checkProgramaDisciplina);
        checkOutros = findViewById(R.id.checkOutros);

        adicionarListenerCheckProg();
        adicionarListenerCheckOutros();

    }

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

        String textoPerfil = campoPerfil.getSelectedItem().toString();

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
    }

}





















