package com.example.vic.vglist;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.app.ActionBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Switch;
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


            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
            final RatingBar ratingBarUser = (RatingBar) findViewById(R.id.ratingBarUser);
            final TextView gameName = (TextView) findViewById(R.id.textGameName);
            final ImageView cover = (ImageView) findViewById(R.id.imgCover);
            final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
            final TextView textDescription = (TextView) findViewById(R.id.textDescription);
            final TextView textYourRating = (TextView) findViewById(R.id.textYourRating);
            final Switch switchState = (Switch) findViewById(R.id.switchState);
            final FloatingActionButton fab = findViewById(R.id.fab);

            game = (Game) getIntent().getSerializableExtra("game");


            gameName.setText(game.getName());
            textDescription.setText(game.getDescription());
            textDescription.setMovementMethod(new ScrollingMovementMethod());
            ratingBar.setRating((float)(game.getRating()/10));
            Glide.with(getApplicationContext())
                    .load(game.getCoverUrl())
                    .into(cover);


            if(dbManager.isAdded(game.getId())){
                fab.setImageDrawable(getDrawable(getResources().getIdentifier("@android:drawable/ic_delete",null, getPackageName())));
                ratingBarUser.setVisibility(View.VISIBLE);
                textYourRating.setVisibility(View.VISIBLE);
                switchState.setVisibility(View.VISIBLE);
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
                        ratingBarUser.setVisibility(View.VISIBLE);
                        textYourRating.setVisibility(View.VISIBLE);
                        switchState.setVisibility(View.VISIBLE);
                        if(game.getState()){
                            switchState.setChecked(true);
                            switchState.setText("Completed");
                        }
                        else{
                            switchState.setText("Playing");
                        }
                    }else{
                        dbManager.deleteGame(game.id);
                        dbManager.echoAll();
                        Snackbar.make(view, "Juego borrado", Snackbar.LENGTH_LONG)
                                .setAction("Juego borrado", null).show();
                        fab.setImageDrawable(getDrawable(getResources().getIdentifier("@android:drawable/ic_input_add",null, getPackageName())));
                        ratingBarUser.setVisibility(View.GONE);
                        textYourRating.setVisibility(View.GONE);
                        switchState.setVisibility(View.GONE);
                    }
                }
            });
            if(dbManager.isAdded(game.getId())) {
                ratingBarUser.setRating(dbManager.getRatingUser(game.getId()));
                game.setState(dbManager.getState(game.getId()));
                if(game.getState()){
                    switchState.setChecked(true);
                    switchState.setText("Completed");
                }
                else switchState.setText("Playing");
            }
            ratingBarUser.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    dbManager.addRatinguser(game.getId(), v);
                }
            });
            switchState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked){
                        dbManager.addState(game.getId(),1);
                        switchState.setText("Completed");
                    }
                    else{
                        dbManager.addState(game.getId(),0);
                        switchState.setText("Playing");
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




