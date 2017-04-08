package com.aphrodite.microclass.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aphrodite.microclass.ui.fragment.HomeFragment;
import com.aphrodite.microclass.ui.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smy on 2017/4/7 0007.
 */

public class TabPageIndicatorAdapter extends FragmentPagerAdapter {
    List<String> titleStr = new ArrayList<>();

    public TabPageIndicatorAdapter(FragmentManager fm, List<String> titleStr) {
        super(fm);
        this.titleStr = titleStr;
    }

    @Override
    public Fragment getItem(int position) {
        //新建一个Fragment来展示ViewPager item的内容，并传递参数
        HomeFragment mainFragment1 = new HomeFragment();
        HomeFragment mainFragment2 = new HomeFragment();
        VideoFragment videoFragment1 = new VideoFragment();
        VideoFragment videoFragment2 = new VideoFragment();
        List<Fragment> list = new ArrayList<>();
        list.add(mainFragment1);
        list.add(videoFragment1);
        list.add(mainFragment2);
        list.add(videoFragment2);
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        list.get(position).setArguments(bundle);

        return list.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleStr.get(position % titleStr.size());
    }

    @Override
    public int getCount() {
        return null == titleStr ? 0 : titleStr.size();
    }

}
