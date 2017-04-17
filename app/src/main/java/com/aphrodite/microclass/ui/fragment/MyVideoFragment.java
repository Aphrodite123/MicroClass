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

/**
 * Created by smy on 2017/4/17 0017.
 */

public class MyVideoFragment  extends BaseFragment {
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
        initListener();
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterVideoList = new RecycleMyUpVideoAdapter(getActivity());
        recyclerView.setAdapter(adapterVideoList);
        CommonFunction.progressDialogShow(getActivity(), "正在加载数据");
        loadData();


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initListener() {

    }

    private void loadData() {
        RetrofitMethod.getvideoInfo(new RetrofitService.OnResponeListener<VideoResponse>() {
            @Override
            public void onSuccess(VideoResponse respone) {
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
