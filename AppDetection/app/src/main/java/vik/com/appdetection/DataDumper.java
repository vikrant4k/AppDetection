package vik.com.appdetection;

import android.content.Context;
import android.util.Log;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;
import vik.com.appdetection.background.app.service.WriteDataService;
import vik.com.appdetection.utils.Constants;

public class DataDumper {

    // Class responsible for dumping the current data collected
    // for the user to a amazon S3 bucket.

    public static void dumpData(Context context) {
        Log.d("Stian", "Dumping data..");

        if (!Constants.enableUpload) {
            Log.d("Stian", "Upload is disabled. Upload aborted. See Constants");
            return;
        }

        uploadWithTransferUtility(context);
    }

    private static void uploadWithTransferUtility(Context context) {

        TransferUtility transferUtility =
            TransferUtility.builder()
                .context(context)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
                .build();

        String filename = WriteDataService.getCurrentDate();
        File file = null;

        try {
            file = new File(context.getFilesDir(), filename);
        } catch (IllegalArgumentException e) {
            Log.d("com.vik", "Uploading was attempted with no csv file available. Aborting upload...");
            return;
        }

        String userId = UserHandler.getCurrentUserId();
        String uploadFilename = userId + "_" + filename;

        TransferObserver uploadObserver =
            transferUtility.upload(
                "uploads/" + uploadFilename,
                file);

        // Attach a listener to the observer to get state update and progress notifications
        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    // Handle a completed upload.
                    Log.d("Stian", "Upload completed");
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                int percentDone = (int)percentDonef;

                Log.d("YourActivity", "ID:" + id + " bytesCurrent: " + bytesCurrent
                    + " bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.d("Stian", "An error occurred while uploading:");
                Log.d("Stian", ex.getMessage());
            }

        });

        // If you prefer to poll for the data, instead of attaching a
        // listener, check for the state and progress in the observer.
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            // Handle a completed upload.
        }

        Log.d("com.vik", "Bytes Transferrred: " + uploadObserver.getBytesTransferred());
        Log.d("com.vik", "Bytes Total: " + uploadObserver.getBytesTotal());
    }
}