package com.aphrodite.microclass.ui.model;

import java.util.List;

/**
 * Created by pc on 2017/4/9.
 */

public class FunnyXunLeiResponse {
    public String result;
    public String clear_cache;
    public String ts;
    public String on_the_top;
    public String min_id;
    public String max_id;
    public String length;
    public List<Item_list> item_list;

    public class Item_list {
        public String ab_type;
        public String comment_num;
        public String cover_item;
        public String display_type;
        public String duration;
        public String f_count;
        public String gcid;
        public String have_favorite;
        public String id;
        public String is_follow;
        public String jump_url;
        public String kind;
        public String play_count;
        public String play_url;
        public String poster_height;
        public String poster_width;
        public String publisher_icon_url;

        public String publisher_id;
        public String publisher_name;
        public String recommend_reason;
        public String res_cover_url;
        public String res_display_type;
        public String res_from;
        public String res_id;
        public String res_subcategories;
        public String res_title;
        public String res_type;
        public String s_ab;
        public String share_count;
        public String upline_time;
        public String v_status;
        public String v_url;

    }
}
