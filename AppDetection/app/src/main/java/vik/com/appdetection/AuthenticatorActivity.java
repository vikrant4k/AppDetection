package vik.com.appdetection;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.SignInStateChangeListener;
import com.amazonaws.mobile.auth.ui.SignInUI;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;


public class AuthenticatorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticator);

        AWSMobileClient.getInstance().initialize(this).execute();

        // Sign-in listener
        Log.d("com.vik.sign", "Attaching sign in/out listener");
        IdentityManager.getDefaultIdentityManager().addSignInStateChangeListener(new SignInStateChangeListener() {
            @Override
            public void onUserSignedIn() {
                Log.d("com.vik.sign", "User Signed In");
            }

            // Sign-out listener
            @Override
            public void onUserSignedOut() {
                Log.d("com.vik.sign", "User Signed Out");
                showSignIn();
            }
        });

        showSignIn();
    }


    /*
     * Display the AWS SDK sign-in/sign-up UI
     */
    private void showSignIn() {
        Log.d("com.vik.sign", "Showing user sign in");
        SignInUI signin = (SignInUI) AWSMobileClient.getInstance().getClient(AuthenticatorActivity.this, SignInUI.class);
        signin.login(AuthenticatorActivity.this, MainActivity.class).execute();
    }

}
