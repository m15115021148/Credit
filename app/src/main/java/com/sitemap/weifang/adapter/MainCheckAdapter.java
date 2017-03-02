package com.sitemap.weifang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sitemap.weifang.R;

/**
 * @desc 首页 查询 扫一扫 适配器
 * Created by chenmeng on 2016/11/16.
 */
public class MainCheckAdapter extends BaseAdapter{
    private Context mContext;
    private static final String[] values = {"查询","名片夹","扫一扫"};
    private static final int[] res = {R.drawable.check,R.drawable.mingbian,R.drawable.scan};
    private Holder holder;

    public MainCheckAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public Object getItem(int position) {
        return values[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_gridview_check_item,null);
            holder = new Holder();
            holder.img = (ImageView) convertView.findViewById(R.id.check_img);
            holder.txt = (TextView) convertView.findViewById(R.id.check_txt);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        holder.txt.setText(values[position]);
        holder.img.setImageResource(res[position]);

        return convertView;
    }

    private class Holder{
        ImageView img;
        TextView txt;
    }

}
