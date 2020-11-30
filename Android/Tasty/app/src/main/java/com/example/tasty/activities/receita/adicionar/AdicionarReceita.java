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

import com.example.tasty.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdicionarReceita extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receita);

        Toolbar toolbar = findViewById(R.id.toolbarAdd);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ImageButton btnAddIngredientes = findViewById(R.id.btnAddIngredientes);
        ImageButton btnAddPreparo = findViewById(R.id.btnAddPreparo);
        Button btnCancelarIngredientes = findViewById(R.id.btnCancelarIngredientes);
        Button btnAdcionarIngredientes = findViewById(R.id.btnAdicionarIngredientes);
        Button btnCancelarPreparo = findViewById(R.id.btnCancelarPreparo);
        Button btnAdcionarPreparo = findViewById(R.id.btnAdicionarPreparo);

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
    }

    public void openDialogIngredientes() {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_ingredientes);
        dialog.show();
    }

    public void openDialogPreparo() {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_preparo   );
        dialog.show();
    }
}