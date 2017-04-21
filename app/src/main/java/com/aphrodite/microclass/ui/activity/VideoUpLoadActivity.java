package com.aphrodite.microclass.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.aphrodite.microclass.R;
import com.aphrodite.microclass.base.BaseActivity;
import com.aphrodite.microclass.net.RetrofitMethod;
import com.aphrodite.microclass.net.RetrofitService;
import com.aphrodite.microclass.ui.model.BaseResponse;
import com.aphrodite.microclass.util.AppManager;
import com.aphrodite.microclass.util.CommonFunction;
import com.aphrodite.microclass.widget.AutoButton;
import com.jmolsmobile.landscapevideocapture.VideoCaptureActivity;
import com.jmolsmobile.landscapevideocapture.configuration.CaptureConfiguration;
import com.jmolsmobile.landscapevideocapture.configuration.PredefinedCaptureConfigurations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;

/**
 * Created by pc on 2017/4/21.
 */

public class VideoUpLoadActivity extends BaseActivity {
    @BindView(R.id.iv_thumbnail)
    ImageView iv_thumbnail;
    @BindView(R.id.title_video)
    EditText title_video;
    @BindView(R.id.btn_capturevideo)
    AutoButton btn_capturevideo;
    @BindView(R.id.btn_uploadvideo)
    AutoButton btn_uploadvideo;


    String videoPath;
    String bitMapPath;

    @Override
    public int getContentView() {
        return R.layout.upload_video_activity;
    }

    @Override
    protected void initViews() {
        btn_uploadvideo.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        btn_capturevideo.setOnClickListener(this);
        btn_uploadvideo.setOnClickListener(this);
        iv_thumbnail.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_capturevideo://拍照
                cameraVideo();
                break;
            case R.id.iv_thumbnail://拍照
                cameraVideo();

                break;
            case R.id.btn_uploadvideo://上传文件
                if (TextUtils.isEmpty(videoPath) && TextUtils.isEmpty(bitMapPath)) {
                    showToast("请先拍摄视频");
                    return;

                }
                String title = title_video.getText().toString();
                if (TextUtils.isEmpty(title)) {
                    showToast("为什么不设置一个视频标题呢。。。。");
                    return;

                }
                UpLoadFile(videoPath, bitMapPath, title);
                break;
        }
    }

    private void cameraVideo() {
        final CaptureConfiguration config = createCaptureConfiguration();
        final Intent intent = new Intent(VideoUpLoadActivity.this, VideoCaptureActivity.class);
        intent.putExtra(VideoCaptureActivity.EXTRA_CAPTURE_CONFIGURATION, config);
        intent.putExtra(VideoCaptureActivity.EXTRA_OUTPUT_FILENAME, System.currentTimeMillis() + "Mircrovideo.mp4");
        startActivityForResult(intent, 101);
    }

    private CaptureConfiguration createCaptureConfiguration() {
        final PredefinedCaptureConfigurations.CaptureResolution resolution = PredefinedCaptureConfigurations.CaptureResolution.RES_1080P;
        final PredefinedCaptureConfigurations.CaptureQuality quality = PredefinedCaptureConfigurations.CaptureQuality.HIGH;

        CaptureConfiguration.Builder builder = new CaptureConfiguration.Builder(resolution, quality);

        return builder.build();
    }

    private Bitmap getThumbnail(String filename) {
        if (filename == null) return null;
        return ThumbnailUtils.createVideoThumbnail(filename, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
    }

    /**
     * 获取视频缩略图*路径
     */
    public String saveVideoBitmap(Bitmap mBitmap) throws IOException {
        File file = new File("/sdcard/Note/");
        if (!file.exists()) {
            file.mkdir();
        }
        File fileName = new File(file, System.currentTimeMillis() + ".png");
        if (fileName.exists()) {
            fileName.delete();
        }
        fileName.createNewFile();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName.getPath();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 101) {
                if (null != data) {
                    videoPath = data.getStringExtra(VideoCaptureActivity.EXTRA_OUTPUT_FILENAME);
                    showToast(videoPath);
                    final Bitmap thumbnail = getThumbnail(videoPath);
                    try {
                        bitMapPath = saveVideoBitmap(thumbnail);
                        showToast(videoPath + "  ===" + bitMapPath);
                        if (!TextUtils.isEmpty(videoPath) && !TextUtils.isEmpty(bitMapPath)) {
                            iv_thumbnail.setImageBitmap(thumbnail);
                            btn_uploadvideo.setVisibility(View.VISIBLE);

                        } else {
                            btn_uploadvideo.setVisibility(View.GONE);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        btn_uploadvideo.setVisibility(View.GONE);
                    }
                    Log.e("data", videoPath);
//
                }

            }

        } else if (resultCode == Activity.RESULT_CANCELED) {
            btn_uploadvideo.setVisibility(View.GONE);
        } else if (resultCode == VideoCaptureActivity.RESULT_ERROR) {
            btn_uploadvideo.setVisibility(View.GONE);
        }
    }

    private void UpLoadFile(String path, String imgPatch, String title) {
        CommonFunction.progressDialogShow(this, "正在上传");
        RetrofitMethod.upVideoPatch(path, imgPatch, title, new RetrofitService.OnResponeListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse respone) {
                CommonFunction.progressDialogDismiss();
                if (null != respone) {
                    showToast("上传成功");
                    AppManager.getAppManager().finishActivity(VideoUpLoadActivity.this);
                } else {
                    showToast("空");
                }

            }

            @Override
            public void onFailure(String value) {
                showToast(value);
                CommonFunction.progressDialogDismiss();
            }
        });

    }
}
