package com.hackathon.myapplication2.com.hackathon.myapplication2.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.hackathon.myapplication2.db.DBHelper;

/**
 * Created by SwatiSh on 4/8/2015.
 */
public class ListenerService extends Service
{
    private ReciverClass myReceiver;
    private DBHelper dbHelper;
    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        ListenerService getService() {
            // Return this instance of LocalService so clients can call public methods
            return ListenerService.this;
        }
    }
    @Override
    public void onCreate()
    {
        dbHelper = new DBHelper(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Let it continue running until it is stopped.

        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

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
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

     @Override
    public void onRebind(Intent intent) {

    }

    public void setNumber(int num)
    {
        Toast.makeText(this, "Service Set Number :"+num, Toast.LENGTH_LONG).show();
    }
}
