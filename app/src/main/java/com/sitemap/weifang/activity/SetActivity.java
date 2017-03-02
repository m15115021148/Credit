package com.sitemap.weifang.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sitemap.weifang.R;
import com.sitemap.weifang.application.MyApplication;
import com.sitemap.weifang.model.LoginModel;
import com.sitemap.weifang.util.DialogUtil;
import com.sitemap.weifang.util.PreferencesUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cn.jpush.android.api.JPushInterface;

@ContentView(R.layout.activity_set)
public class SetActivity extends BaseActivity implements View.OnClickListener{
    private SetActivity mContext;//本类
    @ViewInject(R.id.back)
    private LinearLayout mBack;//返回上一层
    @ViewInject(R.id.title)
    private TextView mTitle;//标题
    @ViewInject(R.id.set_about)
    private RelativeLayout mAbout;//关于
    @ViewInject(R.id.set_push)
    private CheckBox mPush;//推送
    private SharedPreferences preferences;//保存
    @ViewInject(R.id.set_exit)
    private RelativeLayout mExit;//退出登录

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initData();
    }

    /**
     * 初始化view
     */
    private void initData() {
        mBack.setOnClickListener(this);
        mAbout.setOnClickListener(this);
        mPush.setOnClickListener(this);
        mExit.setOnClickListener(this);
        mTitle.setText("设置");

        preferences = mContext.getSharedPreferences("JPush", Context.MODE_PRIVATE);
        if (preferences.getBoolean("isPush", true)) {
            mPush.setChecked(true);
            JPushInterface.resumePush(getApplicationContext());
        }else {
            mPush.setChecked(false);
            JPushInterface.stopPush(getApplicationContext());
        }
        mPush.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    JPushInterface.resumePush(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isPush", true);
                    editor.commit();
                }else {
                    JPushInterface.stopPush(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isPush", false);
                    editor.commit();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v == mBack){
            mContext.finish();
        }
        if (v == mAbout){
            Intent intent = new Intent(mContext,AboutActivity.class);
            startActivity(intent);
        }
        if (v == mExit){
            View dialog = DialogUtil.customPromptDialog(mContext, "确定", "取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MyApplication.isLogin = false;
                            MyApplication.loginModel = new LoginModel();
                            PreferencesUtil.setDataModel(mContext,"userModel",MyApplication.loginModel);
                            PreferencesUtil.isFirstLogin(mContext,"first",false);
                            Intent login = new Intent(mContext,MainActivity.class);
                            startActivity(login);
                            finish();
                        }
                    }, null);
            TextView txt = (TextView) dialog.findViewById(R.id.dialog_tv_txt);
            txt.setText("确定退出吗？");
        }
    }
}
