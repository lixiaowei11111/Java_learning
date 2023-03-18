# 1. OOP 简介

### class和instance

class是一种对象模版，它定义了如何创建实例，因此，**class本身就是一种数据类型**

而instance是对象实例，instance是根据class创建的实例，可以创建多个instance，每个instance类型相同，但各自属性可能不相同

**Java中的修饰符**(修饰符的继承规则看起来基本和cpp一样)

| 修饰符             | 类内部 | 同个包（package） | 子类           | 其他范围 |
| ------------------ | ------ | ----------------- | -------------- | -------- |
| public             | Y      | Y                 | Y              | Y        |
| protected          | Y      | Y                 | Y              | N        |
| 无修饰符(friendly) | Y      | Y                 | Y或者N(见说明) | N        |
| private            | Y      | N                 | N              | N        |

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



## 1.5 多态(类似于c++中的多态)

在继承关系中，子类如果定义了一个与父类方法签名完全相同的方法，被称为覆写（Override）。

```java
//例如，在Person类中，我们定义了run()方法：
class Person{
    public void run(){
        System.out.println("Person.run()");
    }
}
// 在子类Student中，覆写这个run()方法：
class Student extends Person{
    public void run(){
        System.out.println("Student.run()");
    }
};
```

+ **Override和Overload不同的是，如果方法签名不同，就是Overload，Overload方法是一个新方法；如果方法签名相同，并且返回值也相同，就是`Override`。**

+ 注意：方法名相同，方法参数相同，但方法返回值不同，也是不同的方法。**在Java程序中，出现这种情况，编译器会报错**。

总结:

1. Overrider : 子类继承 父类 方法签名相同,返回值也相同
2. Overload: 方法签名不同
3. 加上`@Override`可以让编译器帮助检查是否进行了正确的覆写。希望进行覆写，但是不小心写错了方法签名，编译器会报错。

```java
class Person{
    public void run(){
        System.out.println("Person.run()");
    }
}

class Student extends Person{
    @Override//error
    public void run(String s){
        System.out.println("Student.run()");
    }
};
```

+ 下面的代码中是 运行`Person.run`还是`Student.run()`?

  ```java
  public class Main {
      public static void main(String[] args){
          Person p=new Student();
          p.run();// Student.run()
      }
  };
  class Person{
      public void run(){
          System.out.println("Person.run()");
      }
  }
  
  class Student extends Person{
      @Override
      public void run(){
          System.out.println("Student.run()");
      }
  };

那么，一个实际类型为`Student`，引用类型为`Person`的变量，调用其`run()`方法，调用的是`Person`还是`Student`的`run()`方法？

运行一下上面的代码就可以知道，实际上调用的方法是`Student`的`run()`方法。因此可得出结论：

**Java的实例方法调用是基于运行时的实际类型的动态调用，而非变量的声明类型。**

这个非常重要的特性在面向对象编程中称之为多态。它的英文拼写非常复杂：Polymorphic。

### 多态

多态是指，针对某个类型的**方法调用**，其真正执行的方法**取决于运行时期实际类型的方法**

但是，假设我们编写这样一个方法：

```
public void runTwice(Person p) {
    p.run();
    p.run();
}
```

它传入的参数类型是`Person`，我们是无法知道传入的参数实际类型究竟是`Person`，还是`Student`，还是`Person`的其他子类，因此，也无法确定调用的是不是`Person`类定义的`run()`方法。

所以，多态的特性就是，**运行期才能动态决定调用的子类方法**。对某个类型调用某个方法，执行的实际方法可能是某个子类的覆写方法。这种不确定性的方法调用，究竟有什么作用？

```java
public class Main {
    public static void main(String[] args){
        // 给一个有普通收入、工资收入和享受国务院特殊津贴的小伙伴算税:
        Income incomes[]=new Income[]{
                new Income(3000),
                new Salary(7500),
                new StateCouncilSpecialAllowance(15000)
        };
        System.out.println("这个小伙需要交的税为: "+totalTax(incomes));
    };
    // 4. 现在，我们要编写一个报税的财务软件，对于一个人的所有收入进行报税，可以这么写：
    public static double totalTax(Income... incomes){
        double total=0;
        for (Income income :incomes) {
            total=total+income.getTax();
        }
        return  total;
    }
};
// 多态

