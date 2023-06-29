# Collection

Java标准库自带的`java.util`包提供了集合类：`Collection`，它是除`Map`外所有其他集合类的根接口。Java的`java.util`包主要提供了以下三种类型的集合：

- `List`：一种有序列表的集合，例如，按索引排列的`Student`的`List`；
- `Set`：一种保证没有重复元素的集合，例如，所有无重复名称的`Student`的`Set`；
- `Map`：一种通过键值（key-value）查找的映射表集合，例如，根据`Student`的`name`查找对应`Student`的`Map`。

Java集合的设计有几个特点：一是**实现了接口和实现类相分离**，例如，有序表的接口是`List`，具体的实现类有`ArrayList`，`LinkedList`等，二是支持泛型，我们可以限制在一个集合中只能放入同一种数据类型的元素，例如：

```java
List<String> list = new ArrayList<>(); // 只能放入String类型
```

最后，Java访问集合总是通过统一的方式——迭代器（Iterator）来实现，它最明显的好处在于无需知道集合内部元素是按什么方式存储的。

由于Java的集合设计非常久远，中间经历过大规模改进，我们要注意到有一小部分集合类是遗留类，不应该继续使用：

- `Hashtable`：一种线程安全的`Map`实现；
- `Vector`：一种线程安全的`List`实现；
- `Stack`：基于`Vector`实现的`LIFO`的栈。

还有一小部分接口是遗留接口，也不应该继续使用：

- `Enumeration<E>`：已被`Iterator<E>`取代。

### 小结

Java的集合类定义在`java.util`包中，支持泛型，主要提供了3种集合类，包括`List`，`Set`和`Map`。Java集合使用统一的`Iterator`遍历，尽量不要使用遗留接口。

# 1. List

在集合类中，`List`是最基础的一种集合：它是一种有序列表。

`List`的行为和数组几乎完全相同：`List`内部按照放入元素的先后顺序存放，每个元素都可以通过索引确定自己的位置，`List`的索引和数组一样，从`0`开始。

我们考察`List<E>`接口，可以看到几个主要的接口方法：

- 在末尾添加一个元素：`boolean add(E e)`
- 在指定索引添加一个元素：`boolean add(int index, E e)`
- 删除指定索引的元素：`E remove(int index)`
- 删除某个元素：`boolean remove(Object e)`
- 获取指定索引的元素：`E get(int index)`
- 获取链表大小（包含元素的个数）：`int size()`

但是，实现`List`接口并非只能通过数组（即`ArrayList`的实现方式）来实现，另一种`LinkedList`通过“链表”也实现了List接口。

|                     | ArrayList    | LinkedList           |
| :------------------ | :----------- | -------------------- |
| 获取指定元素        | 速度很快     | 需要从头开始查找元素 |
| 添加元素到末尾      | 速度很快     | 速度很快             |
| 在指定位置添加/删除 | 需要移动元素 | 不需要移动元素       |
| 内存占用            | 少           | 较大                 |

## 1.List的特点 `add`,`size`,`get`

使用`List`时，我们要关注`List`接口的规范。`List`接口允许我们添加重复的元素，

`List`内部的元素可以重复：

`List`还允许添加`null`：

```java
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        // 可以添加重复元素 和 null
        // add size get 方法
        String p1 = "app";
        list.add(p1);
        list.add("pear");
        list.add(p1);
        System.out.println(list.size());// 3
        list.add(null);
        list.add(p1);
        System.out.println(list.size());// 5
        System.out.println(list.get(3));// null
    }
}
```

## 2.创建List `List.of(<T>[] args)`

除了使用`ArrayList`和`LinkedList`，我们还可以通过`List`接口提供的`of()`方法，根据给定元素快速创建`List`：

```java
 // 创建list ,使用list 接口提供的of方法
List<Integer> list2 = List.of(1, 2, 3, 4);
```

但是`List.of()`方法不接受`null`值，如果传入`null`，会抛出`NullPointerException`异常。

## 3. 遍历List

### for 循环遍历

和数组类型，我们要遍历一个`List`，完全可以用`for`循环根据索引配合`get(int)`方法遍历：

```java
// 创建list ,使用list 接口提供的of方法
List<Integer> list2 = List.of(1, 2, 3, 4);

// for 循环实现 List 遍历
for (int i = 0; i < list2.size(); i++) {
    System.out.println(list2.get(i));
}
```



### Iterator 配合 for 循环遍历 `it.hasNext()`以及 `it.next()`

但这种方式并不推荐，一是代码复杂，二是因为`get(int)`方法只有`ArrayList`的实现是高效的，**换成`LinkedList`后，索引越大，访问速度越慢。**



所以我们要始终坚持使用迭代器`Iterator`来访问`List`。`Iterator`本身也是一个对象，但它是由`List`的实例调用`iterator()`方法的时候创建的。`Iterator`对象知道如何遍历一个`List`，并且不同的`List`类型，返回的`Iterator`对象实现也是不同的，但总是具有最高的访问效率。

