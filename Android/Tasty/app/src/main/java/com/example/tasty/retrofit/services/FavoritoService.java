package com.example.tasty.retrofit.services;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface FavoritoService {
    @GET("favorito/{idUsuario}")
    Call<String> verificarFavorito(@Path("idUsuario") int idUsuario);

}
