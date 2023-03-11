package com.Jfpicker.wheelpicker.wheelview.layoutmanager;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.Jfpicker.wheelpicker.utils.WheelLogUtils;

import java.util.ArrayList;

/**
 * 数据无限循环的LayoutManager，自定义列表项的添加复用和删除，自定义滑动处理以配合WheelView滑动到指定位置
 */
public class VerticalLooperLayoutManager extends RecyclerView.LayoutManager implements LayoutManagerScrollEngine {

    private static final String TAG = "VerticalLooperLayoutManager";

    private int itemHeight; //RecyclerView每一列表项的固定高度
    private int dataCount; // RecyclerView的非循环数据大小
    private RecyclerView mRecyclerView; //关联的RecyclerView

    public VerticalLooperLayoutManager(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    public VerticalLooperLayoutManager(RecyclerView recyclerView, int itemHeight) {
        mRecyclerView = recyclerView;
        this.itemHeight = itemHeight;
    }


    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight);
    }


    @Override
    public boolean canScrollVertically() {
        return true;
    }

    /**
     * onLayoutChildren是RecyclerView.LayoutManager类的一个方法，用于测量和布置RecyclerView中的项目。它通常在以下情况下被调用：
     * <p>
     * 当RecyclerView首次设置LayoutManager时：在设置LayoutManager时，RecyclerView会调用onLayoutChildren以获取初始布局。
     * 当RecyclerView的数据集发生更改时：当RecyclerView的数据集更改（例如添加、删除或移动项目）时，
     * RecyclerView会调用onLayoutChildren来重新布局所有项目。
     * 当RecyclerView的尺寸发生更改时：当RecyclerView的尺寸更改时（例如，用户更改了屏幕方向或调整了RecyclerView的大小），
     * RecyclerView会调用onLayoutChildren来重新布局所有项目。
     * 在这些情况下，onLayoutChildren方法都会被调用以确保RecyclerView中的项目能够正确地显示和布局。
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0) {
            return;
        }
        //preLayout主要支持动画，直接跳过
        if (state.isPreLayout()) {
            return;
        }

        //将视图分离放入scrap缓存中，以准备重新对view进行排版
        detachAndScrapAttachedViews(recycler);

        int autualHeight = 0;
        int childCount;
        boolean isFillRecycler;
        do {
            for (int i = 0; i < getItemCount(); i++) {
                //初始化，将在屏幕内的view填充
                View itemView = recycler.getViewForPosition(i);
                addView(itemView);
                measureChildWithMargins(itemView, 0, 0);
                //根据itemView的宽高进行布局
                layoutDecorated(itemView, 0, autualHeight, getWidth(), autualHeight + itemHeight);
                autualHeight += itemHeight;
                //如果当前布局过的itemView的高度总和大于等于RecyclerView的高度，则不再进行布局
                if (autualHeight >= getHeight()) {
                    return;
                }
            }
            childCount = getChildCount();
            isFillRecycler = getChildAt(childCount - 1).getBottom() > getHeight();

        } while (!isFillRecycler);
    }

    @Override
    public void scrollToTargetPosition(int position) {
        scrollTo(position);
    }

    public void scrollTo(int position) {
        if (mRecyclerView == null) {
            return;
        }
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                int translateY = calScrollDistance(position);
                mRecyclerView.scrollBy(0, translateY);
            }
        });
    }

    public void smoothScrollTo(int position) {
        if (mRecyclerView == null) {
            return;
        }
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                int translateY = calScrollDistance(position);
                mRecyclerView.smoothScrollBy(0, translateY);
            }
        });

    }

    private int calScrollDistance(int position) {
        ArrayList<View> screenViews = findScreenViews();
        int centerPosition = getPosition(screenViews.get(screenViews.size() / 2));
        if (centerPosition == position) {
            return 0;
        }
        return (position - centerPosition) * itemHeight;
    }

    /**
     * @return 获取屏幕可见View集合
     */
    public ArrayList<View> findScreenViews() {
        ArrayList<View> screenViews = new ArrayList<>();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i).getTop() >= 0 && getChildAt(i).getBottom() <= getHeight()) {
                screenViews.add(getChildAt(i));
            }
        }
        return screenViews;
    }


    /**
     * @return 获取屏幕可见的第一个View
     */
    public View findFirstVisibleView() {
        ArrayList<View> screenViews = findScreenViews();
        if (screenViews != null && screenViews.size() > 0) {
            return screenViews.get(0);
        }
        return null;
    }

    /**
     * @return 获取屏幕可见的最后一个View
     */
    public View findLastVisibleView() {
        ArrayList<View> screenViews = findScreenViews();
        if (screenViews != null && screenViews.size() > 0) {
            return screenViews.get(screenViews.size() - 1);
        }
        return null;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //1.上下滑动的时候，填充子view
        int trval = fill(dy, recycler);
        if (trval == 0) {
            return 0;
        }
        //2.滚动
        offsetChildrenVertical(-trval);

        //3.回收已经离开界面的
        recyclerHideView(dy, recycler);
        return trval;
    }


    /**
     * 上下滑动的时候，填充新的子View
     */
    private int fill(int dy, RecyclerView.Recycler recycler) {

        WheelLogUtils.print(TAG + ":   " + "滑动距离" + dy);

        if (dy > 0) {
            //向上滑动
            View lastView = getChildAt(getChildCount() - 1);
            if (lastView == null) {
                return 0;
            }
            int lastPos = getPosition(lastView);

            //通过scrollTo方法移动,非平滑的滑动，每次至少平移一个列表项
            if (Math.abs(dy) >= itemHeight) {
                int needAddCount = Math.abs(dy) / itemHeight;
                if (Math.abs(dy) % itemHeight > 0) {
                    needAddCount = needAddCount + 1;
                }
                int totalBottom = lastView.getBottom();
                if (lastPos == getItemCount() - 1) {
                    //如果最后一个列表项是数据的最后一个
                    addItemViewsPositiveOrder(recycler, needAddCount, totalBottom);
                } else {
                    //如果最后一个列表项不是数据的最后一个
                    int count = dataCount - 1 - lastPos;
                    if (count >= needAddCount) {
                        //如果数量足够，不用重新从第一个数据开始
                        for (int i = 1; i <= needAddCount; i++) {
                            totalBottom = addNewItemViewToBottom(recycler, lastPos + i, totalBottom);
                        }
                    } else {
                        //数据不足够，先添加剩余的，然后再次从第一个数据开始添加
                        for (int i = 1; i <= count; i++) {
                            totalBottom = addNewItemViewToBottom(recycler, lastPos + i, totalBottom);
                        }
                        needAddCount = needAddCount - count;
                        addItemViewsPositiveOrder(recycler, needAddCount, totalBottom);
                    }
                }
            }
//            //通过用户交互或者smoothScrollTo方法移动，平滑的移动，每次移动一点点距离
//            //可见的最后一个itemView完全滑进来了，需要补充新的
            else if (lastView.getBottom() < getHeight()) {
                View scrap;
                //判断可见的最后一个itemView的索引，
                // 如果是最后一个，则将下一个itemView设置为第一个，否则设置为当前索引的下一个
                if (lastPos == getItemCount() - 1) {
                    scrap = recycler.getViewForPosition(0);
                } else {
                    scrap = recycler.getViewForPosition(lastPos + 1);
                }
                //将新的itemView add进来并对其测量和布局
                addView(scrap);
                measureChildWithMargins(scrap, 0, 0);
                layoutDecorated(scrap, 0, lastView.getBottom(),
                        getWidth(), lastView.getBottom() + itemHeight);
            }
        } else {
            //向下滚动
            View firstView = getChildAt(0);
            if (firstView == null) {
                return 0;
            }
            int firstPos = getPosition(firstView);

            //通过scrollTo方法移动,非平滑的滑动，每次至少平移一个列表项
            if (Math.abs(dy) >= itemHeight) {
                int needAddCount = Math.abs(dy) / itemHeight;
                if (Math.abs(dy) % itemHeight > 0) {
                    needAddCount = needAddCount + 1;
                }
                int totalTop = firstView.getTop();
                if (firstPos == 0) {
                    //如果第一个列表项是数据的第一个
                    addItemViewsReverseOrder(recycler, needAddCount, totalTop);
                } else {
                    //如果第一个列表项不是数据的第一个，
                    int count = firstPos;
                    if (count >= needAddCount) {
                        //如果数量足够
                        int hasAdd = 0;
                        for (int i = firstPos - 1; i >= 0; i--) {
                            totalTop = addNewItemViewToTop(recycler, i, totalTop);
                            hasAdd++;
                            if (hasAdd == needAddCount) {
                                break;
                            }
                        }
                    } else {
                        //数据不足够
                        for (int i = firstPos - 1; i >= 0; i--) {
                            totalTop = addNewItemViewToTop(recycler, i, totalTop);
                        }
                        needAddCount = needAddCount - count;
                        addItemViewsReverseOrder(recycler, needAddCount, totalTop);
                    }
                }
            }
            //通过用户交互或者smoothScrollTo方法移动，平滑的移动，每次移动一点点距离
            else if (firstView.getTop() >= 0) {
                View scrap;
                if (firstPos == 0) {
                    scrap = recycler.getViewForPosition(getItemCount() - 1);
                } else {
                    scrap = recycler.getViewForPosition(firstPos - 1);
                }
                addView(scrap, 0);
                measureChildWithMargins(scrap, 0, 0);
                layoutDecorated(scrap, 0, firstView.getTop() - itemHeight,
                        getWidth(), firstView.getTop());
            }
        }
        return dy;
    }

    /**
     * 回收界面不可见的view
     */
    private void recyclerHideView(int dy, RecyclerView.Recycler recycler) {
        ArrayList<View> childViews = new ArrayList<>();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view != null) {
                childViews.add(view);
            }
        }
        for (int i = 0; i < childViews.size(); i++) {
            View view = childViews.get(i);
            if (dy > 0) {
                //向上滚动，移除底边完全不在屏幕里的view
                if (view.getBottom() <= 0) {
                    WheelLogUtils.print(TAG + ":   " + "循环: 移除 一个view  childPosition=" + getPosition(view));
                    removeAndRecycleView(view, recycler);
                    WheelLogUtils.print(TAG + ":   " + "剩余=" + getChildCount());
                }
            } else {
                //向下滚动，移除顶边完全不在屏幕里的view
                if (view.getTop() >= getHeight()) {
                    WheelLogUtils.print(TAG + ":   " + "循环: 移除 一个view  childCount=" + getPosition(view));
                    removeAndRecycleView(view, recycler);
                    WheelLogUtils.print(TAG + ":   " + "剩余=" + getChildCount());
                }
            }
        }
    }

    /**
     * 按照数据正序，往列表最下方依次添加needAddCount个列表项
     *
     * @param recycler
     * @param needAddCount
     * @param totalBottom
     */
    private void addItemViewsPositiveOrder(RecyclerView.Recycler recycler, int needAddCount, int totalBottom) {
        if (dataCount == 0) {
            return;
        }
        int divResult = needAddCount / dataCount;
        int remainder = needAddCount % dataCount;
        //如果最后一个列表项是数据的最后一个，则从第一个数据开始重新添加
        if (divResult > 0) {
            for (int m = 0; m < divResult; m++) {
                for (int n = 0; n < dataCount; n++) {
                    totalBottom = addNewItemViewToBottom(recycler, n, totalBottom);
                }
            }
        }
        if (remainder > 0) {
            for (int i = 0; i < remainder; i++) {
                totalBottom = addNewItemViewToBottom(recycler, i, totalBottom);
            }
        }
    }

    /**
     * 按照数据倒序，往列表最上方依次添加needAddCount个列表项
     *
     * @param recycler
     * @param needAddCount
     * @param totalTop
     */
    private void addItemViewsReverseOrder(RecyclerView.Recycler recycler, int needAddCount, int totalTop) {
        if (dataCount == 0) {
            return;
        }
        int divResult = needAddCount / dataCount;
        int remainder = needAddCount % dataCount;
        //如果最后一个列表项是数据的最后一个，则从第一个数据开始重新添加
        if (divResult > 0) {
            for (int m = 0; m < divResult; m++) {
                for (int n = dataCount - 1; n >= 0; n--) {
                    totalTop = addNewItemViewToTop(recycler, n, totalTop);
                }
            }
        }
        if (remainder > 0) {
            int hasAdd = 0;
            for (int n = dataCount - 1; n >= 0; n--) {
                totalTop = addNewItemViewToTop(recycler, n, totalTop);
                hasAdd++;
                if (hasAdd == remainder) {
                    break;
                }
            }
        }
    }

    /**
     * 往列表底部添加一个列表项
     *
     * @param recycler
     * @param adapterPosition
     * @param bottom
     * @return
     */
    private int addNewItemViewToBottom(RecyclerView.Recycler recycler, int adapterPosition, int bottom) {
        View scrap = recycler.getViewForPosition(adapterPosition);
        //将新的itemView add进来并对其测量和布局
        addView(scrap);
        measureChildWithMargins(scrap, 0, 0);
        layoutDecorated(scrap, 0, bottom,
                getWidth(), bottom + itemHeight);
        WheelLogUtils.print(TAG + ":   " + "添加底部view " + adapterPosition + "===" + (bottom + itemHeight));
        return bottom + itemHeight;
    }

    /**
     * 往列表顶部添加一个列表项
     *
     * @param recycler
     * @param adapterPosition
     * @param top
     * @return
     */
    private int addNewItemViewToTop(RecyclerView.Recycler recycler, int adapterPosition, int top) {
        View scrap = recycler.getViewForPosition(adapterPosition);
        addView(scrap, 0);
        measureChildWithMargins(scrap, 0, 0);
        layoutDecorated(scrap, 0, top - itemHeight,
                getWidth(), top);
        WheelLogUtils.print(TAG + ":   " + "添加顶部view " + adapterPosition + "===" + (top - itemHeight));
        return top - itemHeight;
    }


    public int getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }


}
