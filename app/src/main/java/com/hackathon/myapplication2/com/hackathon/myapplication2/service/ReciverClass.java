package com.hackathon.myapplication2.com.hackathon.myapplication2.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by SwatiSh on 4/16/2015.
 */
public class ReciverClass extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        // TODO Auto-generated method stub
        Toast.makeText(context, "Started", Toast.LENGTH_SHORT).show();
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION))
        {
            Log.d("app", "Phone connectivity mode changed");
            boolean noConnectivity = intent.getBooleanExtra(
                    ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

            if (!noConnectivity) {
                Log.d("NetworkCheckReceiver", "connected");
            }
            else
            {
                Log.d("NetworkCheckReceiver", "disconnected");
            }
        }
        else if (intent.getAction().equals(AudioManager.RINGER_MODE_CHANGED_ACTION)) {
            AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

            switch (am.getRingerMode()) {
                case AudioManager.RINGER_MODE_SILENT:
                    Log.i("MyApp", "Silent mode");
                    break;
                case AudioManager.RINGER_MODE_VIBRATE:
                    Log.i("MyApp", "Vibrate mode");
                    break;
                case AudioManager.RINGER_MODE_NORMAL:
                    Log.i("MyApp", "Normal mode");
                    break;
            }
            Log.d("app", "Phone audio mode changed");
        }
    }
}
