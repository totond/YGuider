package com.yanzhikai.guiderview;

import android.app.Activity;
import android.content.Context;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by Administrator on 2017/9/29 0029.
 */

public class YGuideBuilder {
    private YGuider mYGuider;

    public YGuideBuilder(Activity context){
        mYGuider = new YGuider(context);
    }

    public YGuideBuilder addNextHighlight(View itemView, String text, int wOffsetX, int wOffsetY){
        mYGuider.addNextHighlight(itemView,text,wOffsetX,wOffsetY);
        return this;
    }

    public YGuideBuilder addNextHighlight(View itemView, String text, int wOffsetX, int wOffsetY, int wWidth, int wHeight){
        mYGuider.addNextHighlight(itemView,text,wOffsetX,wOffsetY,wWidth,wHeight);
        return this;
    }

    public YGuideBuilder addNextHighlight(View itemView, String text, int wOffsetX, int wOffsetY, int wWidth, int wHeight, String jumpText, String nextText){
        mYGuider.addNextHighlight(itemView,text,wOffsetX,wOffsetY,wWidth,wHeight,jumpText,nextText);
        return this;
    }

    public YGuideBuilder addNextHighlight(RectF highlightRegion, String text, int wOffsetX, int wOffsetY){
        mYGuider.addNextHighlight(highlightRegion,text,wOffsetX,wOffsetY);
        return this;
    }

    public YGuideBuilder addNextHighlight(RectF highlightRegion, String text, int wOffsetX, int wOffsetY, int wWidth, int wHeight){
        mYGuider.addNextHighlight(highlightRegion,text,wOffsetX,wOffsetY,wWidth,wHeight);
        return this;
    }

    public YGuideBuilder addNextHighlight(RectF highlightRegion, String text, int wOffsetX, int wOffsetY, int wWidth, int wHeight, String jumpText, String nextText){
        mYGuider.addNextHighlight(highlightRegion,text,wOffsetX,wOffsetY,wWidth,wHeight,jumpText,nextText);
        return this;
    }

    public YGuider build(){
        mYGuider.prepareTarget();
        return mYGuider;
    }
}
