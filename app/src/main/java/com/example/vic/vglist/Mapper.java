package com.example.vic.vglist;

import android.content.Context;

import com.android.volley.VolleyError;
import com.igdb.api_android_java.callback.onSuccessCallback;
import com.igdb.api_android_java.model.APIWrapper;
import com.igdb.api_android_java.model.Parameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mau on 13/12/2017.
 */

public class Mapper {

    private final String API_KEY = "cf62b2e3faec9509618f5ddc1125e937";
    public Game getGameById(String id, Context context){
        final Game[] toret = new Game[1];
        APIWrapper wrapper = new APIWrapper(context, API_KEY);
        Parameters params = new Parameters()
                .addIds(id)
                .addFields("name,cover.url,rating");
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
                        toret[0] = new Game(id, name, rating, url);
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
        return toret[0];
    }

    public List<Game> searchGames(String search, Context context){
        final List<Game> toret = new ArrayList<Game>();
        APIWrapper wrapper = new APIWrapper(context, API_KEY);
        Parameters params = new Parameters()
                .addSearch(search)
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
                            Game juego = new Game(id,name);
                        toret.add(juego);
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
        return toret;
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
