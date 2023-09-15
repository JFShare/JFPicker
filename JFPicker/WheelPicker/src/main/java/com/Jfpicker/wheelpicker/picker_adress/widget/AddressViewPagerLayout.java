package com.Jfpicker.wheelpicker.picker_adress.widget;

import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_DRAGGING;
import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_IDLE;
import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_SETTLING;

import static com.Jfpicker.wheelpicker.picker_option.entity.LinkageProvider.INDEX_NO_FOUND;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.viewpager2.widget.ViewPager2;

import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.picker_adress.adapter.AddressItemAdapter;
import com.Jfpicker.wheelpicker.picker_adress.adapter.AddressVpAdapter;
import com.Jfpicker.wheelpicker.picker_adress.entity.AddressItemEntity;
import com.Jfpicker.wheelpicker.picker_adress.impl.AddressProvider;
import com.Jfpicker.wheelpicker.rv_listener.OnRecyclerviewStyleListener;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by JF on  2021/11/15
 * @description ViewPager样式的地址选择器，通过接口的形式提供设置列表项样式
 */

public class AddressViewPagerLayout extends LinearLayout {

    private ViewPager2 mViewPager;
    private AddressVpAdapter vpAdapter;
    private ViewPager2.OnPageChangeCallback mCallback;
    private TabLayout mTabLayout;

    private AddressProvider dataProvider;
    private Object firstValue, secondValue, thirdValue;
    private int firstIndex, secondIndex, thirdIndex;
    private AddressItemEntity mProvince = null;
    private AddressItemEntity mCity = null;
    private AddressItemEntity mArea = null;

    private ArrayList<String> tabNameList = new ArrayList<>();
    public static final String defaultTabName = "请选择";
    private OnRecyclerviewStyleListener onRecyclerviewStyleListener;

    public AddressViewPagerLayout(Context context) {
        super(context);
        init(context);
    }

    public AddressViewPagerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AddressViewPagerLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public AddressViewPagerLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        inflate(context, R.layout.jf_wheel_picker_adress_viewpager, this);
        onInit();
    }

    private void onInit() {
        mViewPager = findViewById(R.id.vpAddress);
        mViewPager.setOffscreenPageLimit(3);
        mCallback = new ViewPager2.OnPageChangeCallback() {
            private int mPreviousScrollState, mScrollState = SCROLL_STATE_IDLE;

            @Override
            public void onPageScrollStateChanged(int state) {
                mPreviousScrollState = mScrollState;
                mScrollState = state;
                if (state == ViewPager2.SCROLL_STATE_IDLE && mTabLayout.getSelectedTabPosition()
                        != mViewPager.getCurrentItem()) {
                    final boolean updateIndicator = mScrollState == SCROLL_STATE_IDLE ||
                            (mScrollState == SCROLL_STATE_SETTLING && mPreviousScrollState == SCROLL_STATE_IDLE);
                    mTabLayout.selectTab(mTabLayout.getTabAt(mViewPager.getCurrentItem()), updateIndicator);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                final boolean updateText = mScrollState != SCROLL_STATE_SETTLING ||
                        mPreviousScrollState == SCROLL_STATE_DRAGGING;
                final boolean updateIndicator = !(mScrollState == SCROLL_STATE_SETTLING &&
                        mPreviousScrollState == SCROLL_STATE_IDLE);
                mTabLayout.setScrollPosition(position, positionOffset, updateText, updateIndicator);
            }
        };
        mViewPager.registerOnPageChangeCallback(mCallback);

        mTabLayout = findViewById(R.id.tbAddress);
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
        firstIndex = provider.findFirstIndex(firstValue);
        secondIndex = provider.findSecondIndex(firstIndex, secondValue);
        thirdIndex = provider.findThirdIndex(firstIndex, secondIndex, thirdValue);

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
        vpAdapter = new AddressVpAdapter(getContext(), list,
                onRecyclerviewStyleListener, (viewpagerPosition, clickItemPosition, smoothScroll) -> {
            selectedAddress(viewpagerPosition, clickItemPosition, smoothScroll);
        });
        mViewPager.setAdapter(vpAdapter);
    }

    private void selectedAddress(int vpPosition, int clickItemPosition, boolean smoothScroll) {
        switch (vpPosition) {
            case 0:
                mCity = mArea = null;
                if (mTabLayout.getTabAt(2) != null) {
                    mTabLayout.removeTabAt(2);
                    vpAdapter.removeItem(2);
                }
                if (mTabLayout.getTabAt(1) != null) {
                    mTabLayout.removeTabAt(1);
                    vpAdapter.removeItem(1);
                }

                // 选择省
                mProvince = vpAdapter.getAddressItem(vpPosition, clickItemPosition);
                mTabLayout.getTabAt(vpPosition).setText(mProvince.getName());
                notifySecondItemChanged(vpPosition);
                //添加市
                mTabLayout.addTab(mTabLayout.newTab().setText(getTabName(vpPosition + 1)), true);
                vpAdapter.addItem(vpAdapter.getItemCount(),
                        vpAdapter.getAddressItem(vpPosition, clickItemPosition).getNextList());
                mViewPager.setCurrentItem(vpPosition + 1, smoothScroll);

                break;
            case 1:
                mArea = null;
                if (mTabLayout.getTabAt(2) != null) {
                    mTabLayout.removeTabAt(2);
                    vpAdapter.removeItem(2);
                }
                // 选择市
                mCity = vpAdapter.getAddressItem(vpPosition, clickItemPosition);
                mTabLayout.getTabAt(vpPosition).setText(mCity.getName());
                notifySecondItemChanged(vpPosition);
                //添加区
                mTabLayout.addTab(mTabLayout.newTab().setText(getTabName(vpPosition + 1)), true);
                vpAdapter.addItem(vpAdapter.getItemCount(),
                        vpAdapter.getAddressItem(vpPosition, clickItemPosition).getNextList());
                mViewPager.setCurrentItem(vpPosition + 1, smoothScroll);
                break;
            case 2:
                // 选择区
                mArea = vpAdapter.getAddressItem(vpPosition, clickItemPosition);
                mTabLayout.getTabAt(vpPosition).setText(mArea.getName());
                notifySecondItemChanged(vpPosition);
                break;
            default:
                break;
        }
    }

    /*
            通知ViewPage2某一页的RecyclerView熟悉样式
         */
    public void notifySecondItemChanged(int parentPosition) {
        post(() -> {
            if (vpAdapter == null) {
                return;
            }
            RecyclerView recyclerViewImpl = (RecyclerView) mViewPager.getChildAt(0);
            ViewHolder holder = recyclerViewImpl.findViewHolderForLayoutPosition(parentPosition);
            if (holder instanceof AddressVpAdapter.AddressVpViewHolder) {
                AddressVpAdapter.AddressVpViewHolder parentHolder = (AddressVpAdapter.AddressVpViewHolder) holder;
                if (parentHolder.rvPageItem.getAdapter() != null
                        && parentHolder.rvPageItem.getAdapter() instanceof AddressItemAdapter) {
                    AddressItemAdapter addressItemAdapter = (AddressItemAdapter) parentHolder.rvPageItem.getAdapter();
                    addressItemAdapter.notifyCurrentAddressChanged(mProvince, mCity, mArea);
                }
            }
        });

    }

    //设置tab名称
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


    public void setOnRecyclerviewStyleListener(OnRecyclerviewStyleListener onRecyclerviewStyleListener) {
        this.onRecyclerviewStyleListener = onRecyclerviewStyleListener;
    }


}
