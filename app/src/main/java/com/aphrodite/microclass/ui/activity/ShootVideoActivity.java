package com.aphrodite.microclass.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aphrodite.microclass.R;
import com.aphrodite.microclass.util.CommonFunction;
import com.aphrodite.microclass.util.FileUtils;
import com.aphrodite.microclass.widget.CameraPreview;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by smy on 2017/4/13 0013.
 */

public class ShootVideoActivity extends Activity implements View.OnClickListener {

    private ImageView videoPhoto;//录像截图

    private MediaRecorder mediaRecorder;//录像

    private Camera camera;//相机

    private CameraPreview preview;

    private Camera.Parameters cameraParams;

    private FrameLayout layout;

    private Button back; //取消

    private String path;//文件保存路劲

    private String fileName;//文件保存的名字

    private boolean isTaking;//

    private TextView timer;//计时

    private int hour = 0;//小时

    private int minute = 0;//分钟

    private int second = 0;//秒

    private int allTime = 0;//总时间多少秒

    private boolean bool;

    private ImageView videoPlay;//开始录制

    private Camera.AutoFocusCallback myAutoFocusCallback = null;

    private boolean isControlEnable = true;

    private ImageView exchangeIV;//切换录制视角

    private Button loadBtn;//上传图像

    private static int flag = 0;//按钮点击两次的状态

    private int cameraPosition = 1;//

    private String videoPath;//保存路径
    private String timeStr;//获取录制时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_shoot_video);

        myAutoFocusCallback = new Camera.AutoFocusCallback() {
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    camera.setOneShotPreviewCallback(null);
                } else {

                }
            }
        };
        //初始化控件
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        Log.e("dataerror","initView");
        videoPhoto = (ImageView) findViewById(R.id.shoot_video_thumbnail_iv);
        layout = (FrameLayout) findViewById(R.id.shoot_video_framelay);
        videoPlay = (ImageView) findViewById(R.id.pouse_or_stop_iv);
        timer = (TextView) findViewById(R.id.shoot_timer_tv);
        back = (Button) findViewById(R.id.shoot_video_cancel_btn);
        exchangeIV = (ImageView) findViewById(R.id.shoot_video_self_iv);
        loadBtn = (Button) findViewById(R.id.shoot_video_comfir_btn);
        loadBtn.setOnClickListener(this);
        exchangeIV.setOnClickListener(this);
        back.setOnClickListener(this);
        videoPlay.setOnClickListener(this);
        videoPhoto.setOnClickListener(this);
        initCamera();//初始化照相机
        path = FileUtils.getSDPath() + "";
        fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()) + ".mp4";
    }

    /**
     * 初始化照相机
     */
    private void initCamera() {
        camera = Camera.open();
        Log.e("dataerror","camera"+camera);
        preview = new CameraPreview(this, camera);
        preview.setFocusable(false);
        preview.setEnabled(false);

        cameraParams = camera.getParameters();
        if (Build.VERSION.SDK_INT >= 14) {
            cameraParams.setFocusMode("auto");
            Log.e("dataerror","cameraParams"+cameraParams);
//            camera.autoFocus(myAutoFocusCallback);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    camera.autoFocus(myAutoFocusCallback);
                }
            }, 2000);

        } else {
            cameraParams.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        }

