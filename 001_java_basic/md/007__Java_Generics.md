	# java 泛型

## 1. 什么是泛型

### 1. 什么是向上转型 和向下转型（这个型 其实指的就是类型，class 是类型，interface也是类型）

+ 一、概念
  向上转型是指将子类的实例赋值给父类类型的变量。
  向下转型是指将父类的实例赋值给子类类型的变量。
+ 二、向上转型
  1、向上转型后父类的引用所指向的属性是父类的属性。
  2、如果子类重写了父类的方法，那么父类引用指向的或者调用的方法是子类的方法，这个叫动态绑定（实现多态）。
  3、父类引用不能调用子类自己的方法。
  4、向上转型会丢失子类的新增方法，同时会保留子类重写的方法。
+ 三、向下转型
  1、编译时需要强制类型转换，father前面的必须添加（Son）。
  2、运行时会出现java.lang.ClassCastException错误，可使用instanceof来避免出错此类错误。
  3、向下转型可以得到子类的所有方法（包含父类的方法）。

### 2. 泛型的概念

+ 泛型就是编写模板代码来适应任意类型；

+ 泛型的好处是使用时不必对类型进行强制转换，它通过编译器对类型进行检查；

+ 注意泛型的继承关系：可以把`ArrayList<Integer>`向上转型为`List<Integer>`（`T`不能变！），但不能把`ArrayList<Integer>`向上转型为`ArrayList<Number>`（`T`不能变成父类）。

```java
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // 创建ArrayList<Integer> 类型
        ArrayList<Integer> integerList = new ArrayList<Integer>();
        // 添加一个Integer
        integerList.add(new Integer(23));
        // “向上转型” 为ArrayList<Number>
        ArrayList<Number> numberList=integerList;
        // 添加一个float, 因为Float 也是Number
        numberList.add(new Float(3.14));
        // 从ArrayList<Integer> 获取索引为1(Float)的元素
        Integer n=integerList.get(1);
    }
}
```

我们把一个`ArrayList<Integer>`转型为`ArrayList<Number>`类型后，这个`ArrayList<Number>`就可以接受`Float`类型，因为`Float`是`Number`的子类。但是，`ArrayList<Number>`实际上和`ArrayList<Integer>`是同一个对象，也就是`ArrayList<Integer>`类型，它不可能接受`Float`类型， 所以在获取`Integer`的时候将产生`ClassCastException`。

实际上，编译器为了避免这种错误，根本就不允许把`ArrayList<Integer>`转型为`ArrayList<Number>`。

 `ArrayList<Integer>`和``ArrayList<Number>``两者完全没有继承关系。



## 2. 泛型的使用

### 1. 默认值 以及缩写

+ 使用`ArrayList`时，如果不定义泛型类型时，泛型类型实际上就是`Object`：

编译器如果能自动推断出泛型类型，就可以省略后面的泛型类型。例如，对于下面的代码：

```java
List<Number> list = new ArrayList<Number>();
```

编译器看到泛型类型`List<Number>`就可以自动推断出后面的`ArrayList<T>`的泛型类型必须是`ArrayList<Number>`，因此，可以把代码简写为：

```java
// 泛型的默认值
List list=new ArrayList();// 默认为ArrayList<Object>()
list.add("hello");
list.add("world");
// String first= list.get(0);// 编译器报错 需要的类型为Object; 需要强制转换
String first=(String) list.get(0);
System.out.println(first);// hello
// 2. 泛型的缩写
// 可以省略后面的Number，编译器可以自动推断泛型类型：
List<Number> list = new ArrayList<>();
list2.add(2);
list2.add(3.14);
```



### 2. 泛型接口

除了`ArrayList<T>`使用了泛型，还可以在接口中使用泛型。例如，`Arrays.sort(Object[])`可以对任意数组进行排序，但待排序的元素必须实现`Comparable<T>`这个泛型接口：

```
public interface Comparable<T> {
    /**
     * 返回负数: 当前实例比参数o小
     * 返回0: 当前实例与参数o相等
     * 返回正数: 当前实例比参数o大
     */
    int compareTo(T o);
}
```

这是因为`String`本身已经实现了`Comparable<String>`接口。如果换成我们自定义的`Person`类型试试：

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 3. 泛型接口
        Person[] ps = new Person[]{
                new Person("Bob", 21),
                new Person("Alice", 23),
                new Person("Cc", 99)
        };
        Arrays.sort(ps);
        System.out.println(Arrays.toString(ps));// [Alice,23, Bob,21, Cc,99]

    }
}

class Person implements Comparable<Person> {
    private String name;
    private int score;

    Person(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(Person other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return this.name + "," + this.score;
    }
}
```



## 3. 编写泛型

+ 对于静态方法，我们可以单独改写为“泛型”方法，只需要使用另一个类型即可。

+ 泛型还可以定义多种类型。例如，我们希望`Pair`不总是存储两个类型一样的对象，就可以使用类型`<T, K>`：

```java
public class Main {
    public static void main(String[] args) {

    }
}


// 编写一个泛型 类,多种泛型变量
class Pair<T,V> {
    public Pair(T first, T last){
        this.first=first;
        this.last=last;
    }
    private T first;
    private T last;
    private V center;

    public V getCenter() {
        return center;
    }

