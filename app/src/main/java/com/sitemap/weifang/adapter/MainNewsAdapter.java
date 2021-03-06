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
public class MainNewsAdapter extends BaseAdapter{
    private Context mContext;
    private static final String[] newsValues = {"政策法规","信用动态","信用研究","曝光台"};
    private static final int[] newsRes = {R.drawable.news_1,R.drawable.news_2,R.drawable.news_3,R.drawable.news_4};
    private static final String[] creditValues = {"信用异议","信用申报","信用监督","信用咨询"};
    private static final int[] creditRes = {R.drawable.credit_3,R.drawable.credit_4,R.drawable.credit_1,R.drawable.credit_2};
    private Holder holder;//帮助holder
    private int mType;

    /**
     * 构造
     * @param context
     * @param type 1 新闻栏目  2 信用栏目
     */
    public MainNewsAdapter(Context context,int type){
        this.mContext = context;
        this.mType = type;
    }

    @Override
    public int getCount() {
        if (mType == 1){
            return newsValues.length;
        }else{
            return creditValues.length;
        }
    }

    @Override
    public Object getItem(int position) {
        if (mType == 1){
            return newsValues[position];
        }else{
            return creditValues[position];
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_gridview_news_item,null);
            holder = new Holder();
            holder.img = (ImageView) convertView.findViewById(R.id.news_img);
            holder.txt = (TextView) convertView.findViewById(R.id.news_txt);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        if (mType==1){
            holder.img.setImageResource(newsRes[position]);
            holder.txt.setText(newsValues[position]);
        }else{
            holder.img.setImageResource(creditRes[position]);
            holder.txt.setText(creditValues[position]);
        }

        return convertView;
    }

    private class Holder{
        ImageView img;
        TextView txt;
    }

}
