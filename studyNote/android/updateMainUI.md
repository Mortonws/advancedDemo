~~~
子线程更新UI
~~~
- UI线程定义Handler，在子线程用handler发送Message出来处理
- 使用Activity的RunOnUi方法，但是不在Activity里面，需要传递Activity对象过去
- 使用View的post(Runnable)方法
- AsyncTask的使用，耗时操作在doInBackground中实现，UI更新在别的方法内使用