//        cameraParams.setRotation(90);
        camera.setDisplayOrientation(90);
        camera.setParameters(cameraParams);
        try {
            camera.setPreviewDisplay(preview.getHolder());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        layout.addView(preview);

    }


    /**
     * 开始
     */
    private void startCamera() {

        second = 0;
        minute = 0;
        hour = 0;
        bool = true;
        camera.startPreview();
        camera.unlock();//解锁后才能调用，否则报错
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setCamera(camera);//将camera添加到视频录制端口
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 音麦
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);// 视频源

        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);//输出格式
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);// 声音源码
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);// 视频源码
        mediaRecorder.setVideoSize(640, 480);
        //mediaRecorder.setVideoFrameRate(15);
        //mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
        mediaRecorder.setVideoEncodingBitRate(500 * 1024);

        mediaRecorder.setPreviewDisplay(preview.getHolder().getSurface());
        File file = getSaveFile(System.currentTimeMillis()+"Mircrovideo.mp4");
        videoPath = file.getPath();//视频本地路径

        Log.e("dataerror","videoPath   "+videoPath);
        mediaRecorder.setOutputFile(videoPath);

        try {
            mediaRecorder.prepare();
            timer.setVisibility(View.VISIBLE);
            handler.postDelayed(task, 1000);//执行计时,每一秒进行一次
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaRecorder.start();
            Toast.makeText(this, "开始录制", Toast.LENGTH_SHORT).show();
        } catch (IllegalStateException e) {
            this.finish();
            Toast.makeText(this, "不能录制视频!", Toast.LENGTH_SHORT).show();
        }

    }

    /*
        * 定时器设置，实现计时
        */
    public Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            isControlEnable = true;
            return false;
        }
    });

    public Runnable task = new Runnable() {
        public void run() {
            if (bool) {
                handler.postDelayed(this, 1000);
                allTime++;
                second++;
                if (second >= 60) {
                    minute++;
                    second = second % 60;
                }
                if (minute >= 60) {
                    hour++;
                    minute = minute % 60;
                }
                timer.setText(format(hour) + ":" + format(minute) + ":" + format(second));
//                timeStr = format(hour) + ":" + format(minute) + ":" + format(second);
            }
        }
    };

    /*
     * 格式化时间
     */
    public String format(int i) {
        String s = i + "";
        if (s.length() == 1) {
            s = "0" + s;
        }
        return s;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (mediaRecorder != null) {
            mediaRecorder.release();

        }
        if (camera != null) {
            camera.lock();
            camera.stopPreview();
            camera.release();
        }
//        camera.stopPreview();
//        camera.release();
//        camera=null;
    }

    /**
     * 开始或结束录制
     */
    private void startOrStopVideo() {
        if (isTaking) {
            mediaRecorder.stop();
            Toast.makeText(this, "录制完成，已保存  ", Toast.LENGTH_SHORT).show();

            finish();
        } else {
            // videoSizeLayout.setVisibility(View.GONE);
            startCamera();
            isTaking = true;
        }
    }

    @Override
    public void onClick(View v) {
        final Intent intent;
        switch (v.getId()) {

            case R.id.shoot_video_self_iv://切换拍摄视角
                //摄像头计数
                int cameraCount = 0;
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                cameraCount = Camera.getNumberOfCameras();//得到摄像头数量

                for (int i = 0; i < cameraCount; i++) {
                    Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
                    if (cameraPosition == 1) {
                        //现在是后置，变更为前置
                        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                            camera.stopPreview();//停掉原来摄像头的预览
                            camera.release();//释放资源
                            camera = null;//取消原来摄像头
                            camera = Camera.open(i);//打开当前选中的摄像头
                            camera.setDisplayOrientation(90);//摄像头必须是90度,否则永远是横屏拍摄
                            try {
                                camera.setPreviewDisplay(preview.getHolder());//通过surfaceview显示取景画
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            camera.startPreview();//开始预览
                            cameraPosition = 0;
                            break;
                        }
                    } else {
                        //   现在是前置， 变更为后置
                        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {//表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                            camera.stopPreview();//停掉原来摄像头的预览
                            camera.release();//释放资源
                            camera = null;//取消原来摄像头
                            camera = Camera.open(i);//打开当前选中的摄像头ͷ
                            camera.setDisplayOrientation(90);
                            try {
                                camera.setPreviewDisplay(preview.getHolder());//通过surfaceview显示取景画
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            camera.startPreview();//开始预览
                            cameraPosition = 1;
                            break;
                        }
                    }

                }
                break;

            case R.id.shoot_video_cancel_btn://取消
                finish();
                break;
            case R.id.shoot_video_comfir_btn://确定
                Toast.makeText(this, "确定 录制完成，已保存  ", Toast.LENGTH_SHORT).show();
                //接口
                //结束本页面
                Intent intent1 = new Intent();
                intent1.putExtra("videoPath", videoPath);//录制路径

                intent1.putExtra("videoSeconds", allTime + "");//录制时间
                try {
                    String bitMapPath = saveVideoBitmap(CommonFunction.getVideoImg(videoPath));//获取bitmap的路径
                    intent1.putExtra("bitMapPath", bitMapPath);//获取bitmap的路径
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setResult(RESULT_OK, intent1);
                finish();
                break;
            case R.id.pouse_or_stop_iv://暂停或者停止播放
                if (flag == 0) {//第一次点击状态
                    if (isControlEnable) {
                        if (videoPath != null) {
                            mediaRecorder.release();//释放资源
                        }
                        startCamera();
                        //startOrStopVideo();
                        handler.sendEmptyMessageDelayed(0, 2000);
                        videoPlay.setImageResource(R.drawable.onlease);
                    }
                    flag = 1;
                } else if (flag == 1) {//第二次点击状态
                    mediaRecorder.setOnErrorListener(null);
                    mediaRecorder.setOnInfoListener(null);
                    mediaRecorder.setPreviewDisplay(null);
                    mediaRecorder.stop();//停止记录
                    handler.removeCallbacks(task);//关闭计时器
                    timer.setText(format(0) + ":" + format(0) + ":" + format(0));
                    videoPlay.setImageResource(R.drawable.no_shoot);
                    Log.d("ygy_path", videoPath);
                    //确定结束本页面,并将视频上传到服务器
                    loadBtn.setVisibility(View.VISIBLE);

                    videoPhoto.setVisibility(View.VISIBLE);
                    videoPhoto.setImageBitmap(CommonFunction.getVideoImg(videoPath));//得到保存后的视频缩略图
                    videoPhoto.setOnClickListener(new View.OnClickListener() {//录像截图
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(ShootVideoActivity.this, "跳转播放  ", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent();
//                            intent.putExtra("mrrck_videoPath", videoPath);
//                            intent.setClass(ShootVideoActivity.this, TestVideoActivity.class);
//                            startActivity(intent);
                        }
                    });

                    flag = 0;
                }
                break;

        }
    }



    /**
     * 获取视频缩略图*路径
     */
    public String saveVideoBitmap(Bitmap mBitmap) throws IOException {
        File file = new File("/sdcard/Note/" );
        if (!file.exists()) {
            file.mkdir();
        }
        File fileName = new File(file , System.currentTimeMillis()+ ".png");
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
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
        File dirFile = new File( "/sdcard/" + "MircroClass");
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        return dirFile.getAbsolutePath();
    }
}