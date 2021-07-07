package com.amlzq.androidmonitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onOSBuildClick(View view) {
        startActivity(new Intent(this, OSBuildActivity.class));
    }

    public void onTelephonyClick(View view) {
        startActivity(new Intent(this, TelephonyActivity.class));
    }

    public void onDisplayClick(View view) {
        startActivity(new Intent(this, DisplayActivity.class));
    }

    public void onPackageClick(View view) {
        startActivity(new Intent(this, PackagesActivity.class));
    }
}
