package com.example.tasty.activities.receita.adicionar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


import com.example.tasty.ExpandableHeightListView;
import com.example.tasty.R;

import com.example.tasty.activities.main.MainActivity;
import com.example.tasty.adapters.receita.AddIngredienteAdapter;
import com.example.tasty.adapters.receita.AddPreparoAdapter;
import com.example.tasty.errorHandling.ErroJson;
import com.example.tasty.retrofit.config.RetrofitConfig;
import com.example.tasty.retrofit.models.Receita;
import com.example.tasty.retrofit.models.Usuario;
import com.example.tasty.retrofit.services.ReceitaService;

import com.example.tasty.retrofit.config.RetrofitConfig;
import com.example.tasty.retrofit.models.Categoria;
import com.example.tasty.retrofit.services.CategoriaService;
import com.example.tasty.sessionManagement.SessionManagement;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;


public class AdicionarReceita extends AppCompatActivity {
    List<String> listaCategoriaString;
    List<String> listaIngredientes = new ArrayList<String>();
    List<String> listaPreparo = new ArrayList<String>();
    List<Categoria> listaCategorias;

    int rec = 1;
    Bitmap bitmap;
    byte[] imagemTransformada;

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
        Button btnAddReceita = findViewById(R.id.btnAddReceita);
        final EditText edtTitulo = findViewById(R.id.edtAddTitulo);
        final EditText edtTempo = findViewById(R.id.edtAddTempo);
        final EditText edtPorcao = findViewById(R.id.edtAddPorcao);
        final ImageButton btnAddImagem = findViewById(R.id.btnAddImage);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        carregarCategorias(spinner);

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
                for (String ingrediente : listaIngredientes) {
                    addIngredientes += ingrediente + "\n\n";
                }
                String addPreparo = new String();
                int i = 1;
                for (String preparo : listaPreparo) {
                    addPreparo += i + " - " + preparo + "\n\n";
                    i++;
                }

                final String rendimento = edtPorcao.getText().toString().trim();
                final String tempoDePreparo = edtTempo.getText().toString().trim();
                final String titulo = edtTitulo.getText().toString().trim();
                final Categoria categoriaSelecionada = listaCategorias.get(spinner.getSelectedItemPosition());

