package com.example.tasty.adapters.receita;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasty.R;
import com.example.tasty.retrofit.models.Receita;

import java.util.List;

public class BuscarReceitaAdapter extends RecyclerView.Adapter<BuscarReceitaAdapter.ReceitaViewHolder> {
    Context mContext;
    List<Receita> listaReceitas;

    public BuscarReceitaAdapter(Context mContext, List<Receita> listaReceitas) {
        this.mContext = mContext;
        this.listaReceitas = listaReceitas;
    }

    @NonNull
    @Override
    public ReceitaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.receita_fav_item, viewGroup, false);

        return new ReceitaViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceitaViewHolder holder, int position) {
        // carregar dados aqui

        holder.tvTitulo.setText(listaReceitas.get(position).getTitulo());
        holder.tvCategoria.setText(listaReceitas.get(position).getCategoria().getNome());
        //holder.imgvFoto.setImageResource(listaReceitas.get(position).getFoto());
    }

    @Override
    public int getItemCount() {
        return listaReceitas.size();
    }

    public class ReceitaViewHolder extends RecyclerView.ViewHolder{
        ImageView imgvFoto;
        TextView tvTitulo, tvCategoria;

        public ReceitaViewHolder(@NonNull View itemView){
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvFavNome);
            tvCategoria = itemView.findViewById(R.id.tvFavCategoria);
            imgvFoto = itemView.findViewById(R.id.imgvFoto);
        }
    }
}
