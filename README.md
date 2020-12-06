# DQMaster

#### DQCommonToolbar
> implementation 'com.dashingqi:DQCommonToolbar:0.9.9.2'
- 通用的标题栏，可设置左右标题和主标题（中间），以及返回按钮

#### DQCommonUtils
> implementation 'com.dashingqi:DQCommonUtils:0.9.9.1'
###### 通用的工具类
- ContextUtils
- DateUtils
- DensityUtils
- MotionEventUtils (事件的工具类，目前封装了打印事件的方法)
- ProcessUtils （获取进程名字的工具类）
- ToastExt （Toast的扩展类）

#### DQMVVMBase

###### MVVM架构抽取的Base基类
- BaseMVVMActivity

###### RV
- OptimizeRecyclerView
  - 经过优化后的RecyclerView,
  - 目前使用的场景是：当TabLayout+ViewPager2+RecyclerView中，ViewPager2滑动灵敏，导致TabLayout切换的问题。

###### LiveData
- InitLiveData
  - 带有非空默认值的LiveData
  - 在项目中有时候我们使用MutableLiveData的时候不赋默认值的话，取数据可能会取到null，UI显示上会很难看，有时候涉及到数据的操作坑你会发生crash
  - InitLiveData就是为了解决上述痛点而设计的。
- DiffLiveData
  - 如果当前LiveData中携带的值和将要设置的值是一致的，就不进行设置值的操作了
  - 减少了一次数据操作

#### DQDialog
- 通用的Dialog，主要封装了通用性
> implementation 'com.dashingqi:DQDialog:0.9.9.1'

###### 具体实现例子
- LoadingDialog：可直接使用，带有旋转的菊花
- BottomDialog：可继承，设置自己的布局
- BoxDialog：提示的对话框，可订制性高

#### DQCountDown
- 自定义倒计时控件，采用RxJava实现倒计时功能
> implementation 'com.dashingqi:DQCountDown:0.9.9.1'
###### 简介
- 自定义样式的倒计时控件，通过对外提供的getLayoutId()方法，可以设置自己编写的布局文件
- 唯一注意的点是，布局对应的控件名字要保持一致，（tvHour，tvMinus，tvSecond）

