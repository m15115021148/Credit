package com.sitemap.weifang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sitemap.weifang.R;
import com.sitemap.weifang.model.MyMsgModel;

import java.util.List;

/**
 * @desc 消息列表适配器
 * Created by chenmeng on 2016/11/16.
 */
public class MessageListAdapter extends BaseAdapter {
    private Context mContext;//本类
    private List<String> mList;
    private Holder holder;
    private MyMsgModel msgModel;

    public MessageListAdapter(Context context,List<String> list,MyMsgModel msgModel){
        this.mContext = context;
        this.mList = list;
        this.msgModel = msgModel;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_list_item,null);
            holder = new Holder();
            holder.img = (ImageView) convertView.findViewById(R.id.msg_img);
            holder.title = (TextView) convertView.findViewById(R.id.msg_title);
            holder.content = (TextView) convertView.findViewById(R.id.msg_content);
            holder.data = (TextView) convertView.findViewById(R.id.msg_data);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        if (position==0){
            holder.img.setImageResource(R.drawable.msg_news);
            if (msgModel.getResult().equals("1")){
                holder.content.setText(msgModel.getMsg());
            }else{
                holder.content.setText("暂无最新消息");
            }
        }else if (position==1){
            holder.img.setImageResource(R.drawable.msg_credit);
            if (msgModel.getResult().equals("1")){
                holder.content.setText(msgModel.getCreditService());
            }else{
                holder.content.setText("暂无最新消息");
            }
        }else{
            holder.img.setImageResource(R.drawable.msg_card);
            if (msgModel.getResult().equals("1")){
                holder.content.setText(msgModel.getCard());
            }else{
                holder.content.setText("暂无最新消息");
            }
        }
        holder.title.setText(mList.get(position));

        return convertView;
    }

    private class Holder {
        ImageView img;//图片
        TextView title,content,data;//标题  内容  时间
    }

}
