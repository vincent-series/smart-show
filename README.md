# SmartShow
对Toast和Snackbar的封装，提高性能和用户体验！<br/>
## 添加依赖
1.在Project的gradle文件中<br/>
<pre><code>
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
</code></pre>
2.在Module的grable文件中<br/>

## SmartToast部分
### 特点：
1.全局始终使用一个Toast实例，节省内存<br/>
2.如果Toast正在显示，再次触发同一内容的Toast，不会重复弹出</br>
3.如果Toast正在显示，再次触发Toat，如果内容或位置发生了变化，会重新弹出，具有切换效果，并且上一个Toast会立即消失，不会等到其duration耗尽再弹出另一个<br/>
4.可对Toast原有布局的风格进行修改，如背景颜色，文字大小和颜色等</br>
5.可为Toast设置自定义布局，并进行代码处理</br>
6.内部实现上,除了所必须的Toast单例外，为了减少创建不必要的对象，PlainToastSetting、CustomToastSetting、Runnable三个接口全部由单例SmartToast实现，对外需要暴露何种功能，则返回何种接口类型
### 使用：
第一步，在Application的onCreate()方法中初始化</br>
方式 1：<br/>
<pre><code>
        //使用默认布局的普通Toast
        SmartToast.plainToast(this);
</code></pre>
如果你想对布局风格进行修改,可继续链式调用，不过这并不是必须的<br/>
<pre><code>
        //返回PlainToastSetting对象，对布局进行各种风格设置
        SmartToast.plainToast(this)
                //设置背景颜色，有可选方法，可直接以颜色值为参数
                .backgroundColorRes(R.color.colorPrimary)
                //设置文本颜色，有可选方法，可直接以颜色值为参数
                .textColorRes(R.color.colorAccent)
                //设置文本字体大小，单位为sp
                .textSizeSp(17)
                //设置是否加粗文本
                .textBold(true)
                //如果你想进一步对布局做处理，调用此方法
                .processPlainView(new ProcessViewCallback() {
                    @Override
                    //outParent为显示文本的TextView的父布局，msgView为显示文本的TextView
                    public void processPlainView(LinearLayout outParent, TextView msgView) {
                        //设置下划线
                        msgView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                    }
                });
</pre></code>
方式 2：<br/>
<pre><code>
        使用自定义布局的Toast
        SmartToast.customToast(this)
</code></pre>
如果你想对自定义的布局进行代码处理,可继续链式调用，不过这并不是必须的<br/>
<pre><code>
        返回CustomToastSetting对象
        SmartToast.customToast(this)
                //设置自定义布局，有重载方法，可直接以View为参数
                //在你的自定义布局中，一定要设置显示文本提示的TextView的Id为
                //android:id="@id/custom_toast_msg"
                .view(R.layout.custom_toast)
                //对自定义布局进行代码处理
                .processCustomView(new ProcessViewCallback() {
                    @Override
                    public void processCustomView(View view) {

                    }
                });
</pre></code>
第二步，调用show方法显示Toast<br/>
显示短暂Toast<br/>
<pre><code>
        //在默认位置显示
        SmartToast.show("我是朱志强！");
        //在屏幕顶部显示，距离顶部位置为Toast在Y方向默认的偏移距离
        SmartToast.showAtTop("我是朱志强!");
        //在屏幕中央显示
        SmartToast.showInCenter("我是朱志强！");
        //在指定位置显示，x,y方向偏移量单位为dp
        SmartToast.showAtLocation("我是朱志强",Gravity.LEFT | Gravity.TOP,10,10);
</pre></code>
显示较长时间的Toast<br/>
<pre><code>
        //在默认位置显示
        SmartToast.showLong("我是朱志强！");
        //在屏幕顶部显示，距离顶部位置为Toast在Y方向默认的偏移距离
        SmartToast.showLongAtTop("我是朱志强!");
        //在屏幕中央显示
        SmartToast.showLongInCenter("我是朱志强！");
        //在指定位置显示，x,y方向偏移量单位为dp
        SmartToast.showLongAtLocation("我是朱志强",Gravity.LEFT | Gravity.TOP,10,10);
</pre></code>

