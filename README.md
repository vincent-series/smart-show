## SmartShow

★★★ 本库为androidx支持库版。

### 模块导航：

* [SmartToast](#SmartToast部分)
* [SmartSnackbar](#SmartSnackbar部分)
* [SmartTopBar](#SmartTopbar部分)
* [SmartDialog](#SmartDialog部分)
* [关于实现](#代码实现)
* [关于作者及技术交流](#关于作者)
* [引入该库](#引入SmartShow)

### 功能总览：

1. 优雅封装，简化调用
2. 处理系统bug等，如Android 7.1系统关于Toast的BadTokenException
3. 解决已知的UI性能缺陷，提高用户体验，如Toast重复弹跳等,关闭通知权限后Toast不显示
4. 对不同系统版本及厂商设备进行适配
5. 合理的复用策略，节约内存开销的同时及时解除引用以便被垃圾回收器回收
6. 根据实际开发中的常见需求，进行功能扩展

### 效果展示

![图片加载失败](images/type_toast_all.gif)&nbsp;&nbsp;&nbsp;&nbsp;![图片加载失败](images/topbar_normal.gif)<br/>
![图片加载失败](images/toast_all.gif)&nbsp;&nbsp;&nbsp;&nbsp;![图片加载失败](images/dialog.gif)<br/>

## 代码实现

[回到模块导航](#模块导航)<br/><br/>
如果你对实现感兴趣，请参考我的CSDN博客：

* 2.x 版本 https://blog.csdn.net/jungle_pig/article/details/83959662
* 1.x 版本 https://blog.csdn.net/jungle_pig/article/details/78568493<br/>

## 关于作者

[回到模块导航](#模块导航)<br/><br/>
为使SmartShow库更加健壮，如果您在使用过程中发现任何问题，请联系我，我会立即跟进修复和维护。感谢您的支持！<br/><br/>
作者：朱志强<br/><br/>
微信：w361281607<br/><br/>
<img src="images/wx_2d.jpeg" width="180" height="180"/><br/>
邮箱：coder_zzq@163.com<br/><br/>

## SmartToast部分

[回到模块导航](#模块导航)

1. 内部三种Toast实现策略:系统window + Toast + dialog,根据实际情况择优选取
2. 合理的复用策略，避免Toast重复弹跳
3. 解决Android 7.1的系统bug——Toast BadTokenException
4. 可修改Toast默认布局的风格，如背景颜色，文字大小和颜色等
5. 完美解决应用关闭通知权限后Toast不显示问题
6. 适配android 11对toast的相关限制
7. 配置Toast风格，如背景颜色或文字大小
8. 通过注解完全自定义新的Toast
8. 结合主流app消息提示的效果，提供info、success、error、warning、complete、forbid、wait、fail 8 种类型的Emotion Toast

### 引入依赖

<pre><code>
implementation "io.github.vincent-series:smart-toast:4.0.1"
</code></pre>

### API

[查看API文档](https://github.com/zhiqiang-series/smart-show/tree/master/smart-toast)

#### 普通 Toast

默认样式：![图片加载失败](images/toast_normal.gif)设置背景色：![图片加载失败](images/toast_color.gif)

## SmartDialog部分

[回到模块导航](#模块导航)<br/><br/>

1. 解决因activity、fragment生命周期导致的BadTokenException、NullPointException等问题
2. 提供主流APP中使用的message、input、list、loading等对话框<br/>

#### 效果图

![图片加载失败](images/dialog.gif)

#### 原理

SmartDialog并不是android.app.Dialog的子类,只是个包装器，它内部持有一个真正的Dialog，用来显示。SmartDialog负责处理当Activity、Fragment
生命周期异常时，取消创建或显示所持Dialog。<br/>

#### API

[查看API文档]()



