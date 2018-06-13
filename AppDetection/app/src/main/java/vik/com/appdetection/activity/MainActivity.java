package vik.com.appdetection.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;

import vik.com.appdetection.background.aws.DataDumper;
import vik.com.appdetection.R;
import vik.com.appdetection.background.service.ReciverStartService;

import static com.rvalerio.fgchecker.Utils.hasUsageStatsPermission;


public class MainActivity extends AppCompatActivity  {
    static public final int REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(isUserLoogedIn()==false)
        {
        connectToAWS();}
        setContentView(R.layout.activity_main);
        //final ScreenOnReciever screenOnReciever = new ScreenOnReciever();

        //registerReceiver(screenOnReciever, screenOnReciever.getFilter());
        try {
            requestUsageStatsPermission();
            requestLocationPermission();
            //startForegroundService(new Intent(this, ReciverStartService.class));
            //startService(new Intent(getApplicationContext(), ReciverStartService.class));
            if(ReciverStartService.isServiceRunning==false) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(new Intent(getApplicationContext(), ReciverStartService.class));
                } else {
                    startService(new Intent(getApplicationContext(), ReciverStartService.class));
                }
                ReciverStartService.isServiceRunning=true;
            }
        }
        catch (Exception e)
        {
            Log.e("com.vik","error", e);
        }


    }
    void requestUsageStatsPermission() {
           if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                       && !hasUsageStatsPermission(this)) {
                    startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
                }

        }
    private void requestLocationPermission() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
          ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
    }

    private void connectToAWS() {
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                setUserLoggedIn();
                Log.d("com.vik", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();
    }

    public void dumpData(View view) {
        DataDumper.dumpData(getApplicationContext());
    }

    public void showInformationActivity(View v) {
        Intent intent = new Intent(this, InformationActivity.class);
        startActivity(intent);
    }

    private void setUserLoggedIn()
    {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("logged_in",1);
        editor.commit();
    }

    private boolean isUserLoogedIn()
    {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        int loogedIn = sharedPref.getInt("logged_in",0);
        if(loogedIn==1)
        {
            return true;
        }
        return false;
    }
}
