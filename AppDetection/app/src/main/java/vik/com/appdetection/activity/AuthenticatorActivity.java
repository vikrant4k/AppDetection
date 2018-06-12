package vik.com.appdetection.activity;

import android.app.Activity;
import android.os.Bundle;

import com.amazonaws.mobile.auth.ui.SignInUI;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;

import vik.com.appdetection.R;
import vik.com.appdetection.background.handler.UserHandler;


public class AuthenticatorActivity extends Activity {

    public static String SignedInUser = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticator);
        //Fabric.with(getApplicationContext(), new Crashlytics());

        logUser();

        // Add a call to initialize AWSMobileClient
        final Boolean userHasAgreedToTerms = UserHandler.hasAgreedToTerms(this);

        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                SignInUI signin = (SignInUI) AWSMobileClient.getInstance().getClient(AuthenticatorActivity.this, SignInUI.class);
                if (!userHasAgreedToTerms) {
                    signin.login(AuthenticatorActivity.this, AgreementActivity.class).execute();
                } else {
                    signin.login(AuthenticatorActivity.this, MainActivity.class).execute();
                }

            }




        }).execute();

    }

    private void logUser() {
        // You can call any combination of these three methods
        //Crashlytics.setUserIdentifier(SignedInUser);
    }

}
