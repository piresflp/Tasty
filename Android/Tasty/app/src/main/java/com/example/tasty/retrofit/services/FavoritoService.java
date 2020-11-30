package com.example.tasty.retrofit.services;

import com.example.tasty.retrofit.models.Favorito;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface FavoritoService {
    @GET("favorito/{idUsuario}")
    Call<String> verificarFavorito(@Path("idUsuario") int idUsuario);

    @POST("favorito")
    Call<Favorito> inserirFavorito(@Body Favorito favorito);

    @DELETE("favorito/{idUsuario}/{idReceita}")
    Call<Favorito> removerFavorito(@Path("idUsuario") int idUsuario, @Path("idReceita") int idReceita);

}
