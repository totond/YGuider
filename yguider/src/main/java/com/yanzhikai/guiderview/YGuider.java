package com.yanzhikai.guiderview;

import android.app.Activity;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yanzhikai.guiderview.views.MaskLayout;
import com.yanzhikai.guiderview.beans.ScanTarget;
import com.yanzhikai.guiderview.interfaces.OnGuiderClickListener;
import com.yanzhikai.guiderview.interfaces.OnGuiderListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/25 0025.
 */

public class YGuider {
    public static final String TAG = "guiderview";

    private Activity mActivity;
    private FrameLayout mContentView;
    private MaskLayout mMask;
    private ArrayList<RectF> mScanRegions;
    private ArrayList<ScanTarget> mScanTargets;
    private int mContentLocationX = 0, mContentLocationY = 0;
    private boolean mIsPreparing = false;
    /*
     * 下面是PopupWindow相关属性
     */
    private String defaultJumpText,defaultNextText;

    public YGuider(Activity activity){
        mActivity = activity;
        init();
    }

    private void init(){
        FrameLayout decorView = (FrameLayout)mActivity.getWindow().getDecorView();
        LinearLayout linearLayout = (LinearLayout) decorView.getChildAt(0);
        mContentView = (FrameLayout) linearLayout.getChildAt(1);

        mScanRegions = new ArrayList<>();
        mScanTargets = new ArrayList<>();

        defaultJumpText = mActivity.getResources().getString(R.string.text_jump);
        defaultNextText = mActivity.getResources().getString(R.string.text_next);

        buildMask();

    }

    private void buildMask(){
        mMask = new MaskLayout(mActivity,this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mMask.setLayoutParams(layoutParams);
        mMask.setScanTargets(mScanTargets);
    }

    public void startGuide(){
        if (!mIsPreparing) {
            mIsPreparing = true;
            mContentView.addView(mMask);
        }

    }

    public void addNextHighlight(View itemView, String text, int wOffsetX, int wOffsetY){
        ScanTarget scanTarget = new ScanTarget(itemView,text,wOffsetX,wOffsetY);
        mScanTargets.add(scanTarget);

    }

    public void addNextHighlight(View itemView, String text, int wOffsetX, int wOffsetY, int wWidth, int wHeight){
        ScanTarget scanTarget = new ScanTarget(itemView,text,wOffsetX,wOffsetY);
        scanTarget.setWindowWidth(wWidth);
        scanTarget.setWindowHeight(wHeight);
        mScanTargets.add(scanTarget);
    }

    public void addNextHighlight(View itemView, String text, int wOffsetX, int wOffsetY, int wWidth, int wHeight, String jumpText, String nextText){
        ScanTarget scanTarget = new ScanTarget(itemView,text,wOffsetX,wOffsetY);
        scanTarget.setWindowWidth(wWidth);
        scanTarget.setWindowHeight(wHeight);
        scanTarget.setJumpText(jumpText);
        scanTarget.setNextText(nextText);
        mScanTargets.add(scanTarget);
    }

    public void addNextHighlight(RectF highlightRegion, String text, int wOffsetX, int wOffsetY){
        ScanTarget scanTarget = new ScanTarget(highlightRegion,text,wOffsetX,wOffsetY);
        mScanTargets.add(scanTarget);
    }

    public void addNextHighlight(RectF highlightRegion, String text, int wOffsetX, int wOffsetY, int wWidth, int wHeight){
        ScanTarget scanTarget = new ScanTarget(highlightRegion,text,wOffsetX,wOffsetY);
        scanTarget.setWindowWidth(wWidth);
        scanTarget.setWindowHeight(wHeight);
        mScanTargets.add(scanTarget);
    }

    public void addNextHighlight(RectF highlightRegion, String text, int wOffsetX, int wOffsetY, int wWidth, int wHeight, String jumpText, String nextText){
        ScanTarget scanTarget = new ScanTarget(highlightRegion,text,wOffsetX,wOffsetY);
        scanTarget.setWindowWidth(wWidth);
        scanTarget.setWindowHeight(wHeight);
        scanTarget.setJumpText(jumpText);
        scanTarget.setNextText(nextText);
        mScanTargets.add(scanTarget);
    }

    private void getContentLocation(){
        int[] contentLocation = {0,0};
        mContentView.getLocationOnScreen(contentLocation);
        mContentLocationX = contentLocation[0];
        mContentLocationY = contentLocation[1];

    }

    public void prepareTarget(){
        ViewTreeObserver observerD = mContentView.getViewTreeObserver();
        observerD.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                getContentLocation();
                for (ScanTarget scanTarget : mScanTargets){
                    if (!scanTarget.getIsRegion()){
                        int[] location = {0,0};
                        View view = scanTarget.getTargetView();
                        view.getLocationInWindow(location);
                        scanTarget.setRegion(new RectF(
                                location[0]
                                ,location[1]
                                ,location[0] + view.getWidth()
                                ,location[1] + view.getHeight())
                        );
                    }
                    //迁移区域
                    scanTarget.getRegion().offset(-mContentLocationX,-mContentLocationY);
                    mScanRegions.add(scanTarget.getRegion());
//                    mIsPreparing = false;

                    if (scanTarget.getJumpText() == null){
                        scanTarget.setJumpText(defaultJumpText);
                    }
                    if (scanTarget.getNextText() == null){
                        scanTarget.setNextText(defaultNextText);
                    }
                }

            }
        });

    }

    public void cancelGuide(){
        if (mIsPreparing) {
            mMask.exit();
        }
    }

    public void setMaskRefreshTime(int refreshTime){
        mMask.setRefreshTime(refreshTime);
    }

    public void setMaskColor(@ColorInt int color){
        mMask.setMackColor(color);
    }

    public void setMaskPaint(Paint paint){
        mMask.setsPaint(paint);
    }

    public void setWindowTyperSpeed(int speed){
        mMask.getWindow().setTyperSpeed(speed);
    }

    public void setWindowTyperTextSize(int size){
        mMask.getWindow().setTyperTextSize(size);
    }

    public void setWindowTyperIncrease(int increase){
        mMask.getWindow().setTyperIncrease(increase);
    }

    public void setJumpText(String jumpText) {
        defaultJumpText = jumpText;
    }

    public void setNextText(String nextText) {
        defaultNextText = nextText;
    }

    public void setWindowBackground(@DrawableRes int idRes){
        mMask.getWindow().setContentBackgroundId(idRes);
    }

    public void setWindowJumpAndNextTextSize(int size){
        mMask.getWindow().setTvSize(size);
    }

    public void setWindowContent(@LayoutRes int layouId){
        mMask.getWindow().setContent(layouId);
    }

    public void setIsPreparing(boolean isPreparing) {
        mIsPreparing = isPreparing;
    }

    public boolean getIsPreparing(){
        return mIsPreparing;
    }

    public void setOnGuiderClickListener(OnGuiderClickListener guiderClickListener){
        mMask.setOnGuiderClickListener(guiderClickListener);
    }

    public void setOnGuiderListener(OnGuiderListener onGuiderListener){
        mMask.setOnGuiderListener(onGuiderListener);
    }
}
