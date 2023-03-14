# 1. OOP 简介

### class和instance

class是一种对象模版，它定义了如何创建实例，因此，**class本身就是一种数据类型**

而instance是对象实例，instance是根据class创建的实例，可以创建多个instance，每个instance类型相同，但各自属性可能不相同

**Java中的修饰符**

| 修饰符    | 类内部 | 同个包（package） | 子类           | 其他范围 |
| --------- | ------ | ----------------- | -------------- | -------- |
| public    | Y      | Y                 | Y              | Y        |
| protected | Y      | Y                 | Y              | N        |
| 无修饰符  | Y      | Y                 | Y或者N(见说明) | N        |
| private   | Y      | N                 | N              | N        |

## 说明：

需要特别说明“无修饰符”这个情况，子类能否访问父类中无修饰符的变量/方法，取决于子类的位置。如果子类和父类在同一个包中，那么子类可以访问父类中的无修饰符的变量/方法，否则不行。

### 定义class

在Java中，创建一个类，例如，给这个类命名为`Person`，就是定义一个`class`：

```java
public class Person {
public
    String name;
    int age;
private
    double height;
public float String;
public int oob;
```

一个`class`可以包含多个字段（`field`），字段用来描述一个类的特征。上面的`Person`类，我们定义了两个字段，一个是`String`类型的字段，命名为`name`，一个是`int`类型的字段，命名为`age`。因此，通过`class`，把一组数据汇集到一个对象上，实现了数据封装。

`public`是用来修饰字段的，它表示这个字段可以被外部访问。

### 创建实例

定义了class，只是定义了对象模版，而要根据对象模版创建出真正的对象实例，必须用new操作符。

new操作符可以创建一个实例，然后，我们需要定义一个引用类型的变量来指向这个实例：

`Main.java`

```
public class Main{
    public static void main(String[] args){
        Person p1=new Person();
    };
}


class Person {
public
    String name;
    int age;
private
    double height;
public float String;
public int oob;
};
```

上述代码创建了一个Person类型的实例，并通过变量`ming`指向它。

注意区分`Person ming`是定义`Person`类型的变量`ming`，而`new Person()`是创建`Person`实例。

有了指向这个实例的变量，我们就可以通过这个变量来操作实例。访问实例变量可以用`变量.字段`，例如：

```
ming.name = "Xiao Ming"; // 对字段name赋值
ming.age = 12; // 对字段age赋值
System.out.println(ming.name); // 访问字段name

Person hong = new Person();
hong.name = "Xiao Hong";
hong.age = 15;
```

上述两个变量分别指向两个不同的实例，它们在内存中的结构如下：

```ascii
            ┌──────────────────┐
ming ──────>│Person instance   │
            ├──────────────────┤
            │name = "Xiao Ming"│
            │age = 12          │
            └──────────────────┘
            ┌──────────────────┐
hong ──────>│Person instance   │
            ├──────────────────┤
            │name = "Xiao Hong"│
            │age = 15          │
            └──────────────────┘
```

两个`instance`拥有`class`定义的`name`和`age`字段，且各自都有一份独立的数据，互不干扰。

**一个Java源文件可以包含多个类的定义，但只能定义一个public类，且public类名必须与文件名一致。如果要定义多个public类，必须拆到多个Java源文件中**。



## 1.1 方法

+ 一个`class`可以包含多个`field`，例如，我们给`Person`类就定义了两个`field`：

```java
class Person {
public
    String name;
    int age;
};
```

但是，直接把`field`用`public`暴露给外部**可能会破坏封装性**。比如，代码可以这样写：

```
Person ming = new Person();
ming.name = "Xiao Ming";
ming.age = -99; // age设置为负数 
```

显然，直接操作`field`，容易造成逻辑混乱。为了避免外部代码直接去访问`field`，我们可以用`private`修饰`field`，拒绝外部访问：

```java
class Person {
	private String name;
    private int age;
};
```

把`field`从`public`改成`private`，外部代码不能访问这些`field`，那我们定义这些`field`有什么用？怎么才能给它赋值？怎么才能读取它的值？

所以我们需要使用方法（`method`）来让外部代码可以间接修改`field`：

```java
class Person {
    private String name;
    private int age;
    String sex;// friendly
    public String getName(){
        return this.name;
    };
    public void setName(String s){
        this.name=s;
    };
    public int getAge(){
        return  this.age;
    };
    public void setAge(int age){
        if(age<0||age>150){
            throw new IllegalArgumentException("invalid age value");
        }
        this.age=age;
    };

};
```

虽然外部代码不能直接修改`private`字段，但是，外部代码可以调用方法`setName()`和`setAge()`来间接修改`private`字段。在方法内部，我们就有机会检查参数对不对。比如，`setAge()`就会检查传入的参数，参数超出了范围，直接报错。这样，外部代码就没有任何机会把`age`设置成不合理的值。

