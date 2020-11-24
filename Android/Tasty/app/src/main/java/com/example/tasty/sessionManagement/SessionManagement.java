package com.example.tasty.sessionManagement;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tasty.retrofit.models.Usuario;

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
        int id = usuario.getId();

        editor.putInt(SESSION_KEY, id).commit();
    }

    public void removerSession(){
        editor.putInt(SESSION_KEY, -1).commit();
    }

    public int getSession(){
        // retorna o id do usuário conectado
        return sharedPreferences.getInt(SESSION_KEY, -1);
    }



}
