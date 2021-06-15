# DQMaster

```gradle
allprojects {
    repositories {
        mavenCentral()
        google()
    }
}
```

#### DQCommonToolbar
> implementation 'io.github.dashingqi:common-toolbar:0.0.1'
- 通用的标题栏，可设置左右标题和主标题（中间），以及返回按钮

#### DQCommonUtils
> implementation 'io.github.dashingqi:common-utils:0.0.1'
###### 通用的工具类
- ContextUtils
- DateUtils
- DensityUtils
- MotionEventUtils (事件的工具类，目前封装了打印事件的方法)
- ProcessUtils （获取进程名字的工具类）
- ToastExt （Toast的扩展类）
- SystemServiceUtil 系统服务的工具类 （比如copy）
- UIThreadUtils 在UI线程执行任务的工具类
- AppUtil
  - 用于提供获取全局的Application工具类（主线程和子线程都通用）
  - 采用两种方式获取
    - 1 通过ContentProvider （初始化在主线程中，主线程中优先使用此种方法）
    - 2 通过反射获取到ActivityThread中的Application（子线程中，使用此种方法获取到Application）

#### DQMVVMBase
> implementation 'io.github.dashingqi:mvvm-basic:0.0.1'
###### MVVM架构抽取的Base基类
- BaseMVVMActivity
###### 扩展类
- LogExt (日志的扩展类)

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
- LostMutableLiveData
    - 防止LiveData数据倒灌现象问题，就是解除粘性的效果
    - 正常我们先post或者set的然后在注册监听者监听LiveData的状态变换是能收到为注册之前最新一次的post或者set
    - 而我们的LostMutableLiveData就是为了解决这个问题而出现的
#### DQHttp
> implementation 'io.github.dashingqi:network:0.0.1'
###### ParameterIntercept
- 根据请求方法和Body来做区分，提供拦截的回调方法，可以操作 body，get请求以及multi
###### NetService和NetServcieBuilder
- 采用建造者模式来构建NetService类（网络请求的服务类）
- NetServiceBuilder 用于构建NetService请求网络所需要的参数
- 对于OkHttp，可灵活配置拦截器 包括数据的转换器（response---> bean ）和数据的适配器(请求的返回值可自定义)
- 对于OkHttpClient和Retrofit的配置 采用了高阶函数的方式 在内部采用建造者模式，构建出OkHttpClient.Builder() 和Retrofit.Builder()，
通过高阶函数的特性将构建好的Builder回调，然后配置响应的参数

###### BaseCallAdapterFactory
- 采用工厂模式生产出了一个 CallAdapter （ResposneCallAdapter）
- ResponseCallAdapter的adapt方法中我们拿到了Call对象后 我们执行exexute()得到了Response
- 通过BaseCallback 将结果回调到 onSuccess()或者onFailure()中

###### BaseCallBack
- BaseCallBack extends retrofit.CallBack
- 在BaseCallBack中 根据 response.code，body，以及服务器接口的定义（code，token，）来做不同的回调
- 这个回调是采用的高阶函数的特性，每一个请求都可以添加对应的action（高阶函数），在回调中根据不同的状态去执行的action （action.invoke()）

#### DQDialog
- 通用的Dialog，主要封装了通用性
> implementation 'io.github.dashingqi:dialog:0.0.1'

###### 具体实现例子
- LoadingDialog：可直接使用，带有旋转的菊花
- BottomDialog：可继承，设置自己的布局
- BoxDialog：提示的对话框，可订制性高

#### DQCountDown
- 自定义倒计时控件，采用RxJava实现倒计时功能
> implementation 'io.github.dashingqi:countdown:0.0.1'
###### 简介
- 自定义样式的倒计时控件，通过对外提供的getLayoutId()方法，可以设置自己编写的布局文件
- 唯一注意的点是，布局对应的控件名字要保持一致，（tvHour，tvMinus，tvSecond）

