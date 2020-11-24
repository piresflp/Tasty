package com.example.tasty.activities.usuario.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tasty.R;
import com.example.tasty.activities.main.MainActivity;
import com.example.tasty.errorHandling.ErroJson;
import com.example.tasty.retrofit.config.RetrofitConfig;
import com.example.tasty.retrofit.models.Usuario;
import com.example.tasty.retrofit.services.UsuarioService;
import com.example.tasty.sessionManagement.SessionManagement;
import com.google.gson.Gson;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginTabFragmentActivity extends Fragment {

    EditText edtEmail, edtSenha;
    Button btnEntrar;
    float v = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_login_tab, container, false);

        edtEmail = root.findViewById(R.id.edtEmailLogin);
        edtSenha = root.findViewById(R.id.edtSenhaLogin);
        btnEntrar = root.findViewById(R.id.btnEntrar);

        edtEmail.setTranslationX(800);
        edtSenha.setTranslationX(800);
        btnEntrar.setTranslationX(800);

        edtEmail.setAlpha(v);
        edtSenha.setAlpha(v);
        btnEntrar.setAlpha(v);

        edtEmail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        edtSenha.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        btnEntrar.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = edtEmail.getText().toString().trim();
                final String senha = edtSenha.getText().toString().trim();

                if(isCamposValidos(email, senha)){
                    try{
                        Usuario possivelUsuario = new Usuario();

                        possivelUsuario.setEmail(email);
                        possivelUsuario.setSenha(senha);

                        UsuarioService service = RetrofitConfig.createService(UsuarioService.class);
                        Call<Usuario> call = service.autenticarUsuario(possivelUsuario);
                        call.enqueue(new Callback<Usuario>() {
                            @Override
                            public void onResponse(Response<Usuario> resposta, Retrofit retrofit) {
                                if(resposta.isSuccess()) {
                                    Usuario usuarioLogado = resposta.body();

                                    // salva a sessão do usuário
                                    SessionManagement sessionManagement = new SessionManagement(getContext());
                                    sessionManagement.salvarSession(usuarioLogado);

                                    irParaTelaPrincipal();
                                }
                                else {  // caso o login não tenha sido sucedido
                                    try {
                                        // recebe a mensagem de erro em JSON da API e a exibe ao usuário
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
                            public void onFailure(Throwable t) {
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

    private boolean isCamposValidos(String email, String senha){
        if(email.equals("") || senha.equals("")){
            Toast.makeText(getContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT);
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
