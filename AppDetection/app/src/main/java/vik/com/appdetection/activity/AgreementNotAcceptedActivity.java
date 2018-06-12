package vik.com.appdetection.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import vik.com.appdetection.R;
import vik.com.appdetection.activity.AgreementActivity;

public class AgreementNotAcceptedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement_not_accepted);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void showAgreementActivity(View v) {
        Intent intent = new Intent(this, AgreementActivity.class);
        startActivity(intent);
    }

}
