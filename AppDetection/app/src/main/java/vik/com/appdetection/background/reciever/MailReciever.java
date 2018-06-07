package vik.com.appdetection.background.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;

import vik.com.appdetection.background.app.service.WriteDataService;


public class MailReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("com.vik","mail reciver activated");
        //sendMail(context);
    }

    public void sendMail(Context context)
    {
        try {
            String filename = WriteDataService.getCurrentDate();
            File filelocation = new File(context.getFilesDir(), filename);
        }
        catch (Exception e)
        {
            Log.e("com.vik","error",e);
        }
    }
}
