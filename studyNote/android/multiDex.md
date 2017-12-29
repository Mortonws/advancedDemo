> #### 总结出来MultiDex的原理如下：

1. apk在Applicaion实例化之后，会检查系统版本是否支持MultiDex，判断二级dex是否需要安装；
2. 如果需要安装则会从apk中解压出classes2.dex并将其拷贝到应用的/data/data/<package-name>/code_cache/secondary-dexes/目录下；
3. 通过反射将classes2.dex等注入到当前的ClassLoader的pathList中，完成整体安装流程。