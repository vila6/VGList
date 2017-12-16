package com.example.vic.vglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mau on 14/12/2017.
 */

public class DBManager extends SQLiteOpenHelper {

    public DBManager(Context context) {
        super( context, "VGList_DB3", null,3);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try {
            db.beginTransaction();
            db.execSQL( "CREATE TABLE JUEGOS ( ID int PRIMARY KEY, NAME TEXT, RATING int, COVERURL TEXT)");
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS JUEGOS");
        onCreate(sqLiteDatabase);
    }

    public void addGame(Game game){
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID",game.getId());
        contentValues.put("NAME",game.getName());
        contentValues.put("RATING",game.getRating());
        contentValues.put("COVERURL", game.getCoverUrl());
        this.getWritableDatabase().insertOrThrow("JUEGOS",null,contentValues);
    }

    public void deleteGame(int id){
        this.getWritableDatabase().delete("JUEGOS","ID='"+id+"'",null);
    }

    public void updateGame(int id, int new_rating){
        this.getWritableDatabase().execSQL("UPDATE JUEGOS SET RATING='" + new_rating + "' WHERE ID='" + id + "'");
    }

    public boolean isAdded(int id){
        Boolean toret = false;
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT ID FROM JUEGOS WHERE ID='"+id+"'",null);
        if (cursor.getCount() >= 1) toret = true;
        return toret;
    }

    public ArrayList<Game> getAllGames(){
        ArrayList<Game> toret = new ArrayList<Game>();
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM JUEGOS", null);
        while(cursor.moveToNext()){
            Game actualgame = new Game(cursor.getInt(0),cursor.getString(1),cursor.getDouble(2),cursor.getString(3));
            toret.add(actualgame);
        }
        return toret;
    }

    public void list_all_games(TextView textView){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM JUEGOS",null);
        while(cursor.moveToNext()){
            textView.append(cursor.getString(1) + " " + cursor.getString(2));
        }
    }

    public void echoAll(){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM JUEGOS", null);
        while(cursor.moveToNext()){
            System.out.println(cursor.getString(1) + " " + cursor.getString(2));
        }
    }

}

