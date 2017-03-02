package com.sitemap.weifang.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.sitemap.weifang.R;
import com.sitemap.weifang.application.MyApplication;
import com.sitemap.weifang.config.RequestCode;
import com.sitemap.weifang.config.WebUrlConfig;
import com.sitemap.weifang.http.HttpUtil;
import com.sitemap.weifang.model.CreditCardModel;
import com.sitemap.weifang.model.ResultModel;
import com.sitemap.weifang.util.CreateQRUitl;
import com.sitemap.weifang.util.ToastUtil;
import com.sitemap.weifang.view.MyProgressDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 信用名片
 * @author chenmeng created by 2016/11/21
 */
@ContentView(R.layout.activity_credit_card)
public class CreditCardActivity extends BaseActivity implements View.OnClickListener{
    private CreditCardActivity mContext;//本类
    @ViewInject(R.id.back)
    private LinearLayout mBack;//返回上一层
    @ViewInject(R.id.title)
    private TextView mTitle;//标题
    @ViewInject(R.id.more_content)
    private TextView mAdd;//添加
    @ViewInject(R.id.credit_card_qr)
    private ImageView mQR;//二维码控件显示
    private int isShow = 0;//是否有添加按钮 0 自身没有添加 1 好友 没有添加按钮 2  好友 有添加按钮
    private MyProgressDialog progressDialog;//进度条
    private HttpUtil http;//网络请求
    @ViewInject(R.id.name)
    private TextView mName;//姓名
    @ViewInject(R.id.company)
    private TextView mCompany;// 所属公司
    private String id = "";//个人id 或企业id
    @ViewInject(R.id.phone)
    private TextView mPhone;//电话
    @ViewInject(R.id.email)
    private TextView mEmail;//邮箱

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initData();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();// 关闭进度条
            }
            switch (msg.what){
                case HttpUtil.SUCCESS:
                    //信用名片
                    if (msg.arg1 == RequestCode.CREDITCARD){
                        CreditCardModel model = JSONObject.parseObject(msg.obj.toString(), CreditCardModel.class);
                        if (model.getResult().equals("1")){
                            mName.setText(model.getXm());
                            if(TextUtils.isEmpty(model.getCompany())){
                                model.setCompany("无");
                            }
                            if(TextUtils.isEmpty(model.getPhone())){
                                model.setPhone("无");
                            }
                            if(TextUtils.isEmpty(model.getEmail())){
                                model.setEmail("无");
                            }
                            mCompany.setText(model.getCompany());
                            mPhone.setText(model.getPhone());
                            mEmail.setText(model.getEmail());
                        }else{
                            ToastUtil.showBottomShort(mContext,"数据异常");
                            finish();
                        }

                    }
                    //添加名片
                    if (msg.arg1 == RequestCode.ADDCARD){
                        ResultModel model = JSONObject.parseObject(msg.obj.toString(), ResultModel.class);
                        if (model.getResult().equals("1")){
                            ToastUtil.showBottomShort(mContext,"添加名片申请已发送");
                            finish();
                        }else{
                            ToastUtil.showBottomShort(mContext,model.getErrorMsg());
                        }
                    }
                    break;
                case HttpUtil.EMPTY:
                    //信用名片
                    if (msg.arg1 == RequestCode.CREDITCARD){
                        ToastUtil.showBottomShort(mContext,"暂无结果");
                    }
                    //添加名片
                    if (msg.arg1 == RequestCode.ADDCARD){
                        ToastUtil.showBottomShort(mContext,"添加失败");
                    }
                    break;
                case HttpUtil.FAILURE:
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
     * 获取我的信用名片
     */
    private void getCreditCard(int type ,String id){
        if (MyApplication.getNetObject().isNetConnected()){
            progressDialog = MyProgressDialog.createDialog(mContext);
            if (progressDialog!=null&&!progressDialog.isShowing()){
                progressDialog.setMessage("加载中...");
                progressDialog.show();
            }
            if (type==1){
                http.sendGet(RequestCode.CREDITCARD,WebUrlConfig.getPerson(id));
            }else{
                http.sendGet(RequestCode.CREDITCARD,WebUrlConfig.getCompany(id));
            }
        }else{
            ToastUtil.showBottomShort(mContext, RequestCode.NONETWORK);
        }
    }

    /**
     * 添加名片
     */
    private void getAddCard(String cardID){
        if (MyApplication.getNetObject().isNetConnected()){
            progressDialog = MyProgressDialog.createDialog(mContext);
            if (progressDialog!=null&&!progressDialog.isShowing()){
                progressDialog.setMessage("加载中...");
                progressDialog.show();
            }
            http.sendGet(RequestCode.ADDCARD,WebUrlConfig.getAddCard(MyApplication.loginModel.getUserID(),cardID));
        }else{
            ToastUtil.showBottomShort(mContext, RequestCode.NONETWORK);
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mBack.setOnClickListener(this);
        mTitle.setText("信用名片");
        if (http == null){
            http = new HttpUtil(handler);
        }
        isShow = getIntent().getIntExtra("isShow",0);
        if (isShow == 2){
            mAdd.setVisibility(View.VISIBLE);
            mAdd.setOnClickListener(this);
            mAdd.setText("添加");
            if (MyApplication.loginType==1){
                id = getIntent().getStringExtra("personID");
            }else{
                id = getIntent().getStringExtra("companyID");
            }
            CreateQRUitl.createQRImage(mQR, "WeiFang_"+MyApplication.loginType+"_"+id);
            Log.e("result","---:"+"WeiFang_"+MyApplication.loginType+"_"+id);
            getCreditCard(MyApplication.loginType,id);
        }else if(isShow == 1){//好友 没有添加
            mAdd.setVisibility(View.GONE);
//            mAdd.setOnClickListener(this);
//            mAdd.setText("添加");
            if (MyApplication.loginType==1){
                id = getIntent().getStringExtra("personID");
            }else{
                id = getIntent().getStringExtra("companyID");
            }
            CreateQRUitl.createQRImage(mQR, "WeiFang_"+MyApplication.loginType+"_"+id);
            getCreditCard(MyApplication.loginType,id);
        } else{//我的信用名片
            mAdd.setVisibility(View.GONE);
            String result = "WeiFang_"+MyApplication.loginType+"_"+MyApplication.loginModel.getUserID();
            CreateQRUitl.createQRImage(mQR, result);
            mName.setText(MyApplication.loginModel.getXm());
            if(TextUtils.isEmpty(MyApplication.loginModel.getCompany())){
                MyApplication.loginModel.setCompany("无");
            }
            mCompany.setText(MyApplication.loginModel.getCompany());
            if(TextUtils.isEmpty(MyApplication.loginModel.getPhone())){
                MyApplication.loginModel.setPhone("无");
            }
            mPhone.setText(MyApplication.loginModel.getPhone());
            if(TextUtils.isEmpty(MyApplication.loginModel.getEmail())){
                MyApplication.loginModel.setEmail("无");
            }
            mEmail.setText(MyApplication.loginModel.getEmail());
        }

    }

    @Override
    public void onClick(View v) {
        if (v == mBack){
            mContext.finish();
        }
        if (v == mAdd){
            if (!TextUtils.isEmpty(id)){
                if (MyApplication.loginModel.getUserID().equals(id)){
                    ToastUtil.showBottomShort(mContext,"不能自己添加自己为好友");
                }else{
                    getAddCard(id);
                }

            }
        }
    }
}
