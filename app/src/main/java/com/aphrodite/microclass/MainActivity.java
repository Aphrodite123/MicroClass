package com.aphrodite.microclass;

import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.aphrodite.microclass.base.BaseActivity;
import com.aphrodite.microclass.base.ProjectApplication;
import com.aphrodite.microclass.constant.Config;
import com.aphrodite.microclass.ui.activity.VideoUpLoadActivity;
import com.aphrodite.microclass.ui.adapter.TabPageIndicatorAdapter;
import com.aphrodite.microclass.util.CommonFunction;
import com.aphrodite.microclass.util.PermissionUtil;
import com.aphrodite.microclass.widget.AutoTextView;
import com.aphrodite.microclass.widget.HeadView;
import com.aphrodite.microclass.widget.dialog.SelectPhotoDialog;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.head_view)
    HeadView headView;
    // 抽屉导航布局
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.img_avatar_nav)
    ImageView img_avatar_nav;
    SelectPhotoDialog photoDialog;
    private boolean isShowDetail = false;//是否显示查看原图
    List<String> topName = Arrays.asList("我的视频", "我的文章", "开心一刻", "糗事百科");

    @BindView(R.id.autograph_text)
    AutoTextView autograph_text;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //设置透明状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        headView.setTitleText("微课堂");
        headView.isShowHeadImage(true);
        FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(getSupportFragmentManager(), topName);
        viewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(viewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected void initListener() {
        headView.getHeadImagView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        img_avatar_nav.setOnClickListener(this);
        autograph_text.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
    }

    @Override
    protected void initData() {


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_avatar_nav:
                showDialog();
                break;
            case R.id.autograph_text:
                CommonFunction.redirectActivity(MainActivity.this, VideoUpLoadActivity.class, null);
                break;
            case R.id.floatingActionButton:
                CommonFunction.redirectActivity(MainActivity.this, VideoUpLoadActivity.class, null);

                break;
        }
    }


    public void showDialog() {

        if (null != photoDialog) {
            photoDialog.dismiss();
            photoDialog = null;
        }
        photoDialog = new SelectPhotoDialog(MainActivity.this, isShowDetail);
        photoDialog.show();
        photoDialog.setCallbackImagePicker(new SelectPhotoDialog.CallbackImagePicker() {
            @Override
            public void onCamera() {
                if (PermissionUtil.checkPermission(MainActivity.this, "android.permission.CAMERA", 0)) {
                    GalleryFinal.openCamera(Config.REQUEST_CODE_CAMERA, ProjectApplication.getApplication().functionConfig, mOnHanlderResultCallback);
                } else {
                    CommonFunction.showToast(MainActivity.this, "请在设置中打开相机使用权限...");
                }


            }

            @Override
            public void onSelectPhoto() {
                GalleryFinal.openGallerySingle(Config.REQUEST_CODE_GALLERY, ProjectApplication.getApplication().functionConfig, mOnHanlderResultCallback);
            }

            @Override
            public void onViewPreview() {
//                if (!TextUtils.isEmpty(photoPath)) {
//                    if (!photoPath.contains("file://")) {
//                        photoPath = "file://" + photoPath;
//                    }
//                    ArrayList<String> urlList = new ArrayList<String>();
//                    urlList.add(photoPath);
//                    //点击图片预览大图
//                    Bundle bundle = new Bundle();
//                    bundle.putStringArrayList("urls", urlList);
//                    bundle.putInt("position", 0);
//                    CommonFunction.redirectActivity(context, PreviewImageActivity.class, bundle);
//                }

            }
        });


    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {

            if (resultList != null) {
                Uri uri = Uri.parse("file://" + resultList.get(0).getPhotoPath());
                Log.e("data", uri.toString());
                File file = new File(resultList.get(0).getPhotoPath());

                CommonFunction.ImagLoadPic(MainActivity.this, uri, img_avatar_nav);
                UpLoadheadFile(resultList.get(0).getPhotoPath());

            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            CommonFunction.showToast(MainActivity.this, errorMsg);
        }
    };


    private void UpLoadheadFile(String head) {

    }

}
