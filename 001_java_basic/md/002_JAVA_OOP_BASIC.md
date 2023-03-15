# 1. OOP 简介

### class和instance

class是一种对象模版，它定义了如何创建实例，因此，**class本身就是一种数据类型**

而instance是对象实例，instance是根据class创建的实例，可以创建多个instance，每个instance类型相同，但各自属性可能不相同

**Java中的修饰符**(修饰符的继承规则看起来基本和cpp一样)

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

+ java里面没有拷贝构造,用于自定义拷贝实例, 也没有析构函数(自动回收)

+ 构造函数类似于class 实例化时的生命周期,在创建实例时执行,通常用于执行一些初始化操作

+ 和c++ 一样,Java里面也有默认构造,什么操作也不执行.但是当自己写了构造函数时,默认函数便不再被创建,构造函数也同样可以被重载

+ 在Java中，创建对象实例的时候，按照如下顺序进行初始化：

  1. **先初始化字段**，例如，`int age = 10;`表示字段初始化为`10`，`double salary;`表示字段默认初始化为`0`，`String name;`表示引用类型字段默认初始化为`null`；
  2. **执行构造方法**的代码进行初始化。

+ 没有在构造方法中初始化字段时，引用类型的字段默认是`null`，数值类型的字段用默认值，`int`类型默认值是`0`，布尔类型默认值是`false`：

  ```
  class Person {
      private String name; // 默认初始化为null
      private int age; // 默认初始化为0
  
      public Person() {
      }
  }
  ```

  也可以对字段直接进行初始化：

  ```
  class Person {
      private String name = "Unamed";
      private int age = 10;
  }
  ```

  那么问题来了：既对字段进行初始化，又在构造方法中对字段进行初始化：

  ```
  class Person {
      private String name = "Unamed";
      private int age = 10;
  
      public Person(String name, int age) {
          this.name = name;
          this.age = age;
      }
  }
  ```

示例:

`PersonExample`

```java
public class PersonExample {
    PersonExample(){};// 默认构造
    PersonExample(String name,int age){
        this.name=name;
        this.age=age;
    }
    private String name;
    private int age;
    public String getName(){
        return  name;
    }
    public int getAge(){
        return  age;
    }
}
```

`Main.java`

```java

public class Main{
    // 一个java文件中最外部 只能由一个 public class
    public static void main(String[] args){
        // class 构造函数 静态重载
        PersonExample pe1=new PersonExample("lxw",22);
        System.out.println(pe1.getAge());// 22
        System.out.println(pe1.getName());// lxw

    };
}
```

## 1.3 方法重载(太简单,跳过)

这种方法名相同，但各自的参数不同，称为方法重载（`Overload`）。

注意：方法重载的返回值类型通常都是相同的。

方法重载的目的是，功能类似的方法使用同一名字，更容易记住，因此，调用起来更简单。

其实没啥用, 不如js灵活

## 1.4 Java继承(extends)

Java使用`extends`关键字来实现继承：

在OOP的术语中，我们把`Person`称为超类（super class），父类（parent class），基类（base class），把`Student`称为子类（subclass），扩展类（extended class）。

### 继承树

```ascii
───────────┐
│  Object   │
└───────────┘
      ▲
      │
┌───────────┐
│  Person   │
└───────────┘
      ▲
      │
┌───────────┐
│  Student  │
└───────────┘
```

Java只允许一个class继承自一个类，因此，一个类有且仅有一个父类。只有`Object`特殊，它没有父类。

类似的，如果我们定义一个继承自`Person`的`Teacher`，它们的继承树关系如下：

```ascii
       ┌───────────┐
       │  Object   │
       └───────────┘
             ▲
             │
       ┌───────────┐
       │  Person   │
       └───────────┘
          ▲     ▲
          │     │
          │     │
┌───────────┐ ┌───────────┐
│  Student  │ │  Teacher  │
└───────────┘ └───────────┘
```