对`setName()`方法同样可以做检查，例如，不允许传入`null`和空字符串：

```
public void setName(String name) {
    if (name == null || name.isBlank()) {
        throw new IllegalArgumentException("invalid name");
    }
    this.name = name.strip(); // 去掉首尾空格
}
```

同样，外部代码不能直接读取`private`字段，但可以通过`getName()`和`getAge()`间接获取`private`字段的值。

所以，一个类通过定义方法，就可以给外部代码暴露一些操作的接口，同时，内部自己保证逻辑一致性。

调用方法的语法是`实例变量.方法名(参数);`。一个方法调用就是一个语句，所以不要忘了在末尾加`;`。例如：`ming.setName("Xiao Ming");`。

### 定义方法

从上面的代码可以看出，定义方法的语法是：

```
修饰符 方法返回类型 方法名(方法参数列表) {
    若干方法语句;
    return 方法返回值;
}
```

方法返回值通过`return`语句实现，如果没有返回值，返回类型设置为`void`，可以省略`return`。

### private方法

有`public`方法，自然就有`private`方法。和`private`字段一样，`private`方法不允许外部调用，那我们定义`private`方法有什么用？

定义`private`方法的理由是内部方法是可以调用`private`方法的。例如：

```java
```

### this变量

在方法内部，可以使用一个隐含的变量`this`，它始终指向当前实例。因此，通过`this.field`就可以访问当前实例的字段。

如果没有命名冲突，**可以省略**`this`。例如：

```
class Person {
    private String name;

    public String getName() {
        return name; // 相当于this.name
    }
}
```

但是，如果有局部变量和字段重名，那么局部变量优先级更高，就必须加上`this`：

```
class Person {
    private String name;

    public void setName(String name) {
        this.name = name; // 前面的this不可少，少了就变成局部变量name了
    }
}
```

### 方法参数

方法可以包含0个或任意个参数。方法参数用于接收传递给方法的变量值。调用方法时，必须严格按照参数的定义一一传递。例如：

```
class Person {
    ...
    public void setNameAndAge(String name, int age) {
        ...
    }
}
```

调用这个`setNameAndAge()`方法时，必须有两个参数，且第一个参数必须为`String`，第二个参数必须为`int`：

```
Person ming = new Person();
ming.setNameAndAge("Xiao Ming"); // 编译错误：参数个数不对
ming.setNameAndAge(12, "Xiao Ming"); // 编译错误：参数类型不对
```

### 可变参数

可变参数用`type...`或者`type[]`定义，可变参数相当于数组类型：

用`type[]`定义时,需要注意的两个问题:

​	1. 调用方法需要先构造 `type[]`:

```java
class Group{
    public String[] names;
    public void setNames(String[] names){
        this.names=names;
    };

}
public class Main{
    // 一个java文件中最外部 只能由一个 public class
    public static void main(String[] args){
        // 用 type[] 定义时,需要自己构建type[],比如
        g1.setNames(new String[]{"123","234"})

    };
}
```

2. 另一个问题是，调用方可以传入`null`：

```
Group g = new Group();
g.setNames(null);
```

**而可变参数可以保证无法传入`null`，因为传入0个参数时，接收到的实际值是一个空数组而不是`null`。**

`Main.java`

```java
class Group{
    public String[] names;
    public void setNames(String... names){
        this.names=names;
    };

}
public class Main{
    // 一个java文件中最外部 只能由一个 public class
    public static void main(String[] args){
        // 可变参数, 用 `type...`表示
        Group g1=new Group();
        g1.setNames("123","hahah","aa");
        g1.setNames("dad","dad");
        g1.setNames();
        // 用 type[] 定义时,需要自己构建type[],比如
        g1.setNames(new String[]{"123","234"})

    };
}
```

### 参数绑定(函数参数是值引用)

调用方把参数传递给实例方法时，调用时传递的值会按参数位置一一绑定。

那什么是参数绑定？

**其实就是java和js中,传递引用类型时,默认传递的是地址, 而通过这个特性可以做到在方法内部,改变外部数据的值,产生副作用,c/c++函数可以使用引用值(用于节省内存),但是可以加上const防止参数被修改`const int& age`**

**Java 和Js 中的引用类型 在栈内存上都可以看成一个指针变量,用于指向堆内存**

```java
class Pet{
    private String[] name;
    public String getName(){
        return  this.name[0]+""+this.name[1];
    }
    public void setName(String... name){
        this.name=name;
    }
}
public class Main{
    // 一个java文件中最外部 只能由一个 public class
    public static void main(String[] args){
        // java
        Pet p2=new Pet();
        String s1[]={"hello","java"};
        p2.setName(s1);
        System.out.print(p2.getName());// hello java
        s1[1]="javascript";
        System.out.print(p2.getName());// hello javascript

    };
}
```





## 1.2 构造函数

**注意:**

​	java里面没有拷贝构造,用于自定义拷贝实例, 也没有析构函数(自动回收)

