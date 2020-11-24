package com.example.tasty.sessionManagement;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tasty.retrofit.models.Usuario;
import com.google.gson.Gson;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NOME = "session";
    String SESSION_KEY = "session_usuario";

    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NOME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void salvarSession(Usuario usuario){
        // salva a sessão sempre que o usuário se conecta
        Gson gson = new Gson();
        String usuarioJson = gson.toJson(usuario);

        int id = usuario.getId();

        editor.putString("usuarioLogado", usuarioJson).commit();
        editor.putInt(SESSION_KEY, id).commit();
    }

    public void removerSession(){
        editor.putInt(SESSION_KEY, -1).commit();
    }

    public int getSessionId(){
        // retorna o id do usuário conectado
        return sharedPreferences.getInt(SESSION_KEY, -1);
    }

    public Usuario getSession(){
        Gson gson = new Gson();
        String usuarioJson = sharedPreferences.getString("usuarioLogado", "");
        return gson.fromJson(usuarioJson, Usuario.class);
    }



}
