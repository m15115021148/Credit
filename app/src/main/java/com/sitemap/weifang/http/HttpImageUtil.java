package com.sitemap.weifang.http;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.sitemap.weifang.R;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * @desc 加载网络图片 使用xutil3加载
 * Created by chenmeng on 2016/10/9.
 */
public class HttpImageUtil {

    /**
     * 初始化图片配置属性
     * @return
     */
    private static ImageOptions getInstance(){
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setSize(-1, -1)//默认适应图片大小
//                .setRadius(DensityUtil.dip2px(5))
                        // 如果ImageView的大小不是定义为wrap_content, 不要crop.
//                .setCrop(true)
                        // 加载中或错误图片的ScaleType
                        //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                        //设置图片的质量
                .setConfig(Bitmap.Config.RGB_565)
                        //设置加载过程中的图片
                .setLoadingDrawableId(R.drawable.shibai)
                        //设置加载失败后的图片
                .setFailureDrawableId(R.drawable.shibai)
                        //设置使用缓存
                .setUseMemCache(true)
                        //设置支持gif
                .setIgnoreGif(false)
                        //设置显示圆形图片
//                .setCircular(true)
                .build();
        return imageOptions;
    }

    /**
     * 初始化图片配置属性
     * @return
     */
    private static ImageOptions getInstanceRound(){
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setSize(-1, -1)//默认适应图片大小
                .setRadius(DensityUtil.dip2px(5))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
//                .setCrop(true)
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                //设置图片的质量
                .setConfig(Bitmap.Config.RGB_565)
                //设置加载过程中的图片
                .setLoadingDrawableId(R.drawable.user_img)
                //设置加载失败后的图片
                .setFailureDrawableId(R.drawable.user_img)
                //设置使用缓存
                .setUseMemCache(true)
                //设置支持gif
                .setIgnoreGif(false)
                //设置显示圆形图片
                .setCircular(true)
                .build();
        return imageOptions;
    }



    /**
     * 加载图片
     * @param iv imageview
     * @param path 图片路径
     */
    public static void loadImage(ImageView iv, String path) {
        x.image().bind(iv,path,getInstance());
    }

    /**
     * 加载图片
     * @param iv imageview
     * @param path 图片路径
     */
    public static void loadImageGif(ImageView iv, String path) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                // 默认自动适应大小
                // .setSize(...)
                .setIgnoreGif(false)
                // 如果使用本地文件url, 添加这个设置可以在本地文件更新后刷新立即生效.
                //.setUseMemCache(false)
                .setImageScaleType(ImageView.ScaleType.CENTER).build();

        x.image().bind(iv,path,imageOptions);
    }

    /**
     * 加载图片
     * @param iv imageview
     * @param path 图片路径
     */
    public static void loadRoundImage(ImageView iv, String path) {
        x.image().bind(iv,path,getInstanceRound());
    }

    /**
     * 清除缓存
     */
    public static void clearCache(){
        x.image().clearMemCache();
    }
}
