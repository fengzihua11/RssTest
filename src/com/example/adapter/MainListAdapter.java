
package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.activity.R;
import com.example.beans.SyndEntry;

import java.util.List;

public class MainListAdapter extends BaseAdapter {

    private final LayoutInflater mFactory;
    private Context mContext;
    private List<SyndEntry> mObjects;

    public MainListAdapter(Context context, List<SyndEntry> objects) {
        super();
        mContext = context;
        mFactory = LayoutInflater.from(context);
        mObjects = objects;
    }

    @Override
    public int getCount() {
        if (mObjects == null)
            return 0;
        return mObjects.size();
    }

    @Override
    public Object getItem(int position) {
        if (mObjects == null)
            return null;
        return mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mFactory.inflate(R.layout.maint_list_item, parent, false);
        }

        TextView tv = (TextView) convertView;
        tv.setText(mObjects.get(position).formatStr().toString());

        return tv;
    }

}
