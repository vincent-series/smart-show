# SmartShow
Smart Toast & Snackbar : 简化调用，并处理常见问题，提高性能和用户体验！作者：朱志强<br/>
如果你对实现感兴趣，请参考我的CSDN博客：http://blog.csdn.net/jungle_pig/article/details/78568493 <br/>
该博文已授权《第一行代码》作者郭霖的微信公众号同步发表：http://mp.weixin.qq.com/s/l62PtbmrIOkVKfJ2r0JwEw <br/>
为了让SmartShow库更加健壮，如果您在使用过程中发现任何问题，请联系我，我会立即跟进修复和维护。<br/>
感谢您的支持！<br/>
微信：w361281607
![图片加载失败](images/wx_2d.jpg)<br/><br/>
邮箱：coder_zzq@163.com<br/>

### SmartToast部分
#### 特点：
1.尽可能地复用Toast实例，节约内存<br/>
2.如果Toast正在显示，多次触发同一内容的Toast，不会重复弹出</br>
3.新的Toast(内容或位置发生了变化)会立即弹出，不会等待旧的Toast的duration耗尽再弹出，并具有动画效果（与你手机系统原生Toast的切换动画一致）<br/>
4.可修改Toast默认布局的风格，如背景颜色，文字大小和颜色等</br>
5.可为Toast设置自定义布局，并进行代码处理</br>
6.完美解决Android 7.1的系统bug——Toast BadTokenException
#### 注意
关闭app的系统通知权限,将导致SmartToast无法显示，原因如下：<br/>
Toast的内部原理使用NotificationManagerService，关闭通知权限后，无法显示。<br/>
这是原生Toast本身的特性，而不是SmartShow的bug。<br/>
以淘宝app和优酷app的"再按一次退出程序"的Toast提示为例，关闭他们的通知权限，<br/>
也会导致Toast不显示，感兴趣的话可以去试一试。

### SmartSnackbar部分
#### 特点：
1.Snackbar的显示原理与Toast不同，Toast通过Window展示视图，全局可复用一个实例。Snackbar则是把视图内嵌到当前Activity的android.R.id.content容器或某个CoordinatorLayout中。在获取方式不变（容器不变）的情况下，同一页面可复用一个Snackbar实例，节省内存<br/>
2.同一页面，如果Snackbar正在显示，多次触发同一内容的Snackbar，不会重复弹出</br>
3.同一页面，如果Snackbar正在显示，再次触发Snackbar，如果内容（msg或actionText）发生了变化（不会重建Snackbar实例）或内嵌的容器发生了变化（会重建Snackbar实例），会重新弹出，具有切换效果（与你手机系统原生Snackbar的切换动画一致）。<br/>
4.可修改布局风格，如背景颜色，文字大小和颜色等</br>

### 添加依赖
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
<pre><code>

    implementation 'com.github.the-pig-of-jungle:SmartShow:v2.0.1'

</code></pre>

### 使用SmartShow
第1步，必须初始化
<pre><code>
    //必须初始化

    SmartShow.init(this);
</code></pre>
#### 使用Toast
可获取ToastSetting对SmartToast进行配置,这一步不是必须的<br/>
<pre><code>
   //返回ToastSetting对象

   SmartShow.toastSetting()

      //自定义布局，参数可以是布局资源，也可以View。

      // 在自定义布局中，一定要设置显示文本提示的

      //TextView的Id为android:id="@id/custom_toast_msg"。

       .view(R.layout.custom_toast)

       //设置背景颜色

       .backgroundColorRes(R.color.colorPrimary)

       //文本颜色

       .textColorRes(R.color.colorAccent)

       //设置文本字体大小

       .textSizeSp(18)

       //设置文本是否加粗

       .textBold(true)

       //设置离开当前页面时，该页面的Toast是否立即消失，默认false

       .dismissOnLeave(true)

       //对布局进一步处理

       .processView(new ProcessToastCallback() {

            @Override

            //isCustom 是否是自定义布局；rootView 布局根view

            //outParent 默认布局时，msgView的父布局，也是根布局

            //msgView 显示文本的TxtView

            public void processView(boolean isCustom, View rootView, LinearLayout outParent, TextView msgView) {

                  //...

            }

      });
</code></pre>

调用show方法显示Toast，duration和常用的显示位置体现在方法名上，而不是传参，使得调用非常简易<br/><br/>
Short Toast</br>
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
Long Toast<br/>
<pre><code>
        //在默认位置显示

        SmartToast.showLong("我是朱志强！");

        //在屏幕顶部显示，距离顶部位置为Toast在Y方向默认的偏移距离

        SmartToast.showLongAtTop("我是朱志强!");

        //在屏幕中央显示

        SmartToast.showLongInCenter("我是朱志强！");

        //在指定位置显示，x,y方向偏移量单位为dp

        SmartToast.showLongAtLocation("我是朱志强",Gravity.LEFT | Gravity.TOP,10,10);
</code></pre>
其他方法
<pre><code>
         //Toast是否显示

         SmartToast.isShowing();

         //隐藏Toast

         SmartToast.dismiss();

         //设置离开当前页面时，当前页面的Toast是否立即消失，默认false

         SmartToast.setDismissOnLeave(boolean b);

</code></pre>

