package com.yanzhikai.guiderview.interfaces;

/**
 * YGuider状态变化的回调
 */
public interface OnGuiderChangedListener {
    /**
     * 引导开始时候回调
     */
    void onGuiderStart();

    /**
     * 跳转到下一个目标时候的回调
     * @param nextIndex 下一个目标的index
     */
    void onGuiderNext(int nextIndex);

    /**
     * 引导完成之后的回调
     */
    void onGuiderFinished();
}
