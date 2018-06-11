package vik.com.appdetection.background.handler;

import android.util.Log;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;

import java.util.Map;

public class CredentialHandler implements GetDetailsHandler {

    public static String SIGNED_IN_USER;
    @Override
    public void onSuccess(CognitoUserDetails cognitoUserDetails) {
        Log.d("com.vik","In Success Credential");
        CognitoUserAttributes attributes = cognitoUserDetails.getAttributes();
        Map<String,String> attributesMap=  attributes.getAttributes();
        SIGNED_IN_USER = attributesMap.get("email");
        Log.d("com.vik", "-- user -- "+CredentialHandler.SIGNED_IN_USER);
    }

    @Override
    public void onFailure(Exception exception) {
      Log.e("com.vik","error",exception);
    }
}
