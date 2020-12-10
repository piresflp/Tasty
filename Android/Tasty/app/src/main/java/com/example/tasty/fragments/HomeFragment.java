package com.example.tasty.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tasty.ExpandableHeightListView;
import com.example.tasty.activities.receita.adicionar.AdicionarReceita;
import com.example.tasty.R;
import com.example.tasty.activities.receita.visualizar.ReceitaActivity;
import com.example.tasty.activities.receita.visualizar.ReceitaCategoria;
import com.example.tasty.activities.usuario.LoginCadastroActivity;
import com.example.tasty.adapters.receita.CategoriaAdapter;
import com.example.tasty.adapters.receita.ReceitaHomeAdapter;
import com.example.tasty.errorHandling.ErroJson;
import com.example.tasty.retrofit.config.RetrofitConfig;
import com.example.tasty.retrofit.models.Categoria;
import com.example.tasty.retrofit.models.Receita;
import com.example.tasty.retrofit.services.CategoriaService;
import com.example.tasty.retrofit.services.ReceitaService;
import com.example.tasty.sessionManagement.SessionManagement;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    List<Receita> listaReceitas = new ArrayList<Receita>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        FloatingActionButton button = view.findViewById(R.id.floatingActionButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManagement sessionManagement = new SessionManagement(getContext());
                if(sessionManagement.getSessionId() == -1)
                    irParaOutraActivity(LoginCadastroActivity.class);
                else
                    irParaOutraActivity(AdicionarReceita.class);

            }
        });
        carregarReceitas();
    }

    private void carregarReceitas(){
        ReceitaService service = RetrofitConfig.createService(ReceitaService.class);
        Call<List<Receita>> call = service.consultarReceitasHome();
        call.enqueue(new Callback<List<Receita>>() {
            @Override
            public void onResponse(Response<List<Receita>> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    listaReceitas = response.body();
                    ExpandableHeightListView listView = getView().findViewById(R.id.listViewHome);
                    listView.setExpanded(true);
                    ReceitaHomeAdapter adapter = new ReceitaHomeAdapter(getContext(), R.layout.receita_home_item, listaReceitas);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getActivity(), ReceitaActivity.class);
                            intent.putExtra("receita", listaReceitas.get(position).getId());
                            startActivity(intent);
                        }
                    });
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

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void irParaOutraActivity(Class classOutraActivity)
    {
        Intent intentOutraActivity = new Intent(getActivity(), classOutraActivity);
        startActivity(intentOutraActivity);
    }
}