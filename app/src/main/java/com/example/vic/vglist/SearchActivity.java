package com.example.vic.vglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.igdb.api_android_java.callback.onSuccessCallback;
import com.igdb.api_android_java.model.APIWrapper;
import com.igdb.api_android_java.model.Parameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    public List<Game> gamesList = new ArrayList<Game>();
    private final String API_KEY = "cf62b2e3faec9509618f5ddc1125e937";

    public boolean onCreateOptionsMenu(Menu menu) {
        final DBManager dbManager = new DBManager(getApplicationContext());

        super.onCreateOptionsMenu(menu);

        this.getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searcher = menu.findItem(R.id.btSearch);
        SearchView searchView = (SearchView) searcher.getActionView();

        ListView listGames = (ListView) findViewById(R.id.listGames);
        final CustomAdapter adapter = new CustomAdapter();
        listGames.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String busqueda) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String busqueda) {
                System.out.println("BUSCANDO: "+busqueda);
                APIWrapper wrapper = new APIWrapper(getApplicationContext(), API_KEY);
                Parameters params = new Parameters()
                        .addSearch(busqueda)
                        .addFields("name,rating,cover")
                        .addOrder("popularity:desc")
                        .addLimit("20");

                wrapper.games(params, new onSuccessCallback() {
                    @Override
                    public void onSuccess(JSONArray result) {
                        gamesList.clear();
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
                                gamesList.add(juego);
                                dbManager.echoAll();
                                adapter.notifyDataSetChanged();

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
                return false;
            }
        });
        return true;
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        APIWrapper wrapper = new APIWrapper(getApplicationContext(), API_KEY);
        Parameters params = new Parameters()
                .addOrder("popularity:desc")
                .addLimit("20");

        wrapper.games(params, new onSuccessCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                gamesList.clear();
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
                        gamesList.add(juego);

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

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int i) {
            return gamesList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.list_item,null);

            ImageView imageView = (ImageView)view.findViewById(R.id.imgCover);
            TextView textGame = (TextView) view.findViewById(R.id.textGameName);
            RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);

            if(gamesList.size()>i) {
                Game actualGame = gamesList.get(i);
                Glide.with(getApplicationContext())
                        .load(actualGame.getCoverUrl())
                        .into(imageView);
                textGame.setText((CharSequence) actualGame.getName());
                ratingBar.setRating(actualGame.getRating()/10);
                System.out.println("Juego numero " + i + ": " + actualGame.getName());
            }


            return view;
        }
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
