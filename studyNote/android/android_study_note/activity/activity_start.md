> - Instrumentation: 监控应用与系统相关的交互行为。
> - AMS：组件管理调度中心，什么都不干，但是什么都管。
> - ActivityStarter：Activity启动的控制器，处理Intent与Flag对Activity启动的影响，具体说来有：1 寻找符合启动条件的Activity，如果有多个，让用户选择；2 校验启动参数的合法性；3 返回int参数，代表Activity是否启动成功。
> - ActivityStackSupervisior：这个类的作用你从它的名字就可以看出来，它用来管理任务栈。
> - ActivityStack：用来管理任务栈里的Activity。
> - ActivityThread：最终干活的人，是ActivityThread的内部类，Activity、Service、BroadcastReceiver的启动、切换、调度等各种操作都在这个类里完成。
---
>整个流程主要涉及四个进程：
>- 调用者进程，如果是在桌面启动应用就是Launcher应用进程。
>- ActivityManagerService等所在的System Server进程，该进程主要运行着系统服务组件。
>- Zygote进程，该进程主要用来fork新进程。
>- 新启动的应用进程，该进程就是用来承载应用运行的进程了，它也是应用的主线程（新创建的进程就是主线程），处理组件生命周期、界面绘制等相关事情。
---
> Activity启动过程简介
> - 点击桌面应用图标，Launcher进程将启动Activity（MainActivity）的请求以Binder的方式发送给了AMS。
>
> - AMS接收到启动请求后，交付ActivityStarter处理Intent和Flag等信息，然后再交给ActivityStackSupervisior/ActivityStack处理Activity进栈相关流程。同时以Socket方式请求Zygote进程fork新进程。
>
> - Zygote接收到新进程创建请求后fork出新进程。
>
> - 在新进程里创建ActivityThread对象，新创建的进程就是应用的主线程，在主线程里开启Looper消息循环，开始处理创建Activity。
>
> - ActivityThread利用ClassLoader去加载Activity、创建Activity实例，并回调Activity的onCreate()方法。这样便完成了Activity的启动。