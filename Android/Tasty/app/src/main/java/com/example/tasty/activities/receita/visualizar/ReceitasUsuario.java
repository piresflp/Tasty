package com.example.tasty.activities.receita.visualizar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.tasty.ExpandableHeightListView;
import com.example.tasty.R;
import com.example.tasty.adapters.receita.ReceitaUsuarioAdapter;
import com.example.tasty.errorHandling.ErroJson;
import com.example.tasty.retrofit.config.RetrofitConfig;
import com.example.tasty.retrofit.models.Receita;
import com.example.tasty.retrofit.services.UsuarioService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ReceitasUsuario extends AppCompatActivity {

    List<Receita> listaReceitasUsuario = new ArrayList<Receita>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas_usuario);

        Toolbar toolbar = findViewById(R.id.toolbarAdd);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent =getIntent();
        int idUsuario = intent.getIntExtra("idUsuario", -1);

        UsuarioService service = RetrofitConfig.createService(UsuarioService.class);
        Call<List<Receita>> call = service.consultarReceitasPorId(idUsuario);
        call.enqueue(new Callback<List<Receita>>() {
            @Override
            public void onResponse(Response<List<Receita>> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    listaReceitasUsuario = response.body();
                    ReceitaUsuarioAdapter adapter = new ReceitaUsuarioAdapter(getContext(), R.layout.receita_usuario_item, listaReceitasUsuario);
                    ExpandableHeightListView listView = findViewById(R.id.listViewReceitaUsuario);
                    listView.setExpanded(true);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getContext(), ReceitaActivity.class);
                            intent.putExtra("receita", listaReceitasUsuario.get(position).getId());
                            startActivity(intent);
                        }
                    });
                }
                else {
                    try{
                        Gson gson = new Gson();
                        ErroJson erro = gson.fromJson(response.errorBody().string(), ErroJson.class);
                        Toast.makeText(getContext(), erro.getError(), Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public Context getContext(){
        return (Context)this;
    }
}