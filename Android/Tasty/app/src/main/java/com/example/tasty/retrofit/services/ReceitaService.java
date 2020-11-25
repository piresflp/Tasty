package com.example.tasty.retrofit.services;

import com.example.tasty.retrofit.models.Receita;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface ReceitaService {
    @GET("receita/favorito/{id}")
    Call<String> consultarQuantidadeFavoritos(@Path("id") int id);

    @GET("receita")
    Call<List<Receita>> consultarReceitas();

    @GET("receita/{id}")
    Call<Receita> consultarReceitaPorID(@Path("id") int id);

    @POST("receita")
    Call<Receita> inserirReceita(@Body Receita receita);
    /*
    @POST("usuario/login")
    Call<Usuario> autenticarUsuario(@Body Usuario usuario);

    @PUT("usuario/{id}")
    Call<Usuario> alterarUsuario(@Path("id") int id, @Body Usuario usuario);

    @DELETE("usuario/{id}")
    Call<Usuario> excluirUsuario(@Path("id") int id);
     */
}
