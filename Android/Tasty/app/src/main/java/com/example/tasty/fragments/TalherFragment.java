package com.example.tasty.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tasty.R;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import com.example.tasty.retrofit.config.RetrofitConfig;
import com.example.tasty.retrofit.models.Categoria;
import com.example.tasty.retrofit.services.CategoriaService;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TalherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TalherFragment extends Fragment {
    List<Categoria> listaCategorias;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TalherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TalherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TalherFragment newInstance(String param1, String param2) {
        TalherFragment fragment = new TalherFragment();
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
        return inflater.inflate(R.layout.fragment_talher, container, false);
    }

    private void carregarCategorias(){
        CategoriaService service = RetrofitConfig.createService(CategoriaService.class);
        Call<List<Categoria>> call = service.consultarTodasCategorias();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Response<List<Categoria>> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    listaCategorias = response.body();
                }
                else{}
                //mostrar na tela erro ao carregar categorias
            }

            @Override
            public void onFailure(Throwable t) {
                //mostrar na tela erro ao carregar categorias
            }
        });
    }
}