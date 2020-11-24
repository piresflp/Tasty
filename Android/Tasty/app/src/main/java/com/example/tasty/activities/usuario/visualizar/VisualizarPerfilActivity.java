package com.example.tasty.activities.usuario.visualizar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.tasty.R;
import com.example.tasty.activities.main.MainActivity;
import com.example.tasty.sessionManagement.SessionManagement;

public class VisualizarPerfilActivity extends AppCompatActivity {
    ImageButton btnSair;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_perfil);

        btnSair = findViewById(R.id.btnSair);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagement sessionManagement = new SessionManagement(getApplicationContext());
                sessionManagement.removerSession();

                Intent intentOutraActivity = new Intent(VisualizarPerfilActivity.this, MainActivity.class);
                startActivity(intentOutraActivity);
            }
        });
    }
}