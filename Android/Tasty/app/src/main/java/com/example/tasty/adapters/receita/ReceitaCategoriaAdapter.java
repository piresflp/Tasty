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
import com.example.tasty.retrofit.config.RetrofitConfig;
import com.example.tasty.retrofit.models.Receita;
import com.example.tasty.retrofit.services.ReceitaService;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ReceitaCategoriaAdapter extends ArrayAdapter<Receita> {

    Context context;
    int layoutResourceId;
    List<Receita> dados;


    public ReceitaCategoriaAdapter(@NonNull Context context, int resource, @NonNull List<Receita> dados) {
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

        TextView tvNome = (TextView) view.findViewById(R.id.tvReceitaCategoriaNome);
        final TextView tvQtsFav = (TextView) view.findViewById(R.id.tvQtsFav);

        Receita receita = dados.get(position);
        tvNome.setText(receita.getTitulo());

        int idReceita = receita.getId();
        ReceitaService service = RetrofitConfig.createService(ReceitaService.class);
        Call<String> call = service.consultarQuantidadeFavoritos(idReceita);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                if(response.isSuccess())
                    tvQtsFav.setText(response.body() + " Favoritaram");
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

        return view;
    }
}
