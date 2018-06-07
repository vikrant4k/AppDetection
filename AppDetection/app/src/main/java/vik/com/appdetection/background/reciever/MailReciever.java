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
            Uri apkURI = FileProvider.getUriForFile(
                    context,
                    context.getApplicationContext()
                            .getPackageName() + ".provider", filelocation);
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/plain");
            String to[] = {"vikrant4.k@gmail.com"};
            emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
// the attachment
            emailIntent.setDataAndType(apkURI,"plain/text");
// the mail subject
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "App Detection Data");
            emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
        }
        catch (Exception e)
        {
            Log.e("com.vik","error",e);
        }
    }
}
