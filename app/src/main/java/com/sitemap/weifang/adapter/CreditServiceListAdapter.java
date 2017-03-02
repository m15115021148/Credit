package com.sitemap.weifang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sitemap.weifang.R;
import com.sitemap.weifang.model.CreditServiceModel;
import com.sitemap.weifang.util.DateUtil;

import java.util.List;

/**
 * @desc 信用服务 适配器
 * Created by chenmeng on 2016/12/14.
 */

public class CreditServiceListAdapter extends BaseAdapter{
    private Context mContext;
    private List<CreditServiceModel> mList;
    private Holder holder;

    public CreditServiceListAdapter(Context context,List<CreditServiceModel> list){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.credit_service_list_view_item,null);
            holder = new Holder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.reason = (TextView) convertView.findViewById(R.id.reason);
            holder.result = (TextView) convertView.findViewById(R.id.result);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        CreditServiceModel model = mList.get(position);
        holder.name.setText(model.getMc());
        holder.reason.setText(model.getContent());
        holder.result.setText(model.getResult());

        holder.time.setText(DateUtil.formatDateTime(Long.parseLong(model.getUpdateTime())));

        return convertView;
    }

    private class Holder{
        TextView name,reason,result,time;
    }
}
