package com.yanzhikai.guiderview;

import android.app.Activity;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.yanzhikai.guiderview.interfaces.OnGuiderChangedListener;
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
    //用于存放扫描目标区域
    private ArrayList<RectF> mScanRegions;
    //用于存放扫描目标View
    private ArrayList<ScanTarget> mScanTargets;
    private int mContentLocationX = 0, mContentLocationY = 0;
    private boolean mIsGuiding = false;
    //PopupWindow相关属性
    private String defaultJumpText,defaultNextText;
    //要获取的ContentView的ID
    private @IdRes int mContentId;

    public YGuider(Activity activity){
        this(activity,android.R.id.content);
    }

    /**
     * YGuider构造方法
     * @param activity 传入Activity主要是为了可以获取DecorView
     * @param contentId 用于找到需要遮罩的Layout
     */
    public YGuider(Activity activity, @IdRes int contentId){
        mActivity = activity;
        mContentId = contentId;
        init();
    }

    private void init(){
        //通过DecorView获取
        FrameLayout decorView = (FrameLayout)mActivity.getWindow().getDecorView();
        mContentView = (FrameLayout) decorView.findViewById(mContentId);

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
        //绑定列表
        mMask.setScanTargets(mScanTargets);
    }

    /**
     * 设置MaskLayout的父Layout,调用此方法会重置YGuider
     * @param contentId 父Layout的ID
     */
    public void setContentId(int contentId) {
        this.mContentId = contentId;
        init();
    }

    /**
     * 开始Guide
     */
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

    /**
     * 判断在当前ContentView是否已经初始化宽高属性
     * 如果是，则直接获取目标View的位置信息，写入目标列表
     * 如果不是，则等到ContentView初始化宽高属性之后再获取，因为它会等子View全部Layout完了再进行Layout
     */
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

    //获取ContentView在屏幕位置，相对Window的坐标坐标系
    private void getContentLocation(){
        int[] contentLocation = {0,0};
        mContentView.getLocationInWindow(contentLocation);
        mContentLocationX = contentLocation[0];
        mContentLocationY = contentLocation[1];
    }

    //用于将目标View转为目标坐标区域
    private void prepareTargets(){
        getContentLocation();
        for (ScanTarget scanTarget : mScanTargets) {
            if (!scanTarget.getIsRegion()) {
//                scanTarget.setRegion(getViewLocationRectF(scanTarget.getTargetView()));
                //迁移区域.因为需要的区域是相对于ContentView的坐标系的，getLocationInWindow()获取的坐标是相对于Window的坐标系的
                //所以要用在Window坐标系里面的，View的坐标减去mContentView的坐标，就得到View相对于mContentView的坐标了
//                scanTarget.getRegion().offset(-mContentLocationX, -mContentLocationY);
                mScanRegions.add(scanTarget.viewToRegion(-mContentLocationX, -mContentLocationY));
            }

            //设置跳过和下一步的字符
            if (scanTarget.getJumpText() == null) {
                scanTarget.setJumpText(defaultJumpText);
            }
            if (scanTarget.getNextText() == null) {
                scanTarget.setNextText(defaultNextText);
            }
        }
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

    /**
     * 退出新手引导
     */
    public void cancelGuide(){
        if (mIsGuiding) {
            mMask.exit();
        }
    }

    /**
     * 设置扫描框动画刷新的时间间隔，默认值是20
     * @param refreshTime 单位是ms
     */
    public void setMaskRefreshTime(int refreshTime){
        mMask.setRefreshTime(refreshTime);
    }

    /**
     * 设置扫描框移动动画的持续时间，默认值是500
     * @param moveDuration 单位是ms
     */
    public void setMaskMoveDuration(int moveDuration) {
        mMask.setMoveDuration(moveDuration);
    }

    /**
     * 设置扫描框扩大动画的持续时间，默认值是500
     * @param expandDuration 单位是ms
     */
    public void setExpandDuration(int expandDuration) {
        mMask.setExpandDuration(expandDuration);
    }

    /**
     * 设置遮罩层的颜色，最后是设置成透明的，默认颜色是#aa222222
     * @param color 颜色
     */
    public void setMaskColor(@ColorInt int color){
        mMask.setMackColor(color);
    }

    /**
     * 设置画扫描框的画笔
     * @param paint 画笔
     */
    public void setMaskPaint(Paint paint){
        mMask.setsPaint(paint);
    }

    /**
     * 设置弹窗里面TextView文字出现的速度，默认值是100
     * @param refreshTime 每次增加文字的时间间隔，单位ms
     */
    public void setWindowTyperRefreshTime(int refreshTime){
        mMask.getWindow().setTyperRefreshTime(refreshTime);
    }

    /**
     * 弹窗里面说明文字的字体大小，默认值是18sp
     * @param size 字体大小
     */
    public void setWindowTyperTextSize(int size){
        mMask.getWindow().setTyperTextSize(size);
    }

    /**
     * 设置弹窗里面TextView文字的增长速度，默认值是1
     * @param increase 每次增加多少个字符
     */
    public void setWindowTyperIncrease(int increase){
        mMask.getWindow().setTyperIncrease(increase);
    }

    /**
     * 设置跳过按钮的文字
     * @param jumpText 跳过文字
     */
    public void setJumpText(String jumpText) {
        defaultJumpText = jumpText;
    }

    /**
     * 设置下一步按钮的文字
     * @param nextText 下一步文字
     */
    public void setNextText(String nextText) {
        defaultNextText = nextText;
    }

    /**
     * 设置弹窗背景
     * @param idRes 背景DrawableId
     */
    public void setWindowBackground(@DrawableRes int idRes){
        mMask.getWindow().setContentBackgroundId(idRes);
    }

    /**
     * 设置跳过和下一步按钮文字大小
     * @param size 文字字体大小
     */
    public void setWindowJumpAndNextTextSize(int size){
        mMask.getWindow().setTvSize(size);
    }

    /**
     * 设置自定义弹窗布局
     * 注意新的布局要有TyperTextView类，id设置为ttv_tips
     * 跳过按钮和下一步按钮可以选择实现，但是有的话id请分别设置为tv_jump和tv_next，其他可以自定义
     * @param layouId 布局的id
     */
    public void setWindowContent(@LayoutRes int layouId){
        mMask.getWindow().setContent(layouId);
    }

    public void setIsGuiding(boolean isGuiding) {
        mIsGuiding = isGuiding;
    }

    public boolean getIsPreparing(){
        return mIsGuiding;
    }

    /**
     * 设置点击回调
     * @param guiderClickListener 可以传入OnGuiderClickListener和OnGuiderListener的子类
     */
    public void setOnGuiderClickListener(OnGuiderClickListener guiderClickListener){
        mMask.setOnGuiderClickListener(guiderClickListener);
    }

    /**
     * 设置状态回调
     * @param onGuiderChangedListener 可以传入OnGuiderChangedListener和OnGuiderListener的子类
     */
    public void setOnGuiderChangedListener(OnGuiderChangedListener onGuiderChangedListener){
        mMask.setOnGuiderChangedListener(onGuiderChangedListener);
    }

    /**
     * 设置状态所有回调
     * @param onGuiderListener 可以传入OnGuiderListener的子类
     */
    public void setOnGuiderListener(OnGuiderListener onGuiderListener){
        mMask.setOnGuiderListener(onGuiderListener);
    }
}
