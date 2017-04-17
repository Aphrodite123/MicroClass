package com.aphrodite.microclass.net;

import com.aphrodite.microclass.constant.Config;
import com.aphrodite.microclass.ui.model.BaseResponse;
import com.aphrodite.microclass.ui.model.FunnyXunLeiResponse;
import com.aphrodite.microclass.ui.model.VideoResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by smy on 2017/4/7 0007.
 */

public interface RetrofitAPI {
    @GET(Config.url)
    Call<FunnyXunLeiResponse> getVideo();
    /**
     * 车辆月度检查 ---新建上传相关图片
     */

    @Multipart
    @POST("/sma-upload/simpleFileupload")
    Call<BaseResponse> uploadPic( @Part("description") RequestBody description,
                                           @Part MultipartBody.Part file);

    @Multipart
    @POST("/sma-upload/simpleFileupload")
    Call<BaseResponse> uploadVideo(@Part List<MultipartBody.Part> files);

    /**
     * 根据ID获取报警信息
     */

    @FormUrlEncoded
    @POST("/sma-upload/getvideoInfo")
    Call<VideoResponse> getvideoInfo(@Field("data") String data);
}
