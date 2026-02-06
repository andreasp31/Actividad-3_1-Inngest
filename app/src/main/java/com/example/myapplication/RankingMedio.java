package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankingMedio extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdaptadorRankingF adaptador;
    private ArrayList<Puntuacion> listaPuntuacion;
    private ApiService apiService;
    private TextView nombre1,  nombre2, nombre3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ranking_medio);

        recyclerView = findViewById(R.id.grid);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaPuntuacion = new ArrayList<>();

        adaptador = new AdaptadorRankingF(listaPuntuacion);
        recyclerView.setAdapter(adaptador);

        apiService = RetrofitUsuario.getApiService();

        nombre1 = findViewById(R.id.nombre1);
        nombre2 = findViewById(R.id.nombre2);
        nombre3 = findViewById(R.id.nombre3);

        cargarRanking();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void cargarRanking(){
        Call<ArrayList<Puntuacion>> llamar = apiService.obtenerRanking("medio");
        llamar.enqueue(new Callback<ArrayList<Puntuacion>>() {
            @Override
            public void onResponse(Call<ArrayList<Puntuacion>> call, Response<ArrayList<Puntuacion>> response) {
                if(response.isSuccessful()){
                    ArrayList<Puntuacion> ranking = response.body();
                    listaPuntuacion.clear();
                    listaPuntuacion.addAll(ranking);
                    podio(ranking);
                    adaptador.notifyDataSetChanged();
                }
                else {
                    listaPuntuacion.clear();
                    adaptador.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Puntuacion>> call, Throwable t) {
                listaPuntuacion.clear();
                adaptador.notifyDataSetChanged();
            }
        });
    }
    public void volverMenu(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void podio(List<Puntuacion> ranking){
        if(ranking.size() > 0){
            Puntuacion primero = ranking.get(0);
            nombre1.setText(primero.getNombre());
        }else{
            nombre3.setText("-");
        }
        if(ranking.size() > 1){
            Puntuacion segundo = ranking.get(1);
            nombre2.setText(segundo.getNombre());
        }else{
            nombre3.setText("-");
        }
        if(ranking.size() > 2){
            Puntuacion tercero = ranking.get(2);
            nombre3.setText(tercero.getNombre());
        }else{
            nombre3.setText("-");
        }
    }
}