    public void setCenter(V center) {
        this.center = center;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T getLast() {
        return last;
    }

    public void setLast(T last) {
        this.last = last;
    }
    // 静态方法，需要单独开一个属性
    // 静态泛型方法应该使用其他类型区分:
    public static <K,S> Pair<K,S> create(K first, K last) {
        return new Pair<K,S>(first, last);
    }
}
```


## 4. 擦拭法（java 泛型的实现和缺陷）

+ 泛型是一种类似”模板代码“的技术，不同语言的泛型实现方式不一定相同。

+ Java语言的泛型实现方式是擦拭法（Type Erasure）。

+ 所谓擦拭法是指，虚拟机对泛型其实一无所知，所有的工作都是编译器做的。

  

+ 例如，我们编写了一个泛型类`Pair<T>`，这是**编译器**看到的代码：

  ```java
  public class Pair<T> {
      private T first;
      private T last;
      public Pair(T first, T last) {
          this.first = first;
          this.last = last;
      }
      public T getFirst() {
          return first;
      }
      public T getLast() {
          return last;
      }
  }
  ```

  而**虚拟机**根本不知道泛型。这是虚拟机执行的代码：

  ```java
  public class Pair {
      private Object first;
      private Object last;
      public Pair(Object first, Object last) {
          this.first = first;
          this.last = last;
      }
      public Object getFirst() {
          return first;
      }
      public Object getLast() {
          return last;
      }
  }
  ```

因此，Java使用擦拭法实现泛型，导致了：

- 编译器把类型`<T>`视为`Object`；
- 编译器根据`<T>`实现**安全的强制转型**。

使用泛型的时候，我们编写的代码也是编译器看到的代码：

```java
Pair<String> p = new Pair<>("Hello", "world");
String first = p.getFirst();
String last = p.getLast();
```

而虚拟机执行的代码并没有泛型：

```java
Pair p = new Pair("Hello", "world");
String first = (String) p.getFirst();
String last = (String) p.getLast();
```

所以，**Java的泛型是由编译器在编译时实行的，编译器内部永远把所有类型`T`视为`Object`处理**，但是，**在需要转型的时候，编译器会根据`T`的类型自动为我们实行安全地强制转型。**

了解了Java泛型的实现方式——擦拭法，我们就知道了Java泛型的局限：

### 1. 局限一：`<T>`不能是基本类型，

+ 局限一：`<T>`不能是基本类型，例如`int`，因为实际类型是`Object`，`Object`类型无法持有基本类型：

```java
 // 1. java 泛型变量 不能是基本类型,要使用基础类的包装类型
Pair<int> p=new Pair<>(1,2);//类型实参不能为基元类型
Pair<Integer> p=new Pair<>(1,2);//类型实参不能为基元类型
```

### 2. 局限二：无法取得带泛型的`Class`。

+ **其实就是反射时 获取的class 信息相同**

+ 观察以下代码：

  ```java
  import static java.lang.System.*;
  
  public class Main {
      private static Class c1;
  
      public static void main(String[] args) {
          // 1. java 泛型变量 不能是基本类型,要使用基础类的包装类型
          // Pair<int> p=new Pair<>(1,2);//类型实参不能为基元类型
          // Pair<Integer> p=new Pair<>(1,2);//类型实参不能为基元类型
  
          //2. 反射获取的 class 信息是同一个,不会因为 泛型变量(T)的不同而变化
          Pair<String> pair1 = new Pair("he", "w0");
          Pair<Integer> pair2 = new Pair<>(1, 2);
          Class c1 = pair1.getClass();
          Class c2 = pair2.getClass();
          out.println(c1 == c2);// true
          out.println(c1 == Pair.class);// true
      }
  }
  
  class Pair<T> {
      private T first;
      private T last;
  
      public Pair(T first, T last) {
          this.first = first;
          this.last = last;
      }
  
      public T getFirst() {
          return first;// 可以省略this
      }
  
      public T getLast() {
          return this.last;
      }
  }
  ```

因为`T`是`Object`，我们对`Pair<String>`和`Pair<Integer>`类型获取`Class`时，获取到的是同一个`Class`，也就是`Pair`类的`Class`。

**换句话说，所有泛型实例，无论`T`的类型是什么，`getClass()`返回同一个`Class`实例，因为编译后它们全部都是`Pair<Object>`**。

### 3. 局限三：无法判断带泛型的类型：

+ 不能使用 `instanceof` 来判断类型,进行类型收缩

  ```java
  //3.  无法判断带泛型的类型
          if (pair1 instanceof Pair<String>) {
              // compile error instanceof 的泛型类型非法
          }
  ```

  原因和前面一样，并不存在`Pair<String>.class`，而是只有唯一的`Pair.class`。

### 4. 局限四：不能实例化`T`类型：

```java
public class Pair<T> {
    private T first;
    private T last;

    public Pair() {
        // Compile error:
        first = new T();//类型形参 'T' 不能直接实例化
        last = new T();
    }
}
```

上述代码无法通过编译，因为构造方法的两行语句：

```java
first = new T();
last = new T();
```

擦拭后实际上变成了：

```java
first = new Object();
last = new Object();
```

这样一来，创建`new Pair<String>()`和创建`new Pair<Integer>()`就全部成了`Object`，显然编译器要阻止这种类型不对的代码。

+ **要实例化`T`类型，我们必须借助额外的`Class<T>`参数**：

```java
// 要实例化 T类型, 必须要借用 Reflection的newInstance()方法
class Person<T> {
    private T name;
    private T age;

    public Person(Class<T> classObj) throws InstantiationException, IllegalAccessException {
        name = classObj.newInstance();
        age = classObj.newInstance();
    }
}
```

上述代码借助`Class<T>`参数并通过反射来实例化`T`类型，使用的时候，也必须传入`Class<T>`。例如：

```java
Pair<String> pair = new Pair<>(String.class);
```

因为传入了`Class<String>`的实例，所以我们借助`String.class`就可以实例化`String`类型。

### 5. 不恰当的覆写方法

有些时候，一个看似正确定义的方法会无法通过编译。例如：

```java
class Teacher<T> {
    public boolean equals(T t) {
        // 'Teacher' 中的 'equals(T)' 与 'java.lang.Object' 中的 'equals(Object)' 冲突；两个方法具有相同的擦除，但都没有重写另一个
        return this == t;
    }
}
```

这是因为，定义的`equals(T t)`方法实际上会被擦拭成`equals(Object t)`，而这个方法是继承自`Object`的，编译器会阻止一个实际上会变成覆写的泛型方法定义。

换个方法名，避开与`Object.equals(Object)`的冲突就可以成功编译：

```java
class Teacher<T> {
    public boolean equals(T t) {
        // 'Teacher' 中的 'equals(T)' 与 'java.lang.Object' 中的 'equals(Object)' 冲突；两个方法具有相同的擦除，但都没有重写另一个
        return this == t;
    }

