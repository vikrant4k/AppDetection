package vik.com.appdetection.background.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rvalerio.fgchecker.AppChecker;

public class AppDetectorService extends Service {
    private static  final String TAG_NAME="com.vik.appdetect";
    private AppChecker appChecker;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle=intent.getExtras();
        boolean isScreenOn=(Boolean) bundle.get("screen_state");
        if(isScreenOn)
        {
            Log.d(TAG_NAME,"service started ");
            appChecker=new AppChecker();
            appChecker.whenAny(new AppChecker.Listener() {
                @Override
                public void onForeground(String process) {
                Log.d(TAG_NAME,process);
                }
            }).timeout(2000).start(this);
        }
        else
        {
            Log.d(TAG_NAME,"service stopped");
            this.onDestroy();
        }
        return Service.START_NOT_STICKY;
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
        this.appChecker.stop();
        Log.d(TAG_NAME,"service destroyed ");
        super.onDestroy();
    }
}
