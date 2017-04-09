package com.aphrodite.microclass.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.aphrodite.microclass.R;
import com.aphrodite.microclass.base.BaseFragment;

/**
 * Created by smy on 2017/4/7 0007.
 */

public class HomeFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
    }

    private void initView() {

    }

    private void initListener() {

    }
}
