package com.aphrodite.microclass.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicDefaultFooter;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by smy on 2017/4/7 0007.
 */

public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;

    protected ProjectApplication mApplication;

    protected LayoutInflater mInflater;

    PtrFrameLayout ptrFrameLayout;
    private PtrClassicDefaultHeader mPtrClassicHeader;
    private PtrClassicDefaultFooter mPtrClassicFooter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        mApplication = (ProjectApplication) mActivity.getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        mInflater = inflater;
        handleNecessaryOption(view, inflater, container, savedInstanceState);
        return view;
    }

    /**
     * 头部和尾部刷新的样式
     */
    protected void   initRefreshView(PtrFrameLayout ptrFrameLayout) {
        this.ptrFrameLayout = ptrFrameLayout;
        ptrFrameLayout.setMode(PtrFrameLayout.Mode.BOTH);
        mPtrClassicHeader = new PtrClassicDefaultHeader(getContext());
        ptrFrameLayout.setHeaderView(mPtrClassicHeader);
        ptrFrameLayout.addPtrUIHandler(mPtrClassicHeader);


        mPtrClassicFooter = new PtrClassicDefaultFooter(getContext());
        ptrFrameLayout.setFooterView(mPtrClassicFooter);
        ptrFrameLayout.addPtrUIHandler(mPtrClassicFooter);
    }

    /**
     * 引入fragment布局
     */
    protected abstract int getLayoutId();

    /**
     * 必须在onCreateView中处理的一些操作，如：高德地图定位
     */
    protected void handleNecessaryOption(View view, LayoutInflater inflater, ViewGroup container,
                                         Bundle savedInstanceState) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showToast(String msg) {
        ProjectApplication.getApplication().showToast(msg);
    }
}
