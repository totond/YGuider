package com.yanzhikai.guiderview.interfaces;

/**
 * Created by Administrator on 2017/9/27 0027.
 */

public interface OnGuiderClickListener {
    void onMaskClick();
    void onNextClick(int nextIndex);
    void onTargetClick(int index);
    void onJumpClick();
}
