package com.sitemap.weifang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.sitemap.weifang.R;
import com.sitemap.weifang.adapter.NewsListAdapter;
import com.sitemap.weifang.application.MyApplication;
import com.sitemap.weifang.config.RequestCode;
import com.sitemap.weifang.config.WebUrlConfig;
import com.sitemap.weifang.http.HttpUtil;
import com.sitemap.weifang.model.NewsModel;
import com.sitemap.weifang.util.ToastUtil;
import com.sitemap.weifang.view.MyProgressDialog;
import com.sitemap.weifang.view.PullToRefreshLayout;
import com.sitemap.weifang.view.PullableLinearLayout;
import com.sitemap.weifang.view.PullableListView;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻列表
 */
@ContentView(R.layout.activity_news_list)
public class NewsListActivity extends BaseActivity implements View.OnClickListener ,PullToRefreshLayout.OnRefreshListener{
    private NewsListActivity mContext;//本类
    @ViewInject(R.id.back)
    private LinearLayout mBack;//返回上一层
    @ViewInject(R.id.title)
    private TextView mTitle;//标题
    @ViewInject(R.id.news_list)
    private ListView news_list;//新闻列表
    private NewsListAdapter adapter;//适配器
    private int jumpType = 0;//类型
    private MyProgressDialog progressDialog;//进度条
    private HttpUtil http;//网络请求
    private List<NewsModel> mNewsList = new ArrayList<>();//数据
    private List<NewsModel> mCurrList = new ArrayList<>();//当前显示的数据
    private List<NewsModel> mCurrMoreList = new ArrayList<>();//当前显示更多的数据
    @ViewInject(R.id.refresh_layout)
    private PullToRefreshLayout mRefreshLayout;//刷新布局
    private int page = 0;//当前页
    @ViewInject(R.id.pull_layout)
    private PullableLinearLayout mPullLayout;//刷新
    private int currPos = 0;//显示的当前位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
//        initListViewData();
    }

    private Handler handler = new Handler(){
        /**
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();// 关闭进度条
            }
            switch (msg.what) {
                case HttpUtil.SUCCESS:
                    //获取新闻
                    if (msg.arg1 == RequestCode.GETNEWS){
                        page = 0;
                        mNewsList.clear();
                        mCurrList.clear();
                        mNewsList = JSONObject.parseArray(msg.obj.toString(), NewsModel.class);
                        if (jumpType == 0) {//政策法规
                            for (NewsModel model:mNewsList){
                                if (model.getCkType().equals("10")||model.getCkType().equals("21")
                                        ||model.getCkType().equals("22")||model.getCkType().equals("23")){
                                    mCurrList.add(model);
                                }
                            }
                        } else if (jumpType == 1) {//信用动态
                            for (NewsModel model:mNewsList){
                                if (model.getCkType().equals("7")||model.getCkType().equals("8")
                                        ||model.getCkType().equals("9")||model.getCkType().equals("32")){
                                    mCurrList.add(model);
                                }
                            }
                        } else if (jumpType == 2) {//信用研究
                            for (NewsModel model:mNewsList){
                                if (model.getCkType().equals("11")||model.getCkType().equals("12")
                                        ||model.getCkType().equals("13")){
                                    mCurrList.add(model);
                                }
                            }
                        } else if (jumpType == 3) {//曝光台
                            for (NewsModel model:mNewsList){
                                if (model.getCkType().equals("26")||model.getCkType().equals("27")
                                        ||model.getCkType().equals("30")||model.getCkType().equals("31")){
                                    mCurrList.add(model);
                                }
                            }
                        } else {//推送的信息的消息
                            for (NewsModel model:mNewsList){
                                mCurrList.add(model);
                            }
                        }
                        currPos = 0;
                        if (mCurrList.size()>0&&mCurrList!=null){
                            initListViewData(mCurrList);
                        }else {
                            MyApplication.setEmptyShowText(mContext,news_list,"暂无数据");
                        }
                        mRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                    //更多新闻
                    if (msg.arg1 == RequestCode.GETNEWSMORE){
                        List<NewsModel> newsModelList = JSONObject.parseArray(msg.obj.toString(), NewsModel.class);
                        if (jumpType == 0) {//政策法规
                            for (NewsModel model:newsModelList){
                                if (model.getCkType().equals("10")||model.getCkType().equals("21")
                                        ||model.getCkType().equals("22")||model.getCkType().equals("23")){
                                    mCurrMoreList.add(model);
                                }
                            }
                        } else if (jumpType == 1) {//信用动态
                            for (NewsModel model:newsModelList){
                                if (model.getCkType().equals("7")||model.getCkType().equals("8")
                                        ||model.getCkType().equals("9")||model.getCkType().equals("32")){
                                    mCurrMoreList.add(model);
                                }
                            }
                        } else if (jumpType == 2) {//信用研究
                            for (NewsModel model:newsModelList){
                                if (model.getCkType().equals("11")||model.getCkType().equals("12")
                                        ||model.getCkType().equals("13")){
                                    mCurrMoreList.add(model);
                                }
                            }
                        } else if (jumpType == 3) {//曝光台
                            for (NewsModel model:newsModelList){
                                if (model.getCkType().equals("26")||model.getCkType().equals("27")
                                        ||model.getCkType().equals("30")||model.getCkType().equals("31")){
                                    mCurrMoreList.add(model);
                                }
                            }
                        } else {//推送的信息的消息
                            for (NewsModel model:newsModelList){
                                mCurrMoreList.add(model);
                            }
                        }
                        currPos = mCurrList.size()-1;
                        if (mCurrMoreList.size()>0&&mCurrMoreList!=null){
                            for (NewsModel model:mCurrMoreList){
                                mCurrList.add(model);
                            }
                            initListViewData(mCurrList);
                        }else {
                            MyApplication.setEmptyShowText(mContext,news_list,"暂无数据");
                        }
                        mRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                    break;
                case HttpUtil.EMPTY:
                    //获取新闻
                    if (msg.arg1 == RequestCode.GETNEWS){
                        if (jumpType==4){
                            MyApplication.setEmptyShowText(mContext,news_list,"暂无最新新闻");
                        }else{
                            MyApplication.setEmptyShowText(mContext,news_list,"暂无新闻");
                        }
                        mRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                    if (msg.arg1 == RequestCode.GETNEWSMORE){
                        mRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                    break;
                case HttpUtil.FAILURE:
                    mRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                    mRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
                    ToastUtil.showBottomShort(mContext, RequestCode.ERRORINFO);
                    break;
                case HttpUtil.LOADING:
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 获取新闻
     * @param newsType
     * @param page
     */
    private void getNewsData(String newsType,String page){
        if (MyApplication.getNetObject().isNetConnected()){
            progressDialog = MyProgressDialog.createDialog(mContext);
            if (progressDialog!=null&&!progressDialog.isShowing()){
                progressDialog.setMessage("加载中...");
                progressDialog.show();
            }
            http.sendGet(RequestCode.GETNEWS, WebUrlConfig.getNews(newsType,page));
        }else{
            mRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
            ToastUtil.showBottomShort(mContext, RequestCode.NONETWORK);
        }
    }

    /**
     * 获取推送的消息 新闻
     */
    private void getMyNews(String page){
        if (MyApplication.getNetObject().isNetConnected()){
            progressDialog = MyProgressDialog.createDialog(mContext);
            if (progressDialog!=null&&!progressDialog.isShowing()){
                progressDialog.setMessage("加载中...");
                progressDialog.show();
            }
            http.sendGet(RequestCode.GETNEWS, WebUrlConfig.getMyNews(page));
        }else{
            mRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
            ToastUtil.showBottomShort(mContext, RequestCode.NONETWORK);
        }
    }

    /**
     * 初始化数据
     */
    private void init() {
        mContext = this;
        mBack.setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);
        mPullLayout.setLv(news_list);
        if (http == null){
            http = new HttpUtil(handler);
        }
        jumpType = getIntent().getIntExtra("jumpType", 0);
        if (jumpType == 0) {//新闻
            mTitle.setText("政策法规");
            getNewsData(String.valueOf(1),String.valueOf(0));
        } else if (jumpType == 1) {
            mTitle.setText("信用动态");
            getNewsData(String.valueOf(2),String.valueOf(0));
        } else if (jumpType == 2) {
            mTitle.setText("信用研究");
            getNewsData(String.valueOf(3),String.valueOf(0));
        } else if (jumpType == 3) {
            mTitle.setText("曝光台");
            getNewsData(String.valueOf(4),String.valueOf(0));
        } else {//推送的消息 新闻
            mTitle.setText("新闻");
            getMyNews(String.valueOf(0));
        }
    }

    /**
     * 初始化listview
     */
    private void initListViewData(final List<NewsModel> list) {
        adapter = new NewsListAdapter(mContext,list);
        news_list.setAdapter(adapter);
        news_list.setSelection(currPos);
        news_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(mContext, NewsActivity.class);
                intent.putExtra("newsUrl",list.get(position).getHtml());
                startActivity(intent);
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v == mBack) {
            mContext.finish();
        }
    }

    /**
     * 刷新
     * @param pullToRefreshLayout
     */
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        if (jumpType==4){
            getMyNews(String.valueOf(0));
        }else{
            getNewsData(String.valueOf(jumpType+1),String.valueOf(0));
        }

    }

    /**
     * 加载更多
     * @param pullToRefreshLayout
     */
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        page+=1;
        if (MyApplication.getNetObject().isNetConnected()){
            progressDialog = MyProgressDialog.createDialog(mContext);
            if (progressDialog!=null&&!progressDialog.isShowing()){
                progressDialog.setMessage("加载中...");
                progressDialog.show();
            }
            if (jumpType==4){
                http.sendGet(RequestCode.GETNEWSMORE, WebUrlConfig.getMyNews(String.valueOf(page)));
            }else{
                http.sendGet(RequestCode.GETNEWSMORE, WebUrlConfig.getNews(String.valueOf(jumpType+1),String.valueOf(page)));
            }
        }else{
            mRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
            ToastUtil.showBottomShort(mContext, RequestCode.NONETWORK);
        }

    }
}
