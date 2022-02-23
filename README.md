# JFPicker

# 一、项目的由来
Android 萌新，人菜技术菜，努力生活
**参考开源库： 安卓选择器类库：https://github.com/gzu-liyujiang/AndroidPicker**
AndroidPicker的选择器功能强大而完善，感谢大神开源的代码。
AndroidPicker基本能满足日常开发工作，但为了更快的使用公司规定而固定的选择器样式，比如选择年月以后月日不重置到0的日期选择器、选择时分后分秒不会重置到0的时间选择器，
年日具有左右倾斜效果的日期选择器、文字大小渐变的日期选择器，列表和固定长度列表样式的单选选择器、五级以内的网络请求选择器、ViewPager样式的地址选择器等等。
在阅读AndroidPicker源码后，站在巨人的肩膀上。
**仅为固定公司选择器样式快速开发日常项目而进行的封装，希望可以对大家有所帮助，提供思路与便捷。 如有问题，请gitbub留言联系**
**如何使用**
项目已上传jitPack
```html
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
		dependencies {
    	        implementation 'com.github.JFShare:JFPicker:Tag'
    	}
```
# 二、封装的主要内容

1. **核心滚轮控件改为 由 RecyclerView 和 RecyclerView.ItemDecoration 绘制的的滚轮控件（代码来自同事的分享并更改，应该是网上大神分享的代码，如知道来源请留言）**：
    1. )支持滚轮控件的左右倾斜效果，支持绘制文本的位置（靠左居中靠右），使3D效果更立体。
    2. )支持文字大小的渐变、支持文字透明度的渐变、支持文字加粗选中文字加粗，使滚轮组件的样式更丰富
    3. )因为是基于RecyclerView实现，拥有RecyclerView的特性，滑动更丝滑，没有卡顿的感觉
    4. )因为是基于RecyclerView实现，数据源有Adapter控制，滚轮样式由ItemDecoration控制，替换数据样式更方便
    4. )支持建造者模式动态的设置滚轮控件的属性，更快的设置滚轮样式。

滚轮组件WheelView所有属性如下：

```html

<declare-styleable name="WheelView">
    <!-- 中间分割线外的item数量,整个滑动数量就为 wheelItemCount * 2 + 1  -->
    <attr name="wheelItemCount" format="integer"/>
    <!-- 滑轮item高度 -->
    <attr name="wheelItemSize" format="dimension"/>
    <!-- 滑轮字体大小 -->
    <attr name="wheelTextSize" format="dimension"/>
    <!-- 滑轮字体颜色 -->
    <attr name="wheelTextColor" format="color"/>
    <!-- 滑轮中心字体颜色 -->
    <attr name="wheelTextColorCenter" format="color"/>

    <!-- 分割线颜色 -->
    <attr name="wheelDividerColor" format="color"/>
    <!-- 布局方向 -->
    <attr name="wheelOrientation">
        <enum name="vertical" value="1"/>
        <enum name="horizontal" value="2"/>
    </attr>
    <!-- 水平布局时不考虑只参数,  当垂直布局时的左右靠齐立体效果 -->
    <attr name="wheelGravity">
        <enum name="left" value="1"/>
        <enum name="center" value="2"/>
        <enum name="right" value="3"/>
    </attr>
    <!-- 文字布局 -->
    <attr name="wheelTextGravity">
        <enum name="left" value="1"/>
        <enum name="center" value="2"/>
        <enum name="right" value="3"/>
    </attr>

    <!-- WheelView 整体 左右倾斜的幅度 -->
    <attr name="gravityCoefficient" format="float"/>

    <!--WheelView 所有展示item项3D滚轮效果的角度，最后平均给展示Item 建议 0-180 ，太大滚轮效果太大，等于0就是平面了 -->
    <attr name="itemDegreeTotal" format="float"></attr>

    <!-- 两根分割线的距离 -->
    <attr name="wheelDividerSize" format="dimension"/>

    <!-- 是不是仿ios样式的滚轮 -->
    <attr name="isWheel" format="boolean"></attr>

    <!-- 文字是否加粗，不包含选中文字 -->
    <attr name="isTextBlod" format="boolean"></attr>

    <!-- 选中文字是否加粗 -->
    <attr name="isCenterTextBlod" format="boolean"></attr>

    <!-- 是否支持文字透明度渐变 从中间到两边逐渐透明 -->
    <attr name="alphaGradient" format="boolean"></attr>

    <!-- 是否支持文字大小渐变 从中间到两边逐渐变小 -->
    <attr name="textSizeGradient" format="boolean"></attr>

    <!-- 文字大小渐变 最小的文字尺寸基础-->
    <attr name="minGradientTextSize" format="dimension"></attr>

</declare-styleable>
```

