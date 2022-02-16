package com.amlzq.androidinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        super.onMenuItemSelected(featureId, item);
        switch (item.getItemId()) {
            case R.id.share:
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, "<a href=\"https://github.com/amlzq/AndroidInfo\">An assistant for developers to visualize the info of Android.</a>");
                startActivity(share);
                break;
        }
        return true;
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

    public void onStorageClick(View view) {
        startActivity(new Intent(this, StorageActivity.class));
    }

    public void onNetworkClick(View view) {
        startActivity(new Intent(this, NetworkActivity.class));
    }

    public void onMemoryClick(View view) {
        startActivity(new Intent(this, MemoryActivity.class));
    }
}
