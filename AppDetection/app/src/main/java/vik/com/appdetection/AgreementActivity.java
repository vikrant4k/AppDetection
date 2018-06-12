package vik.com.appdetection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class AgreementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UserHandler.hasAgreedToTerms(this)) {
            showMainActivity();
        }
    }

    public void handleUserDecline(View v) {
        UserHandler.setHasAgreedToTerms(false, this);
        Intent intent = new Intent(this, AgreementNotAcceptedActivity.class);
        startActivity(intent);
    }

    public void handleUserAccept(View v) {
        UserHandler.setHasAgreedToTerms(true, this);
        showMainActivity();
    }

    public void showMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
