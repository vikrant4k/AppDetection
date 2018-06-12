package vik.com.appdetection.background.listener;

import android.util.Log;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;

import java.io.File;

import vik.com.appdetection.background.app.service.WriteDataService;

public class UploadTransferListener implements TransferListener {

    private File file;

    public UploadTransferListener(File file)
    {
        this.file=file;
    }
    @Override
    public void onStateChanged(int id, TransferState state) {
        if(TransferState.COMPLETED==state)
        {
            Log.d("com.vik", "Upload completed");
            Log.d("com.vik","file name "+file.getName());
            String currentDate= WriteDataService.getCurrentDate();
            if(!currentDate.equals(file.getName()))
            {
                file.delete();
            }
        }
    }

    @Override
    public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
        float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
        int percentDone = (int)percentDonef;

        Log.d("com.vik", "ID:" + id + " bytesCurrent: " + bytesCurrent
                + " bytesTotal: " + bytesTotal + " " + percentDone + "%");

    }

    @Override
    public void onError(int id, Exception ex) {
     Log.e("com.vik","file upload failed",ex);
    }
}
