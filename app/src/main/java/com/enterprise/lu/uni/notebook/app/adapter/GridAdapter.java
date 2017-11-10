package com.enterprise.lu.uni.notebook.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.enterprise.lu.uni.notebook.R;

/**
 * Created by Plarent on 11/10/2017.
 */

public class GridAdapter extends BaseAdapter {

    private String[] items;
    private int[] colors;
    private Context context;
    LayoutInflater inflater;

    public GridAdapter(Context context, String[] items, int[] colors){
        this.context = context;
        this.items = items;
        this.colors = colors;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.griditem, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.label);
        textView.setText(items[position]);
        textView.setBackgroundColor(colors[position]);
        return convertView;
    }
}
