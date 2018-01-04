~~~
每次构建时 ProGuard 都会输出下列文件：
~~~
- dump.txt
    - 说明 APK 中所有类文件的内部结构。
- mapping.txt
    - 提供原始与混淆过的类、方法和字段名称之间的转换。
- seeds.txt
    - 列出未进行混淆的类和成员。
- usage.txt
    - 列出从 APK 移除的代码。

这些文件保存在 <module-name>/build/outputs/mapping/release/ 中。

