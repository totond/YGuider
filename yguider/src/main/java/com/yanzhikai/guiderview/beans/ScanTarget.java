package com.yanzhikai.guiderview.beans;

import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;

/**
 * 存储扫描目标信息的类
 */

public class ScanTarget {
    //目标View
    private View mTargetView;
    //目标区域
    private RectF mRegion;
    //说明文字、跳过/忽略帮助的按钮的文字、下一步按钮的文字
    private String mShowText, mJumpText, mNextText;
    //弹出窗口的宽高
    private int mWindowWidth = 300, mWindowHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    //弹处窗口的XY偏移量，初始位置是在搜索框的正中下方
    private int wOffsetX = 0, wOffsetY = 0;
    //判断是否区域
    private boolean mIsRegion = false;

    public ScanTarget(View targetView, String text, int wOffsetX, int wOffsetY){
        mShowText = text;
        mTargetView = targetView;
        this.wOffsetX = wOffsetX;
        this.wOffsetY = wOffsetY;
        init();
    }

    public ScanTarget(RectF region, String text, int wOffsetX, int wOffsetY){
        mShowText = text;
        mRegion = region;
        this.wOffsetX = wOffsetX;
        this.wOffsetY = wOffsetY;
        init();
    }

    private void init(){
        if (mRegion != null){
            mIsRegion = true;
        }
    }

    public RectF viewToRegion(int offsetX, int offsetY){
        if (!mIsRegion){
            RectF rectF = getViewLocationRectF(mTargetView);
            rectF.offset(offsetX,offsetY);
            setRegion(rectF);
        }
        return mRegion;
    }

    //获取View的位置矩阵，相对于Window的坐标系
    private RectF getViewLocationRectF(View view){
        int[] location = {0,0};
        view.getLocationInWindow(location);
        return new RectF(
                location[0]
                ,location[1]
                ,location[0] + view.getWidth()
                ,location[1] + view.getHeight());
    }

    public void setRegion(RectF mRegion) {
        this.mRegion = mRegion;
        init();
    }

    public void setTargetView(View mTargetView) {
        this.mTargetView = mTargetView;
        init();
    }

    public void setJumpText(String jumpText) {
        this.mJumpText = jumpText;
    }

    public void setNextText(String nextText) {
        this.mNextText = nextText;
    }

    public void setWindowWidth(int windowWidth) {
        this.mWindowWidth = windowWidth;
    }

    public void setWindowHeight(int windowHeight) {
        this.mWindowHeight = windowHeight;
    }

    public int getWindowWidth() {
        return mWindowWidth;
    }

    public int getWindowHeight() {
        return mWindowHeight;
    }

    public String getJumpText() {
        return mJumpText;
    }

    public String getNextText() {
        return mNextText;
    }

    public int getwOffsetX() {
        return wOffsetX;
    }

    public String getShowText() {
        return mShowText;
    }

    public int getwOffsetY() {
        return wOffsetY;
    }

    public RectF getRegion() {
        return mRegion;
    }

    public View getTargetView() {
        return mTargetView;
    }

    public boolean getIsRegion(){
        return mIsRegion;
    }
}
