package com.example.vic.vglist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.igdb.api_android_java.callback.onSuccessCallback;
import com.igdb.api_android_java.model.APIWrapper;
import com.igdb.api_android_java.model.Parameters;

import org.json.*;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String API_KEY = "cf62b2e3faec9509618f5ddc1125e937";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Mapper map = new Mapper();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView resultado = (TextView) findViewById(R.id.resultado);
        final EditText busqueda = (EditText) findViewById(R.id.busqueda);

        busqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                resultado.setText("Buscando...");
                List<Game> list = map.searchGames(busqueda.getText().toString(), getApplicationContext());
                for(int i=0; i<list.size(); i++){
                    resultado.setText(resultado.getText().toString() + list.get(i).toString());
                }
                resultado.setText(resultado.getText().toString() + "\n NUMERO DE RESULTADOS: " + list.size());

            }
        });
    }
}