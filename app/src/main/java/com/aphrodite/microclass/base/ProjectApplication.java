package com.aphrodite.microclass.base;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aphrodite.microclass.R;
import com.aphrodite.microclass.util.MircroClassException;

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


    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = getApplicationContext();
        MircroClassException.getInstance().init(mInstance);

    }






    public static ProjectApplication getApplication() {

        return mInstance;
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
