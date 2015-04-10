package com.hackathon.myapplication2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by SwatiSh on 4/8/2015.
 */
public class DBHelper extends SQLiteOpenHelper
{

    public static final String DATABASE_NAME = "HackBotDB";
    public static final String EVENTS_TABLE_NAME = "HackBotEvents";
    public static final String EVENTS_COLUMN_ID = "id";
    public static final String EVENTS_COLUMN_NAME = "name";
    public static final String EVENTS_COLUMN_TIME = "time";
    public static final String EVENTS_COLUMN_DATE = "date";
    public static final String EVENTS_COLUMN_PROBABILITY = "probability";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS HackBotEvents " +
                        "(id integer primary key, name text,time text,date text, probability integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS HackBotEvents");
        onCreate(db);
    }

    public boolean insertEvent  (Integer id, String eventName, String time, String date,Integer probability)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", id);
        contentValues.put("name", eventName);
        contentValues.put("time", time);
        contentValues.put("date", date);
        contentValues.put("probability", probability);

        db.insert("HackBotEvents", null, contentValues);
        return true;
    }
    public Cursor getData(int id, String time){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from HackBotEvents where id="+id+" AND time="+time+"", null );
        return res;
    }
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, EVENTS_TABLE_NAME);
        return numRows;
    }
    public boolean updateEvent (Integer id, String name, String time, String date, Integer probability)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("time",time);
        contentValues.put("date", date);
        contentValues.put("probability", probability);
        db.update("HackBotEvents", contentValues, "id = ? AND time = ?", new String[] { Integer.toString(id) , time } );
        return true;
    }

    public Integer deleteContact (Integer id, String time)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("HackBotEvents",
                "id = ? AND time = ?",
                new String[] { Integer.toString(id), time });
    }

}
