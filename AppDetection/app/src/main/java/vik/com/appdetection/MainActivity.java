package vik.com.appdetection;

import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vik.com.appdetection.background.reciever.ScreenOnReciever;
import vik.com.appdetection.background.service.AppDetectorService;
import vik.com.appdetection.background.service.ReciverStartService;

import static com.rvalerio.fgchecker.Utils.hasUsageStatsPermission;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //final ScreenOnReciever screenOnReciever = new ScreenOnReciever();

        //registerReceiver(screenOnReciever, screenOnReciever.getFilter());
        requestUsageStatsPermission();

        startService(new Intent(this, ReciverStartService.class));
    }
    void requestUsageStatsPermission() {
           if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                       && !hasUsageStatsPermission(this)) {
                    startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
                }
        }
}
