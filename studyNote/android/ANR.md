~~~
造成ANR的场景
~~~

- Service Timeout
    - 对于前台服务，则超时为SERVICE_TIMEOUT = 20s；
    - 对于后台服务，则超时为SERVICE_BACKGROUND_TIMEOUT = 200s;
- BroadcastReceiver Timeout：
    - 对于前台广播，则超时为BROADCAST_FG_TIMEOUT = 10s；
    - 对于后台广播，则超时为BROADCAST_BG_TIMEOUT = 60s
- ContentProvider Timeout：内容提供者,在publish过超时10s;
- InputDispatching Timeout: 输入事件分发超时5s，包括按键和触摸事件。