#### 效果图
①多次触发同一内容的Toast，不会重复弹出</br>
②新的Toast(内容或位置发生了变化)会立即弹出，不会等待旧的Toast的duration耗尽再弹出，并具有动画效果（与你手机系统原生Toast的切换动画一致）<br/></br>
![图片加载失败](images/g_1.gif)<br/><br/>
④修改布局的风格，背景颜色、字体大小、颜色、加粗等</br><br/>
![图片加载失败](images/g_2.gif)<br/><br/>
⑤设置自定义布局<br/><br/>
![图片加载失败](images/g_3.gif)<br/>

#### 使用Snackbar
可获取SnackbarSetting，修改Snackbar布局的默认风格，这一步不是必须的<br/>
<pre><code>
   //返回SnackbarSetting对象

   SmartShow.snackbarSetting()

       //设置背景颜色

       .backgroundColorRes(R.color.colorPrimary)

       //设置消息文本颜色

       .msgTextColor(Color.WHITE)

       //设置消息文本大小

       .msgTextSizeSp(18)

       //设置动作文本颜色

       .actionColorRes(R.color.colorAccent)

       //设置动作文本大小

       .actionSizeSp(18)

       //设置进入新的页面时，该页面的Snackbar是否消失（主要是Indefinite Snackbar而言）

       .dismissOnLeave(true)

       //对布局进一步处理

       .processView(new ProcessSnackbarCallback() {
           @Override

           //layout 父布局；msgView 消息文本View;actionView 动作文本View

           public void processSnackbarView(Snackbar.SnackbarLayout layout, TextView msgView, TextView actionView) {

               //...

       }

   });

</code></pre>

获取当前页面的Snackbar，调用show方法显示，三种duration体现在方法名上，而不是传参，尽可能简化调用<br/><br/>
Short Snackbar<br/>
<pre><code>
        //获取当前页面的Snackbar，显示消息

        SmartSnackbar.get().show("我是朱志强");
</code></pre>
Long Snackbar<br/>
<pre><code>
        //获取当前页面的Snackbar，显示消息

        SmartSnackbar.get().showLong("我是朱志强");
</code></pre>
Indefinite Snackbar<br/>
<pre><code>
        获取当前页面的Snackbar，显示消息和动作文本，传入点击动作文本的回调代码
        SmartSnackbar.get().showIndefinite("我是朱志强", "打赏", new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.d("SmartShow","Thank you !");

            }
        });

        //获取当前页面的Snackbar，显示消息和动作文本，不传第三个参数，默认行为为Snackbar消失

        SmartSnackbar.get().showIndefinite("我是朱志强","打赏");
</code></pre>

显示Short和Long类型的Snackbar时，通常不会显示动作文本，而Indefinite Snackbar通常不会只显示消息文本，但实际上该库为三种Snackbar均提供了以上参数个数为1，2和3的方法。

其他方法：
<pre><code>

         //隐藏SmanrtSnackbar

         SmartSnackbar.dismiss();

         //设置进入新的页面时，当前页面的Snackbar是否消失，默认false

         SmartSnackbar.setDismissOnLeave(boolean b);

</code></pre>
一般情况下，我们不会监听Snackbar的显示和消失,但如有此需要，将当前页面的Activity实现SnackbarCallback接口，然后重写方法即可。在SmartSnackbar显示时，会检测当前页面是否实现该接口，是则进行回调。
<pre><code>
public class SnackbarActivity extends BaseActivity implements SnackbarCallback {


    @Override
    protected int contentLayout() {
        return R.layout.activity_smart_show;
    }

    @Override
    public void onSnackbarShown(Snackbar sb) {
        Log.d("Main", "shown");
    }

    @Override
    public void onSnackbarDismissed(Snackbar sb, int event) {
        Log.d("Main", "dismiss");
    }

}
</code></pre>
### SmartSnackbar获取方式的说明：
以上示例获取SmartSnackbar使用的是public static SnackbarShow get()，<br/>
还可使用public static SnackbarShow get(CoordinatorLayout view)。<br/>
根据谷歌源码，我们知道创建Snackbar时需传入一个当前页面的某个View。<br/>
实际上，Snackar会以该View为基点，沿着整个View Tree上溯，直到找到CoordinatorLayout容器或android.R.id.content 容器，哪个先找到，就将视图嵌入其中。<br/>
为了提高效率，直接将android.R.id.content或者CoordinatorLayout传入会更好。<br/>
以CoordinatorLayout为内嵌容器时，Snackbar会有一些特殊的行为，如可以用手指手动滑动移除，显示时会导致FloatingActionButton升高等。<br/>
所以建议，在使用SmartSnackbar时，如果你的页面想以某个具体CoordinatorLayout作为容器，则调用public static SnackbarShow get(CoordinatorLayout view)。<br/>
否则调用public static SnackbarShow get()，内部会自动将当前Activity的 android.R.id.content作为容器。<br/>
### 效果图
①多次触发同一内容的Snackbar，不会重复弹出</br>
②同一页面，如果Snackbar正在显示，再次触发Snackbar，如果内容（msg或actionText）发生了变化（不会重建Snackbar实例）或内嵌的容器发生了变化（会重建Snackbar实例），会重新弹出，具有切换效果。<br/><br/>
![图片加载失败](images/g_4.gif)<br/><br/>
③可修改布局风格，如背景颜色，文字大小和颜色等</br></br>
![图片加载失败](images/g_5.gif)