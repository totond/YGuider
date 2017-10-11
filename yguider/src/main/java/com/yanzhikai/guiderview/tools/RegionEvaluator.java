package com.yanzhikai.guiderview.tools;

import android.animation.TypeEvaluator;
import android.graphics.RectF;
import android.util.Log;

/**
 * Created by Administrator on 2017/10/9 0009.
 */

public class RegionEvaluator implements TypeEvaluator {
    private RectF startRegion, endRegion;

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        startRegion = (RectF) startValue;
        endRegion = (RectF) endValue;
        float dl = fraction * (endRegion.left - startRegion.left) + startRegion.left;
        float dt = fraction * (endRegion.top - startRegion.top) + startRegion.top;
        float dr = fraction * (endRegion.right - startRegion.right) + startRegion.right;
        float db = fraction * (endRegion.bottom - startRegion.bottom) + startRegion.bottom;
        return new RectF(dl,dt,dr,db);
    }
}