提供了WheelView所有属性的构造类WheelAttrs，以及 WheelAttrs的建造者模式, 可以使用WheelView的getAttrsBuilder()
方法获取当前WheelView的所有属性，使用setWheelAttrs(WheelAttrs attrs)方法设置WheelView的所有属性。

```java
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

    public static class Builder {
        WheelAttrs wheelAttrs;

        public Builder() {
            wheelAttrs = new WheelAttrs();
        }

    }
}

```

2. **保留了AndroidPicker的弹窗模块、部分的数据类和方法接口（针对公司业务进行了修改），再次感谢大佬的开源**,如果你足够了解AndroidPicker的源码，那么不会有任何阅读问题。
3. 针对公司基本固定样式的选择器进行了封装 
   3.1. )日期选择器 YearMonthDayPicker、BirthdayPicker （组件对应YearMonthDayWheelLayout）:
   封装目的 选择年以后月日不重置到0、选择月以后日不会重置到0并且超出上一次选择范围的自动调整到最后一天（比如3月有31天，2月29天,从3月31选择到2月份的时候会保留日为2月29） 示例:
```java
                YearMonthDayPicker picker = new YearMonthDayPicker(DatePickActivity.this);
                picker.setOnDatePickedListener(new OnDatePickedListener() {
                    @Override
                    public void onDatePicked(int year, int month, int day) {
                        Toast.makeText(DatePickActivity.this,
                                year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show();
```

3.2. ) 时间选择器 HourMinuteSecondPicker （组件对应HourMinuteSecondWheelLayout）:
封装目的 选择时以后分秒不重置到0、选择分以后秒不会重置到0，时分秒各自滚动不会限制另外两个不能滚动。因公司业务几乎不需要所以去除了12小时制，如果需要请自定义 示例:

```java
                HourMinuteSecondPicker picker = new HourMinuteSecondPicker(DatePickActivity.this);
                picker.setOnTimePickedListener(new OnTimePickedListener() {
                    @Override
                    public void onTimePicked(int hour, int minute, int second) {
                        Toast.makeText(DatePickActivity.this,
                                hour + "-" + minute + "-" + second, Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show();
```

3.3. ) 时间日期选择器 DateTimePicker、BirthdayWithTimePicker 上面两个的结合
3.4. ) 选项选择器
OptionPicker（组件对应OptionWheelLayout） 三级联动选择器LinkagePicker（组件对应LinkageWheelLayout） 封装目的 使用
OptionEntity，包含id和name 的数据 其他保留了AndroidPicker的样式（LinkageProvider提供三级数据源）

```java
                List<OptionEntity> list = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    if (i % 2 == 1) {
                        list.add(new OptionEntity(i + "", "选项" + i + "是一个非常非常非常非常非常非常非常非常长的文字"));
                    } else {
                        list.add(new OptionEntity(i + "", "选项" + i));
                    }

                }
                OptionPicker picker = new OptionPicker(OptionPickActivity.this, "选项选择", list);
                picker.setOnOptionPickedListener(new OnOptionPickedListener() {
                    @Override
                    public void onOption(String id, String name) {
                        Toast.makeText(OptionPickActivity.this, "选择了id=" + id + "name=" + name, Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show();
```

