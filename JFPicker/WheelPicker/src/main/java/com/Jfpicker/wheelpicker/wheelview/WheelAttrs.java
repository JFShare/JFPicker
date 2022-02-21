package com.Jfpicker.wheelpicker.wheelview;

import android.graphics.Color;

/**
 * @author Created by JF on  2021/11/10
 * @description WheelView所有的属性类，默认样式
 */

public class WheelAttrs {

    private int dividerColor = Color.parseColor("#dedede");
    private float dividerSize = 35;
    private int itemCount = 3;
    private float itemSize = 35;
    private int textColor = Color.parseColor("#999999");
    private int textColorCenter = Color.parseColor("#333333");
    private int wheelGravity = WheelDecoration.GRAVITY_CENTER;
    private int textGravity = WheelDecoration.GRAVITY_CENTER;
    private float textSize = 18;
    private float gravityCoefficient = 0.75F;
    private boolean isWheel = true;
    private float itemDegreeTotal = 180.f;
    private boolean alphaGradient = true;
    private boolean isTextBlod = false;
    private boolean isCenterTextBlod = false;
    private boolean textSizeGradient = false;
    private float minGradientTextSize = 10;

    //非3D效果的WheelView默认样式
    public static WheelAttrs getDefaultNoWheelAttrs() {
        WheelAttrs wheelAttrs = new WheelAttrs();
        wheelAttrs.setWheel(false);
        wheelAttrs.setDividerSize(50);
        wheelAttrs.setItemSize(50);
        wheelAttrs.setItemCount(2);
        return wheelAttrs;
    }

    public static class Builder {
        WheelAttrs wheelAttrs;

        public Builder() {
            wheelAttrs = new WheelAttrs();
        }

        public Builder getDefaultNoWheelAttrs() {
            wheelAttrs = WheelAttrs.getDefaultNoWheelAttrs();
            return this;
        }

        public Builder setDividerColor(int dividerColor) {
            wheelAttrs.dividerColor = dividerColor;
            return this;
        }

        public Builder setDividerSize(float dividerSize) {
            wheelAttrs.dividerSize = dividerSize;
            return this;
        }

        public Builder setItemCount(int itemCount) {
            wheelAttrs.itemCount = itemCount;
            return this;
        }

        public Builder setItemSize(float itemSize) {
            wheelAttrs.itemSize = itemSize;
            return this;
        }

        public Builder setTextColor(int textColor) {
            wheelAttrs.textColor = textColor;
            return this;
        }

        public Builder setTextColorCenter(int textColorCenter) {
            wheelAttrs.textColorCenter = textColorCenter;
            return this;
        }

        public Builder setWheelGravity(int wheelGravity) {
            wheelAttrs.wheelGravity = wheelGravity;
            return this;
        }

        public Builder setTextGravity(int textGravity) {
            wheelAttrs.textGravity = textGravity;
            return this;
        }

        public Builder setTextSize(float textSize) {
            wheelAttrs.textSize = textSize;
            return this;
        }

        public Builder setGravityCoefficient(float gravityCoefficient) {
            wheelAttrs.gravityCoefficient = gravityCoefficient;
            return this;
        }

        public Builder setIsWheel(boolean isWheel) {
            wheelAttrs.isWheel = isWheel;
            return this;
        }

        public Builder setItemDegreeTotal(float itemDegreeTotal) {
            wheelAttrs.itemDegreeTotal = itemDegreeTotal;
            return this;
        }


        public Builder setAlphaGradient(boolean alphaGradient) {
            wheelAttrs.alphaGradient = alphaGradient;
            return this;
        }

        public Builder setTextBlod(boolean textBlod) {
            wheelAttrs.isTextBlod = textBlod;
            return this;
        }

        public Builder setCenterTextBlod(boolean centerTextBlod) {
            wheelAttrs.isCenterTextBlod = centerTextBlod;
            return this;
        }

        public Builder setTextSizeGradient(boolean textSizeGradient) {
            wheelAttrs.textSizeGradient = textSizeGradient;
            return this;
        }

        public Builder setMinGradientTextSize(float minGradientTextSize) {
            wheelAttrs.minGradientTextSize = minGradientTextSize;
            return this;
        }

        public WheelAttrs build() {
            return wheelAttrs;
        }

    }


    public int getDividerColor() {
        return dividerColor;
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
    }

    public float getDividerSize() {
        return dividerSize;
    }

    public void setDividerSize(float dividerSize) {
        this.dividerSize = dividerSize;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public float getItemSize() {
        return itemSize;
    }

    public void setItemSize(float itemSize) {
        this.itemSize = itemSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextColorCenter() {
        return textColorCenter;
    }

    public void setTextColorCenter(int textColorCenter) {
        this.textColorCenter = textColorCenter;
    }

    public int getWheelGravity() {
        return wheelGravity;
    }

    public void setWheelGravity(int wheelGravity) {
        this.wheelGravity = wheelGravity;
    }

    public int getTextGravity() {
        return textGravity;
    }

    public void setTextGravity(int textGravity) {
        this.textGravity = textGravity;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getGravityCoefficient() {
        return gravityCoefficient;
    }

    public void setGravityCoefficient(float gravityCoefficient) {
        this.gravityCoefficient = gravityCoefficient;
    }

    public boolean isWheel() {
        return isWheel;
    }

    public void setWheel(boolean wheel) {
        isWheel = wheel;
    }


    public float getItemDegreeTotal() {
        return itemDegreeTotal;
    }

    public void setItemDegreeTotal(float itemDegreeTotal) {
        this.itemDegreeTotal = itemDegreeTotal;
    }


    public boolean isAlphaGradient() {
        return alphaGradient;
    }

    public void setAlphaGradient(boolean alphaGradient) {
        this.alphaGradient = alphaGradient;
    }

    public boolean isTextBlod() {
        return isTextBlod;
    }

    public void setTextBlod(boolean textBlod) {
        isTextBlod = textBlod;
    }

    public boolean isCenterTextBlod() {
        return isCenterTextBlod;
    }

    public void setCenterTextBlod(boolean centerTextBlod) {
        isCenterTextBlod = centerTextBlod;
    }

    public boolean isTextSizeGradient() {
        return textSizeGradient;
    }

    public void setTextSizeGradient(boolean textSizeGradient) {
        this.textSizeGradient = textSizeGradient;
    }

    public float getMinGradientTextSize() {
        return minGradientTextSize;
    }

    public void setMinGradientTextSize(float minGradientTextSize) {
        this.minGradientTextSize = minGradientTextSize;
    }

}
