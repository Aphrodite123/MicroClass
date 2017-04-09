package com.aphrodite.microclass.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aphrodite.microclass.R;
import com.aphrodite.microclass.base.BaseFragment;
import com.aphrodite.microclass.net.RetrofitMethod;
import com.aphrodite.microclass.net.RetrofitService;
import com.aphrodite.microclass.ui.adapter.RecyclerViewVideoAdapter;
import com.aphrodite.microclass.ui.model.FunnyXunLeiResponse;
import com.aphrodite.microclass.util.CommonFunction;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by pc on 2017/4/8.
 */

public class VideoFragment extends BaseFragment {
    @BindView(R.id.fragment_ptr_home_ptr_frame)
    PtrFrameLayout ptrFrameLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    RecyclerViewVideoAdapter adapterVideoList;
    private int resh_pull = 1;
    List<FunnyXunLeiResponse.Item_list> dataList = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
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

        adapterVideoList = new RecyclerViewVideoAdapter(getActivity());
        recyclerView.setAdapter(adapterVideoList);
        resh_pull=1;
        CommonFunction.progressDialogShow(getActivity(),"正在加载数据");
        loadData();
    }

    private void initListener() {

    }

    private void initData() {

        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {

                resh_pull=2;
                loadData();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                resh_pull=1;
                loadData();
            }
        });

    }

    private void loadData() {
        RetrofitMethod.getXunLeiVideo(new RetrofitService.OnResponeListener<FunnyXunLeiResponse>() {
            @Override
            public void onSuccess(FunnyXunLeiResponse respone) {
                ptrFrameLayout.refreshComplete();
                if (null != respone) {
                    if(resh_pull==1){
                        if (null != dataList) {
                            dataList.clear();
                            dataList = respone.item_list;
                        }
                    }else{
                        dataList.addAll(respone.item_list);
                    }
                    adapterVideoList.setDataList(dataList);
                } else {
                    showToast("为空");
                }

            }

            @Override
            public void onFailure(String value) {
                ptrFrameLayout.refreshComplete();
                showToast("失败");

            }
        });
    }
//    @Override
//    public void onBackPressed() {
//        if (JCVideoPlayer.backPress()) {
//            return;
//        }
//        super.onBackPressed();
//    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
