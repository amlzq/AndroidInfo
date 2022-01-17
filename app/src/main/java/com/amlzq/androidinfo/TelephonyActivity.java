package com.amlzq.androidinfo;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

/**
 * Created by amlzq on 2021/6/27.
 */

public class TelephonyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephony);

        TextView textView = (TextView) findViewById(R.id.text_view);
        StringBuilder builder = new StringBuilder();
        final TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        try {
            builder.append("device_id " + manager.getDeviceId() + "\n");
            builder.append("subscriber_id " + manager.getSubscriberId() + "\n");
            builder.append("sim_serial_number " + manager.getSimSerialNumber() + "\n");
            builder.append("line1_number " + manager.getLine1Number() + "\n");
            builder.append("sim_country_iso " + manager.getSimCountryIso() + "\n");
            builder.append("device_software_version " + manager.getDeviceSoftwareVersion() + "\n");
            builder.append("cell_location " + manager.getCellLocation() + "\n");
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        textView.setText(builder.toString());
    }
}
