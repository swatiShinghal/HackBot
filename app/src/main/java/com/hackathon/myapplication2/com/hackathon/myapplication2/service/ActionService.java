package com.hackathon.myapplication2.com.hackathon.myapplication2.service;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.Toast;
import android.view.Window;

import com.hackathon.myapplication2.db.DBHelper;
import com.hackathon.myapplication2.db.HackBotEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SwatiSh on 4/8/2015.
 */
public class ActionService extends Service
{
    private DBHelper dbHelper;
    private List<HackBotEvent> eventData=new ArrayList<>();

    @Override
    public void onCreate()
    {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        //dbHelper = new DBHelper(intent.)
        //android.provider.Settings.System.putInt(getContentResolver(),
          //              android.provider.Settings.System.SCREEN_BRIGHTNESS,
            //    0);
        AudioManager am;
        am=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(0);
        Toast.makeText(this, "Action Service Started"+Settings.System.SCREEN_BRIGHTNESS, Toast.LENGTH_LONG).show();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

     @Override
    public void onRebind(Intent intent) {

    }

}
