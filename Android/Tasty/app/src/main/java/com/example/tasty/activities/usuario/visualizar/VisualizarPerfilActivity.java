package com.example.tasty.activities.usuario.visualizar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import com.example.tasty.R;
import com.example.tasty.activities.main.MainActivity;
import com.example.tasty.activities.receita.visualizar.ReceitasUsuario;
import com.example.tasty.errorHandling.ErroJson;
import com.example.tasty.retrofit.config.RetrofitConfig;
import com.example.tasty.retrofit.models.DadosPerfilUsuario;
import com.example.tasty.retrofit.models.Usuario;
import com.example.tasty.retrofit.services.ReceitaService;
import com.example.tasty.retrofit.services.UsuarioService;
import com.example.tasty.sessionManagement.SessionManagement;
import com.google.gson.Gson;

import java.util.List;

public class VisualizarPerfilActivity extends AppCompatActivity {
    TextView tvQtdReceitas, tvQtdFavoritos;
    EditText edtNomeCompleto, edtNomeUsuario, edtEmail;
    ImageButton btnSair;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_perfil);

        edtNomeCompleto = findViewById(R.id.edtNomeCompleto);
        edtNomeUsuario = findViewById(R.id.edtNomeUsuario);
        edtEmail = findViewById(R.id.edtEmail);
        tvQtdReceitas = findViewById(R.id.qtsReceitas);
        tvQtdFavoritos = findViewById(R.id.qtsFavoritos);
        RelativeLayout relativeLayout = findViewById(R.id.relativeUsuarioReceitas);

        Gson gson = new Gson();
        SessionManagement sessionManagement = new SessionManagement(getApplicationContext());
        final Usuario usuarioLogado = sessionManagement.getSession();

        carregarDadosUsuario(usuarioLogado);
        carregarReceitasRelacionadas(usuarioLogado.getId());

        btnSair = findViewById(R.id.btnSair);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagement sessionManagement = new SessionManagement(getApplicationContext());
                sessionManagement.removerSession();

                Intent intentOutraActivity = new Intent(VisualizarPerfilActivity.this, MainActivity.class);
                startActivity(intentOutraActivity);
            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReceitasUsuario.class);
                intent.putExtra("idUsuario", usuarioLogado.getId());
                startActivity(intent);
            }
        });
    }

    private void carregarDadosUsuario(Usuario usuarioLogado){

        edtNomeCompleto.setText(usuarioLogado.getNome());
        edtNomeUsuario.setText(usuarioLogado.getNomeDeUsuario());
        edtEmail.setText(usuarioLogado.getEmail());
    }

    private void carregarReceitasRelacionadas(int idUsuario){
        UsuarioService service = RetrofitConfig.createService(UsuarioService.class);
        Call<DadosPerfilUsuario> call = service.consultarDadosPerfilUsuario(idUsuario);

        call.enqueue(new Callback<DadosPerfilUsuario>() {
            @Override
            public void onResponse(Response<DadosPerfilUsuario> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    DadosPerfilUsuario dados = response.body();
                    tvQtdReceitas.setText(String.valueOf(dados.getQtdReceitasCriadas()));
                    tvQtdFavoritos.setText(String.valueOf(dados.getQtdReceitasFavoritadas()));
                }
                else{
                    try {
                        Gson gson = new Gson();
                        ErroJson erro = gson.fromJson(response.errorBody().string(), ErroJson.class);
                        Toast.makeText(getApplicationContext(), erro.getError(), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}