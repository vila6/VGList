package com.example.vic.vglist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by Mau on 14/12/2017.
 */

public class DBManager extends SQLiteOpenHelper {

    public DBManager(Context context) {
        super( context, "DB_VGList", null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try {
            db.beginTransaction();
            db.execSQL( "CREATE TABLE IF NOT EXISTS 'JUEGOS' ("
                    + "ID" + " int PRIMARY KEY,"
                    + "NOMBRE" + " string(255) NOT NULL"
                    + ")" );
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    // más cosas…
}

