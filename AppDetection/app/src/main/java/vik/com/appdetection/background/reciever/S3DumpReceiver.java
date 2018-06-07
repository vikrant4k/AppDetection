package vik.com.appdetection.background.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;

import vik.com.appdetection.DataDumper;
import vik.com.appdetection.background.app.service.WriteDataService;


public class S3DumpReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("com.vik","S3 dump reciver activated");
        dumpData(context);
    }

    public void dumpData(Context context)
    {
        try {
            DataDumper.dumpData(context);
        }
        catch (Exception e)
        {
            Log.e("com.vik","error", e);
        }
    }
}
