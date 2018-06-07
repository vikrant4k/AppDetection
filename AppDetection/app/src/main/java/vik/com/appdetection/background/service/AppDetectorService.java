package vik.com.appdetection.background.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rvalerio.fgchecker.AppChecker;

import vik.com.appdetection.background.app.service.AppChangeService;
import vik.com.appdetection.background.app.service.CreateDataService;
import vik.com.appdetection.background.app.service.WriteDataService;

public class AppDetectorService extends Service {
    private static  final String TAG_NAME="com.vik.appdetect";
    private AppChecker appChecker;
    private AppChangeService appChangeService;
    private CreateDataService createDataService;
    private WriteDataService writeDataService;
    private boolean isCheckerOn=false;
    private AppDetectorService appDetectorService=null;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle=intent.getExtras();
        boolean isScreenOn=(Boolean) bundle.get("screen_state");

        if(isScreenOn)
        {
            appDetectorService=this;
            Log.d(TAG_NAME,"service started ");
            appChecker=new AppChecker();
            appChangeService=new AppChangeService();
            createDataService=new CreateDataService();
            writeDataService=new WriteDataService();
            startTracking();
            try {
                writeDataService.readDataFromFile(this);
            }
            catch(Exception e)
            {
                Log.e("com.vik","error",e);
            }
            //writeDataService.readDataFromFile(this);
            isCheckerOn=true;
            appChecker.whenAny(new AppChecker.Listener() {
                @Override
                public void onForeground(String process) {
                //Log.d(TAG_NAME,process);
                String appName=appChangeService.findAppByPackage(process,getApplicationContext().getPackageManager());
                if(appName!=null){
                    createDataService.createData(appDetectorService,appName);
                }
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
            if(writeDataService!=null)
            {
                writeDataService.writeDataToFile(createDataService.featureDataList,this);
                //writeDataService.readDataFromFile(this);
            }
        }
        super.onDestroy();
    }

    private void startTracking() {
        Log.d(TAG_NAME,"trackimg started");
        try {
            Intent intent1 = new Intent(this, BackgroundDetectedActivitiesService.class);
            startService(intent1);
            Intent intent2 = new Intent(this, GeoLocationService.class);
            startService(intent2);
        }
        catch (Exception e)
        {
            Log.e("com.vik","error",e);
        }
    }

    private void stopTracking() {
        Intent intent = new Intent(this, BackgroundDetectedActivitiesService.class);
        stopService(intent);
        Intent intent2 = new Intent(this, GeoLocationService.class);
        stopService(intent2);
    }
}
