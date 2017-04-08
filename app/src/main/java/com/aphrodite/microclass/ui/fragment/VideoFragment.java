package com.aphrodite.microclass.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aphrodite.microclass.R;
import com.aphrodite.microclass.base.BaseFragment;
import com.aphrodite.microclass.ui.adapter.RecyclerViewVideoAdapter;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by pc on 2017/4/8.
 */

public class VideoFragment extends BaseFragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    RecyclerViewVideoAdapter adapterVideoList;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
    }
    private void initView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterVideoList = new RecyclerViewVideoAdapter(getActivity());
        recyclerView.setAdapter(adapterVideoList);

    }
    private void initListener(){

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