    public boolean same(T t) {
        return this == t;
    }
}
```



### 6. 泛型继承

一个类可以继承自一个泛型类。例如：父类的类型是`Pair<Integer>`，子类的类型是`IntPair`，可以这么继承：

```java
// 一个正常的类 可以继承 自 泛型类
class IntPair extends Pair<Integer>{

    public IntPair(Integer first, Integer last) {
        super(first, last);
    }
}
```

使用的时候，因为子类`IntPair`并没有泛型类型，所以，正常使用即可：

```java
IntPair ip = new IntPair(1, 2);
```

前面讲了，我们无法获取`Pair<T>`的`T`类型，即给定一个变量`Pair<Integer> p`，无法从`p`中获取到`Integer`类型。

但是，在父类是泛型类型的情况下，编译器就必须把类型`T`（对`IntPair`来说，也就是`Integer`类型）保存到子类的class文件中，不然编译器就不知道`IntPair`只能存取`Integer`这种类型。

在继承了泛型类型的情况下，子类可以获取父类的泛型类型。例如：`IntPair`可以获取到父类的泛型类型`Integer`。获取父类的泛型类型代码比较复杂：

```java
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Main {
    public static void main(String[] args) 
     Class<IntPair> clazz = IntPair.class;
        Type t = clazz.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) t;
            Type[] types = pt.getActualTypeArguments(); // 可能有多个泛型类型
            Type firstType = types[0]; // 取第一个泛型类型
            Class<?> typeClass = (Class<?>) firstType;
            System.out.println(typeClass); // Integer
        }
      }
}

class Pair<T> {
    private T first;
    private T last;
    public Pair(T first, T last) {
        this.first = first;
        this.last = last;
    }
    public T getFirst() {
        return first;
    }
    public T getLast() {
        return last;
    }
}

class IntPair extends Pair<Integer> {
    public IntPair(Integer first, Integer last) {
        super(first, last);
    }
}
```

+ **因为Java引入了泛型，所以，只用`Class`来标识类型已经不够了**。实际上，Java的类型系统结构如下：

```
                      ┌────┐
                      │Type│
                      └────┘
                         ▲
                         │
   ┌────────────┬────────┴─────────┬───────────────┐
   │            │                  │               │
┌─────┐┌─────────────────┐┌────────────────┐┌────────────┐
│Class││ParameterizedType││GenericArrayType││WildcardType│
└─────┘└─────────────────┘└────────────────┘└────────────┘
```

让我们解释一下这几种类型：

1. `Class`：表示一个普通的类或接口的类型，例如`String`、`Integer`等。`Class`类型提供了关于类或接口的元数据信息，例如类名、父类、接口、字段、方法等。
2. `ParameterizedType`：表示一个参数化类型，即包含泛型参数的类型，例如`List<String>`。`ParameterizedType`接口提供了获取泛型参数类型的方法。
3. `GenericArrayType`：表示一个泛型数组类型，即数组类型中包含泛型参数，例如`T[]`或`List<String>[]`。`GenericArrayType`接口提供了获取泛型参数类型的方法。
4. `WildcardType`：**表示一个通配符类型，用于表示未知的类型参数**。通配符类型在泛型方法或泛型类中使用，例如`List<?>`或`List<? extends Number>`。`WildcardType`接口提供了获取上界和下界类型的方法。(这个类似于`typescript`中的infer,用于获取未知类型,`infer`主要用于条件类型的推断，而`WildcardType`主要用于通配符类型的表示和限制。)

### 小结

Java的泛型是采用擦拭法实现的；

擦拭法决定了泛型`<T>`：

- 不能是基本类型，例如：`int`；
- 不能获取带泛型类型的`Class`，例如：`Pair<String>.class`；
- 不能判断带泛型类型的类型，例如：`x instanceof Pair<String>`；
- 不能实例化`T`类型，例如：`new T()`。

泛型方法要防止重复定义方法，例如：`public boolean equals(T obj)`；

子类可以获取父类的泛型类型`<T>`。

## 5. extends Wildcards 向下兼容类型(通配符)(类似于 ts的extends 和infer结合体)

### 问题场景

我们前面已经讲到了泛型的继承关系：`Pair<Integer>`不是`Pair<Number>`的子类。

假设我们定义了`Pair<T>`：

```java
class Pair<T> {
    private T first;
    private T last;

    public Pair(T first, T last) {
        this.first = first;
        this.last = last;
    }

    
    public T getFirst() {
        return first;
    }

    public T getLast() {
        return last;
    }
};
```

然后，我们又针对`Pair<Number>`类型写了一个静态方法，它接收的参数类型是`Pair<Number>`：

```java
class PairHelper {
    static int add(Pair<Number> pair) {
        Number first = pair.getFirst();
        Number last = pair.getLast();
        return first.intValue() + last.intValue();
    }
}
```

上述代码是可以正常编译的。使用的时候，我们传入：

```java
public class Main {
    public static void main(String[] args) {
        int sum = PairHelper.add(new Pair<>(1, 2));
    }
}
```

注意：传入的类型是`Pair<Number>`，实际参数类型是`Pair<Integer>`。

既然实际参数是`Integer`类型，试试传入`Pair<Integer>`：

```java
public class Main {
    public static void main(String[] args) {
        int sum = PairHelper.add(new Pair<>(1, 2));
        Pair<Integer> p = new Pair<>(123, 456);// 定义 p的类型为 Pair<Integer>
        int n = add(p);// compile error 需要的类 Pair <Number> 提供的类型:Pair <Integer>
        System.out.println(n);
    }

