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
import com.yanzhikai.guiderview.interfaces.AbstractAnimatorListener;
import com.yanzhikai.guiderview.tools.RegionEvaluator;
import com.yanzhikai.guiderview.beans.ScanTarget;
import com.yanzhikai.guiderview.YGuider;
import com.yanzhikai.guiderview.interfaces.OnGuiderChangedListener;
import com.yanzhikai.guiderview.interfaces.OnGuiderClickListener;
import com.yanzhikai.guiderview.interfaces.OnGuiderListener;

import java.util.ArrayList;

/**
 * 用于覆盖Activity界面的遮罩
 */

public class MaskLayout extends RelativeLayout implements View.OnClickListener,GuidePopupWindow.OnWindowClickListener {
    public static final String TAG = "guiderview";
    private Context mContext;
    private YGuider mYGuider;
    private Paint sPaint;
    private ArrayList<ScannerView> mScannerList;
    private ArrayList<ScanTarget> mScanTargets;
    private boolean isDoingAnimation = false;
    private int scanIndex = 0;
    private OnGuiderClickListener mClickListener;
    private GuidePopupWindow mGuidePopupWindow;
    private OnGuiderChangedListener mChangedListener;
    private int mRefreshTime = 20;
    private int mMoveDuration = 500, mExpandDuration = 500;
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
        setLayerType(LAYER_TYPE_NONE,null);
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
                if (mClickListener != null) {
                    mClickListener.onTargetClick(scanIndex - 1);
                }
            }
        });
        addView(scannerView1);
        mScannerList.add(scannerView1);


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


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < getChildCount(); i++){
            ScannerView child = (ScannerView) getChildAt(i);

            if (!isDoingAnimation){
                clipHighlight(canvas,child.getsRegion());
            }else {
                canvas.drawColor(mMaskColor);
            }
            drawScannerLine(canvas,child);
        }

        if (isDoingAnimation){
            postInvalidateDelayed(mRefreshTime);
        }
    }

    //剪切出高亮部分，给其余部分上色
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
                    , mScanTargets.get(scanIndex));
        }else {
            exit();
        }

    }

    private RectF getCenterRectF(RectF input){
        return new RectF(input.centerX(),input.centerY(),input.centerX(),input.centerY());
    }

    //设置属性动画，主要是将ScannerView从上一个地方移动到下一个目标然后放大
    private void setAnimator(final ScannerView scannerView, ScanTarget target){
        mScannerList.get(0).setScannerRegion(target.getRegion());

        ObjectAnimator moveAnimator = ObjectAnimator.ofObject(scannerView,"layoutRegion",new RegionEvaluator(),scannerView.getLastRegion(),getCenterRectF(target.getRegion()));
        ObjectAnimator expandAnimator = ObjectAnimator.ofObject(scannerView,"layoutRegion",new RegionEvaluator(),scannerView.getLayoutRegion(),scannerView.getsRegion());

        moveAnimator.setDuration(mMoveDuration);
        expandAnimator.setDuration(mExpandDuration);

        AnimatorSet doAnimator = new AnimatorSet();
        doAnimator.play(expandAnimator).after(moveAnimator);

        doAnimator.addListener(new AbstractAnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mGuidePopupWindow.dismiss();
                setClickable(false);
                //启动不断draw来刷新扫描框移动的画面
                isDoingAnimation = true;
                postInvalidate();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isDoingAnimation = false;
                setClickable(true);
                showWindow();
                scanIndex ++;
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
        mGuidePopupWindow.showGuideText(scanTarget.getShowText());
    }

    public void setRefreshTime(int refreshTime) {
        this.mRefreshTime = refreshTime;
    }

    public int getRefreshTime() {
        return mRefreshTime;
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

    public void setMoveDuration(int moveDuration) {
        this.mMoveDuration = moveDuration;
    }

    public void setExpandDuration(int expandDuration) {
        this.mExpandDuration = expandDuration;
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
    }
}
