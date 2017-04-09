package com.aphrodite.microclass.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;

import com.aphrodite.microclass.constant.Config;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.HttpRetryException;
import java.net.SocketTimeoutException;
import java.net.UnknownServiceException;

/**
 * Created by pc on 2017/4/9.
 */

public class NetUtil {
    /**
     * 判断网络连接是否打开,包括移动数据连接
     *
     * @param context 上下文
     * @return 是否联网
     */
    public static boolean isNetworkAvailable(Context context) {
        boolean netstate = false;
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        netstate = true;
                        break;
                    }
                }
            }
        }
        return netstate;
    }



    /**
     * 根据网络请求返回的错误返回提示不同
     */
    public static String onErrorMessage(Throwable e) {
        String errorMessage;

        if (e instanceof HttpRetryException) {             //HTTP错误
            HttpRetryException httpException = (HttpRetryException) e;
            switch (httpException.responseCode()) {
                case Config.UNAUTHORIZED:
                case Config.FORBIDDEN:
                    errorMessage = "权限错误";        //权限错误，需要实现
                    break;
                case Config.NOT_FOUND:
                    errorMessage = "服务器请求失败";
                    break;
                case Config.REQUEST_TIMEOUT:

                case Config.GATEWAY_TIMEOUT:
                    errorMessage = "网关超时";
                    break;
                case Config.INTERNAL_SERVER_ERROR:
                    errorMessage = "内部服务器错误";
                    break;
                case Config.BAD_GATEWAY:
                    errorMessage = "错误的网关";
                    break;
                case Config.SERVICE_UNAVAILABLE:
                default:
                    errorMessage = "请检查网络";
                    break;
            }
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            errorMessage = "解析出错";
        } else if (e instanceof SocketTimeoutException) {
            errorMessage = "网络请求超时，请检查网络";
        } else if (e instanceof UnknownServiceException) {
            errorMessage = "服务异常";
        } else if (e instanceof ConnectException) {
            errorMessage = "网络链接失败";
        } else {
            errorMessage = "获取失败";
        }
        return errorMessage;
    }
}
