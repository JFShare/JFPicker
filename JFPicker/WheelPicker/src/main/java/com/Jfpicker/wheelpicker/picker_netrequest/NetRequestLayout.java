package com.Jfpicker.wheelpicker.picker_netrequest;

import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_DRAGGING;
import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_IDLE;
import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_SETTLING;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.picker_base.OnRecyclerviewStyleListener;
import com.Jfpicker.wheelpicker.picker_option.entity.OptionEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Created by JF on  2021/11/15
 * @description Viewpager样式的五级选择器，用于后台给你某一级数据，你选择获取id后请求下一级数据
 */

public class NetRequestLayout extends LinearLayout {

    private ViewPager2 mViewPager;
    private ViewpagerAdapter mAdapter;

    private TabLayout mTabLayout;
    private LinearLayout llLoading;
    private ProgressBar pbLoading;
    private TextView tvLoading;

    private ViewPager2.OnPageChangeCallback mCallback;
    private int maxIndex = 3;
    private OptionEntity firstData;
    private OptionEntity secondData;
    private OptionEntity thirdDate;
    private OptionEntity fourthData;
    private OptionEntity fifthData;
    private OnNetRequestStartListener onNetRequestStartListener;


    private ArrayList<String> tabNameList = new ArrayList<>();
    public static final String defaultTabName = "请选择";

    public void setOnNetRequestStartListener(OnNetRequestStartListener onNetRequestStartListener) {
        this.onNetRequestStartListener = onNetRequestStartListener;
    }

    public NetRequestLayout(Context context) {
        super(context);
        init(context, null);
    }