    static int add(Pair<Number> p) {// 定义参数类型为 Pair<Number>
        Number first = p.getFirst();
        Number last = p.getLast();
        return first.intValue() + last.intValue();
    }
}


class Pair<T> {
    private T first;
    private T last;

    public Pair(T first, T last) {
        this.first = first;
        this.last = last;
    }


    public T getFirst() {
        return first;
    }

    public T getLast() {
        return last;
    }
};
```

### 解决办法

直接运行，会得到一个编译错误：

```java
incompatible types: Pair<Integer> cannot be converted to Pair<Number>
```

原因很明显，因为`Pair<Integer>`不是`Pair<Number>`的子类，因此，`add(Pair<Number>)`不接受参数类型`Pair<Integer>`。

但是从`add()`方法的代码可知，传入`Pair<Integer>`是完全符合内部代码的类型规范，因为语句：

```java
Number first = p.getFirst();
Number last = p.getLast();
```

实际类型是`Integer`，引用类型是`Number`，没有问题。问题在于方法参数类型定死了只能传入`Pair<Number>`。

有没有办法使得方法参数接受`Pair<Integer>`？办法是有的，这就是使用`Pair<? extends Number>`使得方法接收所有泛型类型为`Number`或`Number`子类的`Pair`类型。我们把代码改写如下：

```java
public class Main {
    public static void main(String[] args) {
        int sum = PairHelper.add(new Pair<>(1, 2));
        Pair<Integer> p = new Pair<>(123, 456);// 定义 p的类型为 Pair<Integer>
        int n = add(p);// compile error 需要的类 Pair <Number> 提供的类型:Pair <Integer>
        System.out.println(n);
    }

//    static int add(Pair<Number> p) {// 定义参数类型为 Pair<Number>
//        Number first = p.getFirst();
//        Number last = p.getLast();
//        return first.intValue() + last.intValue();
//    }
    // 使用 extends 通配符 用于获取 泛型T 为 Number 或者 Number子类的类型
    static int add(Pair<? extends Number> p) {// 定义参数类型为 Pair<Number>
        Number first = p.getFirst();
        Number last = p.getLast();
        return first.intValue() + last.intValue();
    }
}


class Pair<T> {
    private T first;
    private T last;

    public Pair(T first, T last) {
        this.first = first;
        this.last = last;
    }


    public T getFirst() {
        return first;
    }

    public T getLast() {
        return last;
    }
};
```

### java中的协变（covariant）

+ 概念: 示一个泛型类型的子类型的关系。如果类型参数满足协变关系，那么可以将子类型的对象赋值给父类型的引用，或者将子类型的对象传递给期望父类型的参数。在Java中，数组是协变的，通配符类型（`? extends T`）也是协变的。

这样一来，给方法传入`Pair<Integer>`类型时，它符合参数`Pair<? extends Number>`类型。这种使用`<? extends Number>`的泛型定义称之为上界通配符（Upper Bounds Wildcards），即把泛型类型`T`的上界限定在`Number`了。

除了可以传入`Pair<Integer>`类型，我们还可以传入`Pair<Double>`类型，`Pair<BigDecimal>`类型等等，因为`Double`和`BigDecimal`都是`Number`的子类。

如果我们考察对`Pair<? extends Number>`类型调用`getFirst()`方法，实际的方法签名变成了：

```java
<? extends Number> getFirst();
```

即返回值是`Number`或`Number`的子类，因此，可以安全赋值给`Number`类型的变量：

```java
Number x = p.getFirst();
```

然后，我们不可预测实际类型就是`Integer`，例如，下面的代码是无法通过编译的：

```java
Integer x = p.getFirst();
```

这是因为实际的返回类型可能是`Integer`，也可能是`Double`或者其他类型，编译器只能确定类型一定是`Number`的子类（包括`Number`类型本身），但具体类型无法确定。

我们再来考察一下`Pair<T>`的`set`方法：

```java
public class Main {
    public static void main(String[] args) {
        int sum = PairHelper.add(new Pair<>(1, 2));
        Pair<Integer> p = new Pair<>(123, 456);// 定义 p的类型为 Pair<Integer>
        int n = add(p);// compile error 需要的类 Pair <Number> 提供的类型:Pair <Integer>
        System.out.println(n);
    }

    // 使用 extends 通配符 用于获取 泛型T 为 Number 或者 Number子类的类型
    static int add(Pair<? extends Number> p) {// 定义参数类型为 Pair<Number>
        Number first = p.getFirst();
        Number last = p.getLast();
        p.setFirst(new Integer(first.intValue() + 100));
        p.setLast(new Integer(last.intValue() + 100));
        return first.intValue() + last.intValue();
    }
}


class Pair<T> {
    private T first;
    private T last;

    public Pair(T first, T last) {
        this.first = first;
        this.last = last;
    }


    public T getFirst() {
        return first;
    }

