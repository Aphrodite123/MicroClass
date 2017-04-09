package com.aphrodite.microclass.net;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pc on 2017/4/9.
 */

public class RetrofitAPIManager {
    /**
     * 迅雷URl
     */
    public static String BASE_URL_XUNLEI = "http://api-shoulei-ssl.xunlei.com";
    public static String BAE_URL_NEW="http://192.168.0.109:8080";

    public static final int DEFAULT_TIMEOUT = 5;

    public static RetrofitAPI provideClientApi(String flag) {
        String BASE_URL="baidu";
        if("1".equals(flag)){
            BASE_URL=BASE_URL_XUNLEI ;
        }else{
            BASE_URL=BAE_URL_NEW;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(genericClient())
                .build();
        return retrofit.create(RetrofitAPI.class);
    }

    public static OkHttpClient genericClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                .addHeader("Accept-Encoding", "*")
                                .addHeader("Connection", "keep-alive")
                                .addHeader("Accept", "*/*")
                                .build();
                        return chain.proceed(request);
                    }
                }).connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        return httpClient;
    }
}
