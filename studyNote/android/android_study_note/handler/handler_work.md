```
handler消息发送循环流程
```
- Handler通过sendMessage()发送消息Message到消息队列MessageQueue。
- Looper通过loop()不断提取触发条件的Message，并将Message交给对应的target handler来处理。
- target handler调用自身的handleMessage()方法来处理Message。