//1. 假设我们定义一种收入，需要给它报税，那么先定义一个Income类：
class Income{
    public Income(double income){
        this.income=income;
    };
    protected double income;
    public double getTax(){
        return  this.income*0.1;// 10%的税率
    }
}

// 2. 对于工资收入，可以减去一个基数，那么我们可以从Income派生出SalaryIncome，并覆写getTax()：
class Salary extends Income{
    public Salary(double income) {
        super(income);
    }

    @Override
    public double getTax(){
        if(income<5000){
            return 0;
        }
        return (income-5000)*0.2;
    }
}
// 3. 如果你享受国务院特殊津贴，那么按照规定，可以全部免税：
class StateCouncilSpecialAllowance extends Income{
    public StateCouncilSpecialAllowance(double income) {
        super(income);
    }

    @Override
    public double getTax(){
        return 0;
    }
}
```

观察`totalTax()`方法：利用多态，`totalTax()`方法只需要和`Income`打交道，它完全不需要知道`Salary`和`StateCouncilSpecialAllowance`的存在，就可以正确计算出总的税。如果我们要新增一种稿费收入，只需要从`Income`派生，然后正确覆写`getTax()`方法就可以。把新的类型传入`totalTax()`，不需要修改任何代码。

可见，多态具有一个非常强大的功能，就是**允许添加更多类型的子类实现功能扩展，却不需要修改基于父类的代码**。

### 覆写Object方法

因为所有的`class`最终都继承自`Object`，而`Object`定义了几个重要的方法：

- `toString()`：把instance输出为`String`；
- `equals()`：判断两个instance是否逻辑相等；
- `hashCode()`：计算一个instance的哈希值。

在必要的情况下，我们可以覆写`Object`的这几个方法。例如：

```java
class Person{
    private String name;
    private int age;
    // 重写 继承自 Object的 toString func
    @Override
    public String toString(){
        return "Person.name = "+name;
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof Person){
            Person p=(Person) o;
            return  this.name.equals(p.name);
        };
        return false;
    };
    @Override
    public int hashCode(){
        return this.name.hashCode();
        // 这个哈希码的作用是确定该对象在哈希表中的索引位置。
        // hashCode () 定义在JDK的Object.java中，这就意味着Java中的任何类都包含有hashCode () 函数。
    }
}
```

### 调用super

在子类的覆写方法中，如果要调用父类的被覆写的方法，可以通过`super`来调用。例如：

```java
class Person{
    private String name;
    private int age;
    public void run(){
        System.out.println("Person.run()");
    }
}
class Student extends Person{
    @Override
    public void run(){
        // System.out.println("Student.run()");
        // 调用base类的run方法
        super.run();
    }
};
```

### final的用法

1. 不允许 子类 `override`的成员函数或者属性, 可以在方法修饰符前加上 `final`

   + `public final  void run(){}`;相当于
   + `private void run();`
   + `public final int age;`

2. 不希望有 `class`继承该 `class`,可以在class的定义前加上 final

   + `final class Person{};`

   + Java 14中也可以用`permits`关键字指定可以继承的`class`

     `public sealed class Shape permits Rect,Circle,Triangle{
     //    ...
     }`

继承可以允许子类覆写父类的方法。**如果一个父类不允许子类对它的某个方法进行覆写，**可以把该方法标记为`final`。用`final`修饰的方法不能被`Override`：

```java
final class Person{
    // 用final 修饰的class 将不能被 extends
    private String name;
    private int age;
    public final void run(){// 子类 将不能 override 这个方法
        System.out.println("Person.run()");
    }
}

