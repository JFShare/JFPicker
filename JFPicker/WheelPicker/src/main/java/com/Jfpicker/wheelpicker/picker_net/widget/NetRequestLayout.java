package com.Jfpicker.wheelpicker.picker_net.widget;

import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_DRAGGING;
import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_IDLE;
import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_SETTLING;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.picker_net.adapter.NetRequestOptionAdapter;
import com.Jfpicker.wheelpicker.picker_net.adapter.NetVpAdapter;
import com.Jfpicker.wheelpicker.picker_net.listener.OnNetRequestStartListener;
import com.Jfpicker.wheelpicker.rv_listener.OnRecyclerviewStyleListener;
import com.Jfpicker.wheelpicker.picker_option.entity.OptionEntity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by JF on  2021/11/15
 * @description Viewpager样式的五级选择器，用于后台给你某一级数据，你选择获取id后请求下一级数据
 */

public class NetRequestLayout extends LinearLayout {

    private ViewPager2 mViewPager;
    private ViewPager2.OnPageChangeCallback mCallback;
    private NetVpAdapter vpAdapter;
    private TabLayout mTabLayout;
    private int maxIndex = 3;
    private OptionEntity firstData;
    private OptionEntity secondData;
    private OptionEntity thirdDate;
    private OptionEntity fourthData;
    private OptionEntity fifthData;
    private OnNetRequestStartListener onNetRequestStartListener;
    private OnRecyclerviewStyleListener onRecyclerviewStyleListener;

    private ArrayList<String> tabNameList = new ArrayList<>();
    public static final String defaultTabName = "请选择";

    private FrameLayout flStateContent;
    private View viewLoading = null;
    private View viewError = null;

    private int lastQuestVpPosition, lastQuestClickItemPosition;

    public NetRequestLayout(Context context) {
        super(context);
        init(context);
    }

