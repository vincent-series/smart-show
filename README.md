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

## Toast部分<br/>
1.全局始终使用一个Toast实例，节省内存<br/>
2.如果Toast正在显示，再次触发同一内容的Toast，不会重复弹出</br>
3.如果Toast正在显示，再次触发Toat，如果内容或位置发生了变化，会重新弹出，具有切换效果，并且上一个Toast会立即消失，不会等到其duration耗尽再弹出另一个<br/>
4.可对Toast原有布局的风格进行修改，如背景颜色，文字大小和颜色等
5.可为Toast设置自定义布局，并进行处理
### 在项目中使用
