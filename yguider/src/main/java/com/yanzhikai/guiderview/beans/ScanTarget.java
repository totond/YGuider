package com.yanzhikai.guiderview.beans;

import android.graphics.RectF;
import android.view.View;

/**
 * Created by Administrator on 2017/9/28 0028.
 */

public class ScanTarget {
    private View mTargetView;
    private RectF mRegion;
    private String mShowText;
    private int mMoveDuration = 500, mScaleDuration = 500;
    private boolean mIsRegion = false;
    private int wOffsetX = 0, wOffsetY = 0;

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

    public void setRegion(RectF mRegion) {
        this.mRegion = mRegion;
        init();
    }

    public void setTargetView(View mTargetView) {
        this.mTargetView = mTargetView;
        init();
    }

    public String getShowText() {
        return mShowText;
    }

    public int getwOffsetX() {
        return wOffsetX;
    }

    public int getwOffsetY() {
        return wOffsetY;
    }

    public void setMoveDuration(int mMoveDuration) {
        this.mMoveDuration = mMoveDuration;
    }

    public void setScaleDuration(int mScaleDuration) {
        this.mScaleDuration = mScaleDuration;
    }

    public int getMoveDuration() {
        return mMoveDuration;
    }

    public int getScaleDuration() {
        return mScaleDuration;
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
