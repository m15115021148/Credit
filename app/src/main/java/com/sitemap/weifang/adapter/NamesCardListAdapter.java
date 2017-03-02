package com.sitemap.weifang.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sitemap.weifang.R;
import com.sitemap.weifang.http.HttpImageUtil;
import com.sitemap.weifang.model.CardCaseModel;

import org.w3c.dom.Text;

import java.util.List;

/**
 * @desc 名片夹 适配器
 * Created by chenmeng on 2016/12/13.
 */
public class NamesCardListAdapter extends BaseAdapter {
    private Context mContext;
    private List<CardCaseModel> mList;
    private Holder holder;

    public NamesCardListAdapter(Context context,List<CardCaseModel> list){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.names_card_list_view_item,null);
            holder = new Holder();
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.tel = (TextView) convertView.findViewById(R.id.tel);
            holder.isShow = (RelativeLayout) convertView.findViewById(R.id.isShow);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        CardCaseModel model = mList.get(position);
        if (!TextUtils.isEmpty(model.getXm())){
            holder.isShow.setVisibility(View.VISIBLE);
            HttpImageUtil.loadRoundImage(holder.img,"");
            holder.name.setText(model.getXm());
            holder.tel.setText(model.getCode());
        }else{
            holder.isShow.setVisibility(View.GONE);
        }

        return convertView;
    }

    private class Holder{
        ImageView img;
        TextView name,tel;
        RelativeLayout isShow;
    }

}
