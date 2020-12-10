package com.example.tasty.activities.receita.visualizar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import com.example.tasty.R;
import com.example.tasty.errorHandling.ErroJson;
import com.example.tasty.retrofit.config.RetrofitConfig;
import com.example.tasty.retrofit.models.Favorito;
import com.example.tasty.retrofit.models.Receita;
import com.example.tasty.retrofit.services.FavoritoService;
import com.example.tasty.retrofit.services.ReceitaService;
import com.example.tasty.retrofit.services.UsuarioService;
import com.example.tasty.sessionManagement.SessionManagement;
import com.google.gson.Gson;

import java.util.concurrent.CompletableFuture;

public class ReceitaActivity extends AppCompatActivity {
    int idUsuario;
    TextView tvNomeReceita, tvNomeUsuarioReceita, tvPorcoes, tvTempo ,tvIngredientes, tvPreparo, tvQtdFavoritos;
    ImageButton btnFavorito;
    int idReceita;

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
        btnFavorito = findViewById(R.id.btnFavorito);

        SessionManagement session = new SessionManagement(getApplicationContext());
        idUsuario = session.getSessionId();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        idReceita = intent.getIntExtra("receita", -1);
        ReceitaService service = RetrofitConfig.createService(ReceitaService.class);
        Call<Receita> call = service.consultarReceitaPorID(idReceita);
        call.enqueue(new Callback<Receita>() {
            @Override
            public void onResponse(Response<Receita> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Receita receita = response.body();
                    carregarDadosReceita(receita);
                }
                else {
                    try {
                        Gson gson = new Gson();
                        ErroJson erro = gson.fromJson(response.errorBody().string(), ErroJson.class);
                        Toast.makeText(getContext(), erro.getError(), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void carregarDadosReceita(Receita receita){
        tvNomeUsuarioReceita.setText("Publicada por: "+receita.getFkReceitaUsuario().getNomeDeUsuario());
        tvNomeReceita.setText(receita.getTitulo());
        tvPorcoes.setText(Integer.toString(receita.getRendimento()) + " PORÇÕES");
        tvTempo.setText(Integer.toString(receita.getTempoDePreparo()) + " MIN");
        tvIngredientes.setText(receita.getIngredientes());
        tvPreparo.setText(receita.getModoDePreparo());
        setQtdFavoritos(receita);
        verificarBotaoFavorito(receita);
    }

    private void verificarBotaoFavorito(Receita receita){
        UsuarioService service = RetrofitConfig.createService(UsuarioService.class);
        Call<String> call = service.verificarFavorito(idUsuario, idReceita);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                if (response.body().equals("0")) {
                    // receita não está favoritada
                    btnFavorito.setBackground(getResources().getDrawable(R.drawable.baseline_favorite_border_black_24dp));
                    btnFavorito.setOnClickListener(adicionarFavorito);
                }
                else{
                    // receita favoritada
                    btnFavorito.setBackground(getResources().getDrawable(R.drawable.coracao));
                    btnFavorito.setOnClickListener(removerFavorito);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private View.OnClickListener removerFavorito = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FavoritoService service = RetrofitConfig.createService(FavoritoService.class);
            Call<Favorito> call = service.removerFavorito(idUsuario, idReceita);
            call.enqueue(new Callback<Favorito>() {
                @Override
                public void onResponse(Response<Favorito> response, Retrofit retrofit) {
                    if(response.isSuccess()){
                        tvQtdFavoritos.setText(String.valueOf(Integer.parseInt(tvQtdFavoritos.getText().toString())-1));
                        btnFavorito.setBackground(getResources().getDrawable(R.drawable.baseline_favorite_border_black_24dp));
                        btnFavorito.setOnClickListener(adicionarFavorito);
                    }
                    else {
                        try {
                            Gson gson = new Gson();
                            ErroJson erro = gson.fromJson(response.errorBody().string(), ErroJson.class);
                            Toast.makeText(getApplicationContext(), erro.getError(), Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getApplicationContext(), "Erro: "+t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private View.OnClickListener adicionarFavorito = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Favorito novoFavorito = new Favorito(idUsuario, idReceita);

                FavoritoService service = RetrofitConfig.createService(FavoritoService.class);
                Call<Favorito> call = service.inserirFavorito(novoFavorito);
                call.enqueue(new Callback<Favorito>() {
                    @Override
                    public void onResponse(Response<Favorito> response, Retrofit retrofit) {
                        if(response.isSuccess()) {
                            tvQtdFavoritos.setText(String.valueOf(Integer.parseInt(tvQtdFavoritos.getText().toString())+1));
                            btnFavorito.setBackground(getResources().getDrawable(R.drawable.coracao));
                            btnFavorito.setOnClickListener(removerFavorito);
                        }
                        else {
                            try {
                                Gson gson = new Gson();
                                ErroJson erro = gson.fromJson(response.errorBody().string(), ErroJson.class);
                                Toast.makeText(getApplicationContext(), erro.getError(), Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e){
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getApplicationContext(), "Erro: "+t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }
    };

    private void setQtdFavoritos(Receita receita){
        int idReceita = receita.getId();
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

    public Context getContext(){
        return (Context)this;
    }

}