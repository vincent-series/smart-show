## SmartShow
★★★ 从3.0.0版本开始，默认的依赖引用方式引用的是androidx版本，support版本也会同步更新。<br/><br/>
★★★ support版本的源码单独分离成库,详见[smart-show-support](https://github.com/the-pig-of-jungle/smart-show-support)<br/><br/>
★★★ 详细的API文档请查看[wiki](https://github.com/the-pig-of-jungle/smart-show/wiki)。<br/><br/>
★★★ 关注技术公众号，随时获知库的更新及其他优质技术文章。<br/><br/>
<img src="images/gz_logo.jpeg" width="200" height="200"/>
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
技术公众号：<br/><br/>
<img src="images/gz_logo.jpeg" width="180" height="180"/>
## 引入SmartShow
[回到模块导航](#模块导航)<br/>
#### 第一步，在Project的gradle文件中添加jitpack仓库
<pre><code>
allprojects {

    repositories {

        ...

        maven { url 'https://jitpack.io' }

    }

}
</code></pre>
#### 第二步，在Module的gradle文件中添加依赖
* 第一种方式，引入所有模块
<pre><code>
    // for androidx

    implementation ('com.github.the-pig-of-jungle.smart-show:all:3.0.1')

    //如果会使用到SmartSnackbar模块，需添加material库,such as 1.1.0-alpha09

    implementation 'com.google.android.material:material:x.y.z'

</code></pre>

<pre><code>

    implementation ('com.github.the-pig-of-jungle.smart-show-support:all:3.0.1')

    //如果会使用到SmartSnackbar模块，需添加design库

    implementation 'com.android.support:design:x.y.z'

</code></pre>

* 第二种方式，自由引入各个模块<br/>

<pre><code>

    //smart toast for androidx

    implementation 'com.github.the-pig-of-jungle.smart-show:toast:3.0.1'
</code></pre>

<pre><code>

    //smart toast for support

    implementation 'com.github.the-pig-of-jungle.smart-show-support:toast:3.0.1'
</code></pre>


<pre><code>

    // smart dialog for androidx

    implementation 'com.github.the-pig-of-jungle.smart-show:dialog:3.0.1'
</code></pre>

<pre><code>

    // smart dialog for support

    implementation 'com.github.the-pig-of-jungle.smart-show-support:dialog:3.0.1'
</code></pre>

<pre><code>

    // smart topbar for androidx

    implementation('com.github.the-pig-of-jungle.smart-show:topbar:3.0.1')
</code></pre>

<pre><code>

    // smart topbar for support

    implementation('com.github.the-pig-of-jungle.smart-show-support:topbar:3.0.1')
</code></pre>

<pre><code>

    // smart snackbar for androidx

    implementation('com.github.the-pig-of-jungle.smart-show:snackbar:3.0.1')

    //添加material依赖库,such as 1.1.0-alpha09

    implementation 'com.google.android.material:material:x.y.z'

</code></pre>

<pre><code>

    // smart snackbar for support

    implementation('com.github.the-pig-of-jungle.smart-show-support:snackbar:3.0.1')

    //添加与你项目匹配的design依赖库的相应版本

    implementation 'com.android.support:design:x.y.z'
</code></pre>

#### 第三步，在Application的onCreate方法中初始化<br/>
<pre><code>
        SmartShow.init(this);
</code></pre>

## SmartToast部分
[回到模块导航](#模块导航)
1. 使用application context，而不是activity，避免因activity生命周期问题引起的各种问题
2. 复用Toast实例，当Toast正在显示时，多次触发内容和位置均未改变的Toast，不会重复弹出；下一个Toast不会等到上一个Toast的Duration耗尽才弹出
3. 解决传统复用模式的功能缺陷，如正在显示一个内容为"A"的Toast，此时再弹出内容为"B"的Toast时，文本虽改变，但没有弹出效果；如果复用的Toast正在显示，改变其Gravity以改变显示位置会无效，直到消失后再次显示才生效
4. 可修改Toast默认布局的风格，如背景颜色，文字大小和颜色等
5. 可为Toast设置自定义布局
6. 完美解决Android 7.1的系统bug——Toast BadTokenException
7. 完美解决应用关闭通知权限后Toast不显示问题
8. 可配置离开当前页面（退出当前activity或进入新的activity），立即消失正在显示的Toast
9. 结合主流app消息提示的效果，提供info、success、error、warning、complete、forbid、wait、fail 8 种类型的Toast
### API
详细文档请参阅wiki:
* [normal toast](https://github.com/the-pig-of-jungle/smart-show/wiki/NormalToast)
  * [show at bottom](https://github.com/the-pig-of-jungle/smart-show/wiki/NormalToast#%E9%BB%98%E8%AE%A4%E4%BD%8D%E7%BD%AE%E6%98%BE%E7%A4%BA)
  * [show in center](https://github.com/the-pig-of-jungle/smart-show/wiki/NormalToast#%E4%B8%AD%E5%A4%AE%E6%98%BE%E7%A4%BA)
  * [show at top](https://github.com/the-pig-of-jungle/smart-show/wiki/NormalToast#%E9%A1%B6%E9%83%A8%E6%98%BE%E7%A4%BA)
  * [somewhere](https://github.com/the-pig-of-jungle/smart-show/wiki/NormalToast#%E4%BB%BB%E6%84%8F%E4%BD%8D%E7%BD%AE%E6%98%BE%E7%A4%BA)
* [emotion toast](https://github.com/the-pig-of-jungle/smart-show/wiki/EmotionToast)
  * [info](https://github.com/the-pig-of-jungle/smart-show/wiki/EmotionToast#info)
  * [warning](https://github.com/the-pig-of-jungle/smart-show/wiki/EmotionToast#warning)
  * [success](https://github.com/the-pig-of-jungle/smart-show/wiki/EmotionToast#success)
  * [error](https://github.com/the-pig-of-jungle/smart-show/wiki/EmotionToast#error)
  * [fail](https://github.com/the-pig-of-jungle/smart-show/wiki/EmotionToast#fail)
  * [complete](https://github.com/the-pig-of-jungle/smart-show/wiki/EmotionToast#complete)
  * [forbid](https://github.com/the-pig-of-jungle/smart-show/wiki/EmotionToast#forbid)
  * [waiting](https://github.com/the-pig-of-jungle/smart-show/wiki/EmotionToast#waiting)
* [自定义Toast](https://github.com/the-pig-of-jungle/smart-show/wiki/%E8%87%AA%E5%AE%9A%E4%B9%89Toast)
#### 普通 Toast
默认样式：![图片加载失败](images/toast_normal.gif)设置背景色：![图片加载失败](images/toast_color.gif)


## SmartTopbar部分
[回到模块导航](#模块导航)<br/><br/>
默认样式：![图片加载失败](images/topbar_normal.gif)设置背景色：![图片加载失败](images/topbar_color.gif)
1. SmartTopbar在功能以及使用上很像一个顶部的Snackbar<br/>
2. 当Topbar正在显示，多次触发时，若msg和actionTex均未改变，则不会重复弹出，否则有弹出效果
3. 可修改布局风格，如背景颜色，文字大小和颜色等
4. 可配置离开当前Activity时，立即消失正在显示的Topbar。如，在Activity A 上显示了一个Indefinite Topbar，并且用户没有
点击响应，启动activity B，然后再返回A，原来的Topbar已自动消失
5. 可通过手势右滑隐藏Topbar
### API
详细文档请参阅wiki:
* [normal topbar](https://github.com/the-pig-of-jungle/smart-show/wiki/Short-Topbar)
  * [short topbar](https://github.com/the-pig-of-jungle/smart-show/wiki/Short-Topbar)
    * [show message](https://github.com/the-pig-of-jungle/smart-show/wiki/Short-Topbar#%E6%98%BE%E7%A4%BA%E4%B8%80%E6%9D%A1%E6%B6%88%E6%81%AF)
    * [show message and aciton](https://github.com/the-pig-of-jungle/smart-show/wiki/Short-Topbar#%E6%8C%87%E5%AE%9A%E5%8A%A8%E4%BD%9C%E6%8C%89%E9%92%AE%E5%93%8D%E5%BA%94%E7%82%B9%E5%87%BB)
    * [message with icon](https://github.com/the-pig-of-jungle/smart-show/wiki/Short-Topbar#%E4%B8%BA%E6%B6%88%E6%81%AF%E6%96%87%E6%9C%AC%E6%8C%87%E5%AE%9Aicon)
  * [long topbar](https://github.com/the-pig-of-jungle/smart-show/wiki/Long-Topbar)
  * [indefinite topbar](https://github.com/the-pig-of-jungle/smart-show/wiki/Indefinite-Topbar)
* [custom topbar](https://github.com/the-pig-of-jungle/smart-show/wiki/%E5%AE%9A%E5%88%B6%E5%8C%96Topbar)
  * [background](https://github.com/the-pig-of-jungle/smart-show/wiki/%E5%AE%9A%E5%88%B6%E5%8C%96Topbar#%E8%AE%BE%E7%BD%AE%E8%83%8C%E6%99%AF%E9%A2%9C%E8%89%B2)
  * [msg text color and size](https://github.com/the-pig-of-jungle/smart-show/wiki/%E5%AE%9A%E5%88%B6%E5%8C%96Topbar#%E8%AE%BE%E7%BD%AE%E6%B6%88%E6%81%AF%E6%96%87%E6%9C%AC%E9%A2%9C%E8%89%B2%E5%8F%8A%E5%A4%A7%E5%B0%8F)
  * [action text color and size](https://github.com/the-pig-of-jungle/smart-show/wiki/%E5%AE%9A%E5%88%B6%E5%8C%96Topbar#%E8%AE%BE%E7%BD%AE%E5%8A%A8%E4%BD%9C%E6%96%87%E6%9C%AC%E9%A2%9C%E8%89%B2%E5%8F%8A%E5%A4%A7%E5%B0%8F)
  * [进一步处理UI](https://github.com/the-pig-of-jungle/smart-show/wiki/%E5%AE%9A%E5%88%B6%E5%8C%96Topbar#%E8%BF%9B%E4%B8%80%E6%AD%A5%E5%A4%84%E7%90%86ui)
  * [离开页面时自动消失](https://github.com/the-pig-of-jungle/smart-show/wiki/%E5%AE%9A%E5%88%B6%E5%8C%96Topbar#%E8%AE%BE%E7%BD%AE%E7%A6%BB%E5%BC%80%E5%BD%93%E5%89%8Dactivity%E6%97%B6%E6%98%AF%E5%90%A6%E7%AB%8B%E5%8D%B3%E9%9A%90%E8%97%8F%E6%AD%A3%E5%9C%A8%E6%98%BE%E7%A4%BA%E7%9A%84topbar%E9%BB%98%E8%AE%A4false)
* [other API](https://github.com/the-pig-of-jungle/smart-show/wiki/Topbar-other-api)
  * [isShowing](https://github.com/the-pig-of-jungle/smart-show/wiki/Topbar-other-api#%E5%BD%93%E5%89%8D%E6%98%AF%E5%90%A6%E6%9C%89topbar%E5%9C%A8%E6%98%BE%E7%A4%BA)
  * [dismiss](https://github.com/the-pig-of-jungle/smart-show/wiki/Topbar-other-api#%E9%9A%90%E8%97%8F%E5%BD%93%E5%89%8D%E6%AD%A3%E5%9C%A8%E6%98%BE%E7%A4%BA%E7%9A%84topbar)
  * [callback when shown and dismissed](https://github.com/the-pig-of-jungle/smart-show/wiki/Topbar-other-api#%E7%9B%91%E5%90%AC%E6%98%BE%E7%A4%BA%E5%92%8C%E9%9A%90%E8%97%8F)

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
详细文档请参阅wiki:
* 预定义的SmartDialog实现
  * [NotificationDialog](https://github.com/the-pig-of-jungle/smart-show/wiki/NotificationDialog)
  * [EnsureDialog](https://github.com/the-pig-of-jungle/smart-show/wiki/EnsureDialog)
  * [InputTextDialog](https://github.com/the-pig-of-jungle/smart-show/wiki/InputTextDialog)
  * [ClickListDialog](https://github.com/the-pig-of-jungle/smart-show/wiki/ClickListDialog)
  * [ChooseListDialog](https://github.com/the-pig-of-jungle/smart-show/wiki/ChooseListDialog)
  * [LoadingDialog](https://github.com/the-pig-of-jungle/smart-show/wiki/LoadingDialog)

* 自定义
  * [Dialog 创建](https://github.com/the-pig-of-jungle/smart-show/wiki/%E8%87%AA%E5%AE%9A%E4%B9%89Dialog%E7%9A%84%E5%88%9B%E5%BB%BA)
  * [Dialog ContentView](https://github.com/the-pig-of-jungle/smart-show/wiki/%E8%87%AA%E5%AE%9A%E4%B9%89-Dialog-ContentView)
  * [Dialog Style](https://github.com/the-pig-of-jungle/smart-show/wiki/%E8%87%AA%E5%AE%9A%E4%B9%89-Dialog-Style)
  * [Dialog 宽度](https://github.com/the-pig-of-jungle/smart-show/wiki/%E8%87%AA%E5%AE%9A%E4%B9%89-Dialog-%E5%AE%BD%E5%BA%A6)
  * [Dialog 属性](https://github.com/the-pig-of-jungle/smart-show/wiki/%E8%87%AA%E5%AE%9A%E4%B9%89-Dialog-%E5%B1%9E%E6%80%A7)
## SmartSnackbar部分
[回到模块导航](#模块导航)<br/><br/>
![图片加载失败](images/snackbar_color.gif)
1. 复用Snackbar实例，当Snackbar正在显示，多次触发时，若msg和actionTex均未改变，则不会重复弹出，否则会有弹出效果
2. 可修改布局风格，如背景颜色，文字大小和颜色等
3. 可配置离开当前Activity时，立即消失正在显示的Snackbar。如，在Activity A 上显示了一个Indefinite Snackbar，并且用户没有点击响应，启动activity B，然后再返回A，原来的Snackbar已自动消失
### API
详细文档请参阅wiki:
* [normal snackbar](https://github.com/the-pig-of-jungle/smart-show/wiki/Short-Snackbar)
  * [short snackbar](https://github.com/the-pig-of-jungle/smart-show/wiki/Short-Snackbar)
    * [show message](https://github.com/the-pig-of-jungle/smart-show/wiki/Short-Snackbar#%E6%98%BE%E7%A4%BA%E4%B8%80%E6%9D%A1%E6%B6%88%E6%81%AF)
    * [show message and aciton](https://github.com/the-pig-of-jungle/smart-show/wiki/Short-Snackbar#%E6%8C%87%E5%AE%9A%E5%8A%A8%E4%BD%9C%E6%8C%89%E9%92%AE%E5%93%8D%E5%BA%94%E7%82%B9%E5%87%BB)
    * [message with icon](https://github.com/the-pig-of-jungle/smart-show/wiki/Short-Snackbar#%E4%B8%BA%E6%B6%88%E6%81%AF%E6%96%87%E6%9C%AC%E6%8C%87%E5%AE%9Aicon)
  * [long snackbar](https://github.com/the-pig-of-jungle/smart-show/wiki/Long-Snackbar)
  * [indefinite snackbar](https://github.com/the-pig-of-jungle/smart-show/wiki/Indefinite-Snackbar)
* [custom snackbar](https://github.com/the-pig-of-jungle/smart-show/wiki/%E5%AE%9A%E5%88%B6%E5%8C%96Snackbar)
  * [background](https://github.com/the-pig-of-jungle/smart-show/wiki/%E5%AE%9A%E5%88%B6%E5%8C%96Snackbar#%E8%AE%BE%E7%BD%AE%E8%83%8C%E6%99%AF%E9%A2%9C%E8%89%B2)
  * [msg text color and size](https://github.com/the-pig-of-jungle/smart-show/wiki/%E5%AE%9A%E5%88%B6%E5%8C%96Snackbar#%E8%AE%BE%E7%BD%AE%E6%B6%88%E6%81%AF%E6%96%87%E6%9C%AC%E9%A2%9C%E8%89%B2%E5%8F%8A%E5%A4%A7%E5%B0%8F)
  * [action text color and size](https://github.com/the-pig-of-jungle/smart-show/wiki/%E5%AE%9A%E5%88%B6%E5%8C%96Snackbar#%E8%AE%BE%E7%BD%AE%E5%8A%A8%E4%BD%9C%E6%96%87%E6%9C%AC%E9%A2%9C%E8%89%B2%E5%8F%8A%E5%A4%A7%E5%B0%8F)
  * [进一步处理UI](https://github.com/the-pig-of-jungle/smart-show/wiki/%E5%AE%9A%E5%88%B6%E5%8C%96Snackbar#%E8%BF%9B%E4%B8%80%E6%AD%A5%E5%A4%84%E7%90%86ui)
  * [离开页面时自动消失](https://github.com/the-pig-of-jungle/smart-show/wiki/%E5%AE%9A%E5%88%B6%E5%8C%96Snackbar#%E8%AE%BE%E7%BD%AE%E7%A6%BB%E5%BC%80%E5%BD%93%E5%89%8Dactivity%E6%97%B6%E6%98%AF%E5%90%A6%E7%AB%8B%E5%8D%B3%E9%9A%90%E8%97%8F%E6%AD%A3%E5%9C%A8%E6%98%BE%E7%A4%BA%E7%9A%84topbar%E9%BB%98%E8%AE%A4false)
* [other API](https://github.com/the-pig-of-jungle/smart-show/wiki/Snackbar-other-api)
  * [isShowing](https://github.com/the-pig-of-jungle/smart-show/wiki/Snackbar-other-api#%E5%BD%93%E5%89%8D%E6%98%AF%E5%90%A6%E6%9C%89snackbar%E5%9C%A8%E6%98%BE%E7%A4%BA)
  * [dismiss](https://github.com/the-pig-of-jungle/smart-show/wiki/Snackbar-other-api#%E9%9A%90%E8%97%8F%E5%BD%93%E5%89%8D%E6%AD%A3%E5%9C%A8%E6%98%BE%E7%A4%BA%E7%9A%84snackbar)
  * [callback when shown and dismissed](https://github.com/the-pig-of-jungle/smart-show/wiki/Snackbar-other-api#%E7%9B%91%E5%90%AC%E6%98%BE%E7%A4%BA%E5%92%8C%E9%9A%90%E8%97%8F)



