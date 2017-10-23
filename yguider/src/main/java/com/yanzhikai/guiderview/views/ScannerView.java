package com.yanzhikai.guiderview.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * 用于控制扫描框的View，通过控制它来实现扫描框的移动
 */

public class ScannerView extends View {
//    public static final int GONE = 0, MOVING = 1, EXPANDING = 2, STAY_EXPANDED = 3;
//    @IntDef({GONE, MOVING, EXPANDING, STAY_EXPANDED})
//    @Retention(RetentionPolicy.SOURCE)
//    public  @interface ScannerState {}
//    private @ScannerState
//    int mState = GONE;
    //备用Paint
    private Paint sPaint;
    //用于存储上次位置信息
    private RectF lastRegion = new RectF();
    //用于存储目标位置信息
    private RectF sRegion = new RectF();
    //当前View的位置信息
    private RectF layoutRegion = new RectF();
    //当前扫描目标的索引，备用
    private int scanIndex = 0;

    public ScannerView(Context context, float sLeft, float sTop, float sBottom, float sRight) {
        super(context);
        sRegion.left = sLeft;
        sRegion.top = sTop;
        sRegion.bottom = sBottom;
        sRegion.right = sRight;
        init();
    }

    public ScannerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        initPaint();
    }

    //备用
    private void initPaint() {
        sPaint = new Paint();
        sPaint.setStyle(Paint.Style.STROKE);
        sPaint.setColor(Color.RED);
        sPaint.setStrokeWidth(3);
    }

    //备用
    public void setsPaint(Paint sPaint) {
        this.sPaint = sPaint;
    }

    public void setScannerRegion(float left, float top, float bottom, float right){
        //上个目标
        lastRegion.set(sRegion.centerX(),sRegion.centerY(),sRegion.centerX(),sRegion.centerY());

        //目标区域
        sRegion.left = left;
        sRegion.top = top;
        sRegion.bottom = bottom;
        sRegion.right = right;

        //框区域
        layoutRegion.top = sRegion.centerY();
        layoutRegion.bottom = sRegion.centerY();
        layoutRegion.left = sRegion.centerX();
        layoutRegion.right = sRegion.centerX();

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
        layoutParams.width = 0;
        layoutParams.height = 0;
        setLayoutParams(layoutParams);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(widthSize,heightSize);
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * 因为View没有自带的set方法可以直接一起设置View的上下左右位置，所以这里封装Layout方法来实现这个效果。
     * @param lr View的Layout目标区域
     */
    public void setLayoutRegion(RectF lr){
        layoutRegion.set(lr);
        layout((int)lr.left,(int)lr.top,(int)lr.right,(int)lr.bottom);
    }

    public void setScannerRegion(RectF region){
        setScannerRegion(region.left,region.top,region.bottom,region.right);
    }

    public void reset(){
        sRegion.left = 0;
        sRegion.right = 0;
        sRegion.top = 0;
        sRegion.bottom = 0;
    }


    public Paint getsPaint() {
        return sPaint;
    }

    public void setScanIndex(int scanIndex) {
        this.scanIndex = scanIndex;
    }

    public int getScanIndex() {
        return scanIndex;
    }

    public RectF getsRegion() {
        return sRegion;
    }

    public RectF getLayoutRegion() {
        return layoutRegion;
    }

    public RectF getLastRegion() {
        return lastRegion;
    }

//    public void setState(@ScannerState int mState) {
//        this.mState = mState;
//    }
//
//    public int getState() {
//        return mState;
//    }
}
