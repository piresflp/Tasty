package com.example.tasty.activities.receita.pesquisar;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasty.R;
import com.example.tasty.activities.receita.visualizar.ReceitaActivity;
import com.example.tasty.adapters.receita.BuscarReceitaAdapter;
import com.example.tasty.adapters.receita.ReceitaFavAdapter;
import com.example.tasty.errorHandling.ErroJson;
import com.example.tasty.retrofit.config.RetrofitConfig;
import com.example.tasty.retrofit.models.Favorito;
import com.example.tasty.retrofit.models.PesquisaReceita;
import com.example.tasty.retrofit.models.Receita;
import com.example.tasty.retrofit.services.ReceitaService;
import com.google.gson.Gson;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import java.util.ArrayList;
import java.util.List;

public class PesquisarReceitaActivity extends AppCompatActivity {
    EditText edtPesquisa;
    RecyclerView ReceitasRecyclerView;
    BuscarReceitaAdapter receitasAdapter;
    ListView lvReceitasEncontradas;
    List<Receita> listaReceitas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar_receita);

        lvReceitasEncontradas = findViewById(R.id.lvReceitas);
        //ReceitasRecyclerView = findViewById(R.id.rvReceitas);
        listaReceitas = new ArrayList<>();

        edtPesquisa = findViewById(R.id.edtPesquisa);
        edtPesquisa.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    realizarBusca(edtPesquisa.getText().toString().trim());
                }
                return false;
            }
        });
    }

    private void realizarBusca(String input) {
        if (!input.equals(""))
        {
            PesquisaReceita novaPesquisa = new PesquisaReceita(input);
            ReceitaService service = RetrofitConfig.createService(ReceitaService.class);
            Call<List<Receita>> call = service.pesquisarReceitas(novaPesquisa);
            call.enqueue(new Callback<List<Receita>>() {
                @Override
                public void onResponse(Response<List<Receita>> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        if(response.body().size() == 0)
                            Toast.makeText(getApplicationContext(), "Nenhuma receita foi encontrada.", Toast.LENGTH_LONG).show();
                        else{
                            listaReceitas = response.body();
                            final ReceitaFavAdapter adapter = new ReceitaFavAdapter(getApplicationContext(),R.layout.receita_fav_item, listaReceitas);
                            lvReceitasEncontradas.setAdapter(adapter);

                            lvReceitasEncontradas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Gson gson = new Gson();
                                    String receitaJson = gson.toJson(listaReceitas.get(position));
                                    Intent intent = new Intent(PesquisarReceitaActivity.this, ReceitaActivity.class);
                                    intent.putExtra("receita", receitaJson);
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                    else
                    {
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
                    Toast.makeText(getApplicationContext(), "erro2: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
