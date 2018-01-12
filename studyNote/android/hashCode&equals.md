```
Java中equals()和hashCode()有一个契约：
```
- 如果两个对象相等的话，它们的HashCode必须相等；
- 但如果两个对象的HashCode相等的话，这两个对象不一定相等。


- equals 相等的两个对象，hashcode一定相等
- hashcode相等，equals不一定相等