package vik.com.appdetection.background.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import vik.com.appdetection.background.service.AppDetectorService;

public class ScreenOnReciever extends BroadcastReceiver {

    public static boolean wasScreenOn = true;
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                wasScreenOn = false;
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                wasScreenOn = true;
            }
            Intent i = new Intent(context, AppDetectorService.class);
            i.putExtra("screen_state", wasScreenOn);
            Log.d("com.vik", "" + wasScreenOn);
            context.startService(i);
        }
        catch (Exception e)
        {
            Log.e("com.vik","errror",e);
        }
    }

    public IntentFilter getFilter(){
        final IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.setPriority(100);
        return filter;
    }
}
