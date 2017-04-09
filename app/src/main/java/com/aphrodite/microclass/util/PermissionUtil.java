package com.aphrodite.microclass.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by pc on 2017/4/9.
 */

public class PermissionUtil {
    public static boolean checkPermission(Activity activity, String permission, int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(activity,
                    permission)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(activity,
                        new String[]{permission},
                        requestCode);
                return false;
            } else {
                return true;
            }
        } else {
            String packageName = activity.getPackageName();
            PackageManager pm = activity.getPackageManager();
            boolean granted = (PackageManager.PERMISSION_GRANTED ==
                    pm.checkPermission(permission, packageName));
            if (granted) {
                return true;
            } else {
//                LiybApplacation.showToast("没有权限");
                return false;
            }
        }
    }
}
