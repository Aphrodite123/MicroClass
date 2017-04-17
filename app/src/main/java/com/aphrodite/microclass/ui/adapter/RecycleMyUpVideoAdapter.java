package com.aphrodite.microclass.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aphrodite.microclass.R;
import com.aphrodite.microclass.constant.VideoConstant;
import com.aphrodite.microclass.ui.model.VideoResponse;
import com.aphrodite.microclass.util.CommonFunction;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by smy on 2017/4/17 0017.
 */

public class RecycleMyUpVideoAdapter extends RecyclerView.Adapter<RecycleMyUpVideoAdapter.MyViewHolder> {


    private Context context;
    public static final String TAG = "RecyclerViewVideoAdapter";
    List<VideoResponse.Data> dataList = new ArrayList<>();

    public RecycleMyUpVideoAdapter(Context context) {
        this.context = context;
    }

    public void setDataList(  List<VideoResponse.Data> dataList) {
        this.dataList = dataList;
        this.notifyDataSetChanged();

    }

    @Override
    public RecycleMyUpVideoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecycleMyUpVideoAdapter.MyViewHolder holder = new RecycleMyUpVideoAdapter.MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_videoview, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecycleMyUpVideoAdapter.MyViewHolder holder, int position) {
        Log.e("viewHoder",""+dataList.get(position).toString());
        if(null!=dataList&&null!= holder.jcVideoPlayer){
            VideoResponse.Data data=dataList.get(position);
            if(null!=data.videoTitle&&null!=data.videoUrl){

                holder.jcVideoPlayer.setUp(
                        data.videoUrl,
                        data.videoTitle);
//                holder.jcVideoPlayer.ivThumb.setImageBitmap(CommonFunction.getVideoImg(data.videoUrl));
                CommonFunction.ImagLoadPic(holder.jcVideoPlayer.getContext(),data.videoImg,holder.jcVideoPlayer.ivThumb);
//    Picasso.with(holder.jcVideoPlayer.getContext())
//            .load(data.res_cover_url)
//            .into(holder.jcVideoPlayer.ivThumb);
            }else{

                holder.jcVideoPlayer.setUp(
                        "http://192.168.1.19:8080/doc/03.mp4",
                        VideoConstant.videoTitles[0][7]);
                Picasso.with(holder.jcVideoPlayer.getContext())
                        .load(VideoConstant.videoThumbs[0][7])
                        .into(holder.jcVideoPlayer.ivThumb);
            }
        }

    }

    @Override
    public int getItemCount() {
        return null==dataList?0:dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        JCVideoPlayerStandard jcVideoPlayer;

        public MyViewHolder(View itemView) {
            super(itemView);
            jcVideoPlayer = (JCVideoPlayerStandard) itemView.findViewById(R.id.videoplayer);
        }
    }

}