    public T getLast() {
        return last;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public void setLast(T last) {
        this.last = last;
    }
};
```

不出意外，我们会得到一个编译错误：

```java
incompatible types: Integer cannot be converted to CAP#1
where CAP#1 is a fresh type-variable:
    CAP#1 extends Number from capture of ? extends Number
```

编译错误发生在`p.setFirst()`传入的参数是`Integer`类型。有些童鞋会问了，既然`p`的定义是`Pair<? extends Number>`，那么`setFirst(? extends Number)`为什么不能传入`Integer`？

原因还在于擦拭法。如果我们传入的`p`是`Pair<Double>`，显然它满足参数定义`Pair<? extends Number>`，然而，`Pair<Double>`的`setFirst()`显然无法接受`Integer`类型。

这就是`<? extends Number>`通配符的一个重要限制：**方法参数签名`setFirst(? extends Number)`无法传递任何`Number`的子类型给`setFirst(? extends Number)`**。

+ **说人话就是** 

  泛型类 Pair 内部定义的 `setFirst<T first>` 中的这个T可以被 `<? extends Number>`或者`Number替代`,但是不能被`Number`的子类型直接替代,假如直接用`Integer`类型替代(即`setFirst<Integer n>`),此时在`Main`上定义的静态方法`static int add(Pair<? extends Number> p)` ,将 `Double`类型来替换掉 `Pair<T>`中的T类型,就会导致和 Integer 和 Double类型产生冲突



这里唯一的例外是可以给方法参数传入`null`：

```java
p.setFirst(null); // ok, 但是后面会抛出NullPointerException
p.getFirst().intValue(); // NullPointerException
```

### extends通配符的作用

如果我们考察Java标准库的`java.util.List<T>`接口，它实现的是一个类似“可变数组”的列表，主要功能包括：

```java
public interface List<T> {
    int size(); // 获取个数
    T get(int index); // 根据索引获取指定元素
    void add(T t); // 添加一个新元素
    void remove(T t); // 删除一个已有元素
}
```

现在，让我们定义一个方法来处理列表的每个元素：

```java
int sumofList(List<? extends Integer> list) {
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            Integer n = list.get(i);
            sum += n;
        }
        return sum;
    }
```

为什么我们定义的方法参数类型是`List<? extends Integer>`而不是`List<Integer>`？从方法内部代码看，传入`List<? extends Integer>`或者`List<Integer>`是完全一样的，但是，注意到`List<? extends Integer>`的限制：

- 允许调用`get()`方法获取`Integer`的引用；
- 不允许调用`set(? extends Integer)`方法并传入任何`Integer`的引用（`null`除外）。

因此，方法参数类型`List<? extends Integer>`表明了该方法内部只会读取`List`的元素，不会修改`List`的元素（因为无法调用`add(? extends Integer)`、`remove(? extends Integer)`这些方法。<mark>**换句话说，这是一个对参数`List<? extends Integer>`进行只读的方法（恶意调用`set(null)`除外）**</mark>。

### 使用extends限定T类型

在定义泛型类型`Pair<T>`的时候，也可以使用`extends`通配符来限定`T`的类型：

```java
class Student<T extends Number> {
    private T t1;
    private T t2;

    public Student(T t1, T t2) {
        this.t1 = t1;
        this.t2 = t2;
    }
}
```

现在，我们只能定义：

```java
// extends 限制 泛型类型,这时候的作用等价于 ts的 extends
    Student<Number> s = null;
    Student<Integer> s2 = new Student<>(1, 2);
    Student<Double> s3 = null;
```

因为`Number`、`Integer`和`Double`都符合`<T extends Number>`。

非`Number`类型将无法通过编译：

```java
Student<Objects> s4 = null;// compile error
Student<String> s5 = null;// compile error
```

因为`String`、`Object`都不符合`<T extends Number>`，因为它们不是`Number`类型或`Number`的子类。

### 小结

使用类似`<? extends Number>`通配符作为方法参数时表示：

- 方法内部可以调用获取`Number`引用的方法，例如：`Number n = obj.getFirst();`；
- 方法内部无法调用传入`Number`引用的方法（`null`除外），例如：`obj.setFirst(Number n);`。

即一句话总结：使用`extends`通配符表示可以读，不能写。

使用类似`<T extends Number>`定义泛型类时表示：

- 泛型类型限定为`Number`以及`Number`的子类。



## 6. super 通配符 向上兼容类型

我们前面已经讲到了泛型的继承关系：`Pair<Integer>`不是`Pair<Number>`的子类。

考察下面的`set`方法：

```java
void set(Pair<Integer> p, Integer first, Integer last) {
    p.setFirst(first);
    p.setLast(last);
}
```

传入`Pair<Integer>`是允许的，但是传入`Pair<Number>`是不允许的。

<mark>**和`extends`通配符相反**</mark>，这次，我们希望接受`Pair<Integer>`类型，以及`Pair<Number>`、`Pair<Object>`，因为`Number`和`Object`是`Integer`的父类，`setFirst(Number)`和`setFirst(Object)`实际上允许接受`Integer`类型。

我们使用`super`通配符来改写这个方法：

```java
void set(Pair<? super Integer> p, Integer first, Integer last) {
    p.setFirst(first);
    p.setLast(last);
}
```

注意到`Pair<? super Integer>`表示，<mark>**方法参数接受所有泛型类型为`Integer`或`Integer`父类的`Pair`类型**</mark>。

下面的代码可以被正常编译：

```java
import static java.lang.System.*;


public class Main {
    public static void main(String[] args) {
        Pair<Number> p1 = new Pair<>(12.3, 456);
        Pair<Integer> p2 = new Pair<>(123, 456);
        setName(p1, 100);
        setName(p2, 200);
        out.println(p1.getFirst() + "," + p1.getLast());// 100,100
        out.println(p2.getFirst() + "," + p2.getLast());// 200,200
    }

    static void setName(Pair<? super Integer> p, Integer n) {
        p.setFirst(n);
        p.setLast(n);
        // (? super Integer) x = p.getFirst();// 通配符只能做引用形参
        // Integer x=p.getFirst();//这里不能写死为某个特定类型,因为 getFirst方法的必须保证类型和 ? super Integer 相等
    }
}


class Pair<T> {
    private T first;// 在Main的setName方法中 实际类型变为 ? super Integer
    private T last;// 在Main的setName方法中 实际类型变为 ? super Integer
    // windows上

    public Pair(T first, T last) {
        this.first = first;
        this.last = last;
    }
    // 在Main的setName方法中 实际变为了

