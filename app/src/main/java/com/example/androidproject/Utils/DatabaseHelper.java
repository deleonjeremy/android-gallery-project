package com.example.androidproject.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, "finalproj.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user(id integer PRIMARY KEY AUTOINCREMENT, email text, password text)");
        db.execSQL("CREATE TABLE favourites(id integer PRIMARY KEY AUTOINCREMENT, photoUrl text, userId integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS favourites");
    }

    // Inserting Users in database
    public boolean insert(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);

        long ins = db.insert("user", null, contentValues);

        if(ins == -1) return false;
        else return true;
    }

    public boolean insertFavourite(String photoUrl){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("photoUrl", photoUrl);

        long ins = db.insert("favourites", null, contentValues);

        if(ins == -1) return false;
        else return true;
    }

    public Boolean checkEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE email=?", new String[] {email});

        if(cursor.getCount()>0) return false;
        else return true;
    }

    public Boolean checkLogin(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE email=? AND password=?", new String[]{email, password});

        if(cursor.getCount()>0) return true;
        else return false;
    }

    public Cursor getAllFavourites(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM favourites", null);

        return cursor;
    }

}

