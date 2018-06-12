package vik.com.appdetection.background.aws;

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
import vik.com.appdetection.background.handler.CredentialHandler;
import vik.com.appdetection.background.handler.UserHandler;
import vik.com.appdetection.background.listener.UploadTransferListener;
import vik.com.appdetection.utils.Constants;

public class DataDumper {

    // Class responsible for dumping the current data collected
    // for the user to a amazon S3 bucket.

    public static void dumpData(Context context) {
        Log.d("com.vik", "Dumping data..");

        if (!Constants.ENABLE_UPLOAD) {
            Log.d("com.vik", "Upload is disabled. Upload aborted. See Constants");
            return;
        }

        if (!UserHandler.hasAgreedToTerms(context)) {
            Log.d("com.vik", "The user has not agreed to the terms and conditions. Not uploading data");
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

        //String filename = WriteDataService.getCurrentDate();
        File file = null;

        try {

            File dirFiles = context.getFilesDir();
            for (String filename : dirFiles.list())
            {
                if(filename.contains(".csv")) {
                    file = new File(context.getFilesDir(), filename);
                    String userId = UserHandler.getCurrentUserId();
                    Log.d("com.vik", "file name" + filename);
                    Log.d("com.vik", "uploadWithTransferUtility:-- user " + CredentialHandler.SIGNED_IN_USER);
                    String uploadFilename = CredentialHandler.SIGNED_IN_USER + "/" + filename;

                    TransferObserver uploadObserver =
                            transferUtility.upload(
                                    "uploads/" + uploadFilename,
                                    file);

                    Log.d("com.vik", "Uploaded file_name " + uploadFilename);
                    // Attach a listener to the observer to get state update and progress notifications
                    uploadObserver.setTransferListener(new UploadTransferListener(file));
                }
            }

        } catch (IllegalArgumentException e) {
            Log.e("com.vik", "Uploading was attempted with no csv file available. Aborting upload...",e);
            return;
        }
        catch(Exception e)
        {
            Log.e("com.vik","file upload error",e);
            return;
        }







    }
}
