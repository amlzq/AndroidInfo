package com.amlzq.androidinfo;

import android.app.Activity;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.ProxyInfo;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by amlzq on 2021/12/10.
 * <p>
 * Show Network/NetworkInfo class info
 */

public class NetworkActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        StringBuilder builder = new StringBuilder();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        builder.append("isActiveNetworkMetered=" + connectivityManager.isActiveNetworkMetered() + "\n");
        builder.append("isDefaultNetworkActive=" + connectivityManager.isDefaultNetworkActive() + "\n");

        Network[] allNetworks = connectivityManager.getAllNetworks();
        for (int i = 0; i < allNetworks.length; i++) {
            builder.append("Network " + i + "=" + allNetworks[i].toString() + "\n");
        }
        builder.append("\n");

        Network network = connectivityManager.getActiveNetwork();
        if (network != null) {
            builder.append("getActiveNetwork.toString=" + network.toString() + "\n");
        }

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            builder.append("getActiveNetworkInfo.toString=" + networkInfo.toString() + "\n");
        }

        ProxyInfo proxyInfo = connectivityManager.getDefaultProxy();
        if (proxyInfo != null) {
            builder.append("getDefaultProxy.toString=" + proxyInfo.toString() + "\n");
        }

        NetworkStatsManager networkStatsManager = (NetworkStatsManager) getSystemService(Context.NETWORK_STATS_SERVICE);
        // networkStatsManager.querySummary(ConnectivityManager.TYPE_MOBILE, );

        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setText(builder.toString());
    }
}
