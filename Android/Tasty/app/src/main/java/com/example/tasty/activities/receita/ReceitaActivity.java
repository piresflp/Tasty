package com.example.tasty.activities.receita;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import com.example.tasty.R;
import com.example.tasty.errorHandling.ErroJson;
import com.example.tasty.retrofit.config.RetrofitConfig;
import com.example.tasty.retrofit.models.Receita;
import com.example.tasty.retrofit.services.ReceitaService;
import com.google.gson.Gson;

public class ReceitaActivity extends AppCompatActivity {
    TextView tvNomeReceita, tvNomeUsuarioReceita, tvPorcoes, tvTempo ,tvIngredientes, tvPreparo, tvQtdFavoritos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita);

        tvNomeReceita = findViewById(R.id.tvNomeReceita);
        tvNomeUsuarioReceita = findViewById(R.id.tvNomeUsuarioReceita);
        tvPorcoes = findViewById(R.id.tvPorcoes);
        tvTempo = findViewById(R.id.tvTempo);
        tvIngredientes = findViewById(R.id.tvIngredientes);
        tvPreparo = findViewById(R.id.tvPreparo);
        tvQtdFavoritos = findViewById(R.id.tvQtdFavoritos);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String receitaJson = intent.getStringExtra("receita");
        Gson gson = new Gson();
        Receita receita = gson.fromJson(receitaJson, Receita.class);

        tvNomeUsuarioReceita.setText("Publicada por: "+receita.getFkReceitaUsuario().getNomeDeUsuario());
        tvNomeReceita.setText(receita.getTitulo());
        tvPorcoes.setText(Integer.toString(receita.getRendimento()));
        tvTempo.setText(Integer.toString(receita.getTempoDePreparo()));
        tvIngredientes.setText(receita.getIngredientes());
        tvPreparo.setText(receita.getModoDePreparo());
        setQtdFavoritos(receita.getId());
    }

    private void setQtdFavoritos(int idReceita){
        ReceitaService service = RetrofitConfig.createService(ReceitaService.class);
        Call<String> call = service.consultarQuantidadeFavoritos(idReceita);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                if(response.isSuccess())
                    tvQtdFavoritos.setText(response.body());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}