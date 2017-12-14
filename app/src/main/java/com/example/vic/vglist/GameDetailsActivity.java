package com.example.vic.vglist;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.igdb.api_android_java.callback.onSuccessCallback;
import com.igdb.api_android_java.model.APIWrapper;
import com.igdb.api_android_java.model.Parameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mau on 14/12/2017.
 */

public class GameDetailsActivity {

    public class SearchActivity extends AppCompatActivity {

        private final String API_KEY = "cf62b2e3faec9509618f5ddc1125e937";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_gamedetails);
            int id = 7346;
            final TextView gameName = (TextView) findViewById(R.id.textGameName);
            final ImageView cover = (ImageView) findViewById(R.id.imgCover);



            gameName.setText("Cargando...");
            APIWrapper wrapper = new APIWrapper(getApplicationContext(), API_KEY);
            Parameters params = new Parameters()
                    .addIds(String.valueOf(id))
                    .addFields("name,rating,cover");

            wrapper.games(params, new onSuccessCallback(){
                @Override
                public void onSuccess(JSONArray result) {
                    for (int i = 0; i < result.length(); i++) {
                        try {
                            JSONObject jsonobject = result.getJSONObject(i);
                            int id = jsonobject.getInt("id");
                            String name = jsonobject.getString("name");
                            String url = "";
                            if (jsonobject.has("cover")) {
                                JSONObject cover = jsonobject.getJSONObject("cover");
                                url = cover.getString("url");
                            }
                            float rating = 0;
                            if (jsonobject.has("rating")) {
                                rating = (float) jsonobject.getDouble("rating");
                            }
                            Game juego = new Game(id, name, rating, url);
                            Glide.with(getApplicationContext())
                                    .load("http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg")
                                    .into(cover);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }


                @Override
                public void onError(VolleyError error) {
                    System.err.print("Fallito wapo");
                }
            });
        }
    }


}




