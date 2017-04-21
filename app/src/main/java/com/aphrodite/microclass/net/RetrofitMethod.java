package com.aphrodite.microclass.net;

import android.util.Log;

import com.aphrodite.microclass.ui.model.BaseResponse;
import com.aphrodite.microclass.ui.model.FunnyXunLeiResponse;
import com.aphrodite.microclass.ui.model.VideoResponse;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        retrofitService.retrofitPost(RetrofitAPIManager.provideClientApi("2").uploadPic(description, body), listener);


    }

    /**
     * 上传相关文件
     *
     * @param listener
     */
    public static void upVideoPatch(String filePath, String videoImag, String title,
                                    RetrofitService.OnResponeListener<BaseResponse> listener) {
        RequestBody requestFile;
        List<MultipartBody.Part> body = new ArrayList<>();
        File fileVideo = new File(filePath);
        File fileimg = new File(videoImag);
        List<File> fileList = new ArrayList<>();
        fileList.add(fileVideo);
        fileList.add(fileimg);
        for (File file : fileList) {
            requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body.add(MultipartBody.Part.createFormData("file", file.getName(), requestFile));
        }
        Map<String, String> map = new HashMap<>();
        map.put("token", "ew");
        map.put("videoTitle", title);
        JSONObject object = new JSONObject(map);
        String data = object.toString();
        Log.e("data", data);

        RetrofitService retrofitService = new RetrofitService(BaseResponse.class);
        retrofitService.retrofitPost(RetrofitAPIManager.provideClientApi("2").uploadVideo(data, body), listener);


    }

    /**
     * 获取迅雷视频列表
     *
     * @param listener
     */
    public static void getvideoInfo(RetrofitService.OnResponeListener<VideoResponse> listener) {
        Map<String, Object> map = new HashMap<>();
        map.put("token", "ew");
        map.put("checkId", "swd");
        JSONObject object = new JSONObject(map);
        String data = object.toString();
        RetrofitService retrofitService = new RetrofitService(VideoResponse.class);
        retrofitService.retrofitPost(RetrofitAPIManager.provideClientApi("2").getvideoInfo(data), listener);
    }
}
