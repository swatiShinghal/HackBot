package com.hackathon.myapplication2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.Time;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by SwatiSh on 4/8/2015.
 */
public class DBHelper extends SQLiteOpenHelper
{

    public static final String DATABASE_NAME = "HackBotDB";
    public static final String EVENTS_TABLE_NAME = "HackBotEvents";
    public static final String EVENTS_ID = "eventId";
    public static final String EVENTS_NAME = "eventName";
    public static final String EVENTS_COLUMN_TIME_TO_TRIGGER = "timeToTrigger";
    public static final String EVENTS_COLUMN_FIRST_OCCURRENCE = "firstOccurrence";
    public static final String EVENTS_COLUMN_LAST_OCCURRENCE = "lastOccurrence";
    public static final String EVENTS_COLUMN_TIMES_OCCURRED = "timesOccurred";
    public static final String EVENTS_COLUMN_PROBABILITY = "probability";
    public static final String EVENTS_COLUMN_DURATION = "duration";
    public static final String EVENTS_COLUMN_PATTERN = "pattern";
    public static final String EVENTS_COLUMN_REPEATED_WEEKLY = "repeatedWeekly";
    public static final String EVENTS_COLUMN_REPEATED_DAYS = "RepeatInDays";


    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS HackBotEvents " +
                        "(eventId integer , eventName text,timeToTrigger integer,firstOccurrence integer,lastOccurrence integer" +
                        ",timesOccurred integer,probability integer, duration integer, pattern integer, repeatedWeekly text,RepeatInDays integer )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS HackBotEvents");
        onCreate(db);
    }

    public boolean insertEvent  (long eventId, String eventName, Integer probability)
    {
        Time time = new Time(Time.getCurrentTimezone());
        time.setToNow();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(EVENTS_ID, eventId);
        contentValues.put(EVENTS_NAME, eventName);
        contentValues.put(EVENTS_COLUMN_TIME_TO_TRIGGER, (time.hour * 60)+time.minute);
        contentValues.put(EVENTS_COLUMN_FIRST_OCCURRENCE, time.toMillis(false));
        contentValues.put(EVENTS_COLUMN_LAST_OCCURRENCE, time.toMillis(false));
        contentValues.put(EVENTS_COLUMN_TIMES_OCCURRED, 1);
        contentValues.put(EVENTS_COLUMN_PROBABILITY, probability);
        contentValues.put(EVENTS_COLUMN_DURATION, 0);
        contentValues.put(EVENTS_COLUMN_PATTERN, 64);
        contentValues.put(EVENTS_COLUMN_REPEATED_WEEKLY, false);
        contentValues.put(EVENTS_COLUMN_REPEATED_DAYS, 1);

        db.insert(EVENTS_TABLE_NAME, null, contentValues);
        return true;
    }
    public HackBotEvent getData(long time, long range){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query(EVENTS_TABLE_NAME, null, " BETWEEN ? AND ?", new String[] {Long.toString(time - range), Long.toString(time + range)}, null, null, null, null);
        res.moveToFirst() ;
        HackBotEvent event = parseRecord(res);
        //Cursor res =  db.rawQuery( "select * from HackBotEvents where id="+id+" AND time="+time+"", null );

        return event;
    }

    public HackBotEvent getData(long id, long time, long range){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query(EVENTS_TABLE_NAME, null, EVENTS_ID+ "? AND "+EVENTS_COLUMN_TIME_TO_TRIGGER+" BETWEEN ? AND ?", new String[] {Long.toString(id), Long.toString(time - range), Long.toString(time + range)}, null, null, null, null);
        res.moveToFirst() ;
        HackBotEvent event = parseRecord(res);
        //Cursor res =  db.rawQuery( "select * from HackBotEvents where id="+id+" AND time="+time+"", null );

        return event;
    }
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, EVENTS_TABLE_NAME);
        return numRows;
    }
    public boolean updateEvent (long id, long time, long range, long probability, boolean repeatedWeekly)
    {
        Time last = new Time();
        last.setToNow();
        HackBotEvent event = getData(id, time, range);
        String pattern = event.getPattern();
        long noOfDays = getNoOfDays(last.toMillis(false), event.getFirstOccurrence());
        long updatedPattern = getUpdatedPattern(pattern, noOfDays);
        long repeatInDays = event.getRepeatInDays() + 1;
        long timesOccurred = event.getTimesOccurred() + 1;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EVENTS_ID, id);
        contentValues.put(EVENTS_COLUMN_PROBABILITY, probability);
        contentValues.put(EVENTS_COLUMN_LAST_OCCURRENCE, last.toMillis(false));
        contentValues.put(EVENTS_COLUMN_REPEATED_WEEKLY, repeatedWeekly?"true":"false");
        contentValues.put(EVENTS_COLUMN_PATTERN, updatedPattern);
        contentValues.put(EVENTS_COLUMN_TIMES_OCCURRED, timesOccurred);
        contentValues.put(EVENTS_COLUMN_REPEATED_DAYS, repeatInDays);
        db.update(EVENTS_TABLE_NAME, contentValues, EVENTS_ID +"? AND "+EVENTS_COLUMN_TIME_TO_TRIGGER+" BETWEEN ? AND ?", new String[] {Long.toString(id), Long.toString(time - range), Long.toString(time + range)} );
        return true;
    }

    public boolean updateEvent (long id, long time, long range, long duration)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EVENTS_ID, id);
        contentValues.put(EVENTS_COLUMN_DURATION, duration);
        db.update(EVENTS_TABLE_NAME, contentValues, EVENTS_ID +"? AND "+EVENTS_COLUMN_TIME_TO_TRIGGER+" BETWEEN ? AND ?", new String[] {Long.toString(id), Long.toString(time - range), Long.toString(time + range)} );
        return true;
    }


    public Integer deleteEvent (long id, long time, long range)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //return db.query(EVENTS_TABLE_NAME, null, " BETWEEN ? AND ?", new String[] {Long.toString(time - range), Long.toString(time + range)}, null, null, null, null);
        return 0;
    }

    private HackBotEvent parseRecord(Cursor cursor)
    {
        HackBotEvent event= new HackBotEvent();
        event.setEventId(cursor.getInt(0));
        event.setEventName(cursor.getString(1));
        event.setTimeToTrigger(cursor.getInt(2));
        event.setFirstOccurrence(cursor.getInt(3));
        event.setLastOccurrence(cursor.getInt(4));
        event.setTimesOccurred(cursor.getInt(5));
        event.setProbability(cursor.getInt(6));
        event.setDuration(cursor.getInt(7));
        event.setPattern(Long.toBinaryString(cursor.getInt(8)));
        String repWeek = cursor.getString(9);
        if(repWeek.equals("false"))
            event.setRepeatedWeekly(false);
        else
           event.setRepeatedWeekly(true);
        event.setRepeatInDays(cursor.getInt(10));
        return event;
    }

    private long getUpdatedPattern(String pattern, long num)
    {
        long finalPattern = Long.parseLong(pattern, 2);
        if (num <= 7) {
            long patternInt = Long.parseLong(pattern, 2);
            finalPattern = patternInt | (10 * num);
        }
        return finalPattern;
    }
    private long getNoOfDays(long lastDate, long firstDate)
    {
        Date last = new Date(lastDate);
        Date first = new Date(firstDate);
        long noOfDays = (long)( (last.getTime() - first.getTime()) / (1000 * 60 * 60 * 24));
        return noOfDays;
    }
}
