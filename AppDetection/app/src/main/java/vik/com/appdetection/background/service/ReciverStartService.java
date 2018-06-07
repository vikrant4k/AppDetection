package vik.com.appdetection.background.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;;
import android.os.Build.VERSION_CODES;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.Calendar;

import vik.com.appdetection.R;
import vik.com.appdetection.background.reciever.BluetoothReciever;
import vik.com.appdetection.background.reciever.S3DumpReceiver;
import vik.com.appdetection.background.reciever.RestartReciever;
import vik.com.appdetection.background.reciever.ScreenOnReciever;

public class ReciverStartService extends Service {
    private ScreenOnReciever screenOnReciever;
    private RestartReciever restartReciever;
    private BluetoothReciever bluetoothReciever;
    private AlarmManager alarmMgr;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        screenOnReciever=new ScreenOnReciever();
        restartReciever=new RestartReciever();
        bluetoothReciever=new BluetoothReciever();
        // Register the broadcast receiver with the intent filter object.
        registerReceiver(screenOnReciever, screenOnReciever.getFilter());
        //registerReceiver(restartReciever,restartReciever.getFilter());
        registerReceiver(bluetoothReciever,bluetoothReciever.getFilter());
        setUpAlarm(this);
        Log.d("com.vik", "Service onCreate: screenOnOffReceiver is registered.");
        Log.d("com.vik","On Create called");


        return Service.START_STICKY;
    }

    public void setUpAlarm(Context context)
    {
        try {
            Intent intent = new Intent(context, S3DumpReceiver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 55);

// With setInexactRepeating(), you have to use one of the AlarmManager interval
// constants--in this case, AlarmManager.INTERVAL_DAY.
            alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, alarmIntent);
        }
        catch (Exception e)
        {
            Log.d("com.vik","error",e);
        }
    }

    @RequiresApi(api = VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        // startForeground(1,new Notification());
        createNotification();
    }

    @RequiresApi(api = VERSION_CODES.O)
    public void createNotification() {
        NotificationChannel channel = new NotificationChannel("im_channel_id","System", NotificationManager.IMPORTANCE_LOW);
                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.createNotificationChannel(channel);
                Notification notification = new Notification.Builder(this, "im_channel_id")
                    .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)  // the status icon
                    .setWhen(System.currentTimeMillis())  // the time stamp
                    .setContentText("AppDetection")  // the contents of the entry
                    .build();

        startForeground(101, notification);
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