### protected

继承有个特点，就是子类无法访问父类的`private`字段或者`private`方法。例如，`Student`类就无法访问`Person`类的`name`和`age`字段：

这使得继承的作用被削弱了。为了让子类可以访问父类的字段，我们需要把`private`改为`protected`。用`protected`修饰的字段可以被子类访问：

### super

**`super`关键字表示父类（超类）**

1. **在Java中，任何`class`的构造方法，第一行语句必须是调用父类的构造方法。如果没有明确地调用父类的构造方法，编译器会帮我们自动加一句`super();`**

2. 如果父类没有默认的构造方法，子类就必须显式调用`super()`并给出参数以便让编译器定位到父类的一个合适的构造方法/

   当父类中,有带参数的构造函数时,子类参数必须使用super函数来携带父类构造函数时,所需要的参数

3. 子类*不会继承*任何父类的构造方法。子类默认的构造方法是编译器自动生成的，不是继承的。

4. 在子类和父类 没有声明同名属性时,在子类可以直接使用父类的属性,或者使用 `super.属性名`或者`this.属性名`,都会直接查看到父类(类似于js的原型链查找),有声明同名属性时,在子类中调用父类属性时必须加上`super.属性`来做区分

```java
public class Main {
    public static void main(String[] args){
        Student s1=new Student("local",22);
        s1.hello();
    };
}

class Person{
    public Person(String name,int age){
        this.name=name;
        this.age=age;
    };
    protected String name;// private 子类继承不了,需要使用 public或者protected
    protected int age;
    public String getName(){
        return  name;
    }
    public int getAge(){
        return age;
    }
}

class Student extends Person{
    private String name="Son";

    public Student(String name, int age) {
        super(name, age);
        // 当父类中,有带参数的构造函数时,子类参数必须使用super函数来携带父类构造函数时,所需要的参数
    }

    public void hello(){
        System.out.println("Hello"+ name);// Hello Son
        System.out.println("Hello"+super.name);// Hello null
    }
}
```

### 阻止继承(指定哪些类可以继承)

**正常情况下，只要某个class没有`final`修饰符，那么任何类都可以从该class继承。**

从Java 15开始，允许使用`sealed`修饰class，并通过`permits`明确写出能够从该class继承的子类名称。

```java
public sealed class Shape permits Rect,Circle,Triangle{
//    ...
}
```

上述`Shape`类就是一个`sealed`类，它只允许指定的3个类继承它。如果写：

```java
public final class Rect extends Shape {...}
```

是没问题的，因为`Rect`出现在`Shape`的`permits`列表中。但是，如果定义一个`Ellipse`就会报错：

```java
public final class Ellipse extends Shape {...}
// Compile error: class is not allowed to extend sealed class: Shape
```

原因是`Ellipse`并未出现在`Shape`的`permits`列表中。这种`sealed`类主要用于一些框架，防止继承被滥用。

`sealed`类在Java 15中目前是预览状态，要启用它，必须使用参数`--enable-preview`和`--source 15`。

### 向上转型

如果一个引用变量的类型是`Student`，那么它可以指向一个`Student`类型的实例：

```
Student s = new Student();
```

如果一个引用类型的变量是`Person`，那么它可以指向一个`Person`类型的实例：

```
Person p = new Person();
```

现在问题来了：如果`Student`是从`Person`继承下来的，那么，一个引用类型为`Person`的变量，能否指向`Student`类型的实例？

```
Person p = new Student(); // ???
```

这是因为`Student`继承自`Person`，因此，它拥有`Person`的全部功能。`Person`类型的变量，如果指向`Student`类型的实例，对它进行操作，是没有问题的！

这种<mark>**把一个子类类型安全地变为父类类型的赋值，被称为向上转型（upcasting）**</mark>。

向上转型实际上是把一个子类型**安全地变为更加抽象**的父类型：

