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
特点：<br/>
1.全局始终使用一个Toast实例，节省内存<br/>
2.如果Toast正在显示，再次触发同一内容的Toast，不会重复弹出</br>
3.如果Toast正在显示，再次触发Toat，如果内容或位置发生了变化，会重新弹出，具有切换效果，并且上一个Toast会立即消失，不会等到其duration耗尽再弹出另一个<br/>
4.可对Toast原有布局的风格进行修改，如背景颜色，文字大小和颜色等
5.可为Toast设置自定义布局，并进行处理
使用：
第一步，初始化
<pre><code>
        //使用默认布局的普通Toast，返回PlainToastSetting对象，对布局进行各种风格设置
        SmartToast.plainToast(this)
                //设置背景颜色，有可选方法，可直接以颜色值为参数
                .backgroundColorRes(R.color.colorPrimary)
                //设置文本颜色，有可选方法，可直接以颜色值为参数
                .textColorRes(R.color.colorAccent)
                //蛇者文本字体大小，单位为sp
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
