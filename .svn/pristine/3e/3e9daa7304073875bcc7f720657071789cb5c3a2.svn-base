package com.sitemap.weifang.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.sitemap.weifang.R;
import com.sitemap.weifang.application.MyApplication;
import com.sitemap.weifang.config.RequestCode;
import com.sitemap.weifang.config.WebUrlConfig;
import com.sitemap.weifang.http.HttpUtil;
import com.sitemap.weifang.model.RegisterModel;
import com.sitemap.weifang.model.ResultModel;
import com.sitemap.weifang.util.RegularUtil;
import com.sitemap.weifang.util.ToastUtil;
import com.sitemap.weifang.view.MyProgressDialog;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 注册页面
 */
@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private RegisterActivity mContext;//本类
    @ViewInject(R.id.back)
    private LinearLayout mBack;//返回上一层
    @ViewInject(R.id.title)
    private TextView mTitle;//标题
    @ViewInject(R.id.person)
    private LinearLayout mPerson;//个人
    @ViewInject(R.id.company)
    private LinearLayout mCompany;//企业
    @ViewInject(R.id.register_submit)
    private TextView mSubmit;//下一步
    @ViewInject(R.id.register_code)
    private EditText mCode;//帐号
    @ViewInject(R.id.register_psw)
    private EditText mPsw;//密码
    @ViewInject(R.id.register_psw_sure)
    private EditText mPswSure;//确认密码
    @ViewInject(R.id.isShow)
    private LinearLayout mShow;// 布局是否显示
    @ViewInject(R.id.register_name)
    private EditText mName;//用户名称
    private TranslateAnimation mShowAction;//动画
    private boolean isShowAnim;//是否还显示动画样式
    private int type = 1;//是个人注册 还是企业注册 1 为个人  2为企业
    private MyProgressDialog progressDialog;//进度条
    private HttpUtil http;//网络请求
    private boolean isPassRegisterOne = false;//是否通过了注册一
    private String codeID = "";//code的id
    @ViewInject(R.id.register_tel)
    private EditText mTel;//手机号码
    @ViewInject(R.id.register_email)
    private EditText mEmail;//邮箱

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        init();
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
                    //注册一
                    if (msg.arg1 == RequestCode.REGISTERONE){
                        RegisterModel model = JSONObject.parseObject(msg.obj.toString(), RegisterModel.class);
                        if (TextUtils.isEmpty(model.getCodeID())||TextUtils.isEmpty(model.getXm())){
                            isPassRegisterOne = false;
                            ToastUtil.showBottomShort(mContext,model.getErrorMsg());
                        }else{
                            isPassRegisterOne = true;
                            codeID = model.getCodeID();
                            if (!isShowAnim){
                                mShow.setVisibility(View.VISIBLE);
                                mShow.startAnimation(mShowAction);
                                isShowAnim = true;
                            }
                            mSubmit.setText("提交");
                            mCode.setClickable(false);
                            mCode.setFocusable(false);
                            mPerson.setClickable(false);
                            mCompany.setClickable(false);
                            mName.setFocusable(true);
                        }
                    }
                    //注册2
                    if (msg.arg1 == RequestCode.REGISTERTWO){
                        ResultModel model = JSONObject.parseObject(msg.obj.toString(),ResultModel.class);
                        if (model.getResult().equals("1")){//注册成功
                            ToastUtil.showBottomShort(mContext,"注册成功");
                            finish();
                        }else{
                            ToastUtil.showBottomShort(mContext,model.getErrorMsg());
                        }
                    }
                    break;
                case HttpUtil.EMPTY:
                    //注册一
                    if (msg.arg1 == RequestCode.REGISTERONE){
                        isPassRegisterOne = false;
                        ToastUtil.showBottomShort(mContext,"无法注册该app");
                    }
                    //注册2
                    if (msg.arg1 == RequestCode.REGISTERTWO){
                        ToastUtil.showBottomShort(mContext,"注册异常");
                    }
                    break;
                case HttpUtil.FAILURE:
                    ToastUtil.showBottomShort(mContext,RequestCode.ERRORINFO);
                    break;
                case HttpUtil.LOADING:
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 注册一
     */
    private void getRegisterOne(String type,String code){
        if (MyApplication.getNetObject().isNetConnected()){
            progressDialog = MyProgressDialog.createDialog(mContext);
            if (progressDialog!=null&&!progressDialog.isShowing()){
                progressDialog.setMessage("加载中...");
                progressDialog.show();
            }
            String url = WebUrlConfig.getRegisterFirst();
            RequestParams params = http.getParams(url);
            params.addBodyParameter("code",code);
            params.addBodyParameter("type",type);
            http.sendPost(RequestCode.REGISTERONE,params);
        }else{
            ToastUtil.showBottomShort(mContext, RequestCode.NONETWORK);
        }
    }

    /**
     * 注册2
     */
    private void getRegisterTwo(String codeID,String userName,String psw,String type,String phone,String email){
        if (MyApplication.getNetObject().isNetConnected()){
            progressDialog = MyProgressDialog.createDialog(mContext);
            if (progressDialog!=null&&!progressDialog.isShowing()){
                progressDialog.setMessage("加载中...");
                progressDialog.show();
            }
            String password = MyApplication.md5(psw);
            String url = WebUrlConfig.getRegisterSecond();
            RequestParams params = http.getParams(url);
            params.addBodyParameter("codeID",codeID);
            params.addBodyParameter("username",userName);
            params.addBodyParameter("phone",phone);
            params.addBodyParameter("email",email);
            params.addBodyParameter("password",password);
            params.addBodyParameter("type",type);
            Log.e("result",params.toString());
            http.sendPost(RequestCode.REGISTERTWO,params);
        }else{
            ToastUtil.showBottomShort(mContext, RequestCode.NONETWORK);
        }
    }

    /**
     * 初始化数据
     */
    private void init() {
        mTitle.setText("注册");
        mBack.setOnClickListener(this);
        mPerson.setOnClickListener(this);
        mPerson.setSelected(true);
        mCompany.setOnClickListener(this);
        mSubmit.setOnClickListener(this);

        if (http == null){
            http = new HttpUtil(handler);
        }

        //显示动画
        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(500);

    }

    @Override
    public void onClick(View v) {
        if (v == mBack) {
            finish();
        }
        if (v == mPerson) {
            type=1;
            mPerson.setSelected(true);
            mCompany.setSelected(false);
            mCode.setHint("请输入身份证号码");
            mName.setHint("请输入登录名(数字或字母组成)");
        }
        if (v == mCompany) {
            type=2;
            mPerson.setSelected(false);
            mCompany.setSelected(true);
            mCode.setHint("请输入组织机构代码");
            mName.setHint("请输入登录名(数字或字母组成)");
        }
        if (v == mSubmit) {
            if (TextUtils.isEmpty(mCode.getText().toString().trim())){
                ToastUtil.showBottomShort(mContext,"帐号不能为空");
                return;
            }
            if (type==1){
                if (!RegularUtil.isIdCard(mCode.getText().toString().trim())) {
                    ToastUtil.showBottomShort(mContext, "帐号不规范");
                    return;
                }
            }else{
                if (!RegularUtil.isInputCharOrNum(mCode.getText().toString())) {
                    ToastUtil.showBottomShort(mContext, "帐号不规范");
                    return;
                }
            }
            if (!isPassRegisterOne){
                //注册一
                getRegisterOne(String.valueOf(type),mCode.getText().toString().trim());
            }else{
                if (TextUtils.isEmpty(mName.getText().toString().trim())){
                    ToastUtil.showBottomShort(mContext,"登录名不能为空");
                    return;
                }
                if (!RegularUtil.isInputCharOrNum(mName.getText().toString())){
                    ToastUtil.showBottomShort(mContext,"登录名必须为数字或字母");
                    return;
                }
                if (TextUtils.isEmpty(mTel.getText().toString())){
                    ToastUtil.showBottomShort(mContext,"手机号码不能为空");
                    return;
                }
                if (!RegularUtil.isMobileNO(mTel.getText().toString())){
                    ToastUtil.showBottomShort(mContext,"手机号码输入不规范");
                    return;
                }
                if (TextUtils.isEmpty(mEmail.getText().toString())){
                    ToastUtil.showBottomShort(mContext,"邮箱不能为空");
                    return;
                }
                if (!RegularUtil.isEmail(mEmail.getText().toString())){
                    ToastUtil.showBottomShort(mContext,"邮箱输入不规范");
                    return;
                }
                if (TextUtils.isEmpty(mPsw.getText().toString())||TextUtils.isEmpty(mPswSure.getText().toString())){
                    ToastUtil.showBottomShort(mContext,"密码不能为空");
                    return;
                }
                if (!RegularUtil.isInputCharOrNum(mPsw.getText().toString()) || !RegularUtil.isInputCharOrNum(mPswSure.getText().toString())){
                    ToastUtil.showBottomShort(mContext,"密码输入不规范");
                    return;
                }
                if (!mPsw.getText().toString().equals(mPswSure.getText().toString())){
                    ToastUtil.showBottomShort(mContext,"两次密码不一致");
                    return;
                }
                getRegisterTwo(codeID,mName.getText().toString(),mPsw.getText().toString(),String.valueOf(type),mTel.getText().toString(),mEmail.getText().toString());
            }
        }
    }
}
