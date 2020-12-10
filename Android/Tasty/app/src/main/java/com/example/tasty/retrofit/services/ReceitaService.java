package com.example.tasty.retrofit.services;

import com.example.tasty.retrofit.models.PesquisaReceita;
import com.example.tasty.retrofit.models.Receita;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface ReceitaService {
    @GET("receita/favorito/{id}")
    Call<String> consultarQuantidadeFavoritos(@Path("id") int id);

    @GET("receita")
    Call<List<Receita>> consultarReceitas();

    @POST("receita/pesquisar")
    Call<List<Receita>> pesquisarReceitas(@Body PesquisaReceita umaPesquisa);

    @GET("receita/{id}")
    Call<Receita> consultarReceitaPorID(@Path("id") int id);

    @GET("home")
    Call<List<Receita>> consultarReceitasHome();

    @POST("receita")
    Call<Receita> inserirReceita(@Body Receita receita);
}
