package com.yanzhikai.guiderview.views;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yanzhikai.guiderview.R;
import com.yanzhikai.guiderview.tools.RegionEvaluator;
import com.yanzhikai.guiderview.beans.ScanTarget;
import com.yanzhikai.guiderview.YGuider;
import com.yanzhikai.guiderview.interfaces.OnGuiderChangedListener;
import com.yanzhikai.guiderview.interfaces.OnGuiderClickListener;
import com.yanzhikai.guiderview.interfaces.OnGuiderListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/25 0025.
 */

public class MaskLayout extends RelativeLayout implements View.OnClickListener,GuidePopupWindow.OnWindowClickListener {
    public static final String TAG = "guiderview";
    private Context mContext;
    private YGuider mYGuider;
    private Paint sPaint;
    private ArrayList<ScannerView> mScannerList;
    private ArrayList<ScanTarget> mScanTargets;
    private boolean isMoving = false;
    private int scanIndex = 0;
    private OnGuiderClickListener mClickListener;
    private GuidePopupWindow mGuidePopupWindow;
    private OnGuiderChangedListener mChangedListener;
    private @ColorInt int mMaskColor;


    public MaskLayout(Context context, YGuider yGuider) {
        super(context);
        mYGuider = yGuider;
        init(context);
    }


    private void init(Context context) {
        mContext = context;
        checkAPILevel();
        setOnClickListener(this);
        setWillNotDraw(false);

        mMaskColor = mContext.getResources().getColor(R.color.colorTransparency);
//        mMaskColor = Color.argb(99,99,99,99);

        initPaint();
        initScanner();
        mGuidePopupWindow = new GuidePopupWindow(mContext);
        mGuidePopupWindow.setContentBackgroundId(R.drawable.dialog_shape);
        mGuidePopupWindow.setOnWindowClickListener(this);

        if (mChangedListener != null){
            mChangedListener.onGuiderStart();
        }

    }

    //API小于18则关闭硬件加速，否则clipRect()方法不生效
    private void checkAPILevel(){
        if (Build.VERSION.SDK_INT < 18){
            setLayerType(LAYER_TYPE_NONE,null);
        }
    }

    private void initPaint() {
        sPaint = new Paint();
        sPaint.setStyle(Paint.Style.STROKE);
        sPaint.setColor(Color.RED);
        sPaint.setStrokeWidth(3);
    }

    private void initScanner(){
        mScannerList = new ArrayList<>();
        mScanTargets = new ArrayList<>();

        ScannerView scannerView1 = new ScannerView(mContext,0,0,0,0);
        scannerView1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onTargetClick(scanIndex - 1);
            }
        });
        addView(scannerView1);
        mScannerList.add(scannerView1);

//        ScannerView scannerView2 = new ScannerView(mContext);
////        LayoutParams layoutParams2 = new LayoutParams(50,50);
////        scannerView1.setLayoutParams(layoutParams2);
//        addView(scannerView2);
//        mScannerList.add(scannerView2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("guiderviewV", "onMeasure: ");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d("guiderviewV", "onLayout: ");
        super.onLayout(changed, l, t, r, b);
    }

    //    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Log.d(TAG, "onMeasure: ");
//
//        int widthMode = MeasureSpec.AT_MOST;
//        int heightMode = MeasureSpec.AT_MOST;
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//
//        Log.d(TAG, "onMeasure: widthSize" + widthSize);
//        for (int i = 0; i < getChildCount(); i++){
//            View child = getChildAt(i);
////            measureChild(
////                    child
////                    ,MeasureSpec.makeMeasureSpec(child.getLayoutParams().width, MeasureSpec.EXACTLY)
////                    ,MeasureSpec.makeMeasureSpec(child.getLayoutParams().height, MeasureSpec.EXACTLY));
//            child.measure(
//                    MeasureSpec.makeMeasureSpec(child.getLayoutParams().width, MeasureSpec.EXACTLY)
//                    ,MeasureSpec.makeMeasureSpec(child.getLayoutParams().height, MeasureSpec.EXACTLY));
//        }
//
//        super.onMeasure(MeasureSpec.makeMeasureSpec(widthSize, widthMode), MeasureSpec.makeMeasureSpec(heightSize, heightMode));
////        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
////        setMeasuredDimension(widthSize,heightSize);
//
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//
////                break;
//            case MotionEvent.ACTION_UP:
//                return true;
//            case MotionEvent.ACTION_DOWN:
//                return false;
//
//        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return true;
    }

    //    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed,l,t,r,b);
//        for (int i = 0; i < getChildCount(); i++) {
//            ScannerView child = (ScannerView) getChildAt(i);
////            LayoutParams layoutParams = child.getLayoutParams();
////            child.layout((int) child.getsRegion().left
////                    ,(int) child.getsRegion().top
////                    ,(int) child.getsRegion().right
////                    ,(int) child.getsRegion().bottom);
//        }
//        Log.d(TAG, "onLayout: ");
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < getChildCount(); i++){
            ScannerView child = (ScannerView) getChildAt(i);

            if (!isMoving){
                clipHighlight(canvas,child.getsRegion());
            }else {
                canvas.drawColor(mMaskColor);
            }

            drawScannerLine(canvas,child);
        }

        if (isMoving){
            postInvalidateDelayed(20);
        }

    }

    private void clipHighlight(Canvas canvas,RectF rectF){
        canvas.save();
        canvas.clipRect(rectF, Region.Op.DIFFERENCE);
        canvas.drawColor(mMaskColor);
        canvas.restore();
    }

    private void drawScannerLine(Canvas canvas, ScannerView view){
        float y = view.getY() + view.getHeight()/2;
        float x = view.getX() + view.getWidth()/2;

//        canvas.drawRect(view.getLeft(),view.getTop(),view.getRight(),view.getBottom(),view.getsPaint());
        canvas.drawRect(view.getLeft(),view.getTop(),view.getRight(),view.getBottom(),sPaint);
        canvas.drawLine(0,y,view.getLeft(),y,sPaint);
        canvas.drawLine(view.getRight(),y,canvas.getWidth(),y,sPaint);
        canvas.drawLine(x,0,x,view.getTop(),sPaint);
        canvas.drawLine(x,view.getBottom(),x,canvas.getHeight(),sPaint);

    }

    @Override
    public void onClick(View v) {
//        onNext();

        if (mClickListener != null){
            mClickListener.onMaskClick();
        }
    }

    public void onNext(){
        if (scanIndex < mScanTargets.size()) {
            if ((mChangedListener != null)){
                mChangedListener.onGuiderNext(scanIndex);
            }
            setAnimator(mScannerList.get(0)
                    , mScanTargets.get(scanIndex).getRegion());
        }else {
            Log.d(TAG, "onGuiderFinished: ViewAction null");
            exit();
        }

    }

    private RectF getCenterRectF(RectF input){
        return new RectF(input.centerX(),input.centerY(),input.centerX(),input.centerY());
    }

    //设置属性动画，主要是将ScannerView从上一个地方移动到下一个目标然后放大
    private void setAnimator(final ScannerView scannerView, RectF toRegion){
        mScannerList.get(0).setScannerRegion(toRegion);

        ObjectAnimator moveAnimator = ObjectAnimator.ofObject(scannerView,"layoutRegion",new RegionEvaluator(),scannerView.getLastRegion(),getCenterRectF(toRegion));
        ObjectAnimator expandAnimator = ObjectAnimator.ofObject(scannerView,"layoutRegion",new RegionEvaluator(),scannerView.getLayoutRegion(),scannerView.getsRegion());

        AnimatorSet doAnimator = new AnimatorSet();
        doAnimator.play(moveAnimator).before(expandAnimator);

        moveAnimator.setDuration(mScanTargets.get(scanIndex).getMoveDuration());
        expandAnimator.setDuration(mScanTargets.get(scanIndex).getScaleDuration());

        moveAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                scannerView.setState(ScannerView.MOVING);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                scannerView.setState(ScannerView.EXPANDING);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        doAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mGuidePopupWindow.dismiss();

                isMoving = true;
                setClickable(false);
                postInvalidate();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isMoving = false;
                setClickable(true);
                showWindow();
                scanIndex ++;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        doAnimator.start();
    }

    private void showWindow(){
        ScanTarget scanTarget = mScanTargets.get(scanIndex);
        mGuidePopupWindow.setWidth(scanTarget.getWindowWidth());
        mGuidePopupWindow.setHeight(scanTarget.getWindowHeight());
        mGuidePopupWindow.setNextText(scanTarget.getNextText());
        mGuidePopupWindow.setJumpText(scanTarget.getJumpText());
        mGuidePopupWindow.showAsDropDown(
                mScannerList.get(0)
                ,mScanTargets.get(scanIndex).getwOffsetX()
                ,mScanTargets.get(scanIndex).getwOffsetY());
        mGuidePopupWindow.showGuideText(scanTarget.getShowText(),0);
    }

    public void setsPaint(Paint sPaint){
        this.sPaint = sPaint;
    }

    public void setMackColor(@ColorInt int color){
        mMaskColor = color;
    }

    public void setScanTargets(ArrayList<ScanTarget> scanTargets){
        mScanTargets = scanTargets;
    }

    public void setOnGuiderClickListener(OnGuiderClickListener onGuiderClickListener) {
        this.mClickListener = onGuiderClickListener;
    }

    public void setOnGuiderChangedListener(OnGuiderChangedListener mChangedListener) {
        this.mChangedListener = mChangedListener;
    }

    public void setOnGuiderListener(OnGuiderListener onGuiderListener){
        mChangedListener = onGuiderListener;
        mClickListener = onGuiderListener;

    }

    public GuidePopupWindow getWindow(){
        return mGuidePopupWindow;
    }

    @Override
    public void onNextClick() {
        if (mClickListener != null){
            mClickListener.onNextClick(scanIndex);
        }
        onNext();
    }

    public void exit(){
        if (mChangedListener != null){
            Log.d(TAG, "onGuiderFinished: ViewAction");
            mChangedListener.onGuiderFinished();
        }

        reset();

        mYGuider.setIsPreparing(false);
        ViewGroup parent = (ViewGroup)getParent();
        parent.removeView(this);
        mGuidePopupWindow.dismiss();
    }

    private void reset(){
        scanIndex = 0;
        mScannerList.get(0).reset();
    }

    @Override
    public void onJumpClick() {
        if (mClickListener != null){
            mClickListener.onJumpClick();
        }
        exit();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onNext();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow: ");
//        mContext = null;
//        mYGuider = null;
    }
}
