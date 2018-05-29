package vik.com.appdetection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vik.com.appdetection.background.reciever.ScreenOnReciever;
import vik.com.appdetection.background.service.AppDetectorService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ScreenOnReciever screenOnReciever = new ScreenOnReciever();

        registerReceiver(screenOnReciever, screenOnReciever.getFilter());

        //startService(new Intent(this, AppDetectorService.class));
    }
}