class Student extends Person{
    // error 不能继承 被 final 修饰的 class
    @Override
    public void run(){//complier error base 类 使用了 final 修饰的方法不能被更改
        // System.out.println("Student.run()");
        // 调用base类的run方法
        super.run();
    }
};
```



## 1.6 abstract class | 抽象类(类似c++ 中的 抽象类和虚函数)

### 1 抽象方法

+ 如果你想设计这样一个类，该类包含一个特别的成员方法，该方法的具体实现由它的子类确定，那么你可以在父类中声明该方法为抽象方法。

+ Abstract 关键字同样可以用来声明抽象方法，抽象方法只包含一个方法名，而没有方法体。
+ 抽象方法没有定义，方法名后面直接跟一个分号，而不是花括号。

### 2 抽象类

如果一个`class`定义了方法，但没有具体执行代码，这个方法就是抽象方法，抽象方法用`abstract`修饰。

因为无法执行抽象方法，因此这个类也必须申明为抽象类（abstract class）。

使用`abstract`修饰的类就是抽象类。

**抽象类特点:**

1. abstract本身无法被实例化;

2. 抽象类可以强迫子类实现其定义的抽象方法，否则编译会报错(**继承 abstract类的 子类 必须声明 Base类中的抽象方法, 或者本身也为abstract 类**)

3. 抽象方法没有定义，方法名后面直接跟一个分号，而不是花括号(**可以理解为只是在base类中声明了这个abstract 方法,但是并未定义**)

   `public abstract void run();`



### 3. abstract method的实现(对比c++中的virtual function)

如果父类的方法本身不需要实现任何功能，仅仅是为了定义方法签名，目的是让子类去覆写它，那么，可以把父类的方法声明为抽象方法：

```
class Person {
    public abstract void run();
}
```

**把一个方法声明为`abstract`，表示它是一个抽象方法**，本身没有实现任何方法语句。因为这个抽象方法本身是无法执行的，所以，`Person`类也无法被实例化。编译器会告诉我们，无法编译`Person`类，因为它包含抽象方法。

必须把`Person`类本身也声明为`abstract`，才能正确编译它：

```
abstract class Person {
    public abstract void run();
}
```

例如，`Person`类定义了抽象方法`run()`，那么，在实现子类`Student`的时候，就必须覆写`run()`方法：

```java
public class Main {
    public static void main(String[] args){
        // Person p=new Person();// compiler error 'Person' 为 abstract；无法实例化
        Person p=new Student();
        if(p instanceof Student){
            p.run();// 'Student.run'
        }
    }
}

abstract class Person{
    public abstract  void run();
    //abstract 不能有 {}, 相当于 只是声明了这个函数, 但是未定义,需要由子类来定义
}

// 继承 abstract类的 子类 必须声明 Base类中的抽象方法, 或者本身也为abstract 类
//class Student extends Person{
    // compiler error 类 "Student" 必须声明为抽象，或为实现 "Person" 中的抽象方法 "run()"
// }

class Student extends Person{
    public void run(){
        System.out.println("\'Student.run\'");
    }
}
```



### 面向抽象编程

当我们定义了抽象类`Person`，以及具体的`Student`、`Teacher`子类的时候，我们可以通过抽象类`Person`类型去引用具体的子类的实例：

```
Person s = new Student();
Person t = new Teacher();
```

**这种引用抽象类的好处在于，我们对其进行方法调用，并不关心`Person`类型变量的具体子类型**：

```
// 不关心Person变量的具体子类型:
s.run();
t.run();
```

+ 因为其子类 必定会实现 run方法,否则会出现 compiler error

同样的代码，如果引用的是一个新的子类，我们仍然不关心具体类型：

```
// 同样不关心新的子类是如何实现run()方法的：
Person e = new Employee();
e.run();
```

**这种尽量引用高层类型，避免引用实际子类型的方式，称之为面向抽象编程。**

**面向抽象编程的本质就是：**

- 上层代码只定义规范（例如：`abstract class Person`）；
- 不需要子类就可以实现业务逻辑（正常编译）；
- 具体的业务逻辑由不同的子类实现，调用者并不关心。

**实例**: 利用abstract class 来计算需要缴纳的税款

```java
public class Main {
    public static void main(String[] args){
        // 计算税率
        Income sTax=new Salary();
        Income aTax=new ArticleIncome();
        System.out.println("一共需要缴纳税款为: "+ (sTax.getTax(6000)+aTax.getTax(10000)));
    }
}
// 用抽象类 给 一个有工资收入 和 稿费收入的小伙伴算税
// 稿费税率为 14%;
// 工资税率 超过 5000 到8000的部分为 3%
abstract class Income{
    public abstract double getTax(double come);
}

