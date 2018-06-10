package vik.com.appdetection;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;

import io.fabric.sdk.android.Fabric;
import vik.com.appdetection.background.reciever.ScreenOnReciever;
import vik.com.appdetection.background.service.AppDetectorService;
import vik.com.appdetection.background.service.ReciverStartService;

import static com.rvalerio.fgchecker.Utils.hasUsageStatsPermission;


public class MainActivity extends AppCompatActivity  {
    static public final int REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectToAWS();
        setContentView(R.layout.activity_main);
        //final ScreenOnReciever screenOnReciever = new ScreenOnReciever();

        //registerReceiver(screenOnReciever, screenOnReciever.getFilter());
        try {
            requestUsageStatsPermission();
            requestLocationPermission();
            //startForegroundService(new Intent(this, ReciverStartService.class));
            //startService(new Intent(getApplicationContext(), ReciverStartService.class));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(new Intent(getApplicationContext(), ReciverStartService.class));
            } else {
                startService(new Intent(getApplicationContext(), ReciverStartService.class));
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
                Log.d("com.vik", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();
    }

    public void dumpData(View view) {
        DataDumper.dumpData(getApplicationContext());
    }

    public void handleSignOut(View view) {
        Log.d("com.vik.sign", "Should be signout out now");
        IdentityManager.getDefaultIdentityManager().signOut();

    }
}
