package com.sitemap.weifang.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sitemap.weifang.R;
import com.sitemap.weifang.http.HttpUtil;
import com.sitemap.weifang.util.DialogUtil;
import com.sitemap.weifang.util.ImageUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * @desc 关于页面
 * @author chenmeng created by 2016/12/15
 */
@ContentView(R.layout.activity_about)
public class AboutActivity extends BaseActivity implements View.OnClickListener{
    private AboutActivity mContext;//本类
    @ViewInject(R.id.back)
    private LinearLayout mBack;//返回上一层
    @ViewInject(R.id.title)
    private TextView mTitle;//标题
    @ViewInject(R.id.about_img)
    private ImageView mAboutImg;//图片
    @ViewInject(R.id.about_version)
    private TextView mVersion;//版本
    @ViewInject(R.id.about_call_phone)
    private TextView mCall;//电话

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
        mCall.setOnClickListener(this);
        mTitle.setText("关于");
        Bitmap bt = BitmapFactory.decodeResource(getResources(),
                R.drawable.about_bg);
//        Bitmap img = ImageUtil.toRoundCorner(bt, 100);
        mAboutImg.setImageBitmap(bt);
        try {
            mVersion.setText("V"+ getVersionName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if(v == mBack){
            mContext.finish();
        }
        if (v == mCall){
            View dialog = DialogUtil.customPromptDialog(mContext, "确定", "取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.DIAL");
                            intent.setData(Uri.parse("tel:"+ "13564719491"));
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    }, null);
            TextView name = (TextView) dialog.findViewById(R.id.dialog_tv_txt);
            name.setText("你确定要向客服拨打电话吗?");
        }
    }

    /**
     * 获取当前应用的版本号：
     * @return
     * @throws Exception
     */
    private String getVersionName() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
        String version = packInfo.versionName;
        return version;
    }
}
