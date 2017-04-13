package com.aphrodite.microclass.net;

import com.aphrodite.microclass.ui.model.BaseResponse;
import com.aphrodite.microclass.ui.model.FunnyXunLeiResponse;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by pc on 2017/4/9.
 */

public class RetrofitMethod {
    /**
     * 获取迅雷视频列表
     *
     * @param listener
     */
    public static void getXunLeiVideo(RetrofitService.OnResponeListener<FunnyXunLeiResponse> listener) {
        RetrofitService retrofitService = new RetrofitService(FunnyXunLeiResponse.class);
        retrofitService.retrofitPost(RetrofitAPIManager.provideClientApi("1").getVideo(), listener);
//        OkHttpClientManager.get(url,callback);
    }

    /**
     * 上传相关文件
     *
     * @param listener
     */
    public static void uploadPic(String filePath,
                                           RetrofitService.OnResponeListener<BaseResponse> listener) {
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        String descriptionString = file.getName();
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
        RetrofitService retrofitService = new RetrofitService(BaseResponse.class);
        retrofitService.retrofitPost(RetrofitAPIManager.provideClientApi("2").uploadPic( description, body), listener);
    }
}