3.5  ) 列表样式的选项选择器 ，使用RecyclerView的选择器，在传入数据的时候可以设置自定义的布局，要求有一个id是recyclerview_content_text的TextView

```java
                OptionRecyclerViewPicker picker = new OptionRecyclerViewPicker(OptionPickActivity.this, DialogConfig.getDefaultConfig());
                List<OptionEntity> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    list.add(new OptionEntity("" + i, "第" + i + "条数据"));
                }
                picker.setOptionsData(list);
                picker.setOnOptionPickedListener(new OnOptionPickedListener() {
                    @Override
                    public void onOption(String id, String name) {
                        Toast.makeText(OptionPickActivity.this, "选择了id=" + id + "name=" + name, Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show();
``` 

3.6  )地址选择器 AddressPicker
保留了AndroidPicker的地址选择器模板（AddressLoader、AddressParser、AddressReceiver、AddressProvider等）

```java
                AddressPicker picker = new AddressPicker(AdressPickActivity.this);
                picker.setOnAddressPickedListener(new OnAddressPickedListener() {
                    @Override
                    public void onAddressPicked(AddressItemEntity province, AddressItemEntity city, AddressItemEntity county) {
                        toastAdress(province, city, county);
                    }
                });

                picker.show();
```

3.7  )ViewPager样式地址选择器 AddressViewPagerPicker，代码样式来自Android轮子哥的开源 https://github.com/getActivity
通过接口的形式把样式设置暴露出来，通过 setOnRecyclerviewStyleListener 方法设置列表项的样式，实现样式的自定义。
```java
                DialogConfig config = DialogConfig.getDefaultConfig();
                config.getDialogBackground().setDialogCornerRound(CornerRound.Top)
                        .setCornerRadius(15);
                AddressViewPagerPicker picker = new AddressViewPagerPicker(AdressPickActivity.this, config);

                picker.setOnAddressPickedListener(new OnAddressPickedListener() {
                    @Override
                    public void onAddressPicked(AddressItemEntity province, AddressItemEntity city, AddressItemEntity county) {
                        toastAdress(province, city, county);
                    }
                });
                picker.show();
```

3.7  ) 五级的选项选择器NetRequestPicker（组件对应NetRequestLayout），在3.7)ViewPager样式地址选择器 的基础上，做了支持五级的选择器，
用于日常后台只返回给我们第一级数据，通过第一级数据的id请求第二级的数据，用户选择了第二级数据以后，再用第二级数据的id请求第三级的数据，依次类推... 同样可以使用
setOnRecyclerviewStyleListener 设置列表项样式 通过设置 setOnNetRequestStartListener 获取上一级的id，进行下一级数据请求，通过
setNextData方法设置下一级的数据
```java
                DialogConfig config = DialogConfig.getDefaultConfig();
                config.getDialogBackground().setCornerRadius(15).setDialogCornerRound(CornerRound.Top);

                NetRequestPicker picker = new NetRequestPicker(AdressPickActivity.this, config);
                List<OptionEntity> firstData = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    firstData.add(new OptionEntity("0" + i, "第0组第" + i + "个数据"));
                }
                picker.setFirstData(firstData);
                picker.setMaxIndex(5);

                picker.setOnNetRequestStartListener(new NetRequestLayout.OnNetRequestStartListener() {
                    @Override
                    public void onRequest(int parentPosition, OptionEntity entity) {
                        //模拟网络请求
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                List<OptionEntity> nextData = new ArrayList<>();
                                for (int i = 0; i < 10; i++) {
                                    nextData.add(new OptionEntity((parentPosition + 1) + "-" + i, "第" + (parentPosition + 1) + "组第" + i + "个数据"));
                                }
                                picker.setNextData(nextData);
                            }
                        }, 500);
                    }
                });
                picker.setOnNetRequestPickedListener(new OnNetRequestPickedListener() {
                    @Override
                    public void onPicked(OptionEntity firstData, OptionEntity secondData, OptionEntity thirdDate,
                                         OptionEntity fourthData, OptionEntity fifthData) {
                        toastOptions(firstData, secondData, thirdDate, fourthData, fifthData);
                    }
                });

                picker.show();
```

