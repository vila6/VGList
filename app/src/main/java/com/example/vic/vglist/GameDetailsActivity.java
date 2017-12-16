package com.example.vic.vglist;

import android.content.Intent;
import android.os.Bundle;
import android.app.ActionBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by Mau on 16/12/2017.
 */



public class GameDetailsActivity extends AppCompatActivity{
        public Game game;
        private final String API_KEY = "cf62b2e3faec9509618f5ddc1125e937";


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            final DBManager dbManager = new DBManager(getApplicationContext());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_gamedetails);




            final TextView gameName = (TextView) findViewById(R.id.textGameName);
            final ImageView cover = (ImageView) findViewById(R.id.imgCover);
            final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
            game = (Game) getIntent().getSerializableExtra("game");


            gameName.setText(game.getName());
            ratingBar.setRating((float)(game.getRating()/10));
            Glide.with(getApplicationContext())
                    .load(game.getCoverUrl())
                    .into(cover);

            final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            if(dbManager.isAdded(game.getId())){
                fab.setImageDrawable(getDrawable(getResources().getIdentifier("@android:drawable/ic_delete",null, getPackageName())));
            }
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!dbManager.isAdded(game.getId())) {
                        dbManager.addGame(game);
                        dbManager.echoAll();

                        Snackbar.make(view, "Juego añadido", Snackbar.LENGTH_LONG)
                                .setAction("Juego añadido", null).show();
                        fab.setImageDrawable(getDrawable(getResources().getIdentifier("@android:drawable/ic_delete",null, getPackageName())));
                    }else{
                        dbManager.deleteGame(game.id);
                        dbManager.echoAll();
                        Snackbar.make(view, "Juego borrado", Snackbar.LENGTH_LONG)
                                .setAction("Juego borrado", null).show();
                        fab.setImageDrawable(getDrawable(getResources().getIdentifier("@android:drawable/ic_input_add",null, getPackageName())));
                    }
                }
            });


        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }

}




