package com.example.tasty.activities.receita.buscar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasty.R;
import com.example.tasty.activities.receita.visualizar.ReceitaActivity;
import com.example.tasty.adapters.receita.BuscarReceitaAdapter;
import com.example.tasty.adapters.receita.ReceitaFavAdapter;
import com.example.tasty.retrofit.config.RetrofitConfig;
import com.example.tasty.retrofit.models.Receita;
import com.example.tasty.retrofit.services.ReceitaService;
import com.google.gson.Gson;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import java.util.ArrayList;
import java.util.List;

public class BuscarReceitaActivity extends AppCompatActivity {
    RecyclerView ReceitasRecyclerView;
    BuscarReceitaAdapter receitasAdapter;
    List<Receita> listaReceitas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_receita);

        ReceitasRecyclerView = findViewById(R.id.rvReceitas);
        listaReceitas = new ArrayList<>();

        ReceitaService service = RetrofitConfig.createService(ReceitaService.class);
        Call<List<Receita>> call = service.consultarReceitas();
        call.enqueue(new Callback<List<Receita>>() {
            @Override
            public void onResponse(Response<List<Receita>> response, Retrofit retrofit) {
                if(response.isSuccess()) {
                    listaReceitas = response.body();

                    receitasAdapter = new BuscarReceitaAdapter(getApplicationContext(), listaReceitas);
                    ReceitasRecyclerView.setAdapter(receitasAdapter);
                    ReceitasRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


                    /*listViewReceita.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Gson gson = new Gson();
                            String receitaJson = gson.toJson(listaReceitas.get(position));
                            Intent intent = new Intent(getApplicationContext(), ReceitaActivity.class);
                            intent.putExtra("receita", receitaJson);
                            startActivity(intent);
                        }
                    });*/
                }
                else
                    Toast.makeText(getApplicationContext(), "erro1", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable t) {
                    Toast.makeText(getApplicationContext(), "erro2: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
