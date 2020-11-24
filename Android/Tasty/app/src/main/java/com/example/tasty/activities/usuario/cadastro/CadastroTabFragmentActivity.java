package com.example.tasty.activities.usuario.cadastro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import androidx.fragment.app.Fragment;

import com.example.tasty.R;
import com.example.tasty.activities.main.MainActivity;
import com.example.tasty.errorHandling.ErroJson;
import com.example.tasty.retrofit.config.RetrofitConfig;
import com.example.tasty.retrofit.models.Usuario;
import com.example.tasty.retrofit.services.UsuarioService;
import com.example.tasty.sessionManagement.SessionManagement;
import com.google.gson.Gson;

public class CadastroTabFragmentActivity extends Fragment {
    EditText edtNome, edtEmail, edtNomeDeUsuario, edtSenha, edtConfirmarSenha;
    Button btnCadastrar;
    float v = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_cadastro_tab, container, false);

        edtNome = root.findViewById(R.id.edtNome);
        edtEmail = root.findViewById(R.id.edtEmail);
        edtNomeDeUsuario = root.findViewById(R.id.edtNomeUsuario);
        edtSenha = root.findViewById(R.id.edtSenha);
        edtConfirmarSenha = root.findViewById(R.id.edtConfirmarSenha);
        btnCadastrar = root.findViewById(R.id.btnCadastrar);

        edtNome.setTranslationX(800);
        edtEmail.setTranslationX(800);
        edtNomeDeUsuario.setTranslationX(800);
        edtSenha.setTranslationX(800);
        edtConfirmarSenha.setTranslationX(800);
        btnCadastrar.setTranslationX(800);

        edtNome.setAlpha(v);
        edtEmail.setAlpha(v);
        edtNomeDeUsuario.setAlpha(v);
        edtSenha.setAlpha(v);
        edtConfirmarSenha.setAlpha(v);
        btnCadastrar.setAlpha(v);

        edtNome.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(200).start();
        edtEmail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        edtNomeDeUsuario.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        edtSenha.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        edtConfirmarSenha.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        btnCadastrar.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        btnCadastrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String nome = edtNome.getText().toString().trim();
                final String email = edtEmail.getText().toString().trim();
                final String nomeDeUsuario = edtNomeDeUsuario.getText().toString().trim();
                final String senha = edtSenha.getText().toString().trim();

                if(isCamposValidos(nome, email, nomeDeUsuario, senha)){
                    try {
                        Usuario novoUsuario = new Usuario(nome, email, nomeDeUsuario, senha);
                        UsuarioService service = RetrofitConfig.createService(UsuarioService.class);
                        Call<Usuario> call = service.inserirUsuario(novoUsuario);
                        call.enqueue(new Callback<Usuario>(){
                            @Override
                            public void onResponse(Response<Usuario> resposta, Retrofit retrofit){
                                if(resposta.isSuccess()) {
                                    Usuario usuarioCadastrado = resposta.body();

                                    // salva a sessão do usuário
                                    SessionManagement sessionManagement = new SessionManagement(getContext());
                                    sessionManagement.salvarSession(usuarioCadastrado);

                                    irParaTelaPrincipal();
                                    Toast.makeText(getContext(), usuarioCadastrado.toString(), Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    try {
                                        Gson gson = new Gson();
                                        ErroJson erro = gson.fromJson(resposta.errorBody().string(), ErroJson.class);
                                        Toast.makeText(getContext(), erro.getError(), Toast.LENGTH_SHORT).show();
                                    }
                                    catch (Exception e){
                                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Throwable t){
                                Toast.makeText(getContext(), "Erro: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }catch(Exception e){
                        Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        return root;
    }

    private boolean isCamposValidos(String nome, String email, String nomeDeUsuario, String senha){
        if(nome.equals("") || email.equals("") || nomeDeUsuario.equals("")||senha.equals("")) {
            Toast.makeText(getContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return false;
        }

        String confirmarSenha = edtConfirmarSenha.getText().toString().trim();
        if(!senha.trim().equals(confirmarSenha)){
            Toast.makeText(getContext(), "Senhas incompatíveis!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void irParaTelaPrincipal(){
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
