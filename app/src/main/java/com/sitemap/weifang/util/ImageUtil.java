package com.sitemap.weifang.util;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Base64;
import android.util.Log;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * com.sitemap.stm.image
 * 
 * @author chenmeng
 * @Description 图片的处理 圆角图片 圆形图片 缩放图片 裁剪图片 旋转图片
 *              图片格式转换（如Bitmap、Drawable、bytes之间的转换）
 * @date create at 2016年8月24日 下午5:17:03
 */
public class ImageUtil {
	public final static String TAG = "ImageUtil";
	/** 图片最大宽度. */
	public static final int MAX_WIDTH = 4096 / 2;

	/** 图片最大高度. */
	public static final int MAX_HEIGHT = 4096 / 2;

	/**
	 * 圆角图片
	 * 
	 * @param bitmap
	 * @param pixels
	 * @return
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		int color = 0xff424242;
		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		RectF rectF = new RectF(rect);
		float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
		}
		return output;
	}

	/**
	 * 圆形图片
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		if (bitmap == null) {
			return null;
		}
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}
		Bitmap output = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
		return output;
	}

	/**
	 * 缩放图片
	 * 
	 * @param bm
	 *            要缩放图片
	 * @param newWidth
	 *            宽度
	 * @param newHeight
	 *            高度
	 * @return处理后的图片
	 */
	public static Bitmap scaleImage(Bitmap bm, int newWidth, int newHeight) {
		if (bm == null) {
			return null;
		}
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
				true);
		if (bm != null & !bm.isRecycled()) {
			bm.recycle();// 销毁原图片
			bm = null;
		}
		return newbm;
	}

	/**
	 * 旋转图片
	 * 
	 * @param angle
	 *            旋转角度
	 * @param bitmap
	 *            要处理的Bitmap
	 * @return 处理后的Bitmap
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		if (resizedBitmap != bitmap && bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
		return resizedBitmap;
	}

	/**
	 * 描述：裁剪图片.
	 * 
	 * @param file
	 *            File对象
	 * @param desiredWidth
	 *            新图片的宽
	 * @param desiredHeight
	 *            新图片的高
	 * @return Bitmap 新图片
	 */
	public static Bitmap getCutBitmap(File file, int desiredWidth,
			int desiredHeight) {

		Bitmap resizeBmp = null;

		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为true,decodeFile先不创建内存 只获取一些解码边界信息即图片大小信息
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(file.getPath(), opts);

		// 获取图片的原始宽度
		int srcWidth = opts.outWidth;
		// 获取图片原始高度
		int srcHeight = opts.outHeight;

		int[] size = resizeToMaxSize(srcWidth, srcHeight, desiredWidth,
				desiredHeight);
		desiredWidth = size[0];
		desiredHeight = size[1];

		// 默认为ARGB_8888.
		opts.inPreferredConfig = Bitmap.Config.RGB_565;
		// 以下两个字段需一起使用：
		// 产生的位图将得到像素空间，如果系统gc，那么将被清空。当像素再次被访问，如果Bitmap已经decode，那么将被自动重新解码
		opts.inPurgeable = true;
		// 位图可以共享一个参考输入数据(inputstream、阵列等)
		opts.inInputShareable = true;
		// 缩放的比例，缩放是很难按准备的比例进行缩放的，通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
		int sampleSize = findBestSampleSize(srcWidth, srcHeight, desiredWidth,
				desiredHeight);
		opts.inSampleSize = sampleSize;
		// 创建内存
		opts.inJustDecodeBounds = false;
		// 使图片不抖动
		opts.inDither = false;
		resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);

		if (resizeBmp != null) {
			resizeBmp = getCutBitmap(resizeBmp, desiredWidth, desiredHeight);
		}
		return resizeBmp;
	}

	/**
	 * 描述：裁剪图片.
	 * 
	 * @param context
	 *            本类
	 * @param resId
	 *            本地图片id
	 * @param desiredWidth
	 *            图片的宽
	 * @param desiredHeight
	 *            图片的高
	 * @return
	 */
	public static Bitmap getCutBitmap(Context context, int resId,
			int desiredWidth, int desiredHeight) {

		Bitmap resizeBmp = null;

		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为true,decodeFile先不创建内存 只获取一些解码边界信息即图片大小信息
		opts.inJustDecodeBounds = true;

		BitmapFactory.decodeResource(context.getResources(), resId, opts);
		// BitmapFactory.decodeFile(file.getPath(), opts);

		// 获取图片的原始宽度
		int srcWidth = opts.outWidth;
		// 获取图片原始高度
		int srcHeight = opts.outHeight;

		int[] size = resizeToMaxSize(srcWidth, srcHeight, desiredWidth,
				desiredHeight);
		desiredWidth = size[0];
		desiredHeight = size[1];

		// 默认为ARGB_8888.
		opts.inPreferredConfig = Bitmap.Config.RGB_565;
		// 以下两个字段需一起使用：
		// 产生的位图将得到像素空间，如果系统gc，那么将被清空。当像素再次被访问，如果Bitmap已经decode，那么将被自动重新解码
		opts.inPurgeable = true;
		// 位图可以共享一个参考输入数据(inputstream、阵列等)
		opts.inInputShareable = true;
		// 缩放的比例，缩放是很难按准备的比例进行缩放的，通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
		int sampleSize = findBestSampleSize(srcWidth, srcHeight, desiredWidth,
				desiredHeight);
		opts.inSampleSize = sampleSize;
		// 创建内存
		opts.inJustDecodeBounds = false;
		// 使图片不抖动
		opts.inDither = false;
		resizeBmp = BitmapFactory.decodeResource(context.getResources(), resId,
				opts);

		if (resizeBmp != null) {
			resizeBmp = getCutBitmap(resizeBmp, desiredWidth, desiredHeight);
		}
		return resizeBmp;
	}

	/**
	 * 描述：裁剪图片.
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param desiredWidth
	 *            新图片的宽
	 * @param desiredHeight
	 *            新图片的高
	 */
	public static Bitmap getCutBitmap(Bitmap bitmap, int desiredWidth,
			int desiredHeight) {

		if (!checkBitmap(bitmap)) {
			return null;
		}

		if (!checkSize(desiredWidth, desiredHeight)) {
			return null;
		}

		Bitmap resizeBmp = null;

		try {
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();

			int offsetX = 0;
			int offsetY = 0;

			if (width > desiredWidth) {
				offsetX = (width - desiredWidth) / 2;
			} else {
				desiredWidth = width;
			}

			if (height > desiredHeight) {
				offsetY = (height - desiredHeight) / 2;
			} else {
				desiredHeight = height;
			}

			resizeBmp = Bitmap.createBitmap(bitmap, offsetX, offsetY,
					desiredWidth, desiredHeight);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (resizeBmp != bitmap) {
				bitmap.recycle();
			}
		}
		return resizeBmp;
	}

	/**
	 * 找到最合适的SampleSize
	 * 
	 * @param width
	 * @param height
	 * @param desiredWidth
	 * @param desiredHeight
	 * @return
	 */
	private static int findBestSampleSize(int width, int height,
			int desiredWidth, int desiredHeight) {
		double wr = (double) width / desiredWidth;
		double hr = (double) height / desiredHeight;
		double ratio = Math.min(wr, hr);
		float n = 1.0f;
		while ((n * 2) <= ratio) {
			n *= 2;
		}
		return (int) n;
	}

	/**
	 * 获取大小
	 * 
	 * @param srcWidth
	 * @param srcHeight
	 * @param desiredWidth
	 * @param desiredHeight
	 * @return
	 */
	private static int[] resizeToMaxSize(int srcWidth, int srcHeight,
			int desiredWidth, int desiredHeight) {
		int[] size = new int[2];
		if (desiredWidth <= 0) {
			desiredWidth = srcWidth;
		}
		if (desiredHeight <= 0) {
			desiredHeight = srcHeight;
		}
		if (desiredWidth > MAX_WIDTH) {
			// 重新计算大小
			desiredWidth = MAX_WIDTH;
			float scaleWidth = (float) desiredWidth / srcWidth;
			desiredHeight = (int) (desiredHeight * scaleWidth);
		}

		if (desiredHeight > MAX_HEIGHT) {
			// 重新计算大小
			desiredHeight = MAX_HEIGHT;
			float scaleHeight = (float) desiredHeight / srcHeight;
			desiredWidth = (int) (desiredWidth * scaleHeight);
		}
		size[0] = desiredWidth;
		size[1] = desiredHeight;
		return size;
	}

	/**
	 * 检查图片
	 * 
	 * @param bitmap
	 * @return
	 */
	private static boolean checkBitmap(Bitmap bitmap) {
		if (bitmap == null) {
			Log.e(TAG, "原图Bitmap为空了");
			return false;
		}

		if (bitmap.getWidth() <= 0 || bitmap.getHeight() <= 0) {
			Log.e(TAG, "原图Bitmap大小为0");
			return false;
		}
		return true;
	}

	/**
	 * 检查图片大小
	 * 
	 * @param desiredWidth
	 * @param desiredHeight
	 * @return
	 */
	private static boolean checkSize(int desiredWidth, int desiredHeight) {
		if (desiredWidth <= 0 || desiredHeight <= 0) {
			Log.e(TAG, "请求Bitmap的宽高参数必须大于0");
			return false;
		}
		return true;
	}

	/**
	 * 图片的转换 将drawable转换成bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap changeDrawableToBitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * 图片的转换 将bitmap转换成byte
	 * 
	 * @param bm
	 * @return
	 */
	public static byte[] changeBitmapToByte(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 图片的转换 将bitmap转换成Drawable
	 * 
	 * @param context
	 * @param bm
	 * @return
	 */
	public static Drawable changeBitmapToDrawable(Context context, Bitmap bm) {
		return bm == null ? null : new BitmapDrawable(context.getResources(),
				bm);
	}

	/**
	 * 图片的转换 将byte转换成bitmap
	 * 
	 * @param b
	 * @return
	 */
	public static Bitmap changeByteToBitmap(byte[] b) {
		return (b == null || b.length == 0) ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
	}
	
	
	/**
     * 把bitmap转换成Base64编码String
     */
    public static String bitmapToString(Bitmap bitmap) {
        return Base64.encodeToString(changeBitmapToByte(bitmap), Base64.DEFAULT);
    }
}
