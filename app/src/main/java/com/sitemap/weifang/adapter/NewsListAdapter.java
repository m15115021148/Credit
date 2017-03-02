package com.sitemap.weifang.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sitemap.weifang.R;
import com.sitemap.weifang.http.HttpImageUtil;
import com.sitemap.weifang.model.NewsModel;
import com.sitemap.weifang.util.DateUtil;

import java.util.List;

/**
 * @desc 新闻列表适配器
 * Created by zf on 2016/12/14.
 */
public class NewsListAdapter extends BaseAdapter {
    private Context mContext;//本类
    private List<NewsModel> mList;
    private Holder holder;

    public NewsListAdapter(Context context,List<NewsModel> list){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.news_list_item,null);
            holder = new Holder();
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.from = (TextView) convertView.findViewById(R.id.from);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        NewsModel model = mList.get(position);

        HttpImageUtil.loadImage(holder.image,model.getImg());
        holder.title.setText(model.getTitle());
        holder.from.setText(model.getSummary());
        holder.date.setText(DateUtil.formatDateTime(Long.parseLong(model.getCreateTime())));

        return convertView;
    }

    private class Holder {
        ImageView image;//图片
        TextView title,from,date;//标题  来源  时间
    }

}