class Salary extends Income{
    @Override
    public double getTax(double income){
        if(income>5000){
            return (income-5000)*0.03;
        }
        return 0;
    };
}

class ArticleIncome extends Income{
    @Override
    public double getTax(double articleIncome){
        return  articleIncome*0.14;
    }
}
```



## 1.7 Interface | 接口

在抽象类中，抽象方法本质上是定义接口规范：即规定高层类的接口，从而保证所有子类都有相同的接口实现，这样，多态就能发挥出威力。

如果一个抽象类没有字段，所有方法全部都是抽象方法：

```
abstract class Person {
    public abstract void run();
    public abstract String getName();
}
```

就可以把该抽象类改写为接口：`interface`。

在Java中，使用`interface`可以声明一个接口：

```
interface Person {
    void run();
    String getName();
}
```

所谓`interface`，就是**比抽象类还要抽象的纯抽象接口，因为它连字段都不能有(可以有,但是必须用 `public static final `来修饰)**。**因为接口定义的所有方法默认都是`public abstract`的，所以这两个修饰符不需要写出来**（写不写效果都一样）。

### `implements`<class 继承 inertface 使用 implements>

当一个具体的`class`去实现一个`interface`时，需要使用`implements`关键字。举个例子：

```java
interface Person{
    void run();
    String getName();
}

// class 继承 interface 使用 implements(实现,简单易懂, interface 是一种比 abstract class 更 abstract的接口)
class Student implements Person {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(this.name + " run");
    }

    @Override
    public String getName() {
        return this.name;
    }
}
```

在Java中，一个类只能继承自另一个类，不能从多个类继承。但是，一个类可以实现多个`interface`，例如：

```
class Student implements Person, Hello { // 实现了两个interface
    ...
}
```

### 术语

注意区分术语：

Java的接口特指`interface`的定义，**表示一个接口类型和一组方法签名**，而编程接口**泛指接口规范，如方法签名，数据格式，网络协议等。**

抽象类和接口的对比如下：

|            | abstract class       | interface                   |
| :--------- | :------------------- | :-------------------------- |
| 继承       | 只能extends一个class | 可以implements多个interface |
| 字段       | 可以定义实例字段     | 不能定义实例字段            |
| 抽象方法   | 可以定义抽象方法     | 可以定义抽象方法            |
| 非抽象方法 | 可以定义非抽象方法   | 可以定义default方法         |



### 接口继承<interface 继承 interface 使用 extends>

一个`interface`可以继承自另一个`interface`。**`interface`继承自`interface`使用`extends`**，它相当于扩展了接口的方法。例如：

```
interface Animal{
    void run();
}

interface Dog extends Animal{
    void run();
    String say();
}
```

此时，`Person`接口继承自`Hello`接口，因此，`Person`接口现在实际上有3个抽象方法签名，其中一个来自继承的`Hello`接口。

### 继承关系

合理设计`interface`和`abstract class`的继承关系，可以充分复用代码。一般来说，公共逻辑适合放在`abstract class`中，具体逻辑放到各个子类，而接口层次代表抽象程度。可以参考Java的集合类定义的一组接口、抽象类以及具体子类的继承关系：

```ascii
┌───────────────┐
│   Iterable    │
└───────────────┘
        ▲                ┌───────────────────┐
        │                │      Object       │
┌───────────────┐        └───────────────────┘
│  Collection   │                  ▲
└───────────────┘                  │
        ▲     ▲          ┌───────────────────┐
        │     └──────────│AbstractCollection │
┌───────────────┐        └───────────────────┘
│     List      │                  ▲
└───────────────┘                  │
              ▲          ┌───────────────────┐
              └──────────│   AbstractList    │
                         └───────────────────┘
                                ▲     ▲
                                │     │
                                │     │
                     ┌────────────┐ ┌────────────┐
                     │ ArrayList  │ │ LinkedList │
                     └────────────┘ └────────────┘
