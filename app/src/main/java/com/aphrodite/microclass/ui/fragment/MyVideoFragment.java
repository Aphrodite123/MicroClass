package com.aphrodite.microclass.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aphrodite.microclass.R;
import com.aphrodite.microclass.base.BaseFragment;
import com.aphrodite.microclass.net.RetrofitMethod;
import com.aphrodite.microclass.net.RetrofitService;
import com.aphrodite.microclass.ui.adapter.RecycleMyUpVideoAdapter;
import com.aphrodite.microclass.ui.model.VideoResponse;
import com.aphrodite.microclass.util.CommonFunction;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by smy on 2017/4/17 0017.
 */

public class MyVideoFragment extends BaseFragment {
    @BindView(R.id.fragment_ptr_home_ptr_frame)
    PtrFrameLayout ptrFrameLayout;
    List<VideoResponse.Data> dataList = new ArrayList<>();
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    RecycleMyUpVideoAdapter adapterVideoList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initRefreshView(ptrFrameLayout);
        initListener();
        initData();
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterVideoList = new RecycleMyUpVideoAdapter(getActivity());
        recyclerView.setAdapter(adapterVideoList);


    }

    @Override
    public void onResume() {
        super.onResume();
        CommonFunction.progressDialogShow(getActivity(), "正在加载数据");
        loadData();

    }

    private void initListener() {

    }

    private void initData() {

        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadData();
            }
        });

    }

    private void loadData() {
        RetrofitMethod.getvideoInfo(new RetrofitService.OnResponeListener<VideoResponse>() {
            @Override
            public void onSuccess(VideoResponse respone) {
                ptrFrameLayout.refreshComplete();
                if (null != dataList) {
                    dataList.clear();
                    dataList = respone.data;
                }
                adapterVideoList.setDataList(dataList);
            }

            @Override
            public void onFailure(String value) {
                showToast(value);

            }
        });
    }
}
