package com.aphrodite.microclass.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.aphrodite.microclass.R;
import com.aphrodite.microclass.util.AppManager;
import com.aphrodite.microclass.util.SystemBarTintManager;

import butterknife.ButterKnife;

/**
 * Created by smy on 2017/4/7 0007.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private SystemBarTintManager tintManager;

    WindowManager.LayoutParams params ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        initViews();
        initListener();
        initData();
    }

    /**
     * 设置布局文件
     *
     * @return
     */
    public abstract int getContentView();

    /**
     * 获取控件
     */
    protected abstract void initViews();

    /**
     * 设置监听
     */
    protected abstract void initListener();

    /**
     * 设置数据
     */
    protected abstract void initData();

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enteranim, R.anim.exitanim);
    }

    public void showToast(String msg) {
        ProjectApplication.getApplication().showToast(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }
}
