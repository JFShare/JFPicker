package com.Jfpicker.wheelpicker.picker_adress;

import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_DRAGGING;
import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_IDLE;
import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_SETTLING;

import static com.Jfpicker.wheelpicker.picker_option.entity.LinkageProvider.INDEX_NO_FOUND;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.picker_adress.entity.AddressItemEntity;
import com.Jfpicker.wheelpicker.picker_adress.impl.AddressProvider;
import com.Jfpicker.wheelpicker.picker_base.OnRecyclerviewStyleListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by JF on  2021/11/15
 * @description ViewPager样式的地址选择器，通过接口的形式提供设置列表项样式
 */

public class AddressViewPagerLayout extends LinearLayout {

    private ViewPager2 mViewPager;
    private ViewpagerAdapter mAdapter;
    private TabLayout mTabLayout;

    private AddressProvider dataProvider;
    private Object firstValue, secondValue, thirdValue;
    private int firstIndex, secondIndex, thirdIndex;
    private AddressItemEntity mProvince = null;
    private AddressItemEntity mCity = null;
    private AddressItemEntity mArea = null;

    private ArrayList<String> tabNameList = new ArrayList<>();
    public static final String defaultTabName = "请选择";

    private ViewPager2.OnPageChangeCallback mCallback;

    public AddressViewPagerLayout(Context context) {
        super(context);
        init(context, null);
    }

