package vik.com.appdetection.background.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import vik.com.appdetection.background.service.ReciverStartService;

public class RestartReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Log.d("com.vik","brodast intit");
            context.startService(new Intent(context, ReciverStartService.class));
        }
        catch(Exception e)
        {
            Log.e("com.vik","asaa",e);
        }

    }

    public IntentFilter getFilter(){
        final android.content.IntentFilter filter = new IntentFilter();
        filter.addAction("vik.com.appdetection.restart");
        filter.setPriority(100);
        return filter;
    }
}
