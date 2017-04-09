package com.aphrodite.microclass;

import android.net.Uri;
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
import com.aphrodite.microclass.net.RetrofitMethod;
import com.aphrodite.microclass.net.RetrofitService;
import com.aphrodite.microclass.ui.adapter.TabPageIndicatorAdapter;
import com.aphrodite.microclass.ui.model.BaseResponse;
import com.aphrodite.microclass.util.CommonFunction;
import com.aphrodite.microclass.util.PermissionUtil;
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
    List<String> topName = Arrays.asList("头条", "视频", "开心一刻", "浏览记录");

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
                File file=new File(resultList.get(0).getPhotoPath());

               CommonFunction.ImagLoadPic(MainActivity.this,uri,img_avatar_nav);
                UpLoadFile( resultList.get(0).getPhotoPath());

            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            CommonFunction.showToast(MainActivity.this, errorMsg);
        }
    };

    private void UpLoadFile(String  path){
        RetrofitMethod.uploadPic(path, new RetrofitService.OnResponeListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse respone) {
                if(null!=respone){
                    showToast("成功");
                }else{
                    showToast("空");
                }

            }

            @Override
            public void onFailure(String value) {
                showToast(value);
            }
        });

    }
}
