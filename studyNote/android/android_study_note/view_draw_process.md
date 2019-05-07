1. PhoneWindow是Android系统之最基本的窗口系统，每个Activity都会创建一个PhoneWindow,PhoneWindow是Activity和View系统交互的接口；
2. 绘制从根视图的ViewRoot方法的performTraversals()方法开始,视图操作的过程分为三个步骤，分别为测量(measure)，布局(layout),绘制(draw);
3. MeasureSpec表示的是一个32位的整数值，它的高2两位表示测量模式SpecMode,低30位表示某种测量模式下的规格大小SpecSize
    - UNSPECIFIED：不指定测量模式，父视图没有限制子视图的大小，子视图可以是想要的任何尺寸，通常用于系统内部，应用开发中很少使用到。
    - EXACTLY：精确测量模式，当该视图的 layout_width 或者 layout_height 指定为具体数值或者 match_parent 时生效，表示父视图已经决定了子视图的精确大小，这种模式下 View 的测量值就是 SpecSize 的值。
    - AT_MOST：最大值模式，当前视图的 layout_width 或者 layout_height 指定为 wrap_content 时生效，此时子视图的尺寸可以是不超过父视图运行的最大尺寸的任何尺寸。