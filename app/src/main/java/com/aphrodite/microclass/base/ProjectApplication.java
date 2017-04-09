package com.aphrodite.microclass.base;

import android.app.Application;
import android.content.Context;
import android.support.v4.BuildConfig;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aphrodite.microclass.R;
import com.aphrodite.microclass.util.MircroClassException;
import com.aphrodite.microclass.util.PicassoImageLoader;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * Created by smy on 2017/4/7 0007.
 */

public class ProjectApplication extends Application {
    private static ProjectApplication mInstance;
    private static Context context;
    /**
     * 屏幕宽度
     */
    public static int screenWidth;

    /**
     * 屏幕高度
     */
    public static int screenHeight;
    private static Toast mToast;
    public FunctionConfig functionConfig;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = getApplicationContext();
        MircroClassException.getInstance().init(mInstance);

        //配置选择图册
        initGralleyFinal();


    }


    public static ProjectApplication getApplication() {

        return mInstance;
    }


    private void initGralleyFinal() {
        //配置主题
        //ThemeConfig.CYAN
        ThemeConfig theme;
        theme = ThemeConfig.DEFAULT;
        //配置功能
        functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)//开启相机功能
                .setEnableEdit(false)//开启编辑功能
                .setEnableCrop(false)//开启裁剪功能
                .setEnableRotate(false)//开启旋转功能
                .setCropSquare(false)//裁剪正方形
                .setEnablePreview(false)//是否开启预览功能
                .build();

        //配置imageloader
        //设置核心配置信息
        CoreConfig coreConfig = new CoreConfig.Builder(getApplicationContext(), new PicassoImageLoader(),theme)
                .setDebug(BuildConfig.DEBUG)
                .setFunctionConfig(functionConfig).setNoAnimcation(false)//特效
                .build();
        GalleryFinal.init(coreConfig);
    }



    /**
     * 计算屏幕尺寸
     */
    private void calcScreenSize() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    public static void showToast(String msg) {
        if (null == mToast) {
            View toastView = LayoutInflater.from(mInstance).inflate(R.layout.view_toast, null);
            mToast = new Toast(mInstance);
            mToast.setView(toastView);
            TextView toastContent = (TextView) toastView.findViewById(R.id.message);
            toastContent.setText(msg);
        } else {
            TextView toastContent = (TextView) (mToast.getView().findViewById(R.id.message));
            toastContent.setText(msg);
        }
        mToast.show();
    }
}
