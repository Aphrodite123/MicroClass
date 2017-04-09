package com.aphrodite.microclass.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.aphrodite.microclass.R;
import com.aphrodite.microclass.widget.AutoTextView;

/**
 * Created by pc on 2017/4/9.
 */

public class SelectPhotoDialog  extends Dialog implements View.OnClickListener {
    private AutoTextView select_photo_text;
    private AutoTextView photo_text;
    private AutoTextView cancle_text;
    private AutoTextView photo_detail_text;
    private Context context;
    CallbackImagePicker imagePicker;
    private boolean isShowDetail = false;

    public SelectPhotoDialog(Context context, boolean isShowDetail) {
        super(context, R.style.BottomDialogStyleTop);
        this.context = context;
        this.isShowDetail = isShowDetail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_photo);
        Window window = this.getWindow();
        // 此处可以设置dialog显示的位置
        window.setGravity(Gravity.BOTTOM);
        // 占满屏幕
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        initView();
        initListener();
    }

    private void initView() {
        photo_detail_text = (AutoTextView) this.findViewById(R.id.photo_detail_text);


        select_photo_text = (AutoTextView) this.findViewById(R.id.select_photo_text);
        photo_text = (AutoTextView) this.findViewById(R.id.photo_text);
        cancle_text = (AutoTextView) this.findViewById(R.id.cancle_text);
        if (isShowDetail) {
            photo_detail_text.setVisibility(View.VISIBLE);
        } else {
            photo_detail_text.setVisibility(View.GONE);
        }
    }

    private void initListener() {
        select_photo_text.setOnClickListener(this);
        photo_text.setOnClickListener(this);
        cancle_text.setOnClickListener(this);
        photo_detail_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_photo_text:
                if (null != imagePicker) {
                    imagePicker.onSelectPhoto();
                }
                dismiss();
                break;
            case R.id.photo_text:
                if (null != imagePicker) {
                    imagePicker.onCamera();
                }
                dismiss();
                break;
            case R.id.cancle_text:
                dismiss();
                break;
            case R.id.photo_detail_text:
                if (null != imagePicker) {
                    imagePicker.onViewPreview();
                }
                dismiss();
                break;
        }
    }

    public void setCallbackImagePicker(CallbackImagePicker imagePicker) {
        this.imagePicker = imagePicker;
    }

    public interface CallbackImagePicker {
        void onCamera();

        void onSelectPhoto();
        void onViewPreview();
    }
}
