package com.example.tasty.activities.receita.visualizar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tasty.ExpandableHeightListView;
import com.example.tasty.R;
import com.example.tasty.adapters.receita.ReceitaCategoriaAdapter;
import com.example.tasty.retrofit.config.RetrofitConfig;
import com.example.tasty.retrofit.models.Categoria;
import com.example.tasty.retrofit.models.Receita;
import com.example.tasty.retrofit.services.CategoriaService;
import com.example.tasty.retrofit.services.ReceitaService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ReceitaCategoria extends AppCompatActivity {

    List<Receita> listaReceitas = new ArrayList<Receita>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita_categoria);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView tvNomeCategoria = findViewById(R.id.tvNomeCategoria);
        final TextView tvQtsReceita = findViewById(R.id.tvQtsReceitasCategoria);
        final ExpandableHeightListView listView = findViewById(R.id.listViewReceitaCategoria);

        Intent intent = getIntent();
        int idCategoria = intent.getIntExtra("idCategoria", -1);
        CategoriaService service = RetrofitConfig.createService(CategoriaService.class);
        Call<Categoria> call = service.consultarPorID(idCategoria);
        call.enqueue(new Callback<Categoria>() {
            @Override
            public void onResponse(Response<Categoria> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Categoria categoria = response.body();
                    listaReceitas = categoria.getFkCategoriaReceita();
                    tvNomeCategoria.setText(categoria.getNome());
                    tvQtsReceita.setText(String.valueOf(listaReceitas.size()) + " Receitas");

                    ReceitaCategoriaAdapter adapter = new ReceitaCategoriaAdapter(getContext(), R.layout.receita_categoria_item, listaReceitas);
                    listView.setExpanded(true);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getContext(), ReceitaActivity.class);
                            intent.putExtra("receita", listaReceitas.get(position).getId());
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public Context getContext(){
        return (Context)this;
    }

}