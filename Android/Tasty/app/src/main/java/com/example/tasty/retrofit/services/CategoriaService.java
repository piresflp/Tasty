package com.example.tasty.retrofit.services;

import com.example.tasty.retrofit.models.Categoria;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface CategoriaService {
    @GET("categoria")
    Call<List<Categoria>> consultarTodasCategorias();

    @GET("categoria/{id}")
    Call<Categoria> consultarPorID(@Path("id") int id);
}
