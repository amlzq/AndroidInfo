package com.amlzq.androidinfo;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by amlzq on 2022/1/19.
 */

public class MemoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        StringBuilder builder = new StringBuilder();

        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        builder.append("isLowRamDevice=" + manager.isLowRamDevice() + "\n");

        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(memoryInfo);
        builder.append("availMem=" + byteFormat(memoryInfo.availMem) + "\n"); // unit byte
        builder.append("totalMem=" + byteFormat(memoryInfo.totalMem) + "\n");
        builder.append("threshold=" + byteFormat(memoryInfo.threshold) + "\n");
        builder.append("lowMemory=" + memoryInfo.lowMemory + "\n");
        builder.append("describeContents=" + memoryInfo.describeContents() + "\n");

        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setText(builder.toString());
    }

    private static String byteFormat(long size) {
        StringBuffer buffer = new StringBuffer();
        DecimalFormat format = new DecimalFormat(",###.000");
        if (size >= 1024 * 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            buffer.append(format.format(i)).append("GB");
        } else if (size >= 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0));
            buffer.append(format.format(i)).append("MB");
        } else if (size >= 1024) {
            double i = (size / (1024.0));
            buffer.append(format.format(i)).append("KB");
        } else if (size < 1024) {
            if (size <= 0) {
                buffer.append("0B");
            } else {
                buffer.append((int) size).append("B");
            }
        }
        return buffer.toString();
    }

}
