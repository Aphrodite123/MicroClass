package com.aphrodite.microclass.constant;

/**
 * Created by pc on 2017/4/9.
 */

public class Config {
    //迅雷视频列表
    public static final String url="homepage/api/refreshpage?id=37913869004800&length=8&ts=1481010508&mobile_type=android&timestamp=1481010586&nonce=-55281470&accesskey=android.m.xunlei&sig=6SydLQTgkG1LhG5k85yIPu9TDV8=";
    /**
     * 相机选择
     */
    public static final int REQUEST_CODE_CAMERA = 1000;
    /**
     * 图库选择
     */
    public static final int REQUEST_CODE_GALLERY = 1001;

    //对应HTTP的状态码
    /**
     * (Unauthorized/未授权)
     */
    public static final int UNAUTHORIZED = 401;
    /**
     * (Forbidden/禁止)
     */
    public static final int FORBIDDEN = 403;
    /**
     * (Not Found/未找到)
     */
    public static final int NOT_FOUND = 404;
    /**
     * (Request Timeout/请求超时)
     */
    public static final int REQUEST_TIMEOUT = 408;
    /**
     * (Internal Server Error/内部服务器错误)
     */
    public static final int INTERNAL_SERVER_ERROR = 500;
    /**
     * (Bad Gateway/错误的网关)
     */
    public static final int BAD_GATEWAY = 502;
    /**
     * (Service Unavailable/服务无法获得)
     */
    public static final int SERVICE_UNAVAILABLE = 503;
    /**
     * (Gateway Timeout/网关超时)
     */
    public static final int GATEWAY_TIMEOUT = 504;
}