                if (isCamposValidos(rendimento, tempoDePreparo, titulo, categoriaSelecionada))
                {
                    try {
                        Receita novaReceita = new Receita(Integer.parseInt(rendimento), Integer.parseInt(tempoDePreparo), titulo, addIngredientes, addPreparo);

                        SessionManagement session =  new SessionManagement(v.getContext());
                        int idUsuario = session.getSessionId();
                        novaReceita.setIdUsuario(idUsuario);
                        novaReceita.setIdCategoria(categoriaSelecionada.getId());


                        ReceitaService service = RetrofitConfig.createService(ReceitaService.class);
                        Call<Receita> call = service.inserirReceita(novaReceita);
                        call.enqueue(new Callback<Receita>() {
                            @Override
                            public void onResponse(Response<Receita> response, Retrofit retrofit) {
                                if (response.isSuccess()) {
                                    Toast.makeText(v.getContext(), "Receita adicionada com sucesso!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    try {
                                        Gson gson = new Gson();
                                        ErroJson erro = gson.fromJson(response.errorBody().string(), ErroJson.class);
                                        Toast.makeText(v.getContext(), erro.getError(), Toast.LENGTH_SHORT).show();
                                    }
                                    catch (Exception e){
                                        Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(v.getContext(), "Falha ao adicionar receita: "+t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        ExpandableHeightListView listViewIngredientes = findViewById(R.id.listViewIngredientes);
        listViewIngredientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openDialogEditIngredientes(position);
            }
        });
        ExpandableHeightListView listViewPreparo = findViewById(R.id.listViewPreparo);
        listViewPreparo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openDialogEditPreparo(position);
            }
        });

        btnAddImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogImagem();
            }
        });

    }

    private boolean isCamposValidos(String rendimento, String tempoDePreparo, String titulo, Categoria umaCategoria){
        if(rendimento.equals("") || tempoDePreparo.equals("") || titulo.equals("") || umaCategoria == null){
            Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_LONG).show();
            return false;
        }

        if(Integer.parseInt(rendimento) < 0) {
            Toast.makeText(getApplicationContext(), "Rendimento inválido!", Toast.LENGTH_LONG).show();
            return false;
        }

        else if(Integer.parseInt(tempoDePreparo) < 0) {
            Toast.makeText(getApplicationContext(), "Tempo de preparo inválido!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void carregarCategorias(final Spinner spinner){
        CategoriaService service = RetrofitConfig.createService(CategoriaService.class);
        Call<List<Categoria>> call = service.consultarTodasCategorias();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Response<List<Categoria>> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    listaCategorias = response.body();

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
                    listaIngredientes.add(str);
                    AddIngredienteAdapter adapter = new AddIngredienteAdapter(v.getContext(),R.layout.ingrediente_item, listaIngredientes);
                    ExpandableHeightListView listViewIngredientes = findViewById(R.id.listViewIngredientes);
                    listViewIngredientes.setExpanded(true);
                    listViewIngredientes.setAdapter(adapter);
                    dialog.cancel();
                }
            }
        });
    }

    private void openDialogEditIngredientes(final int position){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_ingredientes);
        final EditText edtEditIngredientes = dialog.findViewById(R.id.edtEditIngredientes);
        edtEditIngredientes.setText(listaIngredientes.get(position));
        dialog.show();
        Button btnDeletarIngredientes = dialog.findViewById(R.id.btnDeletarIngredientes);
        btnDeletarIngredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaIngredientes.remove(position);
                AddIngredienteAdapter adapter = new AddIngredienteAdapter(v.getContext(), R.layout.ingrediente_item, listaIngredientes);
                ExpandableHeightListView listViewIngredientes = findViewById(R.id.listViewIngredientes);
                listViewIngredientes.setExpanded(true);
                listViewIngredientes.setAdapter(adapter);
                dialog.cancel();
            }
        });
        Button btnEditarIngredientes = dialog.findViewById(R.id.btnEditarIngredientes);
        btnEditarIngredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaIngredientes.set(position, edtEditIngredientes.getText().toString());
                AddIngredienteAdapter adapter = new AddIngredienteAdapter(v.getContext(), R.layout.ingrediente_item, listaIngredientes);
                ExpandableHeightListView listViewIngredientes = findViewById(R.id.listViewIngredientes);
                listViewIngredientes.setExpanded(true);
                listViewIngredientes.setAdapter(adapter);
                dialog.cancel();
            }
        });
    }

    private void openDialogPreparo() {
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
                    listaPreparo.add(str);
                    AddPreparoAdapter adapter = new AddPreparoAdapter(v.getContext(), R.layout.preparo_item, listaPreparo);
                    ExpandableHeightListView listViewPreparo = findViewById(R.id.listViewPreparo);
                    listViewPreparo.setExpanded(true);
                    listViewPreparo.setAdapter(adapter);
                    dialog.cancel();
                }
            }
        });
    }

    private void openDialogEditPreparo(final int position){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_preparo);
        final EditText edtEditPreparo = dialog.findViewById(R.id.edtEditPreparo);
        edtEditPreparo.setText(listaPreparo.get(position));
        dialog.show();
        Button btnDeletarPreparo = dialog.findViewById(R.id.btnDeletarPreparo);
        btnDeletarPreparo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaPreparo.remove(position);
                AddPreparoAdapter adapter = new AddPreparoAdapter(v.getContext(), R.layout.preparo_item, listaPreparo);
                ExpandableHeightListView listViewPreparo = findViewById(R.id.listViewPreparo);
                listViewPreparo.setExpanded(true);
                listViewPreparo.setAdapter(adapter);
                dialog.cancel();
            }
        });
        Button btnEditarPreparo = dialog.findViewById(R.id.btnEditarPreparo);
        btnEditarPreparo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaPreparo.set(position, edtEditPreparo.getText().toString());
                AddPreparoAdapter adapter = new AddPreparoAdapter(v.getContext(), R.layout.preparo_item, listaPreparo);
                ExpandableHeightListView listViewPreparo = findViewById(R.id.listViewPreparo);
                listViewPreparo.setExpanded(true);
                listViewPreparo.setAdapter(adapter);
                dialog.cancel();
            }
        });
    }

    private void openDialogImagem(){
        Intent galeria = new Intent(Intent.ACTION_PICK);
        galeria.setType("image/*");
        startActivityForResult(galeria, rec);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == rec && resultCode == RESULT_OK)
        {
            Uri uri = data.getData();
            try
            {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                transformar(bitmap);
            }
            catch(Exception e){}
        }
    }

    public void transformar(Bitmap bitmap)
    {
        ByteArrayOutputStream baite = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baite);

        byte[] vetorDeByte = baite.toByteArray();

        imagemTransformada = vetorDeByte;
    }
}