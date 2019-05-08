1. top、left、right、bottom代表View的初始坐标，在绘制完毕后就不会再改变
2. translationX、translationY代表View左上角的偏移量（相对于父容器），比如上图是View进行了平移，那么translation代表水平平移的像素值，translationY代表竖直平移的像素值。
3. x、y代表当前View左上角相对于父容器的坐标，即实时相对坐标。
4. 以上参数的关系可以用这两个公式表示：x = left + translationX 和y = top + translationY。