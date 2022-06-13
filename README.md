# JFPicker

# 一、项目的由来

**参考开源库： 安卓选择器类库：https://github.com/gzu-liyujiang/AndroidPicker.  
感谢大佬的开源。 该代码库仅用于日常开发和学习，禁止用于任何非法用途。**

使用 RecyclerView + RecyclerView.ItemDecoration 替换了AndroidPicker的核心滚轮控件，提供更好的滚轮3D效果和滚动体验。

* 核心的WheelView滚轮控件，样式由WheelDecoration绘制，数据由WheelViewAdapter控制，代码来自同事分享，应该是来自网上大神的分享，如果知道来源请留言  
  经过重构后，支持更多的设置。  
  属性可以动态设置并立即生效 ：
    1. 滚轮效果设置：滚轮样式或RecyclerView平铺样式、是否使用画布偏移实现更立体的3D效果、滚轮的弯曲程度、除选中项上下各几项、每一项高度
    2. 字体设置：字体颜色、选中字体颜色、字体大小、选中字体大小、是否加粗、选中是否加粗
    3. 间隔设置：间隔样式、间割线之间间隔、间割背景颜色、间隔线颜色、间隔线宽度、间隔圆角
    4. 自定义格式化样式：使用自定义透明度渐变、使用自定义文字大小渐变、使用自定义文字格式化

* 在AndroidPicker的基础上重做了部分滚轮弹窗
    1. 年月日选择 YearMonthDayPicker、时分秒选择 HourMinuteSecondPicker 、年月日时分秒选择 DateTimePicker
    2. 带文本的年月日选择 BirthdayPicker、带文本的年月日时分秒选择 BirthdayWithTimePicker
    3. 使用CalendarView日历组件制作的 日历选择，样式优美。CalendarPicker，提供了单选、多选、范围选择
    4. 单项滚轮选择 OptionPicker、三级联动选择 LinkagePicker、列表样式的单项选择 OptionRecyclerViewPicker
    5. 单选选择 RadioPicker、多选选择 CheckBoxPicker、提示弹窗 TipsPicker 、输入弹窗 InputPicker
    6. 地址选择 AddressPicker 、ViewPager样式的地址选择 AddressViewPagerPicker、 ViewPager样式的五级选择
       NetRequestPicker

# 使用的其他开源库

**Android轮子哥的开源 https://github.com/getActivity**
**CalendarView日历组件 https://github.com/huanghaibin-dev/CalendarView**
