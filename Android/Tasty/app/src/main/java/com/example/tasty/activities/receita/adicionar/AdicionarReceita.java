package com.example.tasty.activities.receita.adicionar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import com.example.tasty.R;
import com.example.tasty.retrofit.config.RetrofitConfig;
import com.example.tasty.retrofit.models.Categoria;
import com.example.tasty.retrofit.services.CategoriaService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdicionarReceita extends AppCompatActivity {
    List<String> listaCategoriaString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receita);

        Toolbar toolbar = findViewById(R.id.toolbarAdd);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listaCategoriaString = new ArrayList<String>();
        ImageButton btnAddIngredientes = findViewById(R.id.btnAddIngredientes);
        ImageButton btnAddPreparo = findViewById(R.id.btnAddPreparo);
        Button btnCancelarIngredientes = findViewById(R.id.btnCancelarIngredientes);
        Button btnAdicionarIngredientes = findViewById(R.id.btnAdicionarIngredientes);
        Button btnCancelarPreparo = findViewById(R.id.btnCancelarPreparo);
        Button btnAdicionarPreparo = findViewById(R.id.btnAdicionarPreparo);

        carregarCategorias();

        btnAddIngredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogIngredientes();
            }
        });

        btnAddPreparo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogPreparo();
            }
        });
    }

    private void carregarCategorias(){
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        CategoriaService service = RetrofitConfig.createService(CategoriaService.class);
        Call<List<Categoria>> call = service.consultarTodasCategorias();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Response<List<Categoria>> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    List<Categoria> listaCategorias = response.body();

                    for(Categoria categoria : listaCategorias)
                        listaCategoriaString.add(categoria.getNome());

                    final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,listaCategoriaString){
                        @Override
                        public boolean isEnabled(int position){
                            if(position == 0)
                                return false;
                            return true;
                        }
                    };
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(spinnerArrayAdapter);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro ao carregar categorias: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openDialogIngredientes() {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_ingredientes);
        dialog.show();
    }

    private void openDialogPreparo() {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_preparo   );
        dialog.show();
    }
}