    public NetRequestLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NetRequestLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public NetRequestLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        inflate(context, R.layout.jf_wheel_picker_net_request, this);
        onInit();
    }

    private void onInit() {
        mViewPager = findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(5);
        mCallback = new ViewPager2.OnPageChangeCallback() {
            private int mPreviousScrollState, mScrollState = SCROLL_STATE_IDLE;

            @Override
            public void onPageScrollStateChanged(int state) {
                mPreviousScrollState = mScrollState;
                mScrollState = state;
                if (state == ViewPager2.SCROLL_STATE_IDLE && mTabLayout.getSelectedTabPosition() != mViewPager.getCurrentItem()) {
                    final boolean updateIndicator = mScrollState == SCROLL_STATE_IDLE || (mScrollState == SCROLL_STATE_SETTLING && mPreviousScrollState == SCROLL_STATE_IDLE);
                    mTabLayout.selectTab(mTabLayout.getTabAt(mViewPager.getCurrentItem()), updateIndicator);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                final boolean updateText = mScrollState != SCROLL_STATE_SETTLING || mPreviousScrollState == SCROLL_STATE_DRAGGING;
                final boolean updateIndicator = !(mScrollState == SCROLL_STATE_SETTLING && mPreviousScrollState == SCROLL_STATE_IDLE);
                mTabLayout.setScrollPosition(position, positionOffset, updateText, updateIndicator);
            }
        };
        mViewPager.registerOnPageChangeCallback(mCallback);

        mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                synchronized (this) {
                    if (mViewPager.getCurrentItem() != tab.getPosition()) {
                        mViewPager.setCurrentItem(tab.getPosition());
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        flStateContent = findViewById(R.id.flStateContent);
        viewLoading = findViewById(R.id.llLoading);
        viewError = findViewById(R.id.llError);
        viewError.setOnClickListener(v -> {
            reRequest();
        });
    }

    public void setFirstData(List<OptionEntity> firstData) {
        mTabLayout.removeAllTabs();
        mTabLayout.addTab(mTabLayout.newTab().setText(getTabName(0)), true);
        List<List<OptionEntity>> list = new ArrayList<>();
        list.add(firstData);
        vpAdapter = new NetVpAdapter(getContext(), list, onRecyclerviewStyleListener,
                (viewpagerPosition, clickItemPosition, smoothScroll) ->
                        selectedViewPager(viewpagerPosition, clickItemPosition));
        mViewPager.setAdapter(vpAdapter);
    }

    public void setNextData() {
        dismissLoading();
        showError();
    }

    public void setNextData(List<OptionEntity> nextData) {
        dismissStateLayout();
        if (vpAdapter.getItemCount() >= maxIndex) {
            return;
        }
        if (nextData != null) {
            int currentCount = vpAdapter.getItemCount();
            mTabLayout.addTab(mTabLayout.newTab().setText(getTabName(currentCount)), true);
            vpAdapter.addItem(currentCount, nextData);
            mViewPager.setCurrentItem(currentCount, true);
        }
    }

    private void selectedViewPager(int vpPosition, int clickItemPosition) {

        if (vpAdapter.getItemCount() > maxIndex) {
            return;
        }
        switch (vpPosition) {
            case 0:
                secondData = thirdDate = fourthData = fifthData = null;
                removeTabAt(4);
                removeTabAt(3);
                removeTabAt(2);
                removeTabAt(1);
                firstData = vpAdapter.getOptionItem(vpPosition, clickItemPosition);
                mTabLayout.getTabAt(vpPosition).setText(firstData.getName());
                notifySecondItemChanged(vpPosition);
                break;
            case 1:
                thirdDate = fourthData = fifthData = null;
                removeTabAt(4);
                removeTabAt(3);
                removeTabAt(2);
                secondData = vpAdapter.getOptionItem(vpPosition, clickItemPosition);
                mTabLayout.getTabAt(vpPosition).setText(secondData.getName());
                notifySecondItemChanged(vpPosition);
                break;
            case 2:
                fourthData = fifthData = null;
                removeTabAt(4);
                removeTabAt(3);
                thirdDate = vpAdapter.getOptionItem(vpPosition, clickItemPosition);
                mTabLayout.getTabAt(vpPosition).setText(thirdDate.getName());
                notifySecondItemChanged(vpPosition);
                break;
            case 3:
                fifthData = null;
                removeTabAt(4);
                fourthData = vpAdapter.getOptionItem(vpPosition, clickItemPosition);
                mTabLayout.getTabAt(vpPosition).setText(fourthData.getName());
                notifySecondItemChanged(vpPosition);
                break;
            case 4:
                fifthData = vpAdapter.getOptionItem(vpPosition, clickItemPosition);
                mTabLayout.getTabAt(vpPosition).setText(fifthData.getName());
                notifySecondItemChanged(vpPosition);
                break;
        }

        if (onNetRequestStartListener != null) {
            if (vpAdapter.getItemCount() >= maxIndex) {
                return;
            }
            dismissError();
            showLoading();
            lastQuestVpPosition = vpPosition;
            lastQuestClickItemPosition = clickItemPosition;
            OptionEntity clickEntity = vpAdapter.getOptionItem(vpPosition, clickItemPosition);
            onNetRequestStartListener.onRequest(vpPosition, clickEntity);
        }
    }

    public void reRequest() {
        if (onNetRequestStartListener != null) {
            if (vpAdapter.getItemCount() >= maxIndex) {
                return;
            }
            dismissError();
            showLoading();
            OptionEntity clickEntity = vpAdapter.getOptionItem(lastQuestVpPosition, lastQuestClickItemPosition);
            onNetRequestStartListener.onRequest(lastQuestVpPosition, clickEntity);
        }
    }

    private void removeTabAt(int position) {
        if (mTabLayout.getTabAt(position) != null) {
            mTabLayout.removeTabAt(position);
            vpAdapter.removeItem(position);
        }
    }

    public void notifySecondItemChanged(int parentPosition) {
        if (vpAdapter == null) {
            return;
        }
        RecyclerView recyclerViewImpl = (RecyclerView) mViewPager.getChildAt(0);
        RecyclerView.ViewHolder holder = recyclerViewImpl.findViewHolderForLayoutPosition(parentPosition);
        if (holder instanceof NetVpAdapter.NetVpViewHolder) {
            NetVpAdapter.NetVpViewHolder parentHolder = (NetVpAdapter.NetVpViewHolder) holder;
            if (parentHolder.rvPageItem.getAdapter() != null
                    && parentHolder.rvPageItem.getAdapter() instanceof NetRequestOptionAdapter) {
                NetRequestOptionAdapter adapter = (NetRequestOptionAdapter) parentHolder.rvPageItem.getAdapter();
                adapter.notifyCurrentOptionChanged(firstData, secondData, thirdDate, fourthData, fifthData);
            }
        }
    }


    public void setTabNameList(ArrayList<String> tabNameList) {
        if (tabNameList != null) {
            this.tabNameList = tabNameList;
            for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                if (i > tabNameList.size() - 1) {
                    break;
                }
                String text = mTabLayout.getTabAt(i).getText().toString();
                if (!TextUtils.isEmpty(text) && defaultTabName.equals(text)) {
                    mTabLayout.getTabAt(i).setText(tabNameList.get(i));
                }
            }
        }
    }

    public String getTabName(int position) {
        if (tabNameList == null) {
            return defaultTabName;
        }
        if (position > tabNameList.size() - 1) {
            return defaultTabName;
        }
        return tabNameList.get(position);
    }

    //加载中布局、错误布局、点击触发重新请求View的id
    public void setStateLayout(int loadingLayoutId, int errorLayoutId, int reRequestClickId) {
        flStateContent.removeAllViews();
        viewLoading = LayoutInflater.from(getContext()).inflate(loadingLayoutId,
                flStateContent, false);
        flStateContent.addView(viewLoading);
        viewError = LayoutInflater.from(getContext()).inflate(errorLayoutId,
                flStateContent, false);
        View clickView = viewError.findViewById(reRequestClickId);
        if (clickView != null) {
            clickView.setOnClickListener(v -> {
                reRequest();
            });
        }
        flStateContent.addView(viewError);
    }

    public void dismissStateLayout() {
        flStateContent.setVisibility(GONE);
    }

    public void showLoading() {
        flStateContent.setVisibility(VISIBLE);
        if (viewLoading != null) {
            viewLoading.setVisibility(VISIBLE);
        }
    }

    public void dismissLoading() {
        flStateContent.setVisibility(GONE);
        if (viewLoading != null) {
            viewLoading.setVisibility(GONE);
        }
    }

    public void showError() {
        flStateContent.setVisibility(VISIBLE);
        if (viewError != null) {
            viewError.setVisibility(VISIBLE);
        }
    }

    public void dismissError() {
        flStateContent.setVisibility(GONE);
        if (viewError != null) {
            viewError.setVisibility(GONE);
        }
    }


    public int getMaxIndex() {
        return maxIndex;
    }

    public void setMaxIndex(int maxIndex) {
        this.maxIndex = maxIndex;
    }

    public ViewPager2 getViewPager() {
        return mViewPager;
    }

    public TabLayout getTabLayout() {
        return mTabLayout;
    }


    public OptionEntity getFirstData() {
        return firstData;
    }

    public OptionEntity getSecondData() {
        return secondData;
    }

    public OptionEntity getThirdDate() {
        return thirdDate;
    }

    public OptionEntity getFourthData() {
        return fourthData;
    }

    public OptionEntity getFifthData() {
        return fifthData;
    }

    //放在setFirstData之前设置
    public void setOnRecyclerviewStyleListener(OnRecyclerviewStyleListener onRecyclerviewStyleListener) {
        this.onRecyclerviewStyleListener = onRecyclerviewStyleListener;
    }

    public void setOnNetRequestStartListener(OnNetRequestStartListener onNetRequestStartListener) {
        this.onNetRequestStartListener = onNetRequestStartListener;
    }
}