```

在使用的时候，实例化的对象永远只能是某个具体的子类，但总是通过接口去引用它，因为接口比抽象类更抽象：

```java
List list = new ArrayList(); // 用List接口引用具体子类的实例
Collection coll = list; // 向上转型为Collection接口
Iterable it = coll; // 向上转型为Iterable接口
```

### default方法

在接口中，可以定义`default`方法。例如，把`Person`接口的`run()`方法改为`default`方法：

```java
public class Main {
    public static void main(String[] args){
        Person p=new Student("lxw");
        p.say();// lxwrun
    }
}
//接口定义的所有方法默认都是public abstract的，
// 所以这两个修饰符不需要写出来（写不写效果都一样）。
interface Person{
    void run();
    String getName();
    //interface的 default方法
    default void say(){
        System.out.println(getName()+"run");
    }
}

// class 继承 interface 使用 implements(实现,简单易懂, interface 是一种比 abstract class 更 abstract的接口)
class Student implements Person {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(this.name + " run");
    }

    @Override
    public String getName() {
        return this.name;
    }
}
```



用interface实现计算需要纳税的款额:

```java
interface Income{
    double getTax(double come);
}

class Salary implements Income {
    @Override
    public double getTax(double income){
        if(income>5000){
            return (income-5000)*0.03;
        }
        return 0;
    };
}

class ArticleIncome implements Income {
    @Override
    public double getTax(double articleIncome){
        return  articleIncome*0.14;
    }
}
```





## 1.8 static member | 静态成员

每一个实例成员,访问修改 static成员时, 修改的都是同一个事先定义好了的内存地址

在一个`class`中定义的字段，我们称之为实例字段。实例字段的特点是，每个实例都有独立的字段，各个实例的同名字段互不影响。

还有一种字段，是用`static`修饰的字段，称为静态字段：`static field`。

实例字段在每个实例中都有自己的一个独立“空间”，但是静态字段只有一个共享“空间”，所有实例都会共享该字段。举个例子：

```java
class Person{
    public String name;
    public int age;
    public static int number;// 定义static 属性 number

}
```

我们来看看下面的代码：

```java
public class Main {
    public static void main(String[] args){
        Person y=new Person("ybb",22);
        Person x=new Person("xbb",23);
        y.number=22;
        System.out.println(x.number);// 22
        System.out.println(Person.number);//22  static number是公共属性,每个实例访问修改读取的都是同一片内存地址;
        Person.hobby="22ybb";
        System.out.println(Person.hobby);
        x.number=988;
        System.out.println(x.number);// 988
        System.out.println(Person.number);//988  static number是公共属性,每个实例访问修改读取的都是同一片内存地址;
    }
}

class Person{
    public String name;
    public int age;
    public static int number;// 定义static 属性 number
    static String hobby;
    public Person(String name,int age){
        this.name=name;
        this.age=age;
    }

}
```



对于静态字段，无论修改哪个实例的静态字段，效果都是一样的：所有实例的静态字段都被修改了，原因是静态字段并不属于实例：

```ascii
        ┌──────────────────┐
ming ──▶│Person instance   │
        ├──────────────────┤
        │name = "Xiao Ming"│
        │age = 12          │
        │number ───────────┼──┐    ┌─────────────┐
        └──────────────────┘  │    │Person class │
                              │    ├─────────────┤
                              ├───▶│number = 99  │
        ┌──────────────────┐  │    └─────────────┘
hong ──▶│Person instance   │  │
        ├──────────────────┤  │
        │name = "Xiao Hong"│  │
        │age = 15          │  │
        │number ───────────┼──┘
        └──────────────────┘
```

虽然实例可以访问静态字段，但是它们指向的其实都是`Person class`的静态字段。所以，所有实例共享一个静态字段。

因此，不推荐用`实例变量.静态字段`去访问静态字段，因为在Java程序中，实例对象并没有静态字段。在代码中，实例对象能访问静态字段只是因为编译器可以根据实例类型自动转换为`类名.静态字段`来访问静态对象。

```java
Person.number = 99;
System.out.println(Person.number);
```

### 静态方法

有静态字段，就有静态方法。用`static`修饰的方法称为静态方法。

调用实例方法必须通过一个实例变量，而调用静态方法则不需要实例变量，通过类名就可以调用。静态方法类似其它编程语言的函数。例如：

```java
public class Main {
    public static void main(String[] args){
        Person.setNumber(00);
        System.out.println(Person.number);// 0
    }
}

