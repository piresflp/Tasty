package com.example.tasty.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.tasty.R;
import com.example.tasty.activities.receita.pesquisar.PesquisarReceitaActivity;
import com.example.tasty.activities.usuario.LoginCadastroActivity;
import com.example.tasty.activities.usuario.visualizar.VisualizarPerfilActivity;
import com.example.tasty.adapters.home.PageAdapter;
import com.example.tasty.sessionManagement.SessionManagement;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    TabItem tabHome, tabTalher, tabFavorite;
    ImageButton imgbLupa, imgbPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tabLayout = findViewById(R.id.TabBar);
        tabHome = findViewById(R.id.tabHome);
        tabTalher = findViewById(R.id.tabTalher);
        tabFavorite = findViewById(R.id.tabFavorite);
        imgbLupa = findViewById(R.id.imgbLupa);
        imgbPerfil = findViewById(R.id.imgbPerfil);
        final ViewPager viewPager = findViewById(R.id.viewPager);

        final PageAdapter pagerAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), getApplicationContext());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        imgbPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagement sessionManagement = new SessionManagement(getApplicationContext());
                if(sessionManagement.getSessionId() == -1)
                    irParaOutraActivity(LoginCadastroActivity.class);
                else
                    irParaOutraActivity(VisualizarPerfilActivity.class);
            }
        });

        imgbLupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaOutraActivity(PesquisarReceitaActivity.class);
            }
        });
    }

    private void irParaOutraActivity(Class classOutraActivity)
    {
        Intent intentOutraActivity = new Intent(MainActivity.this, classOutraActivity);
        startActivity(intentOutraActivity);
    }
}