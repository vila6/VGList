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

public class MainActivity extends AppCompatActivity {

    private final String API_KEY = "cf62b2e3faec9509618f5ddc1125e937";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                APIWrapper wrapper = new APIWrapper(getApplicationContext(), API_KEY);
                Parameters params = new Parameters()
                        .addSearch(busqueda.getText().toString())
                        .addFields("name,cover.url,rating")
                        .addOrder("published_at:desc")
                        .addLimit("20");

                wrapper.games(params, new onSuccessCallback(){
                    @Override
                    public void onSuccess(JSONArray result)  {
                        for (int i = 0; i < result.length(); i++) {
                            try {
                                JSONObject jsonobject = result.getJSONObject(i);
                                int id = jsonobject.getInt("id");
                                String name = jsonobject.getString("name");
                                JSONObject cover = jsonobject.getJSONObject("cover");
                                String url = cover.getString("url");
                                float rating = (float) jsonobject.getDouble("rating");
                                Game juego = new Game(id, name, rating, url);
                                System.out.println("ESPAÑA");
                                System.out.println(juego.toString());
                                resultado.setText(resultado.getText() + juego.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // Do something with resulting JSONArray
                       /* ArrayList<String> resultArray = null;
                        try {
                            resultArray = JSONtoString(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(!resultArray.isEmpty()) {
                            resultado.setText("Resultados: " + result.length() + "\n" + resultArray.toString());

                        }else{
                            resultado.setText("Busca bien carallo");
                        }*/

                    }

                    @Override
                    public void onError(VolleyError error) {
                        System.err.print("Fallito wapo");
                    }
                });
            }
        });


    }
    public static ArrayList<String> JSONtoString(JSONArray jsonArray) throws JSONException {
        ArrayList<String> list = new ArrayList<String>();
        if (jsonArray != null) {
            jsonArray.getString(1);
            int len = jsonArray.length();
            for (int i = 0; i < len; i++) {
                list.add(jsonArray.get(i).toString());
            }
        }
        return list;
    }
}
