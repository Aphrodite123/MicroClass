package com.aphrodite.microclass;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import com.aphrodite.microclass.base.BaseActivity;
import com.aphrodite.microclass.ui.adapter.TabPageIndicatorAdapter;
import com.aphrodite.microclass.widget.HeadView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

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
    }

    @Override
    protected void initData() {


    }
}
