package vik.com.appdetection;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.amazonaws.mobile.auth.core.IdentityHandler;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import vik.com.appdetection.utils.Constants;

class UserHandler {

    public static String getCurrentUserId() {
        return IdentityManager.getDefaultIdentityManager().getCachedUserID();
    }

    public static void setHasAgreedToTerms(Boolean val, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE).edit();
        editor.putBoolean("hasAgreedToTerms", val);
        editor.apply();
    }

    public static Boolean hasAgreedToTerms(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE);
        // return prefs.getBoolean("hasAgreedToTerms", new Boolean(false));
        return prefs.getBoolean("hasAgreedToTerms", new Boolean(false));
    }
}