    public AddressViewPagerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AddressViewPagerLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public AddressViewPagerLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
        inflate(context, R.layout.wheel_picker_adress_viewpager, this);
        onInit(context);
    }

    private void onInit(@NonNull Context context) {
        mViewPager = findViewById(R.id.viewpagerAdress);
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

        mTabLayout = findViewById(R.id.tablayoutAdress);
        mTabLayout.addTab(mTabLayout.newTab().setText(getTabName(0)), true);
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
    }

    public void setData(@NonNull AddressProvider provider) {

        if (firstValue != null) {
            firstIndex = provider.findFirstIndex(firstValue);
        } else {
            firstIndex = INDEX_NO_FOUND;
        }
        if (secondValue != null) {
            secondIndex = provider.findSecondIndex(firstIndex, secondValue);
        } else {
            secondIndex = INDEX_NO_FOUND;
        }
        if (thirdValue != null) {
            thirdIndex = provider.findThirdIndex(firstIndex, secondIndex, thirdValue);
        } else {
            thirdIndex = INDEX_NO_FOUND;
        }
        dataProvider = provider;
        changeProvinceData();
        if (firstIndex != INDEX_NO_FOUND && secondIndex != INDEX_NO_FOUND && thirdIndex != INDEX_NO_FOUND) {
            selectedAddress(0, firstIndex, false);
            selectedAddress(1, secondIndex, false);
            selectedAddress(2, thirdIndex, false);
        }
    }

    public void setDefaultValue(Object first, Object second, Object third) {
        this.firstValue = first;
        this.secondValue = second;
        this.thirdValue = third;
        if (dataProvider != null) {
            setData(dataProvider);
        }
    }

    public void changeProvinceData() {
        List<List<AddressItemEntity>> list = new ArrayList<>();
        list.add(dataProvider.provideFirstData());
        mAdapter = new ViewpagerAdapter(list, new OnAdressItmeListener() {
            @Override
            public void onClick(int viewpagerPosition, int clickItemPosition, boolean smoothScroll) {
                selectedAddress(viewpagerPosition, clickItemPosition, smoothScroll);
            }
        });
        mViewPager.setAdapter(mAdapter);
    }

    private void selectedAddress(int type, int position, boolean smoothScroll) {
        switch (type) {
            case 0:
                mCity = mArea = null;
                if (mTabLayout.getTabAt(2) != null) {
                    mTabLayout.removeTabAt(2);
                    mAdapter.removeItem(2);
                }
                if (mTabLayout.getTabAt(1) != null) {
                    mTabLayout.removeTabAt(1);
                    mAdapter.removeItem(1);
                }


                // 记录当前选择的省份
                mProvince = mAdapter.getItem(type).get(position);
                mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).setText(mProvince.getName());

                notifySecondItemChanged(type, position);
                mTabLayout.addTab(mTabLayout.newTab().setText(getTabName(type + 1)), true);
                mAdapter.addItem(mAdapter.getItemCount(), mAdapter.getItem(type).get(position).getNextList());
                mViewPager.setCurrentItem(type + 1, smoothScroll);


                // 如果当前选择的是直辖市，就直接跳过选择城市，直接选择区域
                if (mAdapter.getItem(type + 1).size() == 1) {
                    selectedAddress(type + 1, 0, false);
                }
                break;
            case 1:
                mArea = null;
                if (mTabLayout.getTabAt(2) != null) {
                    mTabLayout.removeTabAt(2);
                    mAdapter.removeItem(2);
                }

                // 记录当前选择的城市
                mCity = mAdapter.getItem(type).get(position);
                mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).setText(mCity.getName());

                notifySecondItemChanged(type, position);
                mTabLayout.addTab(mTabLayout.newTab().setText(getTabName(type + 1)), true);
                mAdapter.addItem(mAdapter.getItemCount(), mAdapter.getItem(type).get(position).getNextList());
                mViewPager.setCurrentItem(type + 1, smoothScroll);

                break;
            case 2:
                // 记录当前选择的区域
                mArea = mAdapter.getItem(type).get(position);
                mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).setText(mArea.getName());

                notifySecondItemChanged(type, position);
                break;
            default:
                break;
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

    public class ViewpagerAdapter extends BaseQuickAdapter<List<AddressItemEntity>, BaseViewHolder> {

        private OnAdressItmeListener listener;

        public ViewpagerAdapter(@Nullable List<List<AddressItemEntity>> data, OnAdressItmeListener listener) {
            super(R.layout.item_onepage_contain_recyclerview, data);
            this.listener = listener;

        }

        @Override
        protected void convert(@NonNull BaseViewHolder holder, List<AddressItemEntity> entity) {
            RecyclerView recyclerView = holder.findView(R.id.viewpage_item_recyclerView);
            AdressItmeAdapter adapter = new AdressItmeAdapter(entity, holder.getAdapterPosition());
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


        public void addItem(@IntRange(from = 0) int position, @NonNull List<AddressItemEntity> item) {

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

    public class AdressItmeAdapter extends BaseQuickAdapter<AddressItemEntity, BaseViewHolder> {

        int parentPosition;

        public AdressItmeAdapter(@Nullable List<AddressItemEntity> data, int parentPosition) {
            super(R.layout.item_content, data);
            this.parentPosition = parentPosition;
        }

        @Override
        protected void convert(@NonNull BaseViewHolder holder, AddressItemEntity entity) {
            holder.setText(R.id.recyclerview_content_text, entity.getName());
            boolean isSelect = false;
            switch (parentPosition) {
                case 0:
                    if (mProvince != null && mProvince.getCode().equals(entity.getCode())) {
                        isSelect = true;
                    }
                    break;
                case 1:
                    if (mCity != null && mCity.getCode().equals(entity.getCode())) {
                        isSelect = true;
                    }
                    break;
                case 2:
                    if (mArea != null && mArea.getCode().equals(entity.getCode())) {
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

    public interface OnAdressItmeListener {
        void onClick(int viewpagerPosition, int clickItemPosition, boolean smoothScroll);
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


    public ViewPager2 getViewPager() {
        return mViewPager;
    }

    public TabLayout getTabLayout() {
        return mTabLayout;
    }

    public AddressItemEntity getProvince() {
        return mProvince;
    }

    public AddressItemEntity getCity() {
        return mCity;
    }

    public AddressItemEntity getArea() {
        return mArea;
    }

    private OnRecyclerviewStyleListener onRecyclerviewStyleListener;

    public OnRecyclerviewStyleListener getOnRecyclerviewStyleListener() {
        return onRecyclerviewStyleListener;
    }

    public void setOnRecyclerviewStyleListener(OnRecyclerviewStyleListener onRecyclerviewStyleListener) {
        this.onRecyclerviewStyleListener = onRecyclerviewStyleListener;
    }


}