class Person{
    public String name;
    public int age;
    public static int number;// 定义static 属性 number
    static String hobby;
    public Person(String name,int age){
        this.name=name;
        this.age=age;
    }
    public static void setNumber(int val){
        Person.number=val;
        // number=val;
        // this.number=val;// compiler error 无法从 static 上下文引用 'Person.this'
    }

}
```

**因为静态方法属于`class`而不属于实例，因此，静态方法内部，无法访问`this`变量，也无法访问实例字段，它只能访问静态字段**。

**通过实例变量也可以调用静态方法，但这只是编译器自动帮我们把实例改写成类名而已**。

通常情况下，通过实例变量访问静态字段和静态方法，会得到一个编译警告。

静态方法经常用于工具类。例如：

- Arrays.sort()
- Math.random()

静态方法也经常用于辅助方法。注意到Java程序的入口`main()`也是静态方法。

### <div id="interfaceStaticiFled">接口的静态字段</div>

因为`interface`是一个纯抽象类，所以它不能定义实例字段。**但是，`interface`是可以有静态字段的**，**并且静态字段必须为`public static final`**类型：

```java
interface Animal{
    public static final int MALE=1;
    //public static final int FEMALE=2;
    // 可以简写成
    int FEMALE=2;// 编译器会自动补上 public static final
}
```

实际上，因为`interface`的字段只能是`public static final`类型，所以我们可以把这些修饰符都去掉，上述代码可以简写为：

```java
interface Person {
    // 编译器会自动加上public statc final:
    int MALE = 1;
    int FEMALE = 2;
}
```

**编译器会自动把该字段变为`public static final`类型**。

### 练习

给`Person`类增加一个静态字段`count`和静态方法`getCount()`，统计实例创建的个数。

```java
class AnimalClass{
    public static int instanceCount=0;
    public AnimalClass(){
        AnimalClass.instanceCount++;
        if(AnimalClass.instanceCount>1){
            throw new RuntimeException("实例个数不能超过2");
        };
    }
}
public class Main {
    public static void main(String[] args){
        AnimalClass a1=new AnimalClass();
        AnimalClass a2=new AnimalClass();// error 实例个数不能超过两个
    }

}
```



### static中方法的运行顺序**:**

+ **静态代码块(`static {}`) \==\=>构造代码块`{}` ===> 构造器”，然后有继承关系的“先父再子”。顺序不变**

`HelloA.java`

```java
public class HelloA {

    public HelloA() {
        System.out.println("helloA");
    }

    {
        System.out.println("AAA");
    }

    static {
        System.out.println("AAA static");
    }

}
```

`HelloB.java`

```java
public class HelloB extends HelloA {

    public HelloB() {
        System.out.println("Hellob");
    }

    {
        System.out.println("BBB");
    }

    static {
        System.out.println("BBB static");
    }

