package com.aphrodite.microclass.ui.model;

import java.util.List;

/**
 * Created by smy on 2017/4/17 0017.
 */

public class VideoResponse extends BaseResponse {
    public List<Data> data;

    public class Data {
        public String videoId;
        public String videoUrl;
        public String videoTitle;
        public String videoImg;
    }
}
