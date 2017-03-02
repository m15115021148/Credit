package com.sitemap.weifang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sitemap.weifang.R;
import com.sitemap.weifang.http.HttpImageUtil;

import java.util.List;

/**
 * @des 上传附件 图片适配器
 * Created by chenmeng on 2016/12/14.
 */

public class CreditGridViewAdapter extends BaseAdapter{
    private Context mContext;
    private List<String> mList;
    private Holder holder;

    public CreditGridViewAdapter(Context context,List<String> list){
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.credit_grid_view_item,null);
            holder = new Holder();
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        if (position==0){

        }else{
            HttpImageUtil.loadImage(holder.img,mList.get(position));
        }

        return convertView;
    }

    private class Holder{
        ImageView img;
    }
}
