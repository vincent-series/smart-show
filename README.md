##### 试试作者其他的开源库? 
* [sharp-retrofit](https://github.com/vincent-series/sharp-retrofit)
### 模块导航：

* [SmartToast（latest version：4.1.5）](https://github.com/vincent-series/smart-show#smarttoast%E9%83%A8%E5%88%86)
* [SmartSnackBar（latest version：4.1.1）](https://github.com/vincent-series/smart-show#smartsnackbar%E9%83%A8%E5%88%86)
* [SmartDialog（latest version：4.1.4）](https://github.com/vincent-series/smart-show#smartdialog%E9%83%A8%E5%88%86)
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
微信：<br/>
<img src="https://images.gitee.com/uploads/images/2022/0326/223808_1e7ed200_10660313.jpeg" width="180" height="180"/><br/>
邮箱：vincent.k.zhu@gmail.com<br/><br/>

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
implementation "io.github.vincent-series:smart-toast:4.1.5"
</code></pre>

### API
[Kotlin User Document](https://github.com/vincent-series/smart-show/wiki/smart-toast-API-for-Kotlin)<br/>
[Java User Document](https://github.com/vincent-series/smart-show/wiki/smart-toast-API-for-Java)
### 历史版本
[查看历史版本](https://github.com/vincent-series/smart-show/wiki/SmartToast%E5%8E%86%E5%8F%B2%E7%89%88%E6%9C%AC)
### 效果图
|classic toast|classic toast|classic toast|
|:------:|:-------:|:--------:|
|默认背景|配置背景和Icon<br/>Icon居于文本左侧|配置背景和Icon<br/>Icon居于文本右侧|
| <img src="images/classic_toast_normal.jpg"> | <img src="images/classic_toast_success.jpg">|<img src="images/classic_toast_error.jpg">|
<hr/>

|emotion toast|emotion toast|emotion toast|
|:------:|:-------:|:--------:|
|信息|成功|错误|
| <img src="images/emotion_toast_info.jpg"> | <img src="images/emotion_toast_success.jpg">|<img src="images/emotion_toast_error.jpg">|
<hr/>

|emotion toast|emotion toast|emotion toast|
|:------:|:-------:|:--------:|
|警告|等待|禁止|
| <img src="images/emotion_toast_warning.jpg"> | <img src="images/emotion_toast_wait.jpg">|<img src="images/emotion_toast_forbid.jpg">|
<hr/>

|emotion toast|emotion toast|emotion toast|
|:------:|:-------:|:--------:|
|失败|完成|任意一种都可配置背景，如|
| <img src="images/emotion_toast_fail.jpg"> | <img src="images/emotion_toast_complete.jpg">|<img src="images/emotion_toast_custom_bg.jpg">|
## SmartSnackBar部分

[回到模块导航](#模块导航)<br/><br/>
SmartSnackBar基于原生Snackbar进行封装改造，不仅使用上更加简易，而且进行了功能扩展:

1. 支持顶部弹出
2. 支持指定背景颜色
3. 支持修改message、actionLabel的文本风格，如字体大小、粗细、颜色
4. 支持显示icon及指定icon位置

### 引入依赖

<pre><code>
implementation "io.github.vincent-series:smart-snackbar:4.1.1"

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
implementation "io.github.vincent-series:smart-dialog:4.1.4"
</code></pre>

### API文档
[Kotlin User Document](https://github.com/vincent-series/smart-show/wiki/smart-dialog-API-doc-for-Kotlin)<br/>
[Java User Document](https://github.com/vincent-series/smart-show/wiki/smart-dialog-API-doc-for-Java)

### 历史版本
[查看历史版本](https://github.com/vincent-series/smart-show/wiki/SmartDialog%E5%8E%86%E5%8F%B2%E7%89%88%E6%9C%AC)

### 效果图
| 通知对话框| 确认对话框 | 延时确认对话框|
|:------:|:-------:|:--------:|
|显示一条通知消息，只有确认按钮|确认用户操作|延时启用确认按钮<br/>一般用于提示用户谨慎操作|
| <img src="images/notification_dialog.jpg"> | <img src="images/acknowledge_dialog.jpg">|<img src="images/acknowledge_delay_dialog.jpg">|

<hr/>

| 文本输入对话框 | 数字输入对话框 |
|:------:|:------:|
| <img src="images/input_text_dialog.jpg">|<img src="images/input_number_dialog.jpg">    |

<hr/>

| 点击即选列表对话框   | 单选列表对话框 | 多选列表对话框 |
|:-------------:|:---------:|:---------:|
| 通过点击列表项完成选择 | 常规单选列表  | 常规多选列表  |
| <img src="images/clicked_list_dialog.jpg">|<img src="images/single_choice_dialog.jpg">|<img src="images/multiple_choice_dialog.jpg">|

<hr/>

|     加载对话框     |     加载对话框      |     加载对话框     |
|:-------------:|:--------------:|:-------------:|
| `BoxSize.LARGE` | `BoxSize.MIDDLE` | `BoxSize.SMALL` |
|       <img src="images/large_box_loading_dialog.jpg">|<img src="images/middle_box_loading_dialog.jpg">|<img src="images/small_box_loading_dialog.jpg">|