`Iterator`对象有两个方法：**`boolean hasNext()`判断是否有下一个元素**，**`E next()`返回下一个元素**。因此，使用`Iterator`遍历`List`代码如下：

```java
// 使用Iterator + for循环 实现 遍历
List<Double> list3 = List.of(1.2, 2.2, 3.14);
for (Iterator<Double> it = list3.iterator(); it.hasNext(); ) {
    System.out.println(it.next());
}
```



### forEach 遍历

```java
// 使用 forEach 循环遍历
List<Double> list3 = List.of(1.2, 2.2, 3.14);
for (Double item : list3
) {
    System.out.println(item.toString() + ": item");
}
```

上述代码就是我们编写遍历`List`的常见代码。

实际上，只要实现了`Iterable`接口的集合类都可以直接用`for each`循环来遍历，Java编译器本身并不知道如何遍历集合对象，但它会自动把`for each`循环变成`Iterator`的调用，原因就在于`Iterable`接口定义了一个`Iterator<E> iterator()`方法，强迫集合类必须返回一个`Iterator`实例。

## 4. List和Array转换

### `list.toArray()`

把`List`变为`Array`有三种方法，第一种是调用`toArray()`方法直接返回一个`Object[]`数组：

```java
// 1. List 实例直接使用 toArray方法,必须使用Object[] 类型 来接收
List<String> list4 = new ArrayList<>();
String[] s1 = {"apple", "pear", "watermelon"};
for (String item : s1
) {
    list4.add(item);
}

Object[] obj1 = list4.toArray();
for (Object item : obj1
) {
    System.out.println(item.toString());
}
```

**这种方法会丢失类型信息，所以实际应用很少。**



### `list.toArray(T[])`

第二种方式是给`toArray(T[])`传入一个类型相同的`Array`，`List`内部自动把元素复制到传入的`Array`中：

```java
// 2. 给toArray(T[])传入一个类型相同的Array，List内部自动把元素复制到传入的Array中
List<Byte> list5 = List.of((byte) 1, (byte) 2);
Byte[] bt1 = list5.toArray(new Byte[2]);
for (Byte item : bt1
) {
    System.out.println(item);
}
```

注意到这个`toArray(T[])`方法的泛型参数`<T>`并不是`List`接口定义的泛型参数`<E>`，所以，我们实际上可以传入其他类型的数组，例如我们传入`Number`类型的数组，返回的仍然是`Number`类型：

```java
List<Byte> list6 = List.of((byte) 1, (byte) 2);
Number[] bt2 = list5.toArray(new Number[list6.size()]);// toArray[T[]]
Number[] bt3 = list5.toArray(Number[]::new);// 箭头函数写法
for (Number item : bt2
) {
System.out.println(item);
}
```

但是，如果我们传入类型不匹配的数组，例如，`String[]`类型的数组，由于`List`的元素是`Integer`，所以无法放入`String`数组，这个方法会抛出`ArrayStoreException`。

如果我们传入的数组大小和`List`实际的元素个数不一致怎么办？根据[List接口](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/util/List.html#toArray(T[]))的文档，我们可以知道：

如果传入的数组不够大，那么`List`内部会创建一个新的刚好够大的数组，填充后返回；如果传入的数组比`List`元素还要多，那么填充完元素后，剩下的数组元素一律填充`null`。

实际上，最常用的是传入一个“恰好”大小的数组：

```java
Integer[] array = list.toArray(new Integer[list.size()]);
```



### `list.toArray(IntFunction<T[]> generator)`

```java
Integer[] array = list.toArray(Integer[]::new);
```

## 5. Array 转 List `List.of(T...)`

反过来，把`Array`变为`List`就简单多了，通过`List.of(T...)`方法最简单：

```java
Byte[] bt4 = {1, 2, 3, 4};
List<Byte> list7 = List.of(bt4);    
```

**对于JDK 11之前的版本**，可以使用`Arrays.asList(T...)`方法把数组转换成`List`。

要注意的是，返回的`List`不一定就是`ArrayList`或者`LinkedList`，因为`List`只是一个接口，如果我们调用`List.of()`，它返回的是一个只读`List`：

```java
import java.util.List;
public class Main {
    public static void main(String[] args) {
        List<Integer> list = List.of(12, 34, 56);
        list.add(999); // UnsupportedOperationException
    }
}
```

对只读`List`调用`add()`、`remove()`方法会抛出`UnsupportedOperationException`。

## 6. 练习

给定一组连续的整数，例如：10，11，12，……，20，但其中缺失一个数字，试找出缺失的数字：

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 构造从start到end的序列：
        final int start = 10;
        final int end = 20;
        List<Integer> list = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            list.add(i);
        }
        // 随机删除List中的一个元素:
        int removed = list.remove((int) (Math.random() * list.size()));
        int found = findMissingNumber(start, end, list);
        System.out.println(list.toString());
        System.out.println("missing number: " + found);
        System.out.println(removed == found ? "测试成功" : "测试失败");
    }
```
