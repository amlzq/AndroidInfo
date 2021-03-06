package com.amlzq.androidinfo;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by amlzq on 2021/6/27.
 */

public class OSBuildActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_osbuild);

        StringBuilder builder = new StringBuilder();
        builder.append("manufacturer=" + Build.MANUFACTURER + "\n");
        builder.append("brand=" + Build.BRAND + "\n");
        builder.append("model=" + Build.MODEL + "\n");
        builder.append("board=" + Build.BOARD + "\n");
        builder.append("board=" + Build.BOARD + "\n");
        builder.append("hardware=" + Build.HARDWARE + "\n");
        builder.append("radio=" + Build.RADIO + "\n");
        builder.append("serial=" + Build.SERIAL + "\n");
        builder.append("display=" + Build.DISPLAY + "\n");
        builder.append("bootloader=" + Build.BOOTLOADER + "\n");
        builder.append("fingerprint=" + Build.FINGERPRINT + "\n");
        builder.append("id=" + Build.ID + "\n");
        builder.append("user=" + Build.USER + "\n");
        builder.append("host=" + Build.HOST + "\n");
        builder.append("time=" + Build.TIME + "\n");
        builder.append("tags=" + Build.TAGS + "\n");
        builder.append("type=" + Build.TYPE + "\n");
        builder.append("product=" + Build.PRODUCT + "\n");
        builder.append("device=" + Build.DEVICE + "\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.append("supported_abis=" + Arrays.toString(Build.SUPPORTED_ABIS) + "\n");
            builder.append("supported_32_bit_abis=" + Arrays.toString(Build.SUPPORTED_32_BIT_ABIS) + "\n");
            builder.append("supported_64_bit_abis=" + Arrays.toString(Build.SUPPORTED_64_BIT_ABIS) + "\n");
        }
        builder.append("version_sdk_int=" + Build.VERSION.SDK_INT + "\n");
        builder.append("version_code_name=" + Build.VERSION.CODENAME + "\n");
        builder.append("version_incremental=" + Build.VERSION.INCREMENTAL + "\n");
        builder.append("version_release=" + Build.VERSION.RELEASE + "\n");
        builder.append("version_release=" + Build.VERSION.RELEASE + "\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            builder.append("version_security_patch=" + Build.VERSION.SECURITY_PATCH + "\n");
            builder.append("version_preview_sdk_int=" + Build.VERSION.PREVIEW_SDK_INT + "\n");
        }

        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setText(builder.toString());
    }
}
