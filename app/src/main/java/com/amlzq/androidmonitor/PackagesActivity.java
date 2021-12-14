package com.amlzq.androidmonitor;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by amlzq on 2021/6/28.
 * <p>
 * Show ApplicationInfo/PackageInfo class info
 */

public class PackagesActivity extends Activity {
    private ListView mListView;
    private PackagesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PackageInfo item = mAdapter.getItem(position);
                Intent intent = new Intent(PackagesActivity.this, PkgDetailActivity.class);
                intent.putExtra("item", item);
                startActivity(intent);
            }
        });

        final PackageManager manager = getPackageManager();
        final List<PackageInfo> packages = manager.getInstalledPackages(PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
        mAdapter = new PackagesAdapter(this, packages);
        mAdapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageInfo item = (PackageInfo) view.getTag();
                try {
                    Intent intent = manager.getLaunchIntentForPackage(item.packageName);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(PackagesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        mListView.setAdapter(mAdapter);
        mListView.setTextFilterEnabled(true);

        SearchView searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    mListView.clearTextFilter();
                    // mAdapter.getFilter().filter("");
                } else {
                    mListView.setFilterText(newText);
                    // mAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
    }

}
