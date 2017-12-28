Activity 启动模式
> - Standard 普通模式。
> - SingleTop 如果启动的Activity 在当前栈顶还有一个实例，则直接调用当前栈顶Activity，使用NewIntent传值；否则new一个Activity再启动。
> - SingleTask 如果当前Activity Stack中没有待启动目标Activity的实例，则new一个Activity启动；否则销毁栈中目标Activity上其他Activity，然后调用目标Activity，使用NewIntent传值。
> - SingleInstance 新起一个Activity Stack放目标Activity；如果在当前Activity新起一个启动模式的Activity，则会把目标Activity压入App的Activity Stack中，在App的Activity Stack中点击返回按钮，先把App的Activity Stack中的Activity销毁，再进入当前Activity Stack。