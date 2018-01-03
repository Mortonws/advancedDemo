
## 设计模式之策略模式
[参考策略模式的解析](https://www.cnblogs.com/java-my-life/archive/2012/05/10/2491891.html)
- 策略模式是对算法的包装，是把使用算法的责任和算法本身分割开来，委派给不同的对象管理。
- 策略模式通常把一个系列的算法包装到一系列的策略类里面，作为一个抽象策略类的子类。
- 用一句话来说，就是：“准备一组算法，并将每一个算法封装起来，使得它们可以互换”。

#### 代码示例

**抽象策略类**
```
public interface Strategy {
    /**
     * 策略方法
     */
    public void strategyInterface();
}
```
**具体策略类**

```
public class ConcreteStrategyA implements Strategy {

    @Override
    public void strategyInterface() {
        //相关的业务
    }

}
```

```
public class ConcreteStrategyB implements Strategy {

    @Override
    public void strategyInterface() {
        //相关的业务
    }

}
```

```
public class ConcreteStrategyC implements Strategy {

    @Override
    public void strategyInterface() {
        //相关的业务
    }

}
```


**环境角色类**
```
public class Context {
    //持有一个具体策略的对象
    private Strategy strategy;
    /**
     * 构造函数，传入一个具体策略对象
     * @param strategy    具体策略对象
     */
    public Context(Strategy strategy){
        this.strategy = strategy;
    }
    /**
     * 策略方法
     */
    public void contextInterface(){
        strategy.strategyInterface();
    }
}
```
### 策略模式的优点
1. 策略模式提供了管理相关的算法族的办法。策略类的等级结构定义了一个算法或行为族。恰当使用继承可以把公共的代码移到父类里面，从而避免代码重复。
2. 使用策略模式可以避免使用多重条件(if-else)语句。多重条件语句不易维护，它把采取哪一种算法或采取哪一种行为的逻辑与算法或行为的逻辑混合在一起，统统列在一个多重条件语句里面，比使用继承的办法还要原始和落后。

### 策略模式的缺点
1. 客户端必须知道所有的策略类，并自行决定使用哪一个策略类。这就意味着客户端必须理解这些算法的区别，以便适时选择恰当的算法类。换言之，策略模式只适用于客户端知道算法或行为的情况。
2. 由于策略模式把每个具体的策略实现都单独封装成为类，如果备选的策略很多的话，那么对象的数目就会很可观。