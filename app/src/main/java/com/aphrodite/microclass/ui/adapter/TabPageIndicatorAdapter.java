package com.aphrodite.microclass.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aphrodite.microclass.ui.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smy on 2017/4/7 0007.
 */

public class TabPageIndicatorAdapter  extends FragmentPagerAdapter {
    List<String> titleStr = new ArrayList<>();

    public TabPageIndicatorAdapter(FragmentManager fm, List<String> titleStr) {
        super(fm);
        this.titleStr = titleStr;
    }

    @Override
    public Fragment getItem(int position) {
        //新建一个Fragment来展示ViewPager item的内容，并传递参数
        HomeFragment mainFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        mainFragment.setArguments(bundle);

        return mainFragment;
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
