package com.example.tasty.retrofit.services;

import com.example.tasty.retrofit.models.Receita;
import com.example.tasty.retrofit.models.Usuario;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface UsuarioService {
    @GET("usuario")
    Call<List<Usuario>> consultarUsuarios();

    @GET("usuario/{id}")
    Call<List<Receita>> consultarReceitasFavoritas(@Path("id") int id);

    @GET("usuario/{id}")
    Call<Usuario> consultarUsuarioPorID(@Path("id") int id);

    @POST("usuario")
    Call<Usuario> inserirUsuario(@Body Usuario usuario);

    @POST("usuario/login")
    Call<Usuario> autenticarUsuario(@Body Usuario usuario);

    @PUT("usuario/{id}")
    Call<Usuario> alterarUsuario(@Path("id") int id, @Body Usuario usuario);

    @DELETE("usuario/{id}")
    Call<Usuario> excluirUsuario(@Path("id") int id);
}
