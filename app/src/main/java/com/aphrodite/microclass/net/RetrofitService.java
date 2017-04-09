package com.aphrodite.microclass.net;

import android.content.Context;
import android.util.Log;

import com.aphrodite.microclass.base.ProjectApplication;
import com.aphrodite.microclass.util.CommonFunction;
import com.aphrodite.microclass.util.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created  on 2017/2/22 0022.
 *
 * @author liyb
 * @version 1.0.0
 */
public class RetrofitService<T> {
    private Class<T> clazz;

    public Context contxt;

    public RetrofitService(Class<T> clazz) {
        this.clazz = clazz;
    }

    public interface OnResponeListener<T> {
        /**
         * 获取到数据
         *
         * @param respone
         */
        void onSuccess(T respone);

        /**
         * 请求失败
         *
         * @param value
         */
        void onFailure(String value);



    }

    /**
     * get请求接口封装
     *
     * @param call
     * @param listener
     */
    public void retrofitGet(Call<T> call, final OnResponeListener listener) {
        if (NetUtil.isNetworkAvailable(ProjectApplication.getApplication())) {
            call.enqueue(new Callback<T>() {
                @Override
                public void onResponse(Call<T> call, Response<T> response) {
                    T t = response.body();
                    listener.onSuccess(t);
                }

                @Override
                public void onFailure(Call<T> call, Throwable t) {
                    listener.onFailure("onFail");
                }
            });
        } else {
            ProjectApplication.showToast("网络不可用");
//            NetUtil.setNetworkMethod(BusManagementApplication.getInstance());
        }

    }

    /**
     * post接口封装
     *
     * @param call
     * @param listener
     */
    public void retrofitPost(Call<T> call, final OnResponeListener listener) {
        if (NetUtil.isNetworkAvailable(ProjectApplication.getApplication())) {
            call.enqueue(new Callback<T>() {
                @Override
                public void onResponse(Call<T> call, Response<T> response) {
                    CommonFunction.progressDialogDismiss();
                    T t = response.body();
                    Log.e("data",response.toString()+"------");
                    if (null != t) {

                            listener.onSuccess(t);

                    } else {
                        listener.onFailure("获取数据失败");
                    }

                }

                @Override
                public void onFailure(Call<T> call, Throwable t) {
                    Log.e("data",t.toString()+"------");
                    CommonFunction.progressDialogDismiss();
                    if (null != t) {
                        listener.onFailure(NetUtil.onErrorMessage(t));

                    } else {
                        listener.onFailure("服务器或者网络出问题，请重试");
                    }
                }
            });
        } else

        {
            ProjectApplication.showToast("网络不可用");
//            NetUtil.setNetworkMethod(BusManagementApplication.getInstance());
        }
    }
}
