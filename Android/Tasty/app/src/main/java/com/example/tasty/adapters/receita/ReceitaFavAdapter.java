package com.example.tasty.adapters.receita;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tasty.R;
import com.example.tasty.retrofit.models.Receita;

import java.util.List;

public class ReceitaFavAdapter extends ArrayAdapter<Receita> {

    Context context;
    int layoutResourceId;
    List<Receita> dados;


    public ReceitaFavAdapter(@NonNull Context context, int resource, @NonNull List<Receita> dados) {
        super(context, resource, dados);

        //Informação global do ambiente. Define o resource usado
        this.context = context;

        //Id do layout que queremos usar de referência
        this.layoutResourceId = resource;

        //Lista de dados
        this.dados = dados;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        //Verifica se existe alguma View pronta
        if(view == null) {

            /*
            Setar o layout de produto
            A classe LayoutInflater é usada para instaciar o conteudo do arquivo XML
            nos objetos da View correspondente

            Em outras palavras, ele pega um arquivo XML como entrada e cria os objetos View a partir dele

             */
            LayoutInflater layoutinflater = LayoutInflater.from(context);
            view = layoutinflater.inflate(layoutResourceId,parent,false);
        }

        TextView titulo = (TextView) view.findViewById(R.id.tvFavNome);
        TextView categoria = (TextView) view.findViewById(R.id.tvFavCategoria);

        Receita receita = dados.get(position);
        titulo.setText(receita.getTitulo());
        categoria.setText(receita.getCategoria());

        return view;
    }
}
