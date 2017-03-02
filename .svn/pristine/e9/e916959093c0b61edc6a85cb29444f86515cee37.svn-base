package com.sitemap.weifang.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.sitemap.weifang.R;
import com.sitemap.weifang.util.ImageUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * @desc 导航页面
 * @author created by chenmeng on 2017/1/13
 */
@ContentView(R.layout.activity_splash)
public class SplashActivity extends BaseActivity implements Runnable {
    private SplashActivity mContext;
    @ViewInject(R.id.splash_img)
    private ImageView iv;//图片
    private Bitmap bt = null;//加载图片类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        WindowManager wm = this.getWindowManager();
        if (bt == null)
            bt = BitmapFactory.decodeResource(getResources(),R.drawable.splash_bg);
        iv.setImageBitmap(bt);
        handler.postDelayed(this, 3000);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Intent intent = new Intent(mContext,MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bt != null) {
            //释放图片内存
            bt.recycle();
            bt = null;
        }
        //强制回收
        System.gc();
    }

    @Override
    public void run() {
        handler.sendEmptyMessage(1);
    }

    /**
     * 改写物理按键——返回的逻辑
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            handler.removeCallbacks(this);
            mContext.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
