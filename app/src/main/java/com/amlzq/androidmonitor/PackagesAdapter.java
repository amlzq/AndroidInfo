package com.amlzq.androidmonitor;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amlzq on 2021/6/28.
 */

public class PackagesAdapter extends BaseAdapter implements Filterable {

    private final Context mContext;
    private List<PackageInfo> mData;
    private final List<PackageInfo> mDataBackup;

    private LayoutInflater mInflater;
    private View.OnClickListener mClickListener;
    private PackageManager manager;


    public PackagesAdapter(Context context, List<PackageInfo> data) {
        this.mContext = context;
        this.mData = data;
        this.mDataBackup = data;
        this.mInflater = LayoutInflater.from(mContext);
        this.manager = context.getPackageManager();
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public PackageInfo getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_package, parent, false);
            holder.mIcon = (ImageView) convertView.findViewById(R.id.icon);
            holder.mAction = (ImageButton) convertView.findViewById(R.id.action);
            holder.mName = (TextView) convertView.findViewById(R.id.name);
            holder.mPkg = (TextView) convertView.findViewById(R.id.pkg);
            holder.mVersion = (TextView) convertView.findViewById(R.id.version);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        refreshItem(getItem(position), holder);
        return convertView;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    private void refreshItem(PackageInfo item, ViewHolder holder) {
        Drawable icon = item.applicationInfo.loadIcon(manager);
        holder.mIcon.setImageDrawable(icon);
        holder.mName.setText(item.applicationInfo.loadLabel(manager));
        holder.mPkg.setText(item.packageName);
        holder.mVersion.setText(item.versionCode + "+" + item.versionName);
        holder.mAction.setTag(item);
        holder.mAction.setOnClickListener(mClickListener);
        holder.mItem = item;
    }

    public void updateData(List<PackageInfo> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    final static class ViewHolder {
        ImageView mIcon;
        ImageButton mAction;
        TextView mName;
        TextView mPkg;
        TextView mVersion;
        PackageInfo mItem;
    }

    private PackagesFilter mFilter;

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new PackagesFilter();
        }
        return mFilter;
    }

    class PackagesFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            List<PackageInfo> newData;
            if (TextUtils.isEmpty(charSequence)) {
                newData = mDataBackup;
            } else {
                newData = new ArrayList<>();
                for (PackageInfo info : mDataBackup) {
                    if (info.packageName.contains(charSequence)) newData.add(info);
                }
            }
            results.values = newData;
            results.count = newData.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mData = (List<PackageInfo>) filterResults.values;
            if (filterResults.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

}
