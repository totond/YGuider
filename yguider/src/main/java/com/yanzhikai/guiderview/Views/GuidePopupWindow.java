package com.yanzhikai.guiderview.Views;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yanzhikai.guiderview.R;


/**
 * Created by Administrator on 2017/9/29 0029.
 */

public class GuidePopupWindow extends PopupWindow {
    private Context mContext;
    private TyperTextView mTyperTextView;
    private TextView tv_jump, tv_next;
    private OnWindowClickListener mOnWindowClickListener;
    private @LayoutRes int mContentId = R.layout.tips_window_layout;
    private int mWidth = 400, mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

    public GuidePopupWindow(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
        View contentView = LayoutInflater.from(mContext).inflate(mContentId, null);
        setContentView(contentView);
        resetWidthAndHeight();


        mTyperTextView = (TyperTextView) contentView.findViewById(R.id.ttv_tips);
        mTyperTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTyperTextView.showAll();
            }
        });
        tv_jump = (TextView) contentView.findViewById(R.id.tv_jump);
        if (tv_jump != null) {
            tv_jump.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnWindowClickListener != null) {
                        mOnWindowClickListener.onJumpClick();
                    }
                }
            });
//            tv_jump.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
        tv_next = (TextView) contentView.findViewById(R.id.tv_next);
        if (tv_next != null) {
            tv_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnWindowClickListener != null) {
                        mOnWindowClickListener.onNextClick();
                    }
                }
            });
//            tv_next.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        }


    }

    public void setContent(@LayoutRes int layoutId){
        mContentId = layoutId;
        init();
    }

    public void resetWidthAndHeight() {
        setWidth(mWidth);
        setHeight(mHeight);
    }



    public void setContentBackgroundId(@DrawableRes int id) {
        setBackgroundDrawable(mContext.getResources().getDrawable(id));
    }

//    public void showAsScannerTop(ScannerView view, int offsetX, int offsetY) {
//        int[] location = {0, 0};
//        view.getLocationOnScreen(location);
//        showAtLocation((View) view.getParent()
//                , Gravity.BOTTOM
//                , location[0]
//                , location[1]);
//        Log.d("window", "showAsScannerTop: " + getContentView().getWidth());
//    }

    public void setJumpText(String text) {
        tv_jump.setText(text);
    }

    public void setNextText(String text) {
        tv_next.setText(text);
    }

    public void setTvSize(int size){
        tv_jump.setTextSize(size);
        tv_next.setTextSize(size);
    }

    public void setTyperIncrease(int increase){
        mTyperTextView.setCharIncrease(increase);
    }

    public void setTyperTextSize(int size) {
        if (mTyperTextView != null) {
            mTyperTextView.setTextSize(size);
        }
    }

    public void setTyperSpeed(int speed) {
        if (mTyperTextView != null) {
            mTyperTextView.setTyperSpeed(speed);
        }
    }

    public void showGuideText(String text, int speed) {
//        mTyperTextView.setTyperSpeed(speed);
        mTyperTextView.animateText(text);
//        mTyperTextView.setText(text);
    }

    public void setOnWindowClickListener(OnWindowClickListener mOnWindowClickListener) {
        this.mOnWindowClickListener = mOnWindowClickListener;
    }

    interface OnWindowClickListener {
        void onNextClick();

        void onJumpClick();
    }
}
