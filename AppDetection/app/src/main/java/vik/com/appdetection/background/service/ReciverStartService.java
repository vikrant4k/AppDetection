package vik.com.appdetection.background.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import vik.com.appdetection.background.reciever.ScreenOnReciever;

public class ReciverStartService extends Service {
    private ScreenOnReciever screenOnReciever;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        screenOnReciever=new ScreenOnReciever();
        // Register the broadcast receiver with the intent filter object.
        registerReceiver(screenOnReciever, screenOnReciever.getFilter());

        Log.d("com.vik", "Service onCreate: screenOnOffReceiver is registered.");
        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(screenOnReciever);
    }
}
