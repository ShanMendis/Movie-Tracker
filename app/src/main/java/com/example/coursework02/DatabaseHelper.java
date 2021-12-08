package com.example.coursework02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
    /*construtor for database*/
    public DatabaseHelper(Context context) {
        super(context, "MovieData.db" , null,1);
    }

    /*create Table o database*/
    @Override
    public void onCreate(SQLiteDatabase DB) {
        /*movieTable in database*/
        DB.execSQL("create Table Moviedetails(name TEXT primary key, year TEXT, director TEXT, artists TEXT, ratings TEXT, reviews TEXT)");

        /*Favourite TAble in database*/
        DB.execSQL("create Table FavList(fav TEXT primary key)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Moviedetails");
        DB.execSQL("drop Table if exists FavList");
    }

                                /**insert data for movieTable */
    public Boolean insertmoviedetails(String name ,String year ,String director, String artists, String ratings ,String reviews) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("year", year);
        contentValues.put("director", director);
        contentValues.put("artists", artists);
        contentValues.put("ratings", ratings);
        contentValues.put("reviews", reviews);

        /*insert values*/
        long result= DB.insert("Moviedetails", null, contentValues);/*insert values*/

        if (result==-1){
            return false;
        }else {
            return true;
        }
    }
                /**insert data for fav Movie */
    public boolean insertfav(String fav){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fav", fav);

        /*insert values*/
        long result= DB.insert("FavList", null, contentValues);

        if (result==-1){
            return false;
        }else {
            return true;
        }
    }


    /**get data by name from movieTable */
    public Cursor getData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select name from Moviedetails",null);
        return cursor;
    }

    /**get data by all from movieTable */
    public Cursor getData1(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from Moviedetails",null);
        return cursor;
    }

    /**delete data from movie table */
    public  boolean deletefav(String fav){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from FavList where fav = ?", new String[]{fav});
        if(cursor.getCount()>0){

            /*delete values*/
            long result = DB.delete("FavList","fav=?",new String[]{fav});
            return result != -1;

        }else{
            return false;
        }
    }

    /**update movie table*/
    public boolean updateamovies(String name ,String year ,String director, String artists, String ratings ,String reviews){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("year", year);
        contentValues.put("director", director);
        contentValues.put("artists", artists);
        contentValues.put("ratings", ratings);
        contentValues.put("reviews", reviews);
        Cursor cursor2 = DB.rawQuery("select name from Moviedetails where name = ?", new String[]{name});
        if(cursor2.getCount()>0){

            /*update values*/
            long result = DB.update("Moviedetails", contentValues,"name=?" , new String[]{name});
            if (result==-1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }

    }

    /**get data by name from fav Table */
    public Cursor getFavMovie(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor1 = DB.rawQuery("select fav from FavList",null);
        return cursor1;
    }
}