    public static void main(String[] args) {
        new HelloB();
    }
}
```

打印结果为

```shell
AAA static
BBB static
AAA
helloA
BBB
Hellob
```





## 1.9 package (类似于cpp 中的 namespace 和 include机制 std::cout) 

+ package机制就是 **为了更好地组织类，Java 提供了包机制，用于区别类名的命名空间。**

### 场景: 

+ 在现实中，如果小明写了一个`Person`类，小红也写了一个`Person`类，现在，小白既想用小明的`Person`，也想用小红的`Person`，怎么办？

  如果小军写了一个`Arrays`类，恰好JDK也自带了一个`Arrays`类，如何解决类名冲突？

  在Java中，我们使用`package`来解决名字冲突。

  Java定义了一种名字空间，称之为包：`package`。一个类总是属于某个包，类名（比如`Person`）只是一个简写，真正的完整类名是`包名.类名`。

  例如：

  小明的`Person`类存放在包`ming`下面，因此，完整类名是`ming.Person`；

  小红的`Person`类存放在包`hong`下面，因此，完整类名是`hong.Person`；

  小军的`Arrays`类存放在包`mr.jun`下面，因此，完整类名是`mr.jun.Arrays`；

  JDK的`Arrays`类存放在包`java.util`下面，因此，完整类名是`java.util.Arrays`。

  小明的`Person.java`文件：

  `/ming/Person.java`

  ```java
  package ming; // 申明包名ming
  
  public class Person {
  }
  ```

  小军的`Arrays.java`文件：

  `/my/arr/Arrays.java`

  ```java
  package my.arr; // 申明包名my.arr
  
  public class Arrays {
  }
  ```

  在Java虚拟机执行的时候，JVM只看完整类名，因此，只要包名不同，类就不同。

  包可以是多层结构，用`.`隔开。例如：`java.util`。

### 要特别注意：

​	==**包没有父子关系**==。java.util和java.util.zip是不同的包，两者没有任何继承关系。

### 目录结构

没有定义包名的`class`，它使用的是默认包，非常容易引起名字冲突，因此，不推荐不写包名的做法。

我们还需要按照包结构把上面的Java文件组织起来。假设以`package_sample`作为根目录，`src`作为源码目录，那么所有文件结构就是：

```ascii
package_sample
└─ src
    │  ming
    │  └─ Person.java
    └─ my
       └─ arr
          └─ Arrays.java
```

即所有Java文件对应的目录层次要和包的层次一致。

编译后的`.class`文件也需要按照包结构存放。如果使用IDE，把编译后的`.class`文件放到`bin`目录下，那么，编译的文件结构就是：

```ascii
package_sample
└─ bin
   │  ming
   │  └─ Person.class
   └─ my
      └─ arr
         └─ Arrays.class
```

### 包作用域(friendly)

位于同一个包的类，可以访问包作用域的字段和方法。不用`public`、`protected`、`private`修饰的字段和方法就是包作用域。例如，`Person`类定义在`hello`包下面：

```java
package hello;

public class Person {
    // 包作用域:
    void hello() {
        System.out.println("Hello!");
    }
}
```

`Main`类也定义在`hello`包下面：

```java
package hello;

public class Main {
    public static void main(String[] args) {
        Person p = new Person();
        p.hello(); // 可以调用，因为Main和Person在同一个包
    }
}
```

### import

+ 类似于cpp 中的 `using std::for_each`;在该文件中使用时,就不要加上前缀来区分了
+ 类似于js中的 `import React from 'react'`,不过java把其拆成了两部; `package react;`和 `import react.React;`
+ 写法:
  1. 使用 **import** 关键字引入 指定 类：`import <packageName>.<className>;` 
  2. 用 **import** 关键字引入，使用通配符 *****：`import <packageName>.*;`
  3. `import static`的语法，它可以导入可以导入一个类的静态字段和静态方法 `import static <packageName>.`
  4. 类文件中可以包含任意数量的 import 声明。import 声明必须在包声明之后，类声明之前。

在一个`class`中，我们总会引用其他的`class`。例如，小明的`ming.Person`类，如果要引用小军的`mr.jun.Arrays`类，他有三种写法：

```java
// Person.java
package ming;

public class Person {
    public void run() {
        mr.jun.Arrays arrays = new mr.jun.Arrays();
    }
}
```

很显然，每次写完整类名比较痛苦。

因此，第二种写法是用`import`语句，导入小军的`Arrays`，然后写简单类名：

```java
package hello;

// 或者
import hello.Person;// 声明变量名
import my.arr.Arrays;//声明变量
// 同一个package内才能访问到
public class Main {
    public static void main(String[] args){
        Hello h=new Hello();
        h.hello();// Hello中的hello==>friendly
        Person p=new Person();
        Arrays a=new Arrays();

    }
}
```

在写`import`的时候，可以使用`*`，表示把这个包下面的所有`class`都导入进来（但不包括子包的`class`）：

```java
package hello;

