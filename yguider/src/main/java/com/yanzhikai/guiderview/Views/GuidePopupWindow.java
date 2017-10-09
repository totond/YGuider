package com.yanzhikai.guiderview.Views;

import android.content.Context;
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
    private View mContentView;

    public GuidePopupWindow(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        mContentView = LayoutInflater.from(context).inflate(R.layout.tips_window_layout,null);
        setContentView(mContentView);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        mTyperTextView = (TyperTextView) mContentView.findViewById(R.id.ttv_tips);
        tv_jump = (TextView) mContentView.findViewById(R.id.tv_jump);
        tv_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnWindowClickListener != null){
                    mOnWindowClickListener.onJumpClick();
                }
            }
        });
        tv_next = (TextView) mContentView.findViewById(R.id.tv_next);
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnWindowClickListener != null){
                    mOnWindowClickListener.onNextClick();
                }
            }
        });
    }

    public void setContentBackgroundId(int id){
        setBackgroundDrawable(mContext.getResources().getDrawable(id));
    }

    public void showAsScannerTop(ScannerView view, int offsetX, int offsetY){
//        showAsDropDown(view, offsetX,(int)-(0));
        int[] location = {0,0};
        view.getLocationOnScreen(location);
        showAtLocation((View) view.getParent()
                , Gravity.NO_GRAVITY
                ,location[0]
                ,location[1]);
    }



    public void showGuideText(String text, int speed){
//        mTyperTextView.setTyperSpeed(speed);
//        mTyperTextView.animateText(text);
        mTyperTextView.setText(text);
    }

    public void setOnWindowClickListener(OnWindowClickListener mOnWindowClickListener) {
        this.mOnWindowClickListener = mOnWindowClickListener;
    }

    interface OnWindowClickListener{
        void onNextClick();
        void onJumpClick();
    }
}
