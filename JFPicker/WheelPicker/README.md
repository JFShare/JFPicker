# AndroidPicker

参考基于开源库： 安卓选择器类库：https://github.com/gzu-liyujiang/AndroidPicker
AndroidPicker的选择器及其强大与完善，感谢大神开源的代码。
为了满足日常的工作，比如倾斜的滚轮控件，选择年以后月日不重置到0、公司固定的样式、更快速的使用等等的要求，在阅读AndroidPicker源码后，站在巨人的肩膀上，进行了二次封装，
（仅为快速进行日常开发工作而二次封装，分享也只是希望有需要的朋友快速使用，如有冒犯或侵权，请留言联系，不会再开源该库）:

1. 替换了AndroidPicker的核心滚轮控件，改为 由 RecyclerView 和 RecyclerView.ItemDecoration绘制的 的3D滚轮控件（代码来自大神的分享）：
    1. ）支持滚轮控件的左右倾斜效果，使3D效果更立体。
    2. ）因为是基于RecyclerView实现，拥有RecyclerView的特性，滑动更丝滑
    3. ）建造者模式动态的设置滚轮控件的属性，更快的设置滚轮样式。
2. 保留了AndroidPicker的基础弹窗、大部分的数据类和方法接口
3. 二次封装了 时间选择器、单项选择器、联动选择器、地址选择器 （自己日常工作会用到的）







