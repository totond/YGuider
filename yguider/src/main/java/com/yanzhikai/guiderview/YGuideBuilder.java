package com.yanzhikai.guiderview;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Administrator on 2017/9/29 0029.
 */

public class YGuideBuilder {
    private YGuider mYGuider;

    public YGuideBuilder(Activity context){
        mYGuider = new YGuider(context);
    }


    public YGuider build(){
        return mYGuider;
    }
}