3.8  ) 日历选择弹窗CalendarPicker(组件对应CalendarLayout)，提供了默认样式，单选多选和范围选择
日历组件CalendarView日历组件 https://github.com/huanghaibin-dev/CalendarView，如果想自定义日历样式请查看大神的git
```java
                DialogConfig config = DialogConfig.getDefaultConfig();
                config.getDialogBackground().setDialogCornerRound(CornerRound.Top)
                        .setCornerRadius(15);
                CalendarPicker picker = new CalendarPicker(CalendarPickActivity.this, config);
                picker.setOnCalendarPickedListener(new OnCalendarPickedListener() {
                    @Override
                    public void onCalendarPicked(int year, int month, int day) {
                        Toast.makeText(CalendarPickActivity.this, "选择了" + year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show();
```

4. 关于弹窗样式 ，保留了AndroidPicker的弹窗样式设置，不过全局的默认样式使用 DefaultDialogConfig设置 ，单个弹窗的样式 使用 DialogConfig
   通过构造方法传入。

5.关于滚轮样式，默认滚轮样式 （有3D效果）

```html

<style name="defaultWheel">
        <item name="wheelDividerColor">#dedede</item>
        <item name="wheelDividerSize">35dp</item>
        <item name="wheelItemCount">3</item>
        <item name="wheelItemSize">35dp</item>
        <item name="wheelTextColor">#999999</item>
        <item name="wheelTextColorCenter">#333333</item>
        <item name="wheelTextGravity">center</item>
        <item name="wheelTextSize">18sp</item>
        <item name="gravityCoefficient">0.75</item>
        <item name="isWheel">true</item>
        <item name="alphaGradient">true</item>
        <item name="isTextBlod">false</item>
        <item name="isCenterTextBlod">false</item>
        <item name="textSizeGradient">false</item>
        <item name="minGradientTextSize">10dp</item>
    


</style>
```   

默认的滚轮列表样式（没有3D效果）

```html

<style name="defaultNoWheel">
        <item name="wheelDividerColor">#dedede</item>
        <item name="wheelDividerSize">50dp</item>
        <item name="wheelItemCount">2</item>
        <item name="wheelItemSize">50dp</item>
        <item name="wheelTextColor">#999999</item>
        <item name="wheelTextColorCenter">#333333</item>
        <item name="wheelTextGravity">center</item>
        <item name="wheelTextSize">18sp</item>
        <item name="gravityCoefficient">0.75</item>
        <item name="isWheel">false</item>
        <item name="alphaGradient">true</item>
        <item name="isTextBlod">false</item>
        <item name="isCenterTextBlod">false</item>
        <item name="textSizeGradient">false</item>
        <item name="minGradientTextSize">10dp</item>
    


</style>

```   

如果想全局替换掉这两个样式，请在自己的 styles 资源文件中定义相同的样式即可，当然，动态设置的样式会覆盖默认样式

## 感谢

**最后，再次感谢开源代码：AndroidPicker https://github.com/gzu-liyujiang/AndroidPicker，
感谢大佬的开源。 该代码库仅用于日常开发和学习，禁止用于任何非法用途。**

## 使用的其他开源库

**Android轮子哥的开源 https://github.com/getActivity
CalendarView日历组件 https://github.com/huanghaibin-dev/CalendarView
BaseRecyclerViewAdapterHelper快速的RecyclerAdapter https://github.com/CymChad/BaseRecyclerViewAdapterHelper**
