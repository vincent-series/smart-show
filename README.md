## SmartShow
★★★ 4.x版起，库的发布迁移至`mavenCentral`，不再发布到`jitpack.io`。 请使用新的依赖方式拉取依赖。<br/>
★★★ `android studio`会默认为新建项目添加`mavenCentral`仓库，老项目请自行添加`mavenCentral`仓库。<br/>
★★★ 4.x版及后续版本全部使用`kotlin`实现。<br/>
★★★ SmartTopBar并入SmartSnackBar，SmartSnackBar现在既支持顶部弹出也支持底部弹出。<br/>
★★★ 4.x版及后续版本不再需要显式初始化，旧版本迁移到4.x版直接将初始化相关代码删除即可。
### 模块导航：

* [SmartToast（latest version：4.0.3）](#SmartToast部分)
* [SmartSnackBar（latest version：4.0.5）](#SmartSnackBar部分)
* [SmartDialog（latest version：4.0.3）](#SmartDialog部分)
* [关于实现](#代码实现)
* [关于作者及技术交流](#关于作者)

## 代码实现

[回到模块导航](#模块导航)<br/><br/>
如果你对实现感兴趣，请参考我的CSDN博客：

* 2.x 版本 https://blog.csdn.net/jungle_pig/article/details/83959662
* 1.x 版本 https://blog.csdn.net/jungle_pig/article/details/78568493<br/>

## 关于作者

[回到模块导航](#模块导航)<br/><br/>
如果您在使用过程中发现任何问题，请联系我，我会立即跟进修复和维护。感谢您的支持！<br/><br/>
作者：朱志强<br/><br/>
微信：w361281607<br/><br/>
<img src="https://images.gitee.com/uploads/images/2022/0326/223808_1e7ed200_10660313.jpeg" width="180" height="180"/><br/>
邮箱：coder_zzq@aliyun.com<br/><br/>

## SmartToast部分

[回到模块导航](#模块导航)

1. 三种Toast实现策略:系统window + Toast + dialog,根据情况动态择优选择
2. 合理的复用策略，避免Toast重复弹跳
3. 解决Android 7.1的系统bug——Toast BadTokenException
4. 可修改Toast默认布局的风格，如背景颜色，文字大小和颜色等
5. 完美解决应用关闭通知权限后Toast不显示问题
6. 适配android 11对toast的相关限制
7. 配置Toast风格，如背景颜色或文字大小
8. 通过注解快速自定义新的Toast
8. 结合主流app消息提示的效果，提供info、success、error、warning、complete、forbid、wait、fail 8 种类型的Emotion Toast

### 引入依赖

<pre><code>
implementation "io.github.vincent-series:smart-toast:4.0.3"

//如需要注解处理功能,引入注解处理库
kapt "io.github.vincent-series:annotation-compiler:1.0.3"
</code></pre>

### API

[查看API文档](https://github.com/vincent-series/smart-show/tree/master/smart-toast)

### 历史版本
[查看历史版本](https://github.com/vincent-series/smart-show/wiki/SmartToast%E5%8E%86%E5%8F%B2%E7%89%88%E6%9C%AC)

### Classic Toast
<img src="https://images.gitee.com/uploads/images/2022/0326/202943_7d421501_10660313.jpeg" width="30%"/>    <img src="https://images.gitee.com/uploads/images/2022/0326/203654_925de088_10660313.jpeg" width="30%">    <img src="https://images.gitee.com/uploads/images/2022/0326/204003_19eca69c_10660313.jpeg" width="30%"/>

### Emotion Toast
<img src="https://images.gitee.com/uploads/images/2022/0326/204345_9baf83b2_10660313.jpeg" width="30%"/>    <img src="https://images.gitee.com/uploads/images/2022/0326/205052_3b6119bf_10660313.jpeg" width="30%">    <img src="https://images.gitee.com/uploads/images/2022/0326/205201_7cd7163e_10660313.jpeg" width="30%">
<hr/>

<img src="https://images.gitee.com/uploads/images/2022/0326/205226_7fbd0f17_10660313.jpeg" width="30%"/>    <img src="https://images.gitee.com/uploads/images/2022/0326/205303_45649a11_10660313.jpeg" width="30%">    <img src="https://images.gitee.com/uploads/images/2022/0326/214221_46bf3210_10660313.jpeg" width="30%">

## SmartSnackBar部分

[回到模块导航](#模块导航)<br/><br/>
SmartSnackBar基于原生Snackbar进行封装改造，不仅使用上更加简易，而且进行了功能扩展:

1. 支持顶部弹出
2. 支持指定背景颜色
3. 支持修改message、actionLabel的文本风格，如字体大小、粗细、颜色
4. 支持显示icon及指定icon位置

### 引入依赖

<pre><code>
implementation "io.github.vincent-series:smart-snackbar:4.0.5"

//material lib,such as 1.5.0
implementation 'com.google.android.material:material:x.y.z'
</code></pre>

### API

[查看API文档](https://github.com/vincent-series/smart-show/tree/master/smart-snackbar)

### 历史版本

[查看历史版本](https://github.com/vincent-series/smart-show/wiki/SmartSnackBar%E5%8E%86%E5%8F%B2%E7%89%88%E6%9C%AC)

#### bottom snackbar

<img src="https://images.gitee.com/uploads/images/2022/0326/214954_49b611b9_10660313.jpeg" width="30%"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://images.gitee.com/uploads/images/2022/0326/215134_489eb2d9_10660313.jpeg" width="30%"/>
<hr/> 

#### top snackbar
<img src="https://images.gitee.com/uploads/images/2022/0326/215408_897ffe72_10660313.jpeg" width="30%"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://images.gitee.com/uploads/images/2022/0326/215707_6e8ef9fe_10660313.jpeg" width="30%"/>

## SmartDialog部分

[回到模块导航](#模块导航)<br/><br/>

1. 解决因activity、fragment生命周期导致的BadTokenException、NullPointException等问题
2. 通过注解快速自定义Dialog
3. 提供主流APP中使用的message、input、list、loading等对话框<br/>

### 引入依赖

<pre><code>
implementation "io.github.vincent-series:smart-dialog:4.0.3"

//如需要注解处理功能,引入注解处理库
kapt "io.github.vincent-series:annotation-compiler:1.0.3"
</code></pre>

### API

[查看API文档](https://github.com/vincent-series/smart-show/tree/master/smart-dialog)

### 历史版本
[查看历史版本](https://github.com/vincent-series/smart-show/wiki/SmartDialog%E5%8E%86%E5%8F%B2%E7%89%88%E6%9C%AC)

#### 效果图

<img src="https://images.gitee.com/uploads/images/2022/0326/221516_8764c1c9_10660313.jpeg" width="30%"/>    <img src="https://images.gitee.com/uploads/images/2022/0326/221601_14f6ae57_10660313.jpeg" width="30%">    <img src="https://images.gitee.com/uploads/images/2022/0326/221636_67d59b13_10660313.jpeg" width="30%">
<hr/>

<img src="https://images.gitee.com/uploads/images/2022/0326/221835_c61500f8_10660313.jpeg" width="30%"/>    <img src="https://images.gitee.com/uploads/images/2022/0326/221939_6f5d683f_10660313.jpeg" width="30%"/>    <img src="https://images.gitee.com/uploads/images/2022/0326/222033_7c956d43_10660313.jpeg" width="30%">
<hr/>

<img src="https://images.gitee.com/uploads/images/2022/0326/222108_c1998f70_10660313.jpeg" width="30%">    <img src="https://images.gitee.com/uploads/images/2022/0326/222138_2fa6d358_10660313.jpeg" width="30%"/>     <img src="https://images.gitee.com/uploads/images/2022/0326/222313_6802d83e_10660313.jpeg" width="30%">
<hr/>

<img src="https://images.gitee.com/uploads/images/2022/0326/222230_af42b43a_10660313.jpeg" width="30%">    <img src="https://images.gitee.com/uploads/images/2022/0326/222349_6bdaea3c_10660313.jpeg" width="30%"/>



