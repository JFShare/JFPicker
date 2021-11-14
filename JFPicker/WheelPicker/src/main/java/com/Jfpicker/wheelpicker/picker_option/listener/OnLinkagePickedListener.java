
package com.Jfpicker.wheelpicker.picker_option.listener;


public interface OnLinkagePickedListener {

    /**
     * 联动选择回调
     *
     * @param first  选中项的第一级条目内容
     * @param second 选中项的第二级条目内容
     * @param third  选中项的第三级条目内容
     */
    void onLinkagePicked(Object first, Object second, Object third);

}
