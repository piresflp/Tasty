package com.example.tasty.activities.receita.adicionar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tasty.ExpandableHeightListView;
import com.example.tasty.R;
import com.example.tasty.activities.main.MainActivity;
import com.example.tasty.adapters.receita.AddIngredienteAdapter;
import com.example.tasty.adapters.receita.AddPreparoAdapter;
import com.example.tasty.adapters.receita.ReceitaFavAdapter;
import com.example.tasty.retrofit.config.RetrofitConfig;
import com.example.tasty.retrofit.models.Receita;
import com.example.tasty.retrofit.services.ReceitaService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class AdicionarReceita extends AppCompatActivity {

    List<String> ingredientes = new ArrayList<String>();
    List<String> preparo = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receita);

        Toolbar toolbar = findViewById(R.id.toolbarAdd);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ImageButton btnAddIngredientes = findViewById(R.id.btnAddIngredientes);
        ImageButton btnAddPreparo = findViewById(R.id.btnAddPreparo);
        Button btnAddReceita = findViewById(R.id.btnAddReceita);
        final EditText titulo = findViewById(R.id.edtAddTitulo);
        final EditText tempo = findViewById(R.id.edtAddTempo);
        final EditText porcao = findViewById(R.id.edtAddPorcao);

        String[] categorias = new String[]{
                "Categoria",
                "Bolos e tortas doces",
                "Carnes",
                "Massas",
                "Bebidas"
        };

        final List<String> categoriaList = new ArrayList<>(Arrays.asList(categorias));
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item,categoriaList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

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

        btnAddReceita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String addIngredientes = new String();
                for (String ingrediente : ingredientes) {
                    addIngredientes += ingrediente + "\n\n";
                }
                String addPreparo = new String();
                int i = 1;
                for (String preparo : preparo) {
                    addPreparo += i + " - " + preparo + "\n\n";
                    i++;
                }
                if (Integer.parseInt(porcao.getText().toString()) != 0 || Integer.parseInt(tempo.getText().toString()) != 0 || !titulo.getText().toString().equals("") || !spinner.getSelectedItem().toString().equals("") || !addIngredientes.equals("") || !addPreparo.equals(""))
                {
                    try {
                        Receita novaReceita = new Receita(Integer.parseInt(porcao.getText().toString()), Integer.parseInt(tempo.getText().toString()), titulo.getText().toString(), spinner.getSelectedItem().toString(), addIngredientes, addPreparo);
                        ReceitaService service = RetrofitConfig.createService(ReceitaService.class);
                        Call<Receita> call = service.inserirReceita(novaReceita);
                        call.enqueue(new Callback<Receita>() {
                            @Override
                            public void onResponse(Response<Receita> response, Retrofit retrofit) {
                                Toast.makeText(v.getContext(), "Receita adicionada com sucesso", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(v.getContext(), MainActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(v.getContext(), "Falha ao adicionar receita", Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void openDialogIngredientes() {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_ingredientes);
        dialog.show();
        Button btnCancelarIngredientes = dialog.findViewById(R.id.btnCancelarIngredientes);
        btnCancelarIngredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        Button btnAdcionarIngredientes = dialog.findViewById(R.id.btnAdicionarIngredientes);
        final EditText edtIngredientes = dialog.findViewById(R.id.edtAddIngredientes);
        btnAdcionarIngredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtIngredientes.getText().equals("")) {
                    String str = edtIngredientes.getText().toString();
                    ingredientes.add(str);
                    AddIngredienteAdapter adapter = new AddIngredienteAdapter(v.getContext(),R.layout.ingrediente_item, ingredientes);
                    ExpandableHeightListView listViewIngredientes = findViewById(R.id.listViewIngredientes);
                    listViewIngredientes.setExpanded(true);
                    listViewIngredientes.setAdapter(adapter);
                    dialog.cancel();
                }
            }
        });
    }

    public void openDialogPreparo() {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_preparo);
        dialog.show();
        Button btnCancelarPreparo = dialog.findViewById(R.id.btnCancelarPreparo);
        btnCancelarPreparo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        Button btnAdcionarPreparo = dialog.findViewById(R.id.btnAdicionarPreparo);
        final EditText edtPreparo = dialog.findViewById(R.id.edtAddPreparo);
        btnAdcionarPreparo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtPreparo.getText().equals("")) {
                    String str = edtPreparo.getText().toString();
                    preparo.add(str);
                    AddPreparoAdapter adapter = new AddPreparoAdapter(v.getContext(), R.layout.preparo_item, preparo);
                    ExpandableHeightListView listViewPreparo = findViewById(R.id.listViewPreparo);
                    listViewPreparo.setExpanded(true);
                    listViewPreparo.setAdapter(adapter);
                    dialog.cancel();
                }
            }
        });
    }
}