    /**
     * public Pair (? super Integer T\,? super Integer T){
     * this.first = first;// 两者类型始终相等且皆为 ? super Integer,可以赋值
     * this.last = last;// 两者类型始终相等且皆为 ? super Integer,可以赋值
     * }
     */

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }
    // 在Main的setName方法中 实际签名为 void setFirst(? super Integer)

    /**
     * public void setFirst(? super T first){
     * this.first = first; // 两者类型始终相等且皆为 ? super Integer,可以赋值
     * }
     */

    public T getLast() {
        return last;
    }
    // 在Main的setName 方法中的 实际类型签名  ? super Integer getLast

    /**
     * public ? super Integer getLast(){
     * return last;// last类型为 ? super Integer ,可以安全返回
     * }
     */

    public void setLast(T last) {
        this.last = last;
    }

}
```

### java中的逆变（contravariant）

+ 概念: 表示一个泛型类型的超类型的关系。如果类型参数满足逆变关系，那么可以将超类型的对象赋值给子类型的引用，或者将超类型的对象传递给期望子类型的参数。在Java中，通配符类型（`? super T`）是逆变的。

考察`Pair<? super Integer>`的`setFirst()`方法，它的方法签名实际上是：

```java
void setFirst(? super Integer);
```

因此，可以安全地传入`Integer`类型。

再考察`Pair<? super Integer>`的`getFirst()`方法，它的方法签名实际上是：

```java
? super Integer getFirst();// 把 ? super Integer 来 替代 T类型变量
```

这里注意到我们无法使用`Integer`类型来接收`getFirst()`的返回值，即下面的语句将无法通过编译：

```java
Integer x = p.getFirst();
```

**因为如果传入的实际类型是`Pair<Number>`，编译器无法将`Number`类型转型为`Integer`**。

```java
? super Integer getFirst();// 把 ? super Integer 想象成Number 然后来替代 T类型变量
```



注意：虽然`Number`是一个抽象类，我们无法直接实例化它。但是，即便`Number`不是抽象类，这里仍然无法通过编译。此外，传入`Pair<Object>`类型时，编译器也无法将`Object`类型转型为`Integer`。

唯一可以接收`getFirst()`方法返回值的是`Object`类型：

```java
Object obj = p.getFirst();
```

因此，使用`<? super Integer>`通配符表示：

- 允许调用`set(? super Integer)`方法传入`Integer`的引用；
- 不允许调用`get()`方法获得`Integer`的引用。

唯一例外是可以获取`Object`的引用：`Object o = p.getFirst()`。

**换句话说，使用`<? super Integer>`通配符作为方法参数，表示方法内部代码对于参数只能写，不能读。**

### 对比extends和super通配符

我们再回顾一下`extends`通配符。**作为方法参数**，`<? extends T>`类型和`<? super T>`类型的区别在于：

- <mark>`<? extends T>`允许调用读方法`T get()`获取`T`的引用，但不允许调用写方法`set(T)`传入`T`的引用（传入`null`除外）</mark>；
- <mark>`<? super T>`允许调用写方法`set(T)`传入`T`的引用，但不允许调用读方法`T get()`获取`T`的引用（获取`Object`除外）</mark>。

一个是允许读不允许写，另一个是允许写不允许读。

先记住上面的结论，我们来看Java标准库的`Collections`类定义的`copy()`方法：

```java
public class Collections {
    // 把src的每个元素复制到dest中:
    public static <T> void copy(List<? super T> dest, List<? extends T> src) {
        for (int i=0; i<src.size(); i++) {
            T t = src.get(i);
            dest.add(t);
        }
    }
}
```

它的作用是把一个`List`的每个元素依次添加到另一个`List`中。它的第一个参数是`List<? super T>`，表示目标`List`，第二个参数`List<? extends T>`，表示要复制的`List`。我们可以简单地用`for`循环实现复制。在`for`循环中，我们可以看到，对于类型`<? extends T>`的变量`src`，我们可以安全地获取类型`T`的引用，而对于类型`<? super T>`的变量`dest`，我们可以安全地传入`T`的引用。

这个`copy()`方法的定义就完美地展示了`extends`和`super`的意图：

- `copy()`方法内部不会读取`dest`，因为不能调用`dest.get()`来获取`T`的引用；
- `copy()`方法内部也不会修改`src`，因为不能调用`src.add(T)`。

这是由编译器检查来实现的。如果在方法代码中意外修改了`src`，或者意外读取了`dest`，就会导致一个编译错误：

```java
public class Collections {
    // 把src的每个元素复制到dest中:
    public static <T> void copy(List<? super T> dest, List<? extends T> src) {
        ...
        T t = dest.get(0); // compile error!
        src.add(t); // compile error!
    }
}
```

这个`copy()`方法的另一个好处是可以安全地把一个`List<Integer>`添加到`List<Number>`，但是无法反过来添加：

```java
// copy List<Integer> to List<Number> ok:
List<Number> numList = ...;
List<Integer> intList = ...;
Collections.copy(numList, intList);

// ERROR: cannot copy List<Number> to List<Integer>:
Collections.copy(intList, numList);
```

### PECS原则

何时使用`extends`，何时使用`super`？为了便于记忆，我们可以用PECS原则：**Producer Extends Consumer Super**。

即：**如果需要返回`T`，它是生产者（Producer），要使用`extends`通配符；如果需要写入`T`，它是消费者（Consumer），要使用`super`通配符**。

还是以`Collections`的`copy()`方法为例：

```java
public class Collections {
    public static <T> void copy(List<? super T> dest, List<? extends T> src) {
        for (int i=0; i<src.size(); i++) {
            T t = src.get(i); // src是producer
            dest.add(t); // dest是consumer
        }
    }
}
```

需要返回`T`的`src`是生产者，因此声明为`List<? extends T>`，需要写入`T`的`dest`是消费者，因此声明为`List<? super T>`。

### 无限定通配符

我们已经讨论了`<? extends T>`和`<? super T>`作为方法参数的作用。实际上，Java的泛型还允许使用无限定通配符（Unbounded Wildcard Type），即只定义一个`?`：

```java
void sample(Pair<?> p) {
        
    }
