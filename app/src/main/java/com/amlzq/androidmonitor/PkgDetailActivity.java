package com.amlzq.androidmonitor;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.MessageDigest;

/**
 * Created by amlzq on 2021/7/2.
 */

public class PkgDetailActivity extends Activity {
    private PackageManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pkg_detail);

        PackageInfo item = getIntent().getParcelableExtra("item");
        ApplicationInfo appInfo = item.applicationInfo;
        manager = getPackageManager();

        ImageView iconView = (ImageView) findViewById(R.id.icon);
        iconView.setImageDrawable(appInfo.loadIcon(manager));

        TextView nameView = (TextView) findViewById(R.id.name);
        nameView.setText(appInfo.loadLabel(manager));

        TextView pkgView = (TextView) findViewById(R.id.pkg);
        pkgView.setText(item.packageName);

        TextView versionView = (TextView) findViewById(R.id.version);
        versionView.setText(item.versionCode + "+" + item.versionName);

        StringBuilder builder = new StringBuilder();
        try {
            final PackageInfo info = manager.getPackageInfo(
                    item.packageName, PackageManager.GET_SIGNATURES);
            Signature signature = info.signatures[0];
            byte[] byteArray = signature.toByteArray();
            String md5 = encode("MD5", byteArray);
            builder.append("MD5: " + md5 + "\n");
            String sha1 = encode("SHA1", byteArray);
            builder.append("SHA1: " + sha1 + "\n");
            String sha256 = encode("SHA256", byteArray);
            builder.append("SHA256: " + sha256 + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        TextView signView = (TextView) findViewById(R.id.sign);
        signView.setText(builder.toString());

        TextView pkgInfoView = (TextView) findViewById(R.id.pkg_info);
        pkgInfoView.setText(toString(item));

        TextView appInfoView = (TextView) findViewById(R.id.app_info);
        appInfoView.setText(toString(appInfo));

        findViewById(R.id.app_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getAppDetailsSettingsIntent(PkgDetailActivity.this);
                startActivity(intent);
            }
        });
    }

    public Intent getAppDetailsSettingsIntent(Context context) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return intent;
    }

    private String encode(String algorithm, byte[] input) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(input);
            return toHexString(digest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String toHexString(byte[] bytes) {
        char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuilder builder = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            builder.append(HEX_DIGITS[(aByte & 0xf0) >>> 4]);
            builder.append(HEX_DIGITS[aByte & 0x0f]);
        }
        return builder.toString();
    }

    private String toString(PackageInfo info) {
        StringBuilder builder = new StringBuilder();
        builder.append("activities.length: " + Util.getLength(info.activities) + "\n");
        builder.append("applicationInfo: " + info.applicationInfo + "\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            builder.append("baseRevisionCode: " + info.baseRevisionCode + "\n");
        }
        builder.append("configPreferences.length: " + Util.getLength(info.configPreferences) + "\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.append("featureGroups.length: " + Util.getLength(info.featureGroups) + "\n");
        }
        builder.append("firstInstallTime: " + info.firstInstallTime + "\n");
        builder.append("gids: " + Util.toString(info.gids) + "\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.append("installLocation: " + info.installLocation + "\n");
        }
        builder.append("instrumentation.length: " + Util.getLength(info.instrumentation) + "\n");
        builder.append("lastUpdateTime: " + info.lastUpdateTime + "\n");
        builder.append("packageName: " + info.packageName + "\n");
        builder.append("permissions.length: " + Util.getLength(info.permissions) + "\n");
        builder.append("providers.length: " + Util.getLength(info.providers) + "\n");
        builder.append("receivers.length: " + Util.getLength(info.receivers) + "\n");
        builder.append("reqFeatures.length: " + Util.getLength(info.reqFeatures) + "\n");
        builder.append("requestedPermissions: " + Util.toString(info.requestedPermissions) + "\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.append("requestedPermissionsFlags: " + Util.toString(info.requestedPermissionsFlags) + "\n");
        }
        builder.append("services.length: " + Util.getLength(info.services) + "\n");
        builder.append("sharedUserId: " + info.sharedUserId + "\n");
        builder.append("sharedUserLabel: " + info.sharedUserLabel + "\n");
        builder.append("signatures.length: " + Util.getLength(info.signatures) + "\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.append("splitNames: " + Util.toString(info.splitNames) + "\n");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            builder.append("splitRevisionCodes: " + Util.toString(info.splitRevisionCodes) + "\n");
        }
        builder.append("versionCode: " + info.versionCode + "\n");
        builder.append("versionName: " + info.versionName + "\n");
        return builder.toString();
    }

    private String toString(ApplicationInfo info) {
        StringBuilder builder = new StringBuilder();
        builder.append("backupAgentName: " + info.backupAgentName + "\n");
        builder.append("className: " + info.className + "\n");
        builder.append("compatibleWidthLimitDp: " + info.compatibleWidthLimitDp + "\n");
        builder.append("dataDir: " + info.dataDir + "\n");
        builder.append("descriptionRes: " + info.descriptionRes + "\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.append("deviceProtectedDataDir " + info.deviceProtectedDataDir + "\n");
        }
        builder.append("enabled: " + info.enabled + "\n");
        builder.append("flags: " + info.flags + "\n");
        builder.append("largestWidthLimitDp: " + info.largestWidthLimitDp + "\n");
        builder.append("manageSpaceActivityName: " + info.manageSpaceActivityName + "\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.append("minSdkVersion: " + info.minSdkVersion + "\n");
        }
        builder.append("nativeLibraryDir: " + info.nativeLibraryDir + "\n");
        builder.append("permission: " + info.permission + "\n");
        builder.append("processName: " + info.processName + "\n");
        builder.append("publicSourceDir: " + info.publicSourceDir + "\n");
        builder.append("requiresSmallestWidthDp: " + info.requiresSmallestWidthDp + "\n");
        builder.append("sharedLibraryFiles: " + Util.toString(info.sharedLibraryFiles) + "\n");
        builder.append("sourceDir: " + info.sourceDir + "\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.append("splitPublicSourceDirs: " + Util.toString(info.splitPublicSourceDirs) + "\n");
            builder.append("splitPublicSourceDirs: " + Util.toString(info.splitSourceDirs) + "\n");
        }
        builder.append("taskAffinity: " + info.taskAffinity + "\n");
        builder.append("theme: " + info.theme + "\n");
        builder.append("uiOptions: " + info.uiOptions + "\n");
        builder.append("uid: " + info.uid + "\n");
        return builder.toString();
    }
}
