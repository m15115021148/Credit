package com.sitemap.weifang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sitemap.weifang.R;
import com.sitemap.weifang.application.MyApplication;
import com.sitemap.weifang.util.DialogUtil;
import com.sitemap.weifang.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Locale;

@ContentView(R.layout.activity_user)
public class UserActivity extends BaseActivity implements View.OnClickListener {
    private UserActivity mContext;//本类
    @ViewInject(R.id.back)
    private LinearLayout mBack;//返回上一层
    @ViewInject(R.id.title)
    private TextView mTitle;//标题
    @ViewInject(R.id.name)
    private TextView name;//姓名
    @ViewInject(R.id.phone)
    private TextView phone;//电话
    @ViewInject(R.id.to_erweima)
    private RelativeLayout to_erweima;//我的二维码
    @ViewInject(R.id.to_card)
    private RelativeLayout to_card;
    @ViewInject(R.id.to_jilu)
    private RelativeLayout to_jilu;//我的信用记录
    @ViewInject(R.id.to_set)
    private RelativeLayout to_set;//设置
    @ViewInject(R.id.user_head)
    private ImageView userHead;//头像
    private PopupWindow popDialog;//dialog
    @ViewInject(R.id.user_qr_code)
    private TextView mQrCode;//我的二维码
    @ViewInject(R.id.user_code)
    private TextView mCode;//我的证件
    @ViewInject(R.id.user_credit_record)
    private TextView mCreditRecord;//我的信用记录
    @ViewInject(R.id.code)
    private TextView code;//我的证件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MyApplication.loginType==1){//个人登录
            MyApplication.setLanguage(this, Locale.SIMPLIFIED_CHINESE);
            to_jilu.setVisibility(View.GONE);
        }else{
            MyApplication.setLanguage(this, Locale.TRADITIONAL_CHINESE);
            to_jilu.setVisibility(View.VISIBLE);
        }
        mContext = this;
        initData();
    }

    private void initData(){
        mBack.setOnClickListener(this);
        mTitle.setText("个人中心");
        to_erweima.setOnClickListener(this);
        to_card.setOnClickListener(this);
        to_jilu.setOnClickListener(this);
        to_set.setOnClickListener(this);
//        userHead.setOnClickListener(this);
        mQrCode.setText(R.string.user_qr_code);
        mCode.setText(R.string.user_code);
        mCreditRecord.setText(R.string.user_credit_record);
        name.setText(MyApplication.loginModel.getXm());
        String codeNum = MyApplication.loginModel.getCode();
        String str = codeNum.substring(codeNum.length()-6,codeNum.length()-2);
        code.setText(MyApplication.loginModel.getCode().replace(str,"****"));
        if(TextUtils.isEmpty(MyApplication.loginModel.getPhone())){
            MyApplication.loginModel.setPhone("无");
        }
        phone.setText(MyApplication.loginModel.getPhone());
    }

    @Override
    public void onClick(View v) {
        if(v == mBack){
            finish();
        }
        if (v==to_erweima){//我的二维码
            Intent intent = new Intent(mContext,CreditCardActivity.class);
            intent.putExtra("isShow",0);//显示自己的名片 二维码  不需要添加按钮
            startActivity(intent);
        }
        if (v==to_card){
        }
        if (v==to_jilu){//我的信用记录
            Intent intent = new Intent(mContext,CreditRecordActivity.class);
            startActivity(intent);
        }
        if (v==to_set){
            Intent intent = new Intent(mContext,SetActivity.class);
            startActivity(intent);
        }
        if (v == userHead){//头像
            final String[] values = {"拍照","本地相册"};
            popDialog = DialogUtil.customPopShowWayDialog(mContext, DialogUtil.DialogShowWay.FROM_DOWN_TO_UP, values,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TextView tv = (TextView) v;
                            String name = tv.getText().toString().trim();
                            if (name.equals(values[0])){
                                ToastUtil.showBottomShort(mContext,values[0]);
                            }
                            if (name.equals(values[1])){
                                ToastUtil.showBottomShort(mContext,values[1]);
                            }
                            popDialog.dismiss();
                        }
                    });
        }
    }
}
