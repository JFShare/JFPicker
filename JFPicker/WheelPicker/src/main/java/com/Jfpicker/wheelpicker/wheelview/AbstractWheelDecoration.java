package com.Jfpicker.wheelpicker.wheelview;

import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Jfpicker.wheelpicker.wheelview.layoutmanager.VerticalLooperLayoutManager;

import java.util.ArrayList;

/**
 * @author Created by JF on  2021/11/10
 * @description 继承自RecyclerView.ItemDecoration的抽象类，主要实现3D滚轮的效果，文字和间隔样式由子类实现
 */
abstract class AbstractWheelDecoration extends RecyclerView.ItemDecoration {

    /**
     * 无效的位置
     */
    public static final int IDLE_POSITION = -1;


    private WheelAttrs attrs;
    float haltItemDegreeTotal;
    float halfItemHeight;
    float itemPreDegree;
    float wheelRadio;

    /**
     * 3D旋转
     */
    Camera camera;
    Matrix matrix;


    int centerRealPosition = IDLE_POSITION;


    AbstractWheelDecoration(WheelAttrs attrs) {
        this.attrs = attrs;
        this.haltItemDegreeTotal = attrs.getItemDegreeTotal() / 2;
        this.halfItemHeight = attrs.getItemSize() / 2.0f;
        this.itemPreDegree = attrs.getItemDegreeTotal() / (attrs.getHalfItemCount() * 2 + 1);
        wheelRadio = (float) radianToRadio(attrs.getItemSize(), itemPreDegree);

        camera = new Camera();
        matrix = new Matrix();
    }


    @Override
    public final void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        centerRealPosition = IDLE_POSITION;
        if (parent.getLayoutManager() == null) return;
        Rect parentRect = new Rect(parent.getLeft(), parent.getTop(), parent.getRight(), parent.getBottom());
        //绘制分割线背景
        drawDividerBg(c, parentRect);
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager llm = (LinearLayoutManager) parent.getLayoutManager();
            //计算中心item
            int startPosition = llm.findFirstVisibleItemPosition();
            if (startPosition < 0) return;
            int endPosition = llm.findLastVisibleItemPosition();
            centerRealPosition = IDLE_POSITION;
            for (int itemPosition = startPosition; itemPosition <= endPosition; itemPosition++) {
                //除去空白项
                if (itemPosition < attrs.getHalfItemCount()) continue;
                if (itemPosition >= llm.getItemCount() - attrs.getHalfItemCount()) break;
                //列表项组件
                View itemView = llm.findViewByPosition(itemPosition);
                Rect itemRect = new Rect(itemView.getLeft(), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                float itemCenterY = itemRect.exactCenterY();
                // scrollOffY : 每一列表项的中心点 和 RecyclerView 中心点的 实际偏移距离
                float scrollOffY = itemCenterY - parentRect.exactCenterY();
                //数据中的实际位置
                int realPosition = itemPosition - attrs.getHalfItemCount();
                //计算中心item, 优先最靠近中心区域的为中心点
                if (Math.abs(scrollOffY) <= halfItemHeight) {
                    centerRealPosition = realPosition;
                    break;
                }
            }

            for (int itemPosition = startPosition; itemPosition <= endPosition; itemPosition++) {
                //除去空白项
                if (itemPosition < attrs.getHalfItemCount()) continue;
                if (itemPosition >= llm.getItemCount() - attrs.getHalfItemCount()) break;
                View itemView = llm.findViewByPosition(itemPosition);
                Rect itemRect = new Rect(itemView.getLeft(), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                //绘制滚轮项
                drawWheel(c, itemRect, itemPosition - attrs.getHalfItemCount(),
                        parentRect.exactCenterX(), parentRect.exactCenterY(), centerRealPosition + attrs.getHalfItemCount() - itemPosition);
            }
        } else if (parent.getLayoutManager() instanceof VerticalLooperLayoutManager) {
            VerticalLooperLayoutManager vlM = (VerticalLooperLayoutManager) parent.getLayoutManager();
            centerRealPosition = IDLE_POSITION;
            //获取屏幕内可见的列表项View
            ArrayList<View> screenViews = vlM.findScreenViews();
            int centerViewListPosition = IDLE_POSITION;
            for (int i = 0; i < screenViews.size(); i++) {
                Rect itemRect = new Rect(screenViews.get(i).getLeft(), screenViews.get(i).getTop(),
                        screenViews.get(i).getRight(), screenViews.get(i).getBottom());
                float itemCenterY = itemRect.exactCenterY();
                // scrollOffY : 每一列表项的中心点 和 RecyclerView 中心点的 实际偏移距离
                float scrollOffY = itemCenterY - parentRect.exactCenterY();
                //数据中的实际位置
                int realPosition = vlM.getPosition(screenViews.get(i));
                //计算中心item, 优先最靠近中心区域的为中心点
                if (Math.abs(scrollOffY) <= halfItemHeight) {
                    centerRealPosition = realPosition;
                    centerViewListPosition = i;
                    break;
                }
            }

            for (int i = 0; i < screenViews.size(); i++) {
                Rect itemRect = new Rect(screenViews.get(i).getLeft(), screenViews.get(i).getTop(),
                        screenViews.get(i).getRight(), screenViews.get(i).getBottom());
                //绘制滚轮项
                drawWheel(c, itemRect, vlM.getPosition(screenViews.get(i)), parentRect.exactCenterX(), parentRect.exactCenterY(),
                        centerViewListPosition - i);
            }
        }

        //绘制分割线
        drawDivider(c, parentRect);
    }

