package com.yanzhikai.guiderview.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yanzhikai.guiderview.R;

import java.util.Random;

/**
 * Typer Effect
 * Created by hanks on 2017/3/15.
 * Modified by yanzhikai on 2017/9/22
 * 修改自hanks的https://github.com/hanks-zyh/HTextView
 * 新增了可以立刻显示全部的方法
 * 改成可以重复播放
 * 加入了播放状态的回调接口
 */

public class TyperTextView extends TextView {

    public static final String TAG ="typertextview";
    public static final int SHOWING = 0x767;
    public static final int SHOW_ALL = 0x167;
    private Random random;
    private CharSequence mText;
    private ShowHandler handler;
    private int charIncrease;
    private int showingIncrease;
    private int typerRefreshTime;
    private AnimationListener animationListener;

    public TyperTextView(Context context) {
        this(context, null);
    }

    public TyperTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TyperTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TyperTextView);
        typerRefreshTime = typedArray.getInt(R.styleable.TyperTextView_typerRefreshTime, 100);
        charIncrease = typedArray.getInt(R.styleable.TyperTextView_charIncrease, 1);
        typedArray.recycle();

        //使用随机数，增加飘忽感
        random = new Random();
        mText = getText();
        handler = new ShowHandler();
    }

    public void setAnimationListener(AnimationListener listener) {
        animationListener = listener;
    }


    public int getTyperRefreshTime() {
        return typerRefreshTime;
    }

    public void setTyperRefreshTime(int typerRefreshTime) {
        this.typerRefreshTime = typerRefreshTime;
    }

    public int getCharIncrease() {
        return charIncrease;
    }

    public void setCharIncrease(int charIncrease) {
        this.charIncrease = charIncrease;
    }

    public void setProgress(float progress) {
        setText(mText.subSequence(0, (int) (mText.length() * progress)));
    }

    public void animateText(CharSequence text) {
        if (text == null) {
            throw new RuntimeException("text must not be null");
        }

        mText = text;
        showingIncrease = charIncrease;
        setText("");
        Message message = Message.obtain();
        message.what = SHOWING;
        handler.sendMessage(message);
    }

    public void showAll(){
        handler.removeMessages(SHOWING);
        Message message = Message.obtain();
        message.what = SHOW_ALL;
        handler.sendMessage(message);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacksAndMessages(null);
    }

    private class ShowHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            int currentLength = getText().length();
            if (currentLength + showingIncrease > mText.length()) {
                showingIncrease = mText.length() - currentLength;
            }
            switch (msg.what){
                case SHOWING:
                    if (currentLength < mText.length()) {

                        append(mText.subSequence(currentLength, currentLength + showingIncrease));

                        long randomTime = typerRefreshTime + random.nextInt(typerRefreshTime);
                        Message message = Message.obtain();
                        message.what = SHOWING;
                        handler.sendMessageDelayed(message, randomTime);
                    } else {
                        if(animationListener != null) {
                            animationListener.onAnimationEnd(TyperTextView.this);
                        }
                    }
                    break;
                case SHOW_ALL:
                    setText(mText);
                    break;
            }
        }
    }

    public interface AnimationListener {
        void onAnimationProceeding(TyperTextView typerTextView);
        void onAnimationEnd(TyperTextView typerTextView);
    }
}