package com.hackathon.myapplication2;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hackathon.myapplication2.com.hackathon.myapplication2.service.ActionService;
import com.hackathon.myapplication2.com.hackathon.myapplication2.service.ListenerService;
import com.hackathon.myapplication2.com.hackathon.myapplication2.service.ReciverClass;
import com.hackathon.myapplication2.db.DBHelper;


public class MainActivity extends ActionBarActivity {

    private ReciverClass myReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Method to start the service
    public void startService(View view) {
        startService(new Intent(getBaseContext(), ListenerService.class));
        startService(new Intent(getBaseContext(), ActionService.class));

    }

    // Method to stop the service
    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), ListenerService.class));
        stopService(new Intent(getBaseContext(), ActionService.class));
    }
}
