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
import android.widget.RelativeLayout;

import com.yanzhikai.guiderview.views.MaskLayout;
import com.yanzhikai.guiderview.beans.ScanTarget;
import com.yanzhikai.guiderview.interfaces.OnGuiderClickListener;
import com.yanzhikai.guiderview.interfaces.OnGuiderListener;

import java.util.ArrayList;

/**
 * 扫描框风格的新手引导
 */

public class YGuider {
    public static final String TAG = "guiderview";

    private Activity mActivity;
    private FrameLayout mContentView;
    private MaskLayout mMask;
    private ArrayList<RectF> mScanRegions;
    private ArrayList<ScanTarget> mScanTargets;
    private int mContentLocationX = 0, mContentLocationY = 0;
    private boolean mIsGuiding = false;
    //PopupWindow相关属性
    private String defaultJumpText,defaultNextText;

    public YGuider(Activity activity){
        mActivity = activity;
        init();
    }

    private void init(){
        //通过DecorView获取
        FrameLayout decorView = (FrameLayout)mActivity.getWindow().getDecorView();
        mContentView = (FrameLayout) decorView.findViewById(android.R.id.content);

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
        if (!mIsGuiding) {
            mIsGuiding = true;
            mContentView.addView(mMask);
        }

    }

    /**
     * 增加一个扫描区域
     * @param targetView 目标View
     * @param text 说明文字
     * @param wOffsetX 弹出窗口的X位置偏移量（初始位置为目标View中间）
     * @param wOffsetY 弹出窗口的Y位置偏移量（初始位置为目标View正下方）
     */
    public void addNextTarget(View targetView, String text, int wOffsetX, int wOffsetY){
        ScanTarget scanTarget = new ScanTarget(targetView,text,wOffsetX,wOffsetY);
        mScanTargets.add(scanTarget);

    }

    /**
     * 增加一个扫描区域
     * @param targetView 目标View
     * @param text 说明文字
     * @param wOffsetX 弹出窗口的X位置偏移量（初始位置为目标View中间）
     * @param wOffsetY 弹出窗口的Y位置偏移量（初始位置为目标View正下方）
     * @param wWidth 弹出窗口的宽度
     * @param wHeight 弹出窗口的高度
     */
    public void addNextTarget(View targetView, String text, int wOffsetX, int wOffsetY, int wWidth, int wHeight){
        ScanTarget scanTarget = new ScanTarget(targetView,text,wOffsetX,wOffsetY);
        scanTarget.setWindowWidth(wWidth);
        scanTarget.setWindowHeight(wHeight);
        mScanTargets.add(scanTarget);
    }

    /**
     * 增加一个扫描区域
     * @param targetView 目标View
     * @param text 说明文字
     * @param wOffsetX 弹出窗口的X位置偏移量（初始位置为目标View中间）
     * @param wOffsetY 弹出窗口的Y位置偏移量（初始位置为目标View正下方）
     * @param wWidth 弹出窗口的宽度
     * @param wHeight 弹出窗口的高度
     * @param jumpText 跳过选项的文字
     * @param nextText 下一步选项的文字
     */
    public void addNextTarget(View targetView, String text, int wOffsetX, int wOffsetY, int wWidth, int wHeight, String jumpText, String nextText){
        ScanTarget scanTarget = new ScanTarget(targetView,text,wOffsetX,wOffsetY);
        scanTarget.setWindowWidth(wWidth);
        scanTarget.setWindowHeight(wHeight);
        scanTarget.setJumpText(jumpText);
        scanTarget.setNextText(nextText);
        mScanTargets.add(scanTarget);
    }

    /**
     * 增加一个扫描区域
     * @param targetRegion 目标区域的坐标矩阵
     * @param text 说明文字
     * @param wOffsetX 弹出窗口的X位置偏移量（初始位置为目标View中间）
     * @param wOffsetY 弹出窗口的Y位置偏移量（初始位置为目标View正下方）
     */
    public void addNextTarget(RectF targetRegion, String text, int wOffsetX, int wOffsetY){
        ScanTarget scanTarget = new ScanTarget(targetRegion,text,wOffsetX,wOffsetY);
        mScanTargets.add(scanTarget);
    }

    /**
     * 增加一个扫描区域
     * @param targetRegion 目标区域的坐标矩阵
     * @param text 说明文字
     * @param wOffsetX 弹出窗口的X位置偏移量（初始位置为目标View中间）
     * @param wOffsetY 弹出窗口的Y位置偏移量（初始位置为目标View正下方）
     * @param wWidth 弹出窗口的宽度
     * @param wHeight 弹出窗口的高度
     */
    public void addNextTarget(RectF targetRegion, String text, int wOffsetX, int wOffsetY, int wWidth, int wHeight){
        ScanTarget scanTarget = new ScanTarget(targetRegion,text,wOffsetX,wOffsetY);
        scanTarget.setWindowWidth(wWidth);
        scanTarget.setWindowHeight(wHeight);
        mScanTargets.add(scanTarget);
    }