```java
class Person{
    public Person(String name,int age){
        this.name=name;
        this.age=age;
    };
    protected String name;// private 子类继承不了,需要使用 public或者protected
    protected int age;
    public String getName(){
        return  name;
    }
    public int getAge(){
        return age;
    }
}

class Student extends Person{
    private String name="Son";

    public Student(String name, int age) {
        super(name, age);
        // 当父类中,有带参数的构造函数时,子类参数必须使用super函数来携带父类构造函数时,所需要的参数
    }

    public void hello(){
        System.out.println("Hello"+ name);// Hello Son
        System.out.println("Hello"+super.name);// Hello null
    }
}
public class Main {
    public static void main(String[] args){
        Student s1=new Student("local",22);
        s1.hello();
        // 向上转型
        // 因为Student继承自Person，因此，它拥有Person的全部功能。Person类型的变量，如果指向Student类型的实例，对它进行操作，是没有问题的！
        Person p1=new Student("lxw",34);
        Person p2=s1;
        Object p3=p2;
    };
}
```

### 向下转型(instanceof 检测实例类型)

和向上转型相反，如果把一个父类类型强制转型为子类类型，就是向下转型（downcasting）。

```java
// 向下转型 向下转型可能会产生 exception
        Person p5=new Student("xx",12);
        Person p6=new Person("ww",44);
        Student s5=(Student) p5;// 将p5的类型强制转换为 Student
        Student s6=(Student) p6;//error class cast exception
```

`Person`类型`p1`实际指向`Student`实例，`Person`类型变量`p2`实际指向`Person`实例。在向下转型的时候，把`p1`转型为`Student`会成功，因为`p1`确实指向`Student`实例，把`p2`转型为`Student`会失败，因为`p2`的实际类型是`Person`，不能把父类变为子类，因为子类功能比父类多，多的功能无法凭空变出来。

因此，向下转型很可能会失败。失败的时候，Java虚拟机会报`ClassCastException`。

```java
// 使用 instanceof 来检测实例的类型(class)
        System.out.println(p5 instanceof Person);// true
        System.out.println(p5 instanceof Student);// true
        System.out.println(p6 instanceof Student);// false
        System.out.println(p6 instanceof Student);// false
        System.out.println(s5 instanceof Student);// true

        Student s7=null;
        System.out.println(s7 instanceof Student);//flase
```

`instanceof`实际上判断一个变量所指向的实例是否是指定类型，或者这个类型的子类。如果一个引用变量为`null`，那么对任何`instanceof`的判断都为`false`。

利用`instanceof`，在向下转型前可以先判断：

```java
// 实现根据 条件来判断是否转换类型
        if(p6 instanceof Student){
            Student s6=(Student) p6;
        }
```

从Java 14开始，判断`instanceof`后，可以直接转型为指定变量，避免再次强制转型。

```java
 if(p6 instanceof Student s6){
            // ...
        }
```

### 区分继承和组合

**在使用继承时，我们要注意逻辑一致性。**

考察下面的`Book`类：

```
class Book {
    protected String name;
    public String getName() {...}
    public void setName(String name) {...}
}
```

这个`Book`类也有`name`字段，那么，我们能不能让`Student`继承自`Book`呢？

```
class Student extends Book {
    protected int score;
}
```

显然，从逻辑上讲，这是不合理的，`Student`不应该从`Book`继承，而应该从`Person`继承。

究其原因，是因为`Student`是`Person`的一种，它们是is关系，而`Student`并不是`Book`。实际上`Student`和`Book`的关系是has关系。

具有has关系不应该使用继承，而是使用组合，即`Student`可以持有一个`Book`实例：

```
class Student extends Person {
    protected Book book;
    protected int score;
}
```

因此，继承是is关系，组合是has关系。



## 1.5 多态

在继承关系中，子类如果定义了一个与父类方法签名完全相同的方法，被称为覆写（Override）。
