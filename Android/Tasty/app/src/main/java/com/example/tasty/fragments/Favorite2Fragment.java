package com.example.tasty.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import com.example.tasty.R;
import com.example.tasty.activities.receita.ReceitaActivity;
import com.example.tasty.adapters.receita.ReceitaFavAdapter;
import com.example.tasty.errorHandling.ErroJson;
import com.example.tasty.retrofit.config.RetrofitConfig;
import com.example.tasty.retrofit.models.Receita;
import com.example.tasty.retrofit.services.UsuarioService;
import com.example.tasty.sessionManagement.SessionManagement;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Favorite2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Favorite2Fragment extends Fragment {

    List<Receita> listaReceitasFavoritas;
    ArrayList<Receita> receitasFavList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Favorite2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Favorite2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Favorite2Fragment newInstance(String param1, String param2) {
        Favorite2Fragment fragment = new Favorite2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //LinearLayout receitaFav = view.findViewById(R.id.receitafav);
        //receitasFavList = new ArrayList<>();
        getListaReceitasFavoritas(view, this.getContext());
        //Toast.makeText(getContext(), listaReceitasFavoritas.toString(), Toast.LENGTH_LONG).show();

        /*receitaFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReceitaActivity.class);
                startActivity(intent);
            }
        });*/

    }

    private void getListaReceitasFavoritas(final View view, final Context context){
        SessionManagement sessionManagement = new SessionManagement(getContext());
        int idUsuario = sessionManagement.getSessionId();

        UsuarioService service = RetrofitConfig.createService(UsuarioService.class);
        Call<List<Receita>> call = service.consultarReceitasFavoritas(idUsuario);
        call.enqueue(new Callback<List<Receita>>() {
            @Override
            public void onResponse(Response<List<Receita>> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    if(response.body().size() == 0){
                        Toast.makeText(getContext(), "a", Toast.LENGTH_LONG).show();
                        // mostrar na tela que nenhuma receita foi favoritada
                    }
                    else {
                        listaReceitasFavoritas = response.body();
                        final ReceitaFavAdapter adapter = new ReceitaFavAdapter(context,R.layout.receita_fav_item, listaReceitasFavoritas);
                        final ListView listViewReceita = view.findViewById(R.id.listViewReceitaFav);
                        listViewReceita.setAdapter(adapter);

                        listViewReceita.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Gson gson = new Gson();
                                String receitaJson = gson.toJson(listaReceitasFavoritas.get(position));
                                Intent intent = new Intent(getActivity(), ReceitaActivity.class);
                                intent.putExtra("receita", receitaJson);
                                startActivity(intent);
                            }
                        });

                    }
                }
                else{
                    try{
                        Gson gson = new Gson();
                        ErroJson erro = gson.fromJson(response.errorBody().string(), ErroJson.class);
                        Toast.makeText(getContext(), erro.getError(), Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                    // mostrar na tela que ocorreu erro na pesquisa
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

                // mostrar na tela que ocorreu erro na pesquisa
            }
        });
    }
}