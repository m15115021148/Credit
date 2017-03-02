package com.sitemap.weifang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.sitemap.weifang.R;
import com.sitemap.weifang.adapter.MainCheckAdapter;
import com.sitemap.weifang.adapter.MainNewsAdapter;
import com.sitemap.weifang.application.MyApplication;
import com.sitemap.weifang.sacn.QRActivity;
import com.sitemap.weifang.util.PreferencesUtil;
import com.sitemap.weifang.util.ToastUtil;
import com.sitemap.weifang.view.MyGridView;
import com.sitemap.weifang.view.PageFlipper;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页页面
 * @author Created by chenmeng on 2016/11/16.
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements View.OnClickListener,PageFlipper.ImageOnClickListener {
    private MainActivity mContext;//本类
    private long exitTime = 0;//退出的时间
    @ViewInject(R.id.main_menu)
    private LinearLayout mMenu;//菜单
    @ViewInject(R.id.main_msg)
    private LinearLayout mMsg;//消息
    @ViewInject(R.id.main_indictor)
    private LinearLayout mLayout;//导航圈的布局
    private List<View> mDotList = new ArrayList<>();//圈的集合
    @ViewInject(R.id.pagerflipper)
    private PageFlipper mPf;//轮播视图
    @ViewInject(R.id.main_gridview_check)
    private MyGridView mGvCheck;//查询栏目gridview
    @ViewInject(R.id.main_news_gridview)
    private MyGridView mGvNews;//新闻栏目gridview
    @ViewInject(R.id.main_credit_gridview)
    private MyGridView mGvCredit;//信用栏目gridview
    @ViewInject(R.id.main_scrollView)
    private ScrollView mSv;//滚动器
    private final  int SCAN_GREQUEST_CODE = 11;
//    public static final int[] images = {R.drawable.play_1,R.drawable.play_2};//轮播图片
    public static final String[] images = {"assets://banner1.gif"};//轮播图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initData();
        initViewPage();
        initGridViewCheck();
        initGridViewNews();
        initGridViewCredit();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPf.start();
        mSv.smoothScrollTo(0,0);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPf.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPf.stop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mSv.smoothScrollTo(0,0);
        mSv.setFocusable(true);
        mGvNews.setFocusable(false);
        mGvCredit.setFocusable(false);
        mGvCheck.setFocusable(false);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mMenu.setOnClickListener(this);
        mMsg.setOnClickListener(this);

        //取出保存的用户信息
        MyApplication.loginModel = PreferencesUtil.getDataModel(mContext,"userModel");
        MyApplication.isLogin = PreferencesUtil.getFirstLogin(mContext,"first");
        if (!TextUtils.isEmpty(PreferencesUtil.getStringTxt(mContext,"loginType"))){
            MyApplication.loginType = Integer.parseInt(PreferencesUtil.getStringTxt(mContext,"loginType"));
        }
    }

    /**
     * 初始化viewpage
     */
    private void initViewPage() {
        mPf.setViews(images);
        mPf.setImageOnClickListener(this);
        MyApplication.drawPoint(this, mLayout, mDotList, images.length, R.drawable.indictor_select, R.drawable.indictor_normal);
        mPf.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mDotList.size(); i++) {
                    if (i == position) {
                        mDotList.get(position).setBackgroundResource(R.drawable.indictor_select);
                    } else {
                        mDotList.get(i).setBackgroundResource(R.drawable.indictor_normal);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化数据 查询 名片夹 扫一扫
     */
    private void initGridViewCheck(){
        MainCheckAdapter adapter = new MainCheckAdapter(this);
        mGvCheck.setAdapter(adapter);
        mGvCheck.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position){
                    case 0://查询
                        intent.setClass(mContext,SearchActivity.class);
                        startActivity(intent);
                        break;
                    case 1://名片夹
                        if (!MyApplication.isLogin){
                            ToastUtil.showBottomShort(mContext,"请先登录");
                            Intent login = new Intent(mContext,LoginActivity.class);
                            startActivity(login);
                            return;
                        }
                        intent.setClass(mContext,NamesCardActivity.class);
                        startActivity(intent);
                        break;
                    case 2:// 开始二维码扫描
                        if (!MyApplication.isLogin){
                            ToastUtil.showBottomShort(mContext,"请先登录");
                            Intent login = new Intent(mContext,LoginActivity.class);
                            startActivity(login);
                            return;
                        }
                        intent.setClass(mContext,QRActivity.class);
                        startActivityForResult(intent,SCAN_GREQUEST_CODE);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // 二维码扫描结束回调
            case SCAN_GREQUEST_CODE:
                if(resultCode == RESULT_OK){
                    try {
                        Bundle bundle = data.getExtras();
                        //显示扫描到的内容
                        String msg=bundle.getString(QRActivity.RESULT);
                        Log.e("result", msg);
                        if (msg.contains("WeiFang_")){
                            String id = msg.split("_")[2];
                            //1 代表个人  2 代表企业
                            int type = Integer.parseInt(msg.split("_")[1]);
                            if (type==MyApplication.loginType){
                                Intent intent = new Intent(mContext,CreditCardActivity.class);
                                intent.putExtra("isShow",2);
                                if (MyApplication.loginType==1){
                                    intent.putExtra("personID",id);
                                }else{
                                    intent.putExtra("companyID",id);
                                }
                                startActivity(intent);
                            }else{
                                ToastUtil.showBottomShort(mContext,"自然人和法人不能相互添加名片");
                            }
                        }else{
                            ToastUtil.showBottomShort(mContext,"二维码不符合规范");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    /**
     * 初始化新闻栏目
     */
    private void initGridViewNews(){
        MainNewsAdapter adapter = new MainNewsAdapter(this,1);
        mGvNews.setAdapter(adapter);
        mGvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position){
                    case 0://新闻
                        intent.setClass(mContext,NewsListActivity.class);
                        intent.putExtra("jumpType",0);
                        startActivity(intent);
                        break;
                    case 1://动态
                        intent.setClass(mContext,NewsListActivity.class);
                        intent.putExtra("jumpType",1);
                        startActivity(intent);
                        break;
                    case 2://诚企名录
                        intent.setClass(mContext,NewsListActivity.class);
                        intent.putExtra("jumpType",2);
                        startActivity(intent);
                        break;
                    case 3://曝光台
                        intent.setClass(mContext,NewsListActivity.class);
                        intent.putExtra("jumpType",3);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 初始化信用栏目
     */
    private void initGridViewCredit(){
        MainNewsAdapter adapter = new MainNewsAdapter(this,2);
        mGvCredit.setAdapter(adapter);
        mGvCredit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!MyApplication.isLogin){
                    ToastUtil.showBottomShort(mContext,"请先登录");
                    Intent intent = new Intent(mContext,LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                Intent intent = new Intent();
                switch (position){
                    case 2://监督
                        intent.setClass(mContext,CreditReportActivity.class);
                        intent.putExtra("jumpType",0);
                        startActivity(intent);
                        break;
                    case 3://咨询
                        intent.setClass(mContext,CreditReportActivity.class);
                        intent.putExtra("jumpType",1);
                        startActivity(intent);
                        break;
                    case 0://异议
                        intent.setClass(mContext,CreditReportActivity.class);
                        intent.putExtra("jumpType",2);
                        startActivity(intent);
                        break;
                    case 1://申报
                        intent.setClass(mContext,CreditReportActivity.class);
                        intent.putExtra("jumpType",3);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     *	退出activity
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序!",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                //退出所有的activity
                Intent intent = new Intent();
                intent.setAction(BaseActivity.TAG_ESC_ACTIVITY);
                sendBroadcast(intent);
//                System.exit(0);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        if(v == mMenu){//菜单
            if (!MyApplication.isLogin){
                ToastUtil.showBottomShort(mContext,"请先登录");
                Intent intent = new Intent(mContext,LoginActivity.class);
                startActivity(intent);
                return;
            }
            Intent intent = new Intent(mContext,UserActivity.class);
            startActivity(intent);
        }
        if (v == mMsg){//消息
            if (!MyApplication.isLogin){
                ToastUtil.showBottomShort(mContext,"请先登录");
                Intent intent = new Intent(mContext,LoginActivity.class);
                startActivity(intent);
                return;
            }
            Intent intent = new Intent(mContext,MessageActivity.class);
            startActivity(intent);
        }
    }

    /**
     *  轮播图片点击事件
     * @param position 位置
     */
    @Override
    public void onClickImageListener(int position) {

    }
}
