package com.yanzhikai.guiderview.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2017/9/26 0026.
 */

public class ScannerView extends View {
    public static final int GONE = 0, MOVING = 1, EXPANDING = 2, STAY_EXPANDED = 3;
    @IntDef({GONE, MOVING, EXPANDING, STAY_EXPANDED})
    @Retention(RetentionPolicy.SOURCE)
    public  @interface ScannerState {}
    private @ScannerState
    int mState = GONE;

    private Paint sPaint;
    private RectF lastRegion = new RectF();
    private RectF sRegion = new RectF();
    private RectF layoutRegion = new RectF();
    private float sLeft = 0, sTop = 0, sRight = 0, sBottom = 0;
    public float lastLeft = 0, lastTop = 0, lastRight = 0, lastBottom = 0;

    private float lastCenterX = 0, lastCenterY = 0;
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
//        setBackgroundColor(Color.BLUE);
    }



    private void initPaint() {
        sPaint = new Paint();
        sPaint.setStyle(Paint.Style.STROKE);
        sPaint.setColor(Color.RED);
        sPaint.setStrokeWidth(3);
    }

    public void setsPaint(Paint sPaint) {
        this.sPaint = sPaint;
    }

    public void setScannerRegion(float left, float top, float bottom, float right){
        lastLeft = sRegion.left;
        lastRight = sRegion.right;
        lastTop = sRegion.top;
        lastBottom = sRegion.bottom;

        lastCenterX = sRegion.centerX();
        lastCenterY = sRegion.centerY();
        lastRegion.set(sRegion.centerX(),sRegion.centerY(),sRegion.centerX(),sRegion.centerY());

        sRegion.left = left;
        sRegion.top = top;
        sRegion.bottom = bottom;
        sRegion.right = right;

        //框区域
        layoutRegion.top = this.sTop = sRegion.centerY();
        layoutRegion.bottom = this.sBottom = sRegion.centerY();
        layoutRegion.left = this.sLeft = sRegion.centerX();
        layoutRegion.right = this.sRight = sRegion.centerX();

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
        layoutParams.width = 0;
        layoutParams.height = 0;
        setLayoutParams(layoutParams);
        Log.d("yguiderview", "onMeasure: bbbb");


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.d("yguiderview", "onMeasure: child heightSize" + heightSize);
        setMeasuredDimension(widthSize,heightSize);
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("yguiderview", "onMeasure: child width" + getHeight());
        Log.d("yguiderview", "onMeasure: child measureheigh" + getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d("Scanner", "onLayout: ");
        super.onLayout(changed, left, top, right, bottom);
    }

    public void setLayoutRegion(RectF lr){
        layoutRegion.set(lr);
        layout((int)lr.left,(int)lr.top,(int)lr.right,(int)lr.bottom);
    }



    public void setScannerRegion(RectF region){
        setScannerRegion(region.left,region.top,region.bottom,region.right);
    }



    public void reset(){
        lastLeft = 0;
        lastRight = 0;
        lastTop = 0;
        lastBottom = 0;
        sRegion.left = 0;
        sRegion.right = 0;
        sRegion.top = 0;
        sRegion.bottom = 0;
    }



    public void setLastCenterX(float lastCenterX) {
        this.lastCenterX = lastCenterX;
    }

    public void setLastCenterY(float lastCenterY) {
        this.lastCenterY = lastCenterY;
    }

    public float getLastCenterX() {
        return lastCenterX;
    }

    public float getLastCenterY() {
        return lastCenterY;
    }

    public Paint getsPaint() {
        return sPaint;
    }

    public void setSLeft(float sLeft) {
        this.sLeft = sLeft;
    }

    public void setSBottom(float sBottom) {
        this.sBottom = sBottom;
    }

    public void setSRight(float sRight) {
        this.sRight = sRight;
    }

    public void setSTop(float sTop) {
        this.sTop = sTop;
    }

    public float getSTop() {
        return sTop;
    }

    public float getSLeft() {
        return sLeft;
    }

    public float getSBottom() {
        return sBottom;
    }

    public float getSRight() {
        return sRight;
    }

    public void setScanIndex(int scanIndex) {
        this.scanIndex = scanIndex;
    }

    public int getScanIndex() {
        return scanIndex;
    }

    public void setState(@ScannerState int mState) {
        this.mState = mState;
    }

    public int getState() {
        return mState;
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
}
