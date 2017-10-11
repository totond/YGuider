package com.yanzhikai.guiderview.beans;

import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/9/28 0028.
 */

public class ScanTarget {
    private View mTargetView;
    private RectF mRegion;
    private String mShowText, mJumpText, mNextText;
    private int mMoveDuration = 500, mScaleDuration = 500;
    private boolean mIsRegion = false;
    private int mWindowWidth = 300, mWindowHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
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

    public void setMoveDuration(int mMoveDuration) {
        this.mMoveDuration = mMoveDuration;
    }

    public void setScaleDuration(int mScaleDuration) {
        this.mScaleDuration = mScaleDuration;
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
