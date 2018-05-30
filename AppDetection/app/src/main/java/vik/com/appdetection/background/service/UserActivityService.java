package vik.com.appdetection.background.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.ActivityTransitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;

public class UserActivityService extends IntentService {
public static int ACTiTIVITY_TYPE=3;
    public UserActivityService()
    {
        super("UserActivityService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("com.vik","Intent arrived");
        if (ActivityTransitionResult.hasResult(intent)) {
            ActivityTransitionResult result = ActivityTransitionResult.extractResult(intent);
            for (ActivityTransitionEvent event : result.getTransitionEvents()) {
                ACTiTIVITY_TYPE=event.getActivityType();
            }
        }

    }

}
