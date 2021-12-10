package com.amlzq.androidmonitor;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.provider.MediaStore;
import android.widget.TextView;

import java.io.File;

/**
 * Created by amlzq on 2021/7/19.
 * https://developer.android.com/guide/topics/data/data-storage.html
 */

public class StorageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        StringBuilder builder = new StringBuilder();

        // internal storage
        File dir = getCacheDir();
        builder.append("getCacheDir():\n" + dir.getAbsolutePath() + "\n");

        dir = getDataDir();
        builder.append("getDataDir():\n" + dir.getAbsolutePath() + "\n");

        dir = getFilesDir();
        builder.append("getFilesDir():\n" + dir.getAbsolutePath() + "\n");

        dir = getObbDir();
        builder.append("getObbDir():\n" + dir.getAbsolutePath() + "\n");

        dir = getCodeCacheDir();
        builder.append("getCodeCacheDir():\n" + dir.getAbsolutePath() + "\n");

        TextView internalStorage = (TextView) findViewById(R.id.internal_storage);
        internalStorage.setText(builder.toString());

        // external storage
        dir = getExternalFilesDir(Environment.DIRECTORY_DCIM);
        builder.append("getExternalFilesDir(Environment.DIRECTORY_DCIM):\n" + dir.getAbsolutePath() + "\n");

        dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        builder.append("getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS):\n" + dir.getAbsolutePath() + "\n");

        dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        builder.append("getExternalFilesDir(Environment.DIRECTORY_PICTURES):\n" + dir.getAbsolutePath() + "\n");

        dir = getExternalCacheDir();
        builder.append("getExternalCacheDir():\n" + dir.getAbsolutePath() + "\n");

        File[] dirs = getExternalFilesDirs(null);
        builder.append("getExternalFilesDirs(null)[0]:\n" + dirs[0].getAbsolutePath() + "\n");
        if (dirs.length > 1) {
            builder.append("getExternalFilesDirs(null)[1]:\n" + dirs[1].getAbsolutePath() + "\n");
        }

        TextView externalStorage = (TextView) findViewById(R.id.external_storage);
        externalStorage.setText(builder.toString());

        Uri mediaScannerUri = MediaStore.getMediaScannerUri();
        builder.append("mediaScannerUri:\n" + mediaScannerUri + "\n");

        String version = MediaStore.getVersion(this);
        builder.append("version:\n" + version + "\n");

        TextView mediaStore = (TextView) findViewById(R.id.media_store);
        mediaStore.setText(builder.toString());

        StorageManager manager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
        StorageVolume volume = manager.getPrimaryStorageVolume();
        volume.getDescription(this);
    }
}
