package vik.com.appdetection.background.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rvalerio.fgchecker.AppChecker;

import vik.com.appdetection.background.app.service.AppChangeService;

public class AppDetectorService extends Service {
    private static  final String TAG_NAME="com.vik.appdetect";
    private AppChecker appChecker;
    private AppChangeService appChangeService;
    private boolean isCheckerOn=false;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle=intent.getExtras();
        boolean isScreenOn=(Boolean) bundle.get("screen_state");
        if(isScreenOn)
        {
            Log.d(TAG_NAME,"service started ");
            startTracking();
            appChecker=new AppChecker();
            appChangeService=new AppChangeService();
            isCheckerOn=true;
            appChecker.whenAny(new AppChecker.Listener() {
                @Override
                public void onForeground(String process) {
                //Log.d(TAG_NAME,process);
                appChangeService.findAppByPackage(process,getApplicationContext().getPackageManager());
                }
            }).timeout(2000).start(this);
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
        //this.appChecker.stop();
        if(isCheckerOn)
        {
            appChecker.stop();
            stopTracking();
        }
        super.onDestroy();
    }

    private void startTracking() {
        Log.d(TAG_NAME,"trackimg started");
        Intent intent1 = new Intent(this, BackgroundDetectedActivitiesService.class);
        startService(intent1);
    }

    private void stopTracking() {
        Intent intent = new Intent(this, BackgroundDetectedActivitiesService.class);
        stopService(intent);
    }
}
