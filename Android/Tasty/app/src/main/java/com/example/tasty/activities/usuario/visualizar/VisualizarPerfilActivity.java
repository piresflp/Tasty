package com.example.tasty.activities.usuario.visualizar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tasty.R;
import com.example.tasty.activities.main.MainActivity;
import com.example.tasty.retrofit.models.Usuario;
import com.example.tasty.sessionManagement.SessionManagement;
import com.google.gson.Gson;

public class VisualizarPerfilActivity extends AppCompatActivity {
    EditText edtNomeCompleto, edtNomeUsuario, edtEmail;
    ImageButton btnSair;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_perfil);

        edtNomeCompleto = findViewById(R.id.edtNomeCompleto);
        edtNomeUsuario = findViewById(R.id.edtNomeUsuario);
        edtEmail = findViewById(R.id.edtEmail);

        carregarDadosUsuario();

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

    private void carregarDadosUsuario(){
        Gson gson = new Gson();
        SessionManagement sessionManagement = new SessionManagement(getApplicationContext());
        Usuario usuarioLogado = sessionManagement.getSession();

        edtNomeCompleto.setText(usuarioLogado.getNome());
        edtNomeUsuario.setText(usuarioLogado.getNomeDeUsuario());
        edtEmail.setText(usuarioLogado.getEmail());
    }

}