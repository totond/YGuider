package com.yanzhikai.guiderview.Views;

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


    public MaskLayout(Context context, YGuider yGuider) {
        super(context);
        mYGuider = yGuider;
        init(context);
    }


    private void init(Context context) {
        mContext = context;
        checkAPILevel();
//        setBackgroundResource(R.color.colorTransparency);
        setOnClickListener(this);
        setWillNotDraw(false);
        initPaint();
        initScanner();
        mGuidePopupWindow = new GuidePopupWindow(mContext);
        mGuidePopupWindow.setContentBackgroundId(R.drawable.dialog_shape);
        mGuidePopupWindow.setOnWindowClickListener(this);

        if (mChangedListener != null){
            mChangedListener.onGuiderStart();
        }

//        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                onNext();
//            }
//        });

    }

    private void checkAPILevel(){
        if (Build.VERSION.SDK_INT < 18){
            setLayerType(LAYER_TYPE_NONE,null);
        }
    }

    private void initPaint() {
        sPaint = new Paint();
        sPaint.setAlpha(60);
        sPaint.setStyle(Paint.Style.STROKE);

    }

    private void initScanner(){
        mScannerList = new ArrayList<>();
        mScanTargets = new ArrayList<>();

        ScannerView scannerView1 = new ScannerView(mContext,0,0,0,0);
//        LayoutParams layoutParams1 = new LayoutParams(50,50);
//        scannerView1.setLayoutParams(layoutParams1);
        scannerView1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: asdasdasdas");
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

        Log.d("guiderviewV", "onDraw: ");
        Log.d(TAG, "onDraw: canvas()" + canvas.toString());
        for (int i = 0; i < getChildCount(); i++){
            ScannerView child = (ScannerView) getChildAt(i);

            if (!isMoving){
                clipHighlight(canvas,child.getsRegion());
            }else {
                canvas.drawColor(Color.argb(188,33,33,33));
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
        canvas.drawColor(Color.argb(188,33,33,33));
        canvas.restore();
    }

    private void drawScannerLine(Canvas canvas, ScannerView view){
        float y = view.getY() + view.getHeight()/2;
        float x = view.getX() + view.getWidth()/2;

        canvas.drawRect(view.getLeft(),view.getTop(),view.getRight(),view.getBottom(),view.getsPaint());
        canvas.drawLine(0,y,view.getLeft(),y,view.getsPaint());
        canvas.drawLine(view.getRight(),y,canvas.getWidth(),y,view.getsPaint());
        canvas.drawLine(x,0,x,view.getTop(),view.getsPaint());
        canvas.drawLine(x,view.getBottom(),x,canvas.getHeight(),view.getsPaint());

//        switch (view.getState()) {
//            case ScannerView.MOVING:
//                Log.d(TAG, "drawScannerLine: MOVING");
//                canvas.drawRect(view.getLeft(),view.getTop(),view.getRight(),view.getBottom(),view.getsPaint());
//                canvas.drawLine(0,y,view.getLeft(),y,view.getsPaint());
//                canvas.drawLine(view.getRight(),y,canvas.getWidth(),y,view.getsPaint());
//                canvas.drawLine(x,0,x,view.getTop(),view.getsPaint());
//                canvas.drawLine(x,view.getBottom(),x,canvas.getHeight(),view.getsPaint());
//                break;
//            case ScannerView.EXPANDING:
//                Log.d(TAG, "drawScannerLine: EXPANDING" + view.getSLeft());
//                canvas.drawRect(view.getLeft(),view.getTop(),view.getRight(),view.getBottom(),view.getsPaint());
//                canvas.drawLine(0,y,view.getLeft(),y,view.getsPaint());
//                canvas.drawLine(view.getRight(),y,canvas.getWidth(),y,view.getsPaint());
//                canvas.drawLine(x,0,x,view.getTop(),view.getsPaint());
//                canvas.drawLine(x,view.getBottom(),x,canvas.getHeight(),view.getsPaint());
//                break;
//        }

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

    private void setAnimator(final ScannerView scannerView, RectF toRegion){
        mScannerList.get(0).setScannerRegion(toRegion);

        ObjectAnimator moveRAnimator = ObjectAnimator.ofObject(scannerView,"layoutRegion",new RegionEvaluator(),scannerView.getLastRegion(),getCenterRectF(toRegion));


        ObjectAnimator LRAnimator = ObjectAnimator.ofObject(scannerView,"layoutRegion",new RegionEvaluator(),scannerView.getLayoutRegion(),scannerView.getsRegion());

        AnimatorSet moveAnimator = new AnimatorSet();
//        moveAnimator.playTogether(objectAnimatorX,objectAnimatorY);


        AnimatorSet scaleAnimator = new AnimatorSet();
        scaleAnimator.play(LRAnimator);
//        scaleAnimator.playTogether(objectAnimatorTop,objectAnimatorLeft,objectAnimatorBottom,objectAnimatorRight);

        AnimatorSet doAnimator = new AnimatorSet();
        doAnimator.play(moveRAnimator).before(scaleAnimator);

        moveRAnimator.setDuration(mScanTargets.get(scanIndex).getMoveDuration());
        scaleAnimator.setDuration(mScanTargets.get(scanIndex).getScaleDuration());
        moveRAnimator.addListener(new Animator.AnimatorListener() {
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
