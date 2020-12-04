# DQMaster

#### DQCommonToolbar
> implementation 'com.dashingqi:DQCommonToolbar:0.9.9.2'
- 通用的标题栏

#### DQCommonUtils
> implementation 'com.dashingqi:DQCommonUtils:0.9.9.1'
###### 通用的工具类
- ContextUtils
- DateUtils
- DensityUtils

#### DQMVVMBase
###### MVVM架构抽取的Base基类
- BaseMVVMActivity

#### DQDialog
> implementation 'com.dashingqi:DQDialog:0.9.9.1'
###### 通用的Dialog
- LoadingDialog：可直接使用，带有旋转的菊花
![loading_dialog.gif](https://upload-images.jianshu.io/upload_images/4997216-40e8dd85cef3a40f.gif?imageMogr2/auto-orient/strip)
- BottomDialog：可继承，设置自己的布局
![bottom_dialog.gif](https://upload-images.jianshu.io/upload_images/4997216-f30abba8990a5353.gif?imageMogr2/auto-orient/strip)
- BoxDialog：提示的对话框，可订制性高

#### DQCountDown
>
###### 简介
- 自定义样式的倒计时控件，通过对外提供的getLayoutId()方法，可以设置自己编写的布局文件
- 唯一注意的点是，布局对应的控件名字要保持一致，（tvHour，tvMinus，tvSecond）
> implementation 'com.dashingqi:DQCountDown:0.9.9.1'
