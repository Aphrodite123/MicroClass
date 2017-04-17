package com.aphrodite.microclass.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.aphrodite.microclass.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by pc on 2017/4/9.
 */

public class CommonFunction {

    public static void redirectActivity(Context fromClass, Class<?> toClass, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(fromClass, toClass);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        fromClass.startActivity(intent);

    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static void showToast(Context context, String contentText) {
        Toast.makeText(context, contentText, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param id  String 文件下面的id
     * @param str 替换的字符串
     * @return
     */
    public static String getResStringFormat(Context mContext, int id, String str) {
        String restr = mContext.getResources().getString(id);
        String replaceStr = String.format(restr, str);
        return replaceStr;
    }

    /**
     * 获取资源文件字符串
     *
     * @param mContext
     * @param id
     * @return
     */
    public static String getResString(Context mContext, int id) {
        return mContext.getResources().getString(id);
    }

    public static ProgressDialog progressDialog;


    /**
     * 显示加载框
     */
    public static void progressDialogShow(Context context, String message) {
        if (context instanceof Activity) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(context);
            }
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

    }

    /**
     * 隐藏加载框
     */
    public static void progressDialogDismiss() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public static void ImagLoadPic(Context context, Uri uri, ImageView imageView) {

        Picasso.with(context)
                .load(uri)
                .placeholder(context.getResources().getDrawable(R.drawable.image_placeholder))
                .error(context.getResources().getDrawable(R.drawable.image_placeholder))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(imageView);
    }

    public static void ImagLoadPic(Context context, String path, ImageView imageView) {

        Picasso.with(context)
            .load(path)
            .into(imageView);
    }

    public static void ImagLoadPic(Context context, File file, ImageView imageView) {
        Picasso.with(context)
                .load(file)
                .placeholder(context.getResources().getDrawable(R.drawable.image_placeholder))
                .error(context.getResources().getDrawable(R.drawable.image_placeholder))
                .resize(50, 50)
                .centerInside()
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(imageView);

    }

    /**
     * 获取视频缩略图*
     */
    public static Bitmap getVideoImg(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            try {
                // retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }


        return bitmap;
    }


}