    /**
     * 绘制滚轮效果
     * adapterPosition 当前列表项对应的数据源位置
     * centerOffset 居中列表列表项位置 - 当前列表项位置
     */
    void drawWheel(Canvas c, Rect rect, int adapterPosition, float parentCenterX, float parentCenterY, int centerOffset) {

        float itemCenterY = rect.exactCenterY();
        float scrollOffY = itemCenterY - parentCenterY;
        float rotateDegreeX = scrollOffY * itemPreDegree / attrs.getItemSize();
        c.save();
        if (attrs.isWheel()) {
            float rotateSinX = (float) Math.sin(Math.toRadians(rotateDegreeX));
            float rotateOffY = scrollOffY - wheelRadio * rotateSinX;
            //平移画布，使靠外的项贴近中间项，不至于因为滚轮的圆弧效果间距过大，实现因旋转导致界面视角的偏移，为了更好的滚轮显示效果
            if (attrs.isTranslateYZ()) {
                c.translate(0.0f, -rotateOffY);
            }
            camera.save();
            //平移z轴，为了更好的滚轮显示效果
            if (attrs.isTranslateYZ()) {
                float z = (float) (wheelRadio * (1 - Math.abs(Math.cos(Math.toRadians(rotateDegreeX)))));
                camera.translate(0, 0, z);
            }
            //旋转x轴实现圆弧效果。如果不旋转x轴，就没有圆弧效果，只是平铺而已
            camera.rotateX(-rotateDegreeX);
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate(-parentCenterX, -itemCenterY);
            matrix.postTranslate(parentCenterX, itemCenterY);
            c.concat(matrix);
        }

        drawItem(c, rect, adapterPosition, centerOffset);
        c.restore();
    }


    /**
     * 画item,  如何画法可以在此方法中实现
     */
    abstract void drawItem(Canvas c, Rect rect, int adapterPosition, int centerOffset);

    /**
     * 画分割线背景 如何画法可以在此方法中实现
     */
    abstract void drawDividerBg(Canvas c, Rect rect);

    /**
     * 画分割线 如何画法可以在此方法中实现
     */
    abstract void drawDivider(Canvas c, Rect rect);

    static double radianToRadio(int radian, float degree) {
        return radian * 180d / (degree * Math.PI);
    }
}
