package com.hackathon.myapplication2.com.hackathon.myapplication2.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Created by SwatiSh on 4/17/2015.
 */
public class UIClass
{
    protected Context context;
    public UIClass(Context context)
    {
       this.context = context.getApplicationContext();
    }

    ListenerService mService;
    boolean mBound = false;

    public  void onStart()
    {
        Intent intent = new Intent(context, ListenerService.class);
        context.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }



    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            ListenerService.LocalBinder binder = (ListenerService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    public void setNumber() {
        if (mBound)
        mService.setNumber(10);
    }
}
