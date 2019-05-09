- [view事件分发机制](https://blog.csdn.net/carson_ho/article/details/54136311)
- [view事件分发机制](https://blog.csdn.net/a553181867/article/details/51287844)
---
> 用一段伪代码来阐述3个方法(dispatchTouchEvent,onInterceptTouchEvent,onTouchEvent)的关系 & 事件传递规则

```
 /**
  * 点击事件产生后
  */
  // 步骤1：调用dispatchTouchEvent（）
  public boolean dispatchTouchEvent(MotionEvent ev) {

    boolean consume = false; //代表 是否会消费事件

    // 步骤2：判断是否拦截事件
    if (onInterceptTouchEvent(ev)) {
      // a. 若拦截，则将该事件交给当前View进行处理
      // 即调用onTouchEvent (）方法去处理点击事件
        consume = onTouchEvent (ev) ;

    } else {

      // b. 若不拦截，则将该事件传递到下层
      // 即 下层元素的dispatchTouchEvent（）就会被调用，重复上述过程
      // 直到点击事件被最终处理为止
      consume = child.dispatchTouchEvent (ev) ;
    }

    // 步骤3：最终返回通知 该事件是否被消费（接收 & 处理）
    return consume;

   }
```