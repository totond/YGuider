package com.yanzhikai.guiderview.interfaces;

/**
 * 各种点击事件的回调
 */

public interface OnGuiderClickListener {
    /**
     * 当遮罩层空白处被点击时候的回调
     */
    void onMaskClick();

    /**
     * 当下一步按钮被点击时的回调
     * @param nextIndex 下一目标的index
     */
    void onNextClick(int nextIndex);

    /**
     * 当前高亮/扫描目标区域被点击时的回调
     * @param index 当前目标区域的index
     */
    void onTargetClick(int index);

    /**
     * 当跳过按钮被点击时的回调
     */
    void onJumpClick();
}
