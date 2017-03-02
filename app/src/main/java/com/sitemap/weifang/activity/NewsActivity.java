package com.sitemap.weifang.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sitemap.weifang.R;
import com.sitemap.weifang.util.ToastUtil;
import com.sitemap.weifang.view.MyProgressDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * @desc 新闻页面
 * @author chenmeng created by 2016/11/17
 */
@ContentView(R.layout.activity_news)
public class NewsActivity extends BaseActivity implements View.OnClickListener {
    private NewsActivity mContext;//本类
    @ViewInject(R.id.back)
    private LinearLayout mBack;//返回上一层
    @ViewInject(R.id.title)
    private TextView mTitle;//标题
    @ViewInject(R.id.news_web_view)
    private WebView mWb;//webview
    private String newsUrl = null;//网页url
    private MyProgressDialog progressDialog;//进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initData();
        initWebView();
    }

    @Override
    public void onPause() {
        super.onPause();
        mWb.onPause();
    }

    @Override
    public void onResume() {
        mWb.onResume();
        super.onResume();
    }

    /**
     * 初始化数据
     */
    private void initData(){
        mBack.setOnClickListener(this);
        mTitle.setText("新闻详情");
        progressDialog = MyProgressDialog.createDialog(this);
        newsUrl = getIntent().getStringExtra("newsUrl");
        Log.e("jack","newsUrl:"+newsUrl);
//        newsUrl = "http://www.wfcredit.gov.cn/detail.findnewsbyid?id=2616";
    }

    /**
     * 初始化webview
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {

        // 可加载js
        WebSettings setting = mWb.getSettings();

        // 设置webview自适应屏幕
        setting.setLoadWithOverviewMode(true);// 设置webview加载的页面的模式，也设置为true。这方法可以让你的页面适应手机屏幕的分辨率，完整的显示在屏幕上
        setting.setDomStorageEnabled(true);
        setting.setJavaScriptEnabled(true);
        setting.setSupportZoom(true);
//		setting.setTextSize(WebSettings.TextSize.NORMAL);
//		setting.setDefaultFontSize(16);
        setting.setCacheMode(WebSettings.LOAD_DEFAULT);// 设置 缓存模式
//		setting.setBuiltInZoomControls(true);// 设置支持缩放
        // 同一编码
        setting.setDefaultTextEncodingName("UTF-8");
        mWb.loadUrl(newsUrl);
        // 用webview打开该网页
        mWb.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
                // /返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.setMessage("加载中...");
                    progressDialog.show();
                }

            }
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                ToastUtil.showBottomShort(mContext,"网页加载失败！");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();// 关闭进度条
                }
            }


        });

    }

    @Override
    public void onClick(View v) {
        if(v == mBack){
            finish();
        }
    }

    /**
     * 改写物理按键——返回的逻辑
     *
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWb.canGoBack()) {
                mWb.goBack();// 返回上一页面
                return true;
            } else {
                this.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