// 或者
import hello.Person;
import my.arr.*;
// 同一个package内才能访问到
public class Main {
    public static void main(String[] args){
        Hello h=new Hello();
        h.hello();// Hello中的hello==>friendly
        Person p=new Person();
        Arrays a=new Arrays();
    }
}
```

我们一般不推荐这种写法，因为在导入了多个包后，很难看出`Arrays`类属于哪个包。

还有一种`import static`的语法，它可以导入可以导入一个类的静态字段和静态方法：

```java
package main;

// 导入System类的所有静态字段和静态方法:
import static java.lang.System.*;

public class Main {
    public static void main(String[] args) {
        // 相当于调用System.out.println(…)
        out.println("Hello, world!");
    }
}
```

`import static`很少使用。

Java编译器最终编译出的`.class`文件只使用*完整类名*，因此，在代码中，当编译器遇到一个`class`名称时：

- 如果是完整类名，就直接根据完整类名查找这个`class`；
- 如果是简单类名，按下面的顺序依次查找：
  - 查找当前`package`是否存在这个`class`；
  - 查找`import`的包是否包含这个`class`；
  - 查找`java.lang`包是否包含这个`class`。

如果按照上面的规则还无法确定类名，则编译报错。

```java
// Main.java
package test;

import java.text.Format;

public class Main {
    public static void main(String[] args) {
        java.util.List list; // ok，使用完整类名 -> java.util.List
        Format format = null; // ok，使用import的类 -> java.text.Format
        String s = "hi"; // ok，使用java.lang包的String -> java.lang.String
        System.out.println(s); // ok，使用java.lang包的System -> java.lang.System
        MessageFormat mf = null; // 编译错误：无法找到MessageFormat: MessageFormat cannot be resolved to a type
    }
}
```

因此，编写class的时候，编译器会自动帮我们做两个import动作：

- 默认自动`import`当前`package`的其他`class`；
- 默认自动`import java.lang.*`。

 注意：**自动导入的是java.lang包，但类似java.lang.reflect这些包仍需要手动导入**。

如果有多个`class`名称相同，例如，`mr.jun.Arrays`和`java.util.Arrays`，那么只能`import`其中一个，另一个必须写完整类名。

### 最佳实践

为了避免名字冲突，我们需要确定唯一的包名。推荐的做法是使用倒置的域名来确保唯一性。例如：

- org.apache
- org.apache.commons.log
- com.liaoxuefeng.sample

子包就可以根据功能自行命名。

要注意不要和`java.lang`包的类重名，即自己的类不要使用这些名字：

- String
- System
- Runtime
- ...

要注意也不要和JDK常用类重名：

- java.util.List
- java.text.Format
- java.math.BigInteger
- ...

### 编译和运行

假设我们创建了如下的目录结构：

```ascii
work
├── bin
└── src
    └── com
        └── itranswarp
            ├── sample
            │   └── Main.java
            └── world
                └── Person.java
```

其中，`bin`目录用于存放编译后的`class`文件，`src`目录按包结构存放Java源码，我们怎么一次性编译这些Java源码呢？

首先，确保当前目录是`work`目录，即存放`src`和`bin`的父目录：

```
$ ls
bin src
```

然后，编译`src`目录下的所有Java文件：

```
$ javac -d ./bin src/**/*.java
```

命令行`-d`指定输出的`class`文件存放`bin`目录，后面的参数`src/**/*.java`表示`src`目录下的所有`.java`文件，包括任意深度的子目录。

注意：**Windows不支持`**`这种搜索全部子目录的做法，所以在Windows下编译必须依次列出所有`.java`文件**：

```shell
xxx\work>  javac -d bin src/com/itranswarp/sample/Main.java src/com/itranswarp/world/Person.java
```

如果编译无误，则`javac`命令没有任何输出。可以在`bin`目录下看到如下`class`文件：

```lua
bin
└── com
    └── itranswarp
        ├── sample
        │   └── Main.class
        └── world
            └── Person.class
```

现在，我们就可以直接运行`class`文件了。根据当前目录的位置确定classpath，例如，当前目录仍为`work`，则classpath为`bin`或者`./bin`：

```shell
$ java -cp bin work.src.com.itranswarp.Sample.Main
Hello world
```

## 1.10 scope | 作用域

