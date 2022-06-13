
package com.Jfpicker.wheelpicker.dialog.annotation;
/**
 * 使用了AndroidPicker的Dialog代码，根据自身的需求做了相应的修改
 * 主要修改：DefaultDialogConfig定义全局的弹窗样式。通过构造方法传入 DialogConfig，定义私有的弹窗样式。
 * 源码地址：https://github.com/gzu-liyujiang/AndroidPicker
 */
public @interface CornerRound {
    int No = 0;
    int Top = 1;
    int All = 2;
}
