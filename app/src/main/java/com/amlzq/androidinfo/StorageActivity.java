package com.amlzq.androidinfo;

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
import java.util.List;

/**
 * Created by amlzq on 2021/7/19.
 * <p>
 * https://developer.android.com/guide/topics/data/data-storage.html
 */

public class StorageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        StringBuilder builder = new StringBuilder();

        // internal storage
        builder.append("INTERNAL STORAGE\n");
        File dir = getDataDir();
        builder.append("getDataDir()=" + dir.getAbsolutePath() + "\n");

        dir = getCacheDir();
        builder.append("getCacheDir()=" + dir.getAbsolutePath() + "\n");

        dir = getFilesDir();
        builder.append("getFilesDir()=" + dir.getAbsolutePath() + "\n");

        dir = getCodeCacheDir();
        builder.append("getCodeCacheDir()=" + dir.getAbsolutePath() + "\n");

        builder.append("\n");

        // external storage
        builder.append("EXTERNAL STORAGE\n");

        dir = getExternalCacheDir();
        builder.append("getExternalCacheDir()=" + dir.getAbsolutePath() + "\n");

        dir = getExternalFilesDir(Environment.DIRECTORY_DCIM);
        builder.append("getExternalFilesDir(Environment.DIRECTORY_DCIM)=" + dir.getAbsolutePath() + "\n");

        dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        builder.append("getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)=" + dir.getAbsolutePath() + "\n");

        dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        builder.append("getExternalFilesDir(Environment.DIRECTORY_PICTURES)=" + dir.getAbsolutePath() + "\n");

        File[] dirs = getExternalFilesDirs(null);
        builder.append("getExternalFilesDirs(null)[0]=" + dirs[0].getAbsolutePath() + "\n");
        if (dirs.length > 1) {
            builder.append("getExternalFilesDirs(null)[1]=" + dirs[1].getAbsolutePath() + "\n");
        }

        dir = getObbDir();
        builder.append("getObbDir()=" + dir.getAbsolutePath() + "\n");

        builder.append("\n");

        // MediaStore
        builder.append("MediaStore API\n");

        Uri mediaScannerUri = MediaStore.getMediaScannerUri();
        builder.append("mediaScannerUri=" + mediaScannerUri + "\n");

        String version = MediaStore.getVersion(this);
        builder.append("version=" + version + "\n");

        builder.append("\n");

        StorageManager manager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
        List<StorageVolume> storageVolumes = manager.getStorageVolumes();
        for (int i = 0; i < storageVolumes.size(); i++) {
            StorageVolume volume = storageVolumes.get(i);
            builder.append("StorageVolume " + i + "=" + volume.toString() + "\n");
        }
        StorageVolume volume = manager.getPrimaryStorageVolume();
        builder.append("getPrimaryStorageVolume=" + volume.toString() + "\n");

        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setText(builder.toString());
    }
}
