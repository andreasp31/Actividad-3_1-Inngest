package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GuardarJugadorD extends AppCompatActivity {

    EditText nombreJugador;
    String nombreTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_guardar_jugador_d);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void volverMenu(View view){
        Intent intent = new Intent(this,NivelJugar.class);
        startActivity(intent);
    }

    public void irSudoku(View view){
        nombreJugador = findViewById(R.id.apodo);
        nombreTexto = nombreJugador.getText().toString().trim();
        if (nombreTexto.isEmpty()) {
            nombreTexto = "Jugador";
        }
        //Guardamos el usuario para el ranking después
        System.out.println("Enviando nombre: " + nombreTexto);
        Intent intent = new Intent(this, SudokuDificil.class);
        intent.putExtra("nombreJugador", nombreTexto);
        startActivity(intent);
    }
}