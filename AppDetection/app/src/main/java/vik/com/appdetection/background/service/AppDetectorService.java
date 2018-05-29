package vik.com.appdetection.background.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class AppDetectorService extends Service {

    private static  final String TAG_NAME="com.vik.appdetect";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle=intent.getExtras();
        boolean isScreenOn=(Boolean) bundle.get("screen_state");
        if(isScreenOn)
        {
            Log.d(TAG_NAME,"service started ");
        }
        else
        {
            Log.d(TAG_NAME,"service stopped");
            this.onDestroy();
        }
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Bundle bundle=intent.getExtras();
        String screen_state=bundle.getString("screen_state");
        Log.d(TAG_NAME,"screen state "+screen_state);
        return null;
    }


    @Override
    public void onDestroy() {
        Log.d(TAG_NAME,"service destroyed ");
        super.onDestroy();
    }
}
