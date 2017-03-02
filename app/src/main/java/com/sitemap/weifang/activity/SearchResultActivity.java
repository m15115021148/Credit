package com.sitemap.weifang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sitemap.weifang.R;
import com.sitemap.weifang.adapter.SearchResultAdapter;
import com.sitemap.weifang.application.MyApplication;
import com.sitemap.weifang.model.SearchCompanyModel;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询
 */
@ContentView(R.layout.activity_search_result)
public class SearchResultActivity extends BaseActivity implements View.OnClickListener{
    private SearchResultActivity mContext;//本类
    @ViewInject(R.id.back)
    private LinearLayout mBack;//返回上一层
    @ViewInject(R.id.title)
    private TextView mTitle;//标题
    @ViewInject(R.id.result_list_view)
    private ListView mLv;//listView
    private List<SearchCompanyModel> mList;//数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData(){
        mBack.setOnClickListener(this);
        mTitle.setText("查询结果");
        mList = new ArrayList<>();
        mList.clear();
        mList = JSONObject.parseArray(getIntent().getStringExtra("SearchCompanyModel"),SearchCompanyModel.class);
        if (mList.size()>0&&mList!=null){
            SearchResultAdapter adapter = new SearchResultAdapter(this,mList);
            mLv.setAdapter(adapter);
            mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(mContext,CreditRecordActivity.class);
                    intent.putExtra("companyID",mList.get(position).getCompanyID());
                    startActivity(intent);
                }
            });

        }else{
            MyApplication.setEmptyShowText(mContext,mLv,"暂无数据");
        }
    }

    @Override
    public void onClick(View v) {
        if(v == mBack){
            mContext.finish();
        }
    }
}