```

因为`<?>`通配符既没有`extends`，也没有`super`，因此：

- **不允许调用`set(T)`方法并传入引用（`null`除外）；**
- **不允许调用`T get()`方法并获取`T`引用（只能获取`Object`引用）。**

换句话说，既不能读，也不能写，那只能做一些`null`判断：

```java
static boolean isNull(Pair<?> p) {
        return p.getFirst() == null || p.getLast() == null;
    }
```

大多数情况下，可以引入泛型参数`<T>`消除`<?>`通配符：

```java
static <T> boolean isNull2(Pair<T> p) {
        return p.getFirst() == null || p.getLast() == null;
    }
```

**`<?>`通配符有一个独特的特点，就是：`Pair<?>`是所有`Pair<T>`的超类**：

```java
import static java.lang.System.*;
public class Main {
    public static void main(String[] args) {
        // <?> 通用通配符的特点, Pair<?> 是 所有 Pair<T>的超类
        Pair<Integer> p3 = new Pair<>(123, 456);
        Pair<?> p4 = p3;// 安全地向上转型
        out.println(p4.getFirst() + "" + p4.getLast());
    }
}
class Pair<T> {
    private T first;
    private T last;

    public Pair(T first, T last) {
        this.first = first;
        this.last = last;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T getLast() {
        return last;
    }

}
```

上述代码是可以正常编译运行的，因为`Pair<Integer>`是`Pair<?>`的子类，可以安全地向上转型。

### 小结

使用类似`<? super Integer>`通配符作为方法参数时表示：

- 方法内部可以调用传入`Integer`引用的方法，例如：`obj.setFirst(Integer n);`；
- 方法内部无法调用获取`Integer`引用的方法（`Object`除外），例如：`Integer n = obj.getFirst();`。

即使用`super`通配符表示只能写不能读。

使用`extends`和`super`通配符要遵循PECS原则。

无限定通配符`<?>`很少使用，可以用`<T>`替换，同时它是所有`<T>`类型的超类。

## 7. 通俗解释

当涉及到Java的类型转换时，通配符的理解可能会更加清晰。让我们通过类型转换的角度来解释`<? extends T>`和`<? super T>`的含义。

假设我们有一个泛型类`Box<T>`，表示一个装有物品的盒子，其中`T`表示物品的类型。现在有两个子类：`Fruit`和`Apple`，其中`Apple`是`Fruit`的子类。

1. `<? extends T>`：上界通配符

   - 当我们声明`Box<? extends Fruit>`时，它表示一个盒子，里面装有`Fruit`或`Fruit`的子类的物品。

   - 通过`Box<? extends Fruit>`，我们可以安全地获取（读取）其中的物品，因为我们知道它们至少是`Fruit`类型的。

   - 例如，我们可以执行以下操作：

     ```java
     codeBox<? extends Fruit> box = new Box<Apple>();
     Fruit fruit = box.getItem(); // 可以安全地获取物品，返回类型为 Fruit
     ```

   - 但是，我们无法向这个盒子中放入（写入）物品，因为我们无法确定具体是什么类型的子类。下面的示例将无法通过编译：

     ```java
     codeBox<? extends Fruit> box = new Box<Apple>();
     box.putItem(new Apple()); // 编译错误，无法确定放入的是哪种具体类型的子类
     ```

2. `<? super T>`：下界通配符

   - 当我们声明`Box<? super Apple>`时，它表示一个盒子，可以装有`Apple`或`Apple`的父类的物品。

   - 通过`Box<? super Apple>`，我们可以安全地向其中放入（写入）`Apple`对象，因为它们至少是`Apple`类型的父类。

   - 例如，我们可以执行以下操作：

     ```java
     codeBox<? super Apple> box = new Box<Fruit>();
     box.putItem(new Apple()); // 可以安全地放入 Apple 对象
     ```

   - 但是，我们无法安全地获取（读取）这个盒子中的物品，因为我们只知道它们是`Apple`的某个父类，无法确定具体是什么类型。下面的示例将无法通过编译：

     ```java
     codeBox<? super Apple> box = new Box<Fruit>();
     Apple apple = box.getItem(); // 编译错误，无法确定获取的具体类型,不能知道? 是什么类型

+ 综上所述，通配符`<? extends T>`用于读取数据，通配符`<? super T>`用于写入数据。根据上界和下界的限制，它们提供了类型安全性并帮助我们在不同情境下正确处理泛型对象。



## 8. ` java`中的 泛型和反射

Java的部分反射API也是泛型。例如：`Class<T>`就是泛型：

```java
// compile warning:
Class clazz = String.class;
String str = (String) clazz.newInstance();

// no warning:
Class<String> clazz = String.class;
String str = clazz.newInstance();
```

调用`Class`的`getSuperclass()`方法返回的`Class`类型是`Class<? super T>`：

```java
Class<? super String> sup = String.class.getSuperclass();
```

构造方法`Constructor<T>`也是泛型：

```java
        // 构造方法 constructor<T>也是泛型
        Class<Integer> clazz4 = Integer.class;
        Constructor<Integer> cons = clazz4.getConstructor(int.class);
        Integer i = cons.newInstance(123);
```

我们可以声明带泛型的数组，但不能用`new`操作符创建带泛型的数组：

+ 类似于 ts中的错误 : 不能将类型当作值来使用

  ```java
  Pair<String>[] ps = null; // ok
  Pair<String>[] ps = new Pair<String>[2]; // compile error!
  ```

+ **必须通过强制转型实现带泛型的数组**：