    /**
     * 增加一个扫描区域
     * @param targetRegion 目标区域的坐标矩阵
     * @param text 说明文字
     * @param wOffsetX 弹出窗口的X位置偏移量（初始位置为目标View中间）
     * @param wOffsetY 弹出窗口的Y位置偏移量（初始位置为目标View正下方）
     * @param wWidth 弹出窗口的宽度
     * @param wHeight 弹出窗口的高度
     * @param jumpText 跳过选项的文字
     * @param nextText 下一步选项的文字
     */
    public void addNextTarget(RectF targetRegion, String text, int wOffsetX, int wOffsetY, int wWidth, int wHeight, String jumpText, String nextText){
        ScanTarget scanTarget = new ScanTarget(targetRegion,text,wOffsetX,wOffsetY);
        scanTarget.setWindowWidth(wWidth);
        scanTarget.setWindowHeight(wHeight);
        scanTarget.setJumpText(jumpText);
        scanTarget.setNextText(nextText);
        mScanTargets.add(scanTarget);
    }

    /**
     * 增加一些Target
     * @param targets 一些ScanTarget对象
     */
    public void addTarget(ScanTarget... targets){
        for (ScanTarget target : targets) {
            mScanTargets.add(target);
        }
    }

    /**
     * 进入下一个引导
     */
    public void startNextGuide(){
        mMask.onNext();
    }

    //获取ContentView在屏幕位置
    private void getContentLocation(){
        int[] contentLocation = {0,0};
        mContentView.getLocationOnScreen(contentLocation);
        mContentLocationX = contentLocation[0];
        mContentLocationY = contentLocation[1];

    }

    /**
     * 移除目标
     * @param index 目标的index
     * @return 移除目标成功与否
     */
    public boolean removeTarget(int index){
        if (index >= 0 && index < mScanTargets.size()){
            mScanTargets.remove(index);
            return true;
        }
        return false;
    }

    /**
     * 清除所有扫描目标
     */
    public void clearTargets(){
        mScanTargets.clear();
    }

    //在View初始化宽高属性之后，获取它们的宽高信息，写入目标列表
    public void prepare(){
        ViewTreeObserver observerD = mContentView.getViewTreeObserver();
        if (mContentView.getWidth() != 0 && mContentView.getHeight() != 0) {
            prepareTargets();
        }else {
            observerD.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    prepareTargets();
                }
            });
        }
    }

    //用于将目标View转为目标坐标区域
    private void prepareTargets(){
        getContentLocation();
        for (ScanTarget scanTarget : mScanTargets) {
            if (!scanTarget.getIsRegion()) {
                scanTarget.setRegion(getViewLocationRectF(scanTarget.getTargetView()));
                //迁移区域.因为区域是以相对ContentView的坐标系定义的
                scanTarget.getRegion().offset(-mContentLocationX, -mContentLocationY);
                mScanRegions.add(scanTarget.getRegion());
            }

            if (scanTarget.getJumpText() == null) {
                scanTarget.setJumpText(defaultJumpText);
            }
            if (scanTarget.getNextText() == null) {
                scanTarget.setNextText(defaultNextText);
            }
        }
    }

    private RectF getViewLocationRectF(View view){
        int[] location = {0,0};
        view.getLocationInWindow(location);
        return new RectF(
                location[0]
                ,location[1]
                ,location[0] + view.getWidth()
                ,location[1] + view.getHeight());
    }

    //退出新手引导
    public void cancelGuide(){
        if (mIsGuiding) {
            mMask.exit();
        }
    }

    /**
     * 设置扫描框动画刷新的频率
     * @param refreshTime 单位是ms
     */
    public void setMaskRefreshTime(int refreshTime){
        mMask.setRefreshTime(refreshTime);
    }

    /**
     * 设置扫描框移动动画的持续时间
     * @param moveDuration 单位是ms
     */
    public void setMaskMoveDuration(int moveDuration) {
        mMask.setMoveDuration(moveDuration);
    }

    /**
     * 设置扫描框扩大动画的持续时间
     * @param expandDuration 单位是ms
     */
    public void setExpandDuration(int expandDuration) {
        mMask.setExpandDuration(expandDuration);
    }

    public void setMaskColor(@ColorInt int color){
        mMask.setMackColor(color);
    }

    public void setMaskPaint(Paint paint){
        mMask.setsPaint(paint);
    }

    /**
     * 设置弹窗里面TextView文字出现的速度
     * @param refreshTime 每次增加文字的时间间隔
     */
    public void setWindowTyperRefreshTime(int refreshTime){
        mMask.getWindow().setTyperRefreshTime(refreshTime);
    }

    public void setWindowTyperTextSize(int size){
        mMask.getWindow().setTyperTextSize(size);
    }

    /**
     * 设置弹窗里面TextView文字的增长速度
     * @param increase 每次增加多少个字符
     */
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
        mIsGuiding = isPreparing;
    }

    public boolean getIsPreparing(){
        return mIsGuiding;
    }

    public void setOnGuiderClickListener(OnGuiderClickListener guiderClickListener){
        mMask.setOnGuiderClickListener(guiderClickListener);
    }

    public void setOnGuiderListener(OnGuiderListener onGuiderListener){
        mMask.setOnGuiderListener(onGuiderListener);
    }
}