    public NetRequestLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NetRequestLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public NetRequestLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
        inflate(context, R.layout.wheel_picker_net_request, this);
        onInit(context);
    }

    private void onInit(@NonNull Context context) {
        mViewPager = findViewById(R.id.viewpager);
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


        mTabLayout = findViewById(R.id.tablayout);
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
        llLoading = findViewById(R.id.llLoading);
        llLoading.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        pbLoading = findViewById(R.id.pbLoading);
        tvLoading = findViewById(R.id.tvLoading);
    }

    public void setFirstData(List<OptionEntity> firstData) {
        mTabLayout.removeAllTabs();
        mTabLayout.addTab(mTabLayout.newTab().setText(getTabName(0)), true);

        List<List<OptionEntity>> list = new ArrayList<>();
        list.add(firstData);
        mAdapter = new ViewpagerAdapter(list, new OnNetRequestItemClickListener() {
            @Override
            public void onClick(int viewpagerPosition, int clickItemPosition, boolean smoothScroll) {
                selectedViewPager(viewpagerPosition, clickItemPosition, smoothScroll);
            }
        });
        mViewPager.setAdapter(mAdapter);

    }

    public void setNextData(List<OptionEntity> nextData) {
        dissLoading();
        if (mAdapter.getItemCount() >= maxIndex) {
            return;
        }
        if (nextData != null) {
            int currentCount = mAdapter.getItemCount();
            mTabLayout.addTab(mTabLayout.newTab().setText(getTabName(currentCount)), true);
            mAdapter.addItem(currentCount, nextData);
            mViewPager.setCurrentItem(currentCount, true);
        }
    }

    private void selectedViewPager(int viewpagerPosition, int itemPosition, boolean smoothScroll) {

        if (mAdapter.getItemCount() > maxIndex) {
            return;
        }
        switch (viewpagerPosition) {
            case 0:
                secondData = thirdDate = fourthData = fifthData = null;
                if (mTabLayout.getTabAt(4) != null) {
                    mTabLayout.removeTabAt(4);
                    mAdapter.removeItem(4);
                }
                if (mTabLayout.getTabAt(3) != null) {
                    mTabLayout.removeTabAt(3);
                    mAdapter.removeItem(3);
                }
                if (mTabLayout.getTabAt(2) != null) {
                    mTabLayout.removeTabAt(2);
                    mAdapter.removeItem(2);
                }
                if (mTabLayout.getTabAt(1) != null) {
                    mTabLayout.removeTabAt(1);
                    mAdapter.removeItem(1);
                }
                firstData = mAdapter.getItem(viewpagerPosition).get(itemPosition);
                mTabLayout.getTabAt(viewpagerPosition).setText(firstData.getName());

                notifySecondItemChanged(viewpagerPosition, itemPosition);
                break;
            case 1:
                thirdDate = fourthData = fifthData = null;
                if (mTabLayout.getTabAt(4) != null) {
                    mTabLayout.removeTabAt(4);
                    mAdapter.removeItem(4);
                }
                if (mTabLayout.getTabAt(3) != null) {
                    mTabLayout.removeTabAt(3);
                    mAdapter.removeItem(3);
                }
                if (mTabLayout.getTabAt(2) != null) {
                    mTabLayout.removeTabAt(2);
                    mAdapter.removeItem(2);
                }

                secondData = mAdapter.getItem(viewpagerPosition).get(itemPosition);
                mTabLayout.getTabAt(viewpagerPosition).setText(secondData.getName());

                notifySecondItemChanged(viewpagerPosition, itemPosition);
                break;
            case 2:
                fourthData = fifthData = null;
                if (mTabLayout.getTabAt(4) != null) {
                    mTabLayout.removeTabAt(4);
                    mAdapter.removeItem(4);
                }
                if (mTabLayout.getTabAt(3) != null) {
                    mTabLayout.removeTabAt(3);
                    mAdapter.removeItem(3);
                }
                thirdDate = mAdapter.getItem(viewpagerPosition).get(itemPosition);
                mTabLayout.getTabAt(viewpagerPosition).setText(thirdDate.getName());

                notifySecondItemChanged(viewpagerPosition, itemPosition);
                break;
            case 3:
                fifthData = null;
                if (mTabLayout.getTabAt(4) != null) {
                    mTabLayout.removeTabAt(4);
                    mAdapter.removeItem(4);
                }
                fourthData = mAdapter.getItem(viewpagerPosition).get(itemPosition);
                mTabLayout.getTabAt(viewpagerPosition).setText(fourthData.getName());
                notifySecondItemChanged(viewpagerPosition, itemPosition);
                break;
            case 4:
                fifthData = mAdapter.getItem(viewpagerPosition).get(itemPosition);
                mTabLayout.getTabAt(viewpagerPosition).setText(fifthData.getName());
                notifySecondItemChanged(viewpagerPosition, itemPosition);
                break;
        }

        if (onNetRequestStartListener != null) {
            if (mAdapter.getItemCount() >= maxIndex) {
                return;
            }
            showLoading();
            OptionEntity clickEntity = mAdapter.getItem(viewpagerPosition).get(itemPosition);
            onNetRequestStartListener.onRequest(viewpagerPosition, clickEntity);
        }
    }

    public void notifySecondItemChanged(int parentPositon, int itemPosition) {
        if (mAdapter == null) {
            return;
        }
        View view = mAdapter.getViewByPosition(parentPositon, R.id.viewpage_item_recyclerView);
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            if (recyclerView.getAdapter() != null) {
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }
    }

    public class ViewpagerAdapter extends BaseQuickAdapter<List<OptionEntity>, BaseViewHolder> {
        private OnNetRequestItemClickListener listener;

        public ViewpagerAdapter(@Nullable List<List<OptionEntity>> data, OnNetRequestItemClickListener listener) {
            super(R.layout.item_onepage_contain_recyclerview, data);
            this.listener = listener;

        }

        @Override
        protected void convert(@NonNull BaseViewHolder holder, List<OptionEntity> entity) {
            RecyclerView recyclerView = holder.findView(R.id.viewpage_item_recyclerView);
            NetRequestOptionAdapter adapter = new NetRequestOptionAdapter(entity, holder.getAdapterPosition());
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    if (listener != null) {
                        listener.onClick(holder.getAdapterPosition(), position, true);
                    }
                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
        }

        public void addItem(@IntRange(from = 0) int position, @NonNull List<OptionEntity> item) {

            if (getData() == null) {
                return;
            }

            if (position < getData().size()) {
                getData().add(position, item);
            } else {
                getData().add(item);
                position = getData().size() - 1;
            }
            notifyItemInserted(position);
        }

        public void removeItem(@IntRange(from = 0) int position) {
            getData().remove(position);
            notifyItemRemoved(position);
        }
    }

    public class NetRequestOptionAdapter extends BaseQuickAdapter<OptionEntity, BaseViewHolder> {
        int parentPosition;

        public NetRequestOptionAdapter(@Nullable List<OptionEntity> data, int parentPosition) {
            super(R.layout.item_content, data);
            this.parentPosition = parentPosition;
        }

        @Override
        protected void convert(@NonNull BaseViewHolder holder, OptionEntity entity) {
            holder.setText(R.id.recyclerview_content_text, entity.getName());
            boolean isSelect = false;
            switch (parentPosition) {
                case 0:
                    if (firstData != null && firstData.getId().equals(entity.getId())) {
                        isSelect = true;
                    }
                    break;
                case 1:
                    if (secondData != null && secondData.getId().equals(entity.getId())) {
                        isSelect = true;
                    }
                    break;
                case 2:
                    if (thirdDate != null && thirdDate.getId().equals(entity.getId())) {
                        isSelect = true;
                    }
                    break;
                case 3:
                    if (fourthData != null && fourthData.getId().equals(entity.getId())) {
                        isSelect = true;
                    }
                    break;
                case 4:
                    if (fifthData != null && fifthData.getId().equals(entity.getId())) {
                        isSelect = true;
                    }
                    break;
                default:
                    isSelect = false;
                    break;
            }
            if (isSelect) {
                holder.setTextColor(R.id.recyclerview_content_text, Color.parseColor("#0000FF"));
            } else {
                holder.setTextColor(R.id.recyclerview_content_text, Color.parseColor("#333333"));
            }
            if (onRecyclerviewStyleListener != null) {
                onRecyclerviewStyleListener.onStyle(holder.getView(R.id.recyclerView_content_framelayout),
                        holder.getView(R.id.recyclerview_content_text),
                        entity, isSelect);
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


    public void showLoading() {
        llLoading.setVisibility(VISIBLE);
    }

    public void dissLoading() {
        llLoading.setVisibility(GONE);
    }

    public interface OnNetRequestItemClickListener {
        void onClick(int viewpagerPosition, int clickItemPosition, boolean smoothScroll);
    }

    public interface OnNetRequestStartListener {
        void onRequest(int parentPosition, OptionEntity entity);
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

    public LinearLayout getLlLoading() {
        return llLoading;
    }

    public ProgressBar getPbLoading() {
        return pbLoading;
    }

    public TextView getTvLoading() {
        return tvLoading;
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

    private OnRecyclerviewStyleListener onRecyclerviewStyleListener;

    public OnRecyclerviewStyleListener getOnRecyclerviewStyleListener() {
        return onRecyclerviewStyleListener;
    }

    public void setOnRecyclerviewStyleListener(OnRecyclerviewStyleListener onRecyclerviewStyleListener) {
        this.onRecyclerviewStyleListener = onRecyclerviewStyleListener;
    }

}
