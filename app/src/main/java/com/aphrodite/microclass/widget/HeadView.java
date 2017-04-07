package com.aphrodite.microclass.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aphrodite.microclass.R;
import com.aphrodite.microclass.base.BaseActivity;
import com.aphrodite.microclass.util.AppManager;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by smy on 2017/4/7 0007.
 */

public class HeadView extends LinearLayout {
    TextView titleText;
    AutoImageView img_back;
    BaseActivity baseActivity;
    CircleImageView img_avatar;

    public HeadView(Context context) {
        super(context);
        init(context);
    }

    public HeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public HeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.app_head_view, this, true);
        baseActivity = (BaseActivity) context;
        findViewById();
        initListener();
    }

    private void findViewById() {
        titleText = (TextView) this.findViewById(R.id.title);
        img_back = (AutoImageView) this.findViewById(R.id.img_back);
        img_avatar = (CircleImageView) this.findViewById(R.id.img_avatar);
    }

    private void initListener() {
        img_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManager.getAppManager().finishActivity(baseActivity);
            }
        });
    }

    public void setTitleText(String title) {
        titleText.setText(title);
    }

    public void isShowBackImage(boolean isShow) {
        if (isShow) {
            img_back.setVisibility(VISIBLE);
        } else {
            img_back.setVisibility(GONE);
        }
    }

    public void isShowHeadImage(boolean isShow) {
        if (isShow) {
            img_avatar.setVisibility(VISIBLE);
        } else {
            img_avatar.setVisibility(GONE);
        }
    }

    public View getHeadImagView() {
        return img_avatar;
    }
}
