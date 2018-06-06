package vik.com.appdetection.background.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import vik.com.appdetection.background.reciever.BluetoothReciever;
import vik.com.appdetection.background.reciever.RestartReciever;
import vik.com.appdetection.background.reciever.ScreenOnReciever;

public class ReciverStartService extends Service {
    private ScreenOnReciever screenOnReciever;
    private RestartReciever restartReciever;
    private BluetoothReciever bluetoothReciever;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        screenOnReciever=new ScreenOnReciever();
        restartReciever=new RestartReciever();
        bluetoothReciever=new BluetoothReciever();
        // Register the broadcast receiver with the intent filter object.
        registerReceiver(screenOnReciever, screenOnReciever.getFilter());
        //registerReceiver(restartReciever,restartReciever.getFilter());
        registerReceiver(bluetoothReciever,bluetoothReciever.getFilter());
        Log.d("com.vik", "Service onCreate: screenOnOffReceiver is registered.");
        Log.d("com.vik","On Create called");


        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(1,new Notification());

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d("com.vik", "TASK REMOVED");

        PendingIntent service = PendingIntent.getService(
                getApplicationContext(),
                1001,
                new Intent(getApplicationContext(), ReciverStartService.class),
                PendingIntent.FLAG_ONE_SHOT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000, service);
    }

    @Override
    public void onDestroy() {
        Log.d("com.vik","Reciver Detsroy called");
        unregisterReceiver(screenOnReciever);
        super.onDestroy();
        /**
        //Intent broadcastIntent = new Intent("vik.com.appdetection.restart");
        Intent intent = new Intent(this, RestartReciever.class);

// Create the pending intent and wrap our intent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

// Get the alarm manager service and schedule it to go off after 3s
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (3 * 1000), pendingIntent);

        Toast.makeText(this, "Alarm set in " + 3 + " seconds", Toast.LENGTH_LONG).show();
         **/
    }



}
