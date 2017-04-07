package com.aphrodite.microclass.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by smy on 2017/4/7 0007.
 */

public class MircroClassException extends Exception implements Thread.UncaughtExceptionHandler {
    public static final String TAG = "MircroClassException";

    //程序的Context对象
    private Context mContext;

    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler实例
    private static MircroClassException INSTANCE = new MircroClassException();


    /**
     * 保证只有一个CrashHandler实例
     */
    private MircroClassException() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static MircroClassException getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        this.mContext = context;

        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // 打印错误日志
        Log.e(TAG, Log.getStackTraceString(ex));

//        mDefaultHandler.uncaughtException(thread, ex);
        boolean isHandle = handleException(thread, ex);

        // 如果异常处理失败，则使用系统默认的handler进行处理
        if (!isHandle && mDefaultHandler != null) {
            // 如果错误日志处理失败，则让系统默认的异常处理器来处理
//

            showCrashDialog(thread, ex);
        }
    }

    /**
     * 自定义异常处理:收集错误信息&发送错误报告
     *
     * @param ex
     * @return true:处理了该异常信息;否则返回false
     */
    private boolean handleException(final Thread thread, final Throwable ex) {
        if (ex == null || mContext == null) {
            return false;
        }

        boolean success = true;
        try {
            // 保存到sd卡
            success = saveToSDCard(ex);
        } catch (Exception e) {
        } finally {
            if (!success) {
                return false;
            } else {
                showCrashDialog(thread, ex);
            }
        }
        return true;
    }

    /**
     * 显示异常提示进行关闭的dialog
     */
    private void showCrashDialog(final Thread thread, final Throwable ex) {


        // 显示异常信息&发送报告
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                // 拿到未捕获的异常，

                showTipToExceptionDialog(AppManager.getAppManager().currentActivity(), thread, ex);
                Looper.loop();
            }
        }.start();
    }

    private boolean saveToSDCard(Throwable ex)
            throws Exception {
        boolean append = false;
        File file = getSaveFile("MircroClassException.LOG");
        if (System.currentTimeMillis() - file.lastModified() > 5000) {
            append = true;
        }
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, append)));
        // 导出发生异常的时间
        pw.println(getDataTime("yyyy-MM-dd-HH-mm-ss"));
        // 导出手机信息
        dumpPhoneInfo(pw);
        pw.println();
        // 导出异常的调用栈信息
        ex.printStackTrace(pw);
        pw.println();
        pw.close();
        return append;
    }

    private void dumpPhoneInfo(PrintWriter pw)
            throws PackageManager.NameNotFoundException {
        // 应用的版本名称和版本号
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.print("App Version: ");
        pw.print(pi.versionName);
        pw.print('_');
        pw.println(pi.versionCode);
        pw.println();

        // android版本号
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);
        pw.println();

        // 手机制造商
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);
        pw.println();

        // 手机型号
        pw.print("Model: ");
        pw.println(Build.MODEL);
        pw.println();

        // cpu架构
        pw.print("CPU ABI: ");
        pw.println(Build.CPU_ABI);
        pw.println();
    }

    /**
     * 指定格式返回当前系统时间
     */
    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    private File getSaveFile(String fileNmae)

    {
        File file = new File(getSaveFolder() + File.separator + fileNmae);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 获取文件夹对象
     *
     * @return 返回SD卡下的指定文件夹对象，若文件夹不存在则创建
     */
    public static String getSaveFolder() {
        File dirFile = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "MircroClass");
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        return dirFile.getAbsolutePath();
    }

    //提示登录对话框
    public void showTipToExceptionDialog(final Context mContext, Thread thread, Throwable ex) {
        if (mContext instanceof Activity) {


            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);  //先得到构造器
            builder.setTitle("友情提示"); //设置标题
            builder.setMessage("程序出错"); //设置内容

            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AppManager.getAppManager().finishAllActivity();
//                    AppManager.getAppManager().finishActivity();
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(-1);
                    dialog.dismiss(); //关闭dialog

                }
            });
            builder.show();
        } else {
            mDefaultHandler.uncaughtException(thread, ex);
        }

    }
}
