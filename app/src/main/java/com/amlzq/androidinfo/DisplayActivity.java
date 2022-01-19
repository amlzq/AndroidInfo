package com.amlzq.androidinfo;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

/**
 * Created by amlzq on 2021/6/27.
 */

public class DisplayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        refresh();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        refresh();
    }

    public String getOrientationName(int orient) {
        String orientName;
        switch (orient) {
            case Configuration.ORIENTATION_UNDEFINED:
                orientName = "undefined";
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                orientName = "portrait";
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                orientName = "landscape";
                break;
            case Configuration.ORIENTATION_SQUARE:
                orientName = "square";
                break;
            default:
                orientName = "unknown";
        }
        return orientName;
    }

    void refresh() {
        TextView textView = (TextView) findViewById(R.id.text_view);
        StringBuilder builder = new StringBuilder();
        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        builder.append("density=" + metrics.density + "\n");
        builder.append("scaledDensity=" + metrics.scaledDensity + "\n");
        builder.append("scaledDensity=" + metrics.scaledDensity + "\n");
        builder.append("heightPixels=" + metrics.heightPixels + "\n");
        builder.append("orientationName=" + getOrientationName(getResources().getConfiguration().orientation) + "\n");
        textView.setText(builder.toString());
    }
}
