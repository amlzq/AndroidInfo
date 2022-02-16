package com.amlzq.androidinfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;

/**
 * Created by amlzq on 2021/7/2.
 */

public class PkgDetailActivity extends Activity {
    private PackageManager manager;
    private PackageInfo item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pkg_detail);

        item = getIntent().getParcelableExtra("item");
        manager = getPackageManager();

        ImageView iconView = (ImageView) findViewById(R.id.icon);
        iconView.setImageDrawable(item.applicationInfo.loadIcon(manager));

        TextView appInfoView = (TextView) findViewById(R.id.app_info);
        appInfoView.setText(toString(item.applicationInfo));

        TextView pkgInfoView = (TextView) findViewById(R.id.pkg_info);
        pkgInfoView.setText(toString(item));

        TextView signView = (TextView) findViewById(R.id.signatures);
        signView.setText(getSignatures(item.packageName));

        TextView permView = (TextView) findViewById(R.id.permissions);
        permView.setText(getPermissions(item.packageName));

        TextView actView = (TextView) findViewById(R.id.activities);
        actView.setText(getActivities(item.packageName));

        TextView servView = (TextView) findViewById(R.id.services);
        servView.setText(getServices(item.packageName));

        TextView receView = (TextView) findViewById(R.id.receivers);
        receView.setText(getReceivers(item.packageName));

        TextView provView = (TextView) findViewById(R.id.providers);
        provView.setText(getProviders(item.packageName));

        TextView confView = (TextView) findViewById(R.id.configPreferences);
        confView.setText(getConfigurations(item.packageName));

        TextView metaView = (TextView) findViewById(R.id.metaData);
        metaView.setText(getMetaData(item.packageName));

        TextView instView = (TextView) findViewById(R.id.instrumentations);
        instView.setText(getInstrumentation(item.packageName));

        findViewById(R.id.icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = manager.getLaunchIntentForPackage(item.packageName);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(PkgDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getAppDetailsSettingsIntent(PkgDetailActivity.this, item.packageName);
                startActivity(intent);
            }
        });
    }

    public Intent getAppDetailsSettingsIntent(Context context, String packageName) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", packageName, null));
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

    private String toString(ApplicationInfo info) {
        StringBuilder builder = new StringBuilder();
        builder.append(info.toString() + "\n");
        builder.append("label=" + info.loadLabel(manager) + "\n");
        builder.append("labelRes=" + info.labelRes + "\n");
        builder.append("targetSdkVersion=" + info.targetSdkVersion + "\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.append("minSdkVersion=" + info.minSdkVersion + "\n");
        }
        builder.append("backupAgentName=" + info.backupAgentName + "\n");
        builder.append("className=" + info.className + "\n");
        builder.append("compatibleWidthLimitDp=" + info.compatibleWidthLimitDp + "\n");
        builder.append("dataDir=" + info.dataDir + "\n");
        builder.append("descriptionRes=" + info.descriptionRes + "\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.append("deviceProtectedDataDir " + info.deviceProtectedDataDir + "\n");
        }
        builder.append("enabled=" + info.enabled + "\n");
        builder.append("flags=" + info.flags + "\n");
        builder.append("isAppDebuggable=" + ((info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) + "\n");
        builder.append("isAppTestOnly=" + ((info.flags & ApplicationInfo.FLAG_TEST_ONLY) != 0) + "\n");
        builder.append("isAppAllowBackup=" + ((info.flags & ApplicationInfo.FLAG_ALLOW_BACKUP) != 0) + "\n");
        builder.append("largestWidthLimitDp=" + info.largestWidthLimitDp + "\n");
        builder.append("manageSpaceActivityName=" + info.manageSpaceActivityName + "\n");
        builder.append("nativeLibraryDir=" + info.nativeLibraryDir + "\n");
        builder.append("permission=" + info.permission + "\n");
        builder.append("processName=" + info.processName + "\n");
        builder.append("publicSourceDir=" + info.publicSourceDir + "\n");
        builder.append("requiresSmallestWidthDp=" + info.requiresSmallestWidthDp + "\n");
        builder.append("sharedLibraryFiles=" + Util.toString(info.sharedLibraryFiles) + "\n");
        builder.append("sourceDir=" + info.sourceDir + "\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.append("splitPublicSourceDirs=" + Util.toString(info.splitPublicSourceDirs) + "\n");
            builder.append("splitPublicSourceDirs=" + Util.toString(info.splitSourceDirs) + "\n");
        }
        builder.append("taskAffinity=" + info.taskAffinity + "\n");
        builder.append("theme=" + info.theme + "\n");
        builder.append("uiOptions=" + info.uiOptions + "\n");
        builder.append("uid=" + info.uid + "\n");
        return builder.toString();
    }

    private String toString(PackageInfo info) {
        StringBuilder builder = new StringBuilder();
        builder.append(info.toString() + "\n");
        builder.append("packageName=" + info.packageName + "\n");
        builder.append("versionCode=" + info.versionCode + "\n");
        builder.append("versionName=" + info.versionName + "\n");
        builder.append("applicationInfo=" + info.applicationInfo + "\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            builder.append("baseRevisionCode=" + info.baseRevisionCode + "\n");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.append("featureGroups.length=" + Util.getLength(info.featureGroups) + "\n");
        }
        builder.append("firstInstallTime=" + info.firstInstallTime + "\n");
        builder.append("gids=" + Util.toString(info.gids) + "\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.append("installLocation=" + info.installLocation + "\n");
        }
        builder.append("lastUpdateTime=" + info.lastUpdateTime + "\n");
        builder.append("packageName=" + info.packageName + "\n");
        builder.append("reqFeatures.length=" + Util.getLength(info.reqFeatures) + "\n");
        builder.append("requestedPermissions=" + Util.toString(info.requestedPermissions) + "\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.append("requestedPermissionsFlags=" + Util.toString(info.requestedPermissionsFlags) + "\n");
        }
        builder.append("sharedUserId=" + info.sharedUserId + "\n");
        builder.append("sharedUserLabel=" + info.sharedUserLabel + "\n");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.append("splitNames=" + Util.toString(info.splitNames) + "\n");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            builder.append("splitRevisionCodes=" + Util.toString(info.splitRevisionCodes) + "\n");
        }
        return builder.toString();
    }

    private String toString(ActivityInfo info) {
        StringBuilder builder = new StringBuilder();
        builder.append(info.toString() + "\n");
        builder.append("name=" + info.name + "\n");
        builder.append("processName=" + info.packageName + "\n");
        builder.append("processName=" + info.processName + "\n");
        builder.append("permission=" + info.permission + "\n");
        builder.append("describeContents=" + info.describeContents() + "\n");
        builder.append("flags=" + Integer.toHexString(info.flags) + "\n");
        return builder.toString();
    }

    private String getSignatures(String packageName) {
        StringBuilder builder = new StringBuilder();
        try {
            final PackageInfo info = manager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            Signature[] signatures = info.signatures;
            builder.append("signatures.length=" + signatures.length + "\n");
            byte[] byteArray = signatures[0].toByteArray();
            String md5 = encode("MD5", byteArray);
            builder.append("MD5=" + md5 + "\n");
            String sha1 = encode("SHA1", byteArray);
            builder.append("SHA1=" + sha1 + "\n");
            String sha256 = encode("SHA256", byteArray);
            builder.append("SHA256=" + sha256 + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    private String getPermissions(String packageName) {
        StringBuilder builder = new StringBuilder();
        try {
            final PackageInfo info = manager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            String[] permissions = info.requestedPermissions;
            builder.append("permissions.length=" + permissions.length + "\n");
            for (String item : permissions) {
                builder.append(item + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    private String getActivities(String packageName) {
        StringBuilder builder = new StringBuilder();
        try {
            final PackageInfo info = manager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            ActivityInfo[] activities = info.activities;
            builder.append("activities.length=" + activities.length + "\n");
            for (ActivityInfo item : activities) {
                builder.append(toString(item));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    private String getServices(String packageName) {
        StringBuilder builder = new StringBuilder();
        try {
            final PackageInfo info = manager.getPackageInfo(packageName, PackageManager.GET_SERVICES);
            ServiceInfo[] services = info.services;
            builder.append("services.length=" + services.length + "\n");
            for (ServiceInfo item : services) {
                builder.append("name=" + item.name + "\n");
                builder.append("packageName=" + item.packageName + "\n");
                builder.append("processName=" + item.processName + "\n");
                builder.append("permission=" + item.permission + "\n");
                builder.append("describeContents=" + item.describeContents() + "\n");
                builder.append("flags=" + Integer.toHexString(item.flags) + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    private String getReceivers(String packageName) {
        StringBuilder builder = new StringBuilder();
        try {
            final PackageInfo info = manager.getPackageInfo(packageName, PackageManager.GET_RECEIVERS);
            ActivityInfo[] receivers = info.receivers;
            builder.append("receivers.length=" + receivers.length + "\n");
            for (ActivityInfo item : receivers) {
                builder.append(toString(item));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    private String getProviders(String packageName) {
        StringBuilder builder = new StringBuilder();
        try {
            final PackageInfo info = manager.getPackageInfo(packageName, PackageManager.GET_PROVIDERS);
            ProviderInfo[] providers = info.providers;
            builder.append("providers.length=" + providers.length + "\n");
            for (ProviderInfo item : providers) {
                builder.append("name=" + item.name + "\n");
                builder.append("packageName=" + item.packageName + "\n");
                builder.append("processName=" + item.processName + "\n");
                builder.append("describeContents=" + item.describeContents() + "\n");
                builder.append("flags=" + Integer.toHexString(item.flags) + "\n");
                builder.append("authority=" + item.authority + "\n");
                builder.append("readPermission=" + item.readPermission + "\n");
                builder.append("writePermission=" + item.writePermission + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    private String getConfigurations(String packageName) {
        StringBuilder builder = new StringBuilder();
        try {
            final PackageInfo info = manager.getPackageInfo(packageName, PackageManager.GET_CONFIGURATIONS);
            ConfigurationInfo[] configPreferences = info.configPreferences;
            builder.append("configPreferences.length=" + configPreferences.length + "\n");
            for (ConfigurationInfo item : configPreferences) {
                builder.append("reqNavigation=" + item.reqNavigation + "\n");
                builder.append("describeContents=" + item.describeContents() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    private String getMetaData(String packageName) {
        try {
            final PackageInfo info = manager.getPackageInfo(packageName, PackageManager.GET_META_DATA);
            Bundle bundle = info.applicationInfo.metaData;
            return "metaData\n" + Util.toString(bundle) + "\n";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getInstrumentation(String packageName) {
        StringBuilder builder = new StringBuilder();
        try {
            final PackageInfo info = manager.getPackageInfo(packageName, PackageManager.GET_INSTRUMENTATION);
            InstrumentationInfo[] instrumentations = info.instrumentation;
            for (InstrumentationInfo item : instrumentations) {
                builder.append("name=" + item.name + "\n");
                builder.append("describeContents=" + item.describeContents() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
