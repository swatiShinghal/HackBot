package com.hackathon.myapplication2.com.hackathon.myapplication2.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.widget.Toast;

import com.hackathon.myapplication2.db.DBHelper;

/**
 * Created by SwatiSh on 4/8/2015.
 */
public class ListenerService extends Service
{
    private ReciverClass myReceiver;
    @Override
    public void onCreate()
    {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Let it continue running until it is stopped.
        //dbHelper = new DBHelper(intent.)
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        /*IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");

        ReciverClass myReceiver = new ReciverClass();
        registerReceiver(myReceiver, filter);*/
        IntentFilter filter1=new IntentFilter(
                AudioManager.RINGER_MODE_CHANGED_ACTION);
        IntentFilter filter2 = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        myReceiver = new ReciverClass();
        registerReceiver(myReceiver,filter1);
        registerReceiver(myReceiver, filter2);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
        unregisterReceiver(myReceiver);
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
