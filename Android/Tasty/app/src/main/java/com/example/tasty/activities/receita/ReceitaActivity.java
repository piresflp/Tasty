package com.example.tasty.activities.receita;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tasty.R;
import com.example.tasty.errorHandling.ErroJson;
import com.example.tasty.retrofit.models.Receita;
import com.google.gson.Gson;

public class ReceitaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String receitaJson = intent.getStringExtra("receita");
        Gson gson = new Gson();
        Receita receita = gson.fromJson(receitaJson, Receita.class);
        TextView tvNomeReceita = findViewById(R.id.tvNomeReceita);
        TextView tvNomeUsuarioReceita = findViewById(R.id.tvNomeUsuarioReceita);
        TextView tvPorcoes = findViewById(R.id.tvPorcoes);
        TextView tvTempo = findViewById(R.id.tvTempo);
        TextView tvIngredientes = findViewById(R.id.tvIngredientes);
        TextView tvPreparo = findViewById(R.id.tvPreparo);

        tvNomeReceita.setText(receita.getTitulo());
        tvPorcoes.setText(Integer.toString(receita.getRendimento()));
        tvTempo.setText(Integer.toString(receita.getTempoDePreparo()));
        tvIngredientes.setText(receita.getIngredientes());
        tvPreparo.setText(receita.getModoDePreparo());
    }
}