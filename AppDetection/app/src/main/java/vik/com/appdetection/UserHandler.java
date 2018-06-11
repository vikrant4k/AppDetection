package vik.com.appdetection;

import android.content.Context;
import android.util.Log;
import com.amazonaws.mobile.auth.core.IdentityHandler;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;

class UserHandler {

    private static Boolean hasAgreedToTerms = false;

    public static String getCurrentUserId() {
        return IdentityManager.getDefaultIdentityManager().getCachedUserID();
    }

    public static void setHasAgreedToTerms(Boolean val) {
        hasAgreedToTerms = val;
    }

    public static Boolean hasAgreedToTerms() {
        return hasAgreedToTerms;
    }
}
