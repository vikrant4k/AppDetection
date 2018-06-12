package vik.com.appdetection.background.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import vik.com.appdetection.background.aws.DataDumper;


public class S3DumpReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("com.vik","S3 dump reciver activated");
        dumpData(context);
    }

    public void dumpData(Context context)
    {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(cm!=null) {
                NetworkInfo ni = cm.getActiveNetworkInfo();
                if (ni != null && ni.isConnected()) {
                    DataDumper.dumpData(context);
                }
            }
        }
        catch (Exception e)
        {
            Log.e("com.vik","error", e);
        }
    }
}
