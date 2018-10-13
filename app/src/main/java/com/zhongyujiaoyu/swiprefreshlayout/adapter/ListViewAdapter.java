package com.zhongyujiaoyu.swiprefreshlayout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhongyujiaoyu.swiprefreshlayout.R;

import java.util.List;

/**
 * Created by Administrator on 2018/10/8.
 */

public class ListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> data;
    private LayoutInflater inflater;

    public ListViewAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.data = list;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_listview_item, null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.text_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(data.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }
}