  ```java
  // 可以声明带泛型的数组，但不能用new操作符创建带泛型的数组(类似于ts中的不能将类型作为值来使用的错误提示)
  Pair<String>[] ps = null;// ok
  // Pair<String>[] ps2 = new Pair<String>[2];// compiler error
  // 必须通过强制转型实现带泛型的数组
  @SuppressWarnings("unchecked")
  Pair<String>[] ps3 = (Pair<String>[]) new Pair[2];
  ```
  

+ 使用泛型数组要特别小心，因为数组实际上在运行期没有泛型，编译器可以强制检查变量`ps`，因为它的类型是泛型数组。但是，编译器不会检查变量`arr`，因为它不是泛型数组。因为这两个变量实际上指向同一个数组，所以，操作`arr`可能导致从`ps`获取元素时报错，例如，以下代码演示了不安全地使用带泛型的数组：

  ```java
  Pair[] arr = new Pair[2];
  Pair<String>[] ps4 = (Pair<String>[]) arr;
  ps4[0] = new Pair<String>("a", "b");
  arr[1] = new Pair<Integer>(1, 2);
  
  // ClassCastException:
  Pair<String> p = ps4[1];
  String s = p.getFirst();
  ```

+ 要安全地使用泛型数组，必须扔掉`arr`的引用：

  ```java
  Pair<String>[] ps4 = (Pair<String>[]) new Pair[2];
  ps4[0] = new Pair<String>("a", "b");
  // ClassCastException:
  Pair<String> p = ps4[0];
  String s = p.getFirst();
  ```

+ 上面的代码中，由于拿不到原始数组的引用，就只能对泛型数组`ps`进行操作，这种操作就是安全的。

+ 带泛型的数组实际上是编译器的类型擦除：

  ```java
  Pair[] arr = new Pair[2];
  Pair<String>[] ps = (Pair<String>[]) arr;
  
  System.out.println(ps.getClass() == Pair[].class); // true
  
  String s1 = (String) arr[0].getFirst();
  String s2 = ps[0].getFirst();
  ```

+ 所以我们不能直接创建泛型数组`T[]`，因为擦拭后代码变为`Object[]`：

  ```java
  // compile error:
  class Abc<T> {
      T[] createArray() {
          // 类型形参 'T' 不能直接实例化 compile error
          return new T[5];
      }
  }
  ```

+ 必须借助`Class<T>`来创建泛型数组：

  ```java
  T[] createArray(Class<T> cls) {
      return (T[]) Array.newInstance(cls, 5);
  }
  ```

+ 我们还可以利用可变参数创建泛型数组`T[]`：

  ```java
  ublic class ArrayHelper {
      @SafeVarargs
      static <T> T[] asArray(T... objs) {
          return objs;
      }
  }
  
  String[] ss = ArrayHelper.asArray("a", "b", "c");
  Integer[] ns = ArrayHelper.asArray(1, 2, 3);
  ```

### 谨慎使用泛型可变参数

在上面的例子中，我们看到，通过：

```java
static <T> T[] asArray(T... objs) {
    return objs;
}
```

似乎可以安全地创建一个泛型数组。但实际上，这种方法非常危险。以下代码来自《Effective Java》的示例：

```java
import java.util.Arrays;

public class Main {
     public static void main(String[] args) {
        String[] arr = asArray("one", "two", "three");
        System.out.println(Arrays.toString(arr));
        // ClassCastException:
        String[] firstTwo = pickTwo("one", "two", "three");
        System.out.println(Arrays.toString(firstTwo));
    }

    static <K> K[] pickTwo(K k1, K k2, K k3) {
        return asArray(k1, k2);
    }

    static <T> T[] asArray(T... objs) {
        return objs;
    }
```

直接调用`asArray(T...)`似乎没有问题，但是在另一个方法中，我们返回一个泛型数组就会产生`ClassCastException`，**原因还是因为擦拭法，在`pickTwo()`方法内部，编译器无法检测`K[]`的正确类型，因此返回了`Object[]`**。

如果仔细观察，可以发现编译器对所有可变泛型参数都会发出警告，除非确认完全没有问题，才可以用`@SafeVarargs`消除警告。

### 小结

部分反射API是泛型，例如：`Class<T>`，`Constructor<T>`；

可以声明带泛型的数组，但不能直接创建带泛型的数组，必须强制转型；

**可以通过`Array.newInstance(Class<T>, int)`创建`T[]`数组，需要强制转型**；

同时使用泛型和可变参数时需要特别小心。

## 9. `java` 和 `typescript` 类型系统中的协变 和逆变

在`Java`和`TypeScript`中，"协变"（`covariance`）和"逆变"（`contravariance`）是与类型参数的子类型关系有关的概念。它们描述了泛型类型在赋值、方法参数和返回值等方面的行为。

在`Java`中：

- 协变（`covariant`）：表示一个泛型类型的子类型的关系。如果类型参数满足协变关系，那么可以将子类型的对象赋值给父类型的引用，或者将子类型的对象传递给期望父类型的参数。在`Java`中，数组是协变的，通配符类型（`? extends T`）也是协变的。
- 逆变（`contravariant`）：表示一个泛型类型的超类型的关系。如果类型参数满足逆变关系，那么可以将超类型的对象赋值给子类型的引用，或者将超类型的对象传递给期望子类型的参数。在`Java`中，通配符类型（`? super T`）是逆变的。

在`TypeScript`中：

- 协变（`covariant`）：表示一个泛型类型的子类型的关系。如果类型参数满足协变关系，那么可以将子类型的对象赋值给父类型的引用，或者将子类型的对象传递给期望父类型的参数。在`TypeScript`中，泛型类型参数默认是协变的。
- 逆变（`contravariant`）：表示一个泛型类型的超类型的关系。如果类型参数满足逆变关系，那么可以将超类型的对象赋值给子类型的引用，或者将超类型的对象传递给期望子类型的参数。在`TypeScript`中，逆变类型的处理相对较为复杂，需要使用特殊的语法进行声明。

需要注意的是，在`Java`和`TypeScript`中，泛型类型的协变和逆变关系是由编译器进行静态类型检查的。确保在使用协变和逆变时不会引发类型错误。
