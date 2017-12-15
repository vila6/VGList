package com.example.vic.vglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

import java.sql.SQLException;

/**
 * Created by Mau on 14/12/2017.
 */

public class DBManager extends SQLiteOpenHelper {

    public DBManager(Context context) {
        super( context, "VGList_DB", null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try {
            db.beginTransaction();
            db.execSQL( "CREATE TABLE JUEGOS ( ID int PRIMARY KEY, NAME TEXT, PERSONALRATING int)");
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

    public void addGame(int id, String name, int personalRating){
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID",id);
        contentValues.put("NAME",name);
        contentValues.put("PERSONALRATING",personalRating);
        this.getWritableDatabase().insertOrThrow("JUEGOS",null,contentValues);
    }

    public void deleteGame(int id){
        this.getWritableDatabase().delete("JUEGOS","ID='"+id+"'",null);
    }

    public void updateGame(int id, int new_rating){
        this.getWritableDatabase().execSQL("UPDATE JUEGOS SET PERSONALRATING='" + new_rating + "' WHERE ID='" + id + "'");
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

