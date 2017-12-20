```
主要是简述 AsyncTask 的缺点
```

- [x] 串行-并行-串行，现在默认使用的是串行方法，并行需要制定特定的方法；
- [x] 如果并行会造成并发线程不安全的情况；
- [x] 如果task的doInBackground()未执行完成，会持有当前activity的引用，当执行完成才会释放，或者调用cancel方法，但是会回调onCancel方法，如果doInBackground方法中有不能中断的操作，比如decodeBitmap，则调用cancel方法也不能取消task；需要正确的取消task；
- [x] 屏幕旋转之后，activity销毁之后重绘，此时task持有的是之前旧activity的引用，如果在onPostExecute操作中对UI进行操作，则不会有效果；