package com.aphrodite.microclass.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by smy on 2017/4/13 0013.
 */

public class FileUtils {

    public static String getSDPath() {
        // 判断sdcard是否存在
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            // 获取根目录
            File sdDir = Environment.getExternalStorageDirectory();
            return sdDir.getPath();
        }
        return "/sdcard";
    }
}
