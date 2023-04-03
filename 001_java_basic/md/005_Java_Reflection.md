# 1. `Class`类(注意大小写区别)

## 1. 对Java反射的个人理解(**==反射,其实就是获取class的定义==**)

+ 前置知识:

 	1. jvm是动态加载class定义的;只有代码执行时,才会加载,不会一次性把所有的class定义放到内存中
 	2. jvm在加载`class`定义后,会根据 `Class`(**注意Class是大写**)这个 类 生成一个关联 `class`定义的实例



`jvm`加载一个`class`的定义时,会使用 `Class` 生成一个对应这个`class`生成一个`Class`实例,这个实例里面包含class定义的信息(如field,className,method等);

## 2. `Class`

除了`int`等基本类型外，Java的其他类型全部都是`class`（包括`interface`）。例如：

- `String`
- `Object`
- `Runnable`
- `Exception`
- ...

仔细思考，我们可以得出结论：`class`（包括`interface`）的本质是数据类型（`Type`）。无继承关系的数据类型无法赋值：

```java
Number n = new Double(123.456); // OK
String s = new Double(123.456); // compile error!
```

而`class`是由JVM在执行过程中动态加载的。JVM在第一次读取到一种`class`类型时，将其加载进内存。

每加载一种`class`，JVM就为其创建一个`Class`类型的实例，并关联起来。注意：这里的`Class`类型是一个名叫`Class`的`class`。它长这样：

```java
public final class Class {
    private Class() {}
}
```

以`String`类为例，当JVM加载`String`类时，它首先读取`String.class`文件到内存，然后，为`String`类创建一个`Class`实例并关联起来：

```java\
Class cls = new Class(String);
```

这个`Class`实例是JVM内部创建的，如果我们查看JDK源码，可以发现`Class`类的构造方法是`private`，只有JVM能创建`Class`实例，我们自己的Java程序是无法创建`Class`实例的。

所以，JVM持有的每个`Class`实例都指向一个数据类型（`class`或`interface`）：

```ascii
┌───────────────────────────┐
│      Class Instance       │──────> String
├───────────────────────────┤
│name = "java.lang.String"  │
└───────────────────────────┘
┌───────────────────────────┐
│      Class Instance       │──────> Random
├───────────────────────────┤
│name = "java.util.Random"  │
└───────────────────────────┘
┌───────────────────────────┐
│      Class Instance       │──────> Runnable
├───────────────────────────┤
│name = "java.lang.Runnable"│
└───────────────────────────┘
```

一个`Class`实例包含了该`class`的所有完整信息：

```ascii
┌───────────────────────────┐
│      Class Instance       │──────> String
├───────────────────────────┤
│name = "java.lang.String"  │
├───────────────────────────┤
│package = "java.lang"      │
├───────────────────────────┤
│super = "java.lang.Object" │
├───────────────────────────┤
│interface = CharSequence...│
├───────────────────────────┤
│field = value[],hash,...   │
├───────────────────────────┤
│method = indexOf()...      │
└───────────────────────────┘
```

由于JVM为每个加载的`class`创建了对应的`Class`实例，并在实例中保存了该`class`的所有信息，包括类名、包名、父类、实现的接口、所有方法、字段等，因此，如果获取了某个`Class`实例，我们就可以通过这个`Class`实例获取到该实例对应的`class`的所有信息。

**这种通过`Class`实例获取`class`信息的方法称为反射（Reflection）**。

如何获取一个`class`的`Class`实例？有三个方法：

方法一：直接通过一个`class`的==静态变量`class`==获取：

```java
Class cls=String.class;
```

方法二：如果我们有一个实例变量，可以通过该==实例变量==提供的`getClass()`方法获取：

```java
String s="Hello";
Class cls=s.getClass();
```

方法三：如果知道一个`class`的完整类名，可以通过静态方法`Class.forName()`获取：

```java
Class cls=Class.forName("java.lang.String");
```

因为`Class`实例在JVM中是唯一的，所以，上述方法获取的`Class`实例是**同一个实例**。可以用`==`比较两个`Class`实例：

```java
public class Main {
    public static void main(String[] args){
        // 反射 获取的都是同一个 interface 或者 class 定义 ,所以获取Class的实例 都是相等的
        // c++ 中 structure class 是一个东西
        //java中 interface 是比 class 更抽象的 class
        Class cls1=String.class;
        Class cls2="".getClass();
        // cls1 和 cls2 都是Class 类的 同一个实例对象
        System.out.println(cls1==cls2);// true

    }
}
```

注意一下`Class`实例比较和`instanceof`的差别：

```java
// instanceof 可以匹配类型, 和 类型的子类
// == 只能精确匹配class,因为那是不同类型的Class对应的实例不一样
Integer n=new Integer(234);

// 1. instanceof
System.out.println(n instanceof Integer);// true 因为n是Integer类型
System.out.println(n instanceof Number);// true 因为n是Number类型的子类

// 2. ==
System.out.println(n.getClass()==Integer.class);// true 因为n.getClass()返回Integer.class
boolean b4=n.getClass()==Number.class;// compiler error
// java: 不可比较的类型: java.lang.Class<capture#1,
// 共 ? extends java.lang.Integer>和java.lang.Class<java.lang.Number>
System.out.println(b4);// error 因为Integer.class!=Number.class
```

用`instanceof`不但匹配指定类型，还匹配指定类型的子类。而用`==`判断`class`实例可以精确地判断数据类型，但不能作子类型比较。

通常情况下，我们应该用`instanceof`判断数据类型，因为面向抽象编程的时候，我们不关心具体的子类型。只有在需要精确判断一个类型是不是某个`class`的时候，我们才使用`==`判断`class`实例。

**因为反射的目的是为了获得某个实例的信息**。因此，当我们拿到某个`Object`实例时，我们可以通过反射获取该`Object`的`class`信息：

```java
void printObjectInfo(Object obj) {
    Class cls = obj.getClass();
}
```

要从`Class`实例获取获取的基本信息，参考下面的代码：

```java
public class Main {
    public static void main(String[] args) {
        printClassInfo("".getClass());
        /**
         * Class name: java.lang.String
         * Simple name: String
         * Package name: java.lang
         * is interface: false
         * is enum: false
         * is array: false
         * is primitive: false
         * */
        printClassInfo(Runnable.class);
        /**
         * Class name: java.lang.Runnable
         * Simple name: Runnable
         * Package name: java.lang
         * is interface: true
         * is enum: false
         * is array: false
         * is primitive: false
         */
        printClassInfo(java.time.Month.class);
        /**
         * Class name: java.time.Month
         * Simple name: Month
         * Package name: java.time
         * is interface: false
         * is enum: true
         * is array: false
         * is primitive: false
         */
        printClassInfo(String[].class);
        /**
         * Class name: [Ljava.lang.String;
         * Simple name: String[]
         * is interface: false
         * is enum: false
         * is array: true
         * is primitive: false
         */
        printClassInfo(int.class);
        /**
         * Class name: int
         * Simple name: int
         * is interface: false
         * is enum: false
         * is array: false
         * is primitive: true
         */


    }

    static void printClassInfo(Class cls) {
        System.out.println("Class name: " + cls.getName());
        System.out.println("Simple name: " + cls.getSimpleName());
        if (cls.getPackage() != null) {
            System.out.println("Package name: " + cls.getPackage().getName());
        }
        System.out.println("is interface: " + cls.isInterface());
        System.out.println("is enum: " + cls.isEnum());
        System.out.println("is array: " + cls.isArray());
        System.out.println("is primitive: " + cls.isPrimitive());
    }
}

```

注意到数组（例如`String[]`）也是一种类，而且不同于`String.class`，它的类名是`[Ljava.lang.String;`。此外，JVM为每一种基本类型如`int`也创建了`Class`实例，通过`int.class`访问。

如果获取到了一个`Class`实例，我们就可以通过该`Class`实例来创建对应类型的实例：

```java
public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        // 获取 String类型的 Class 实例
        Class cls=String.class;
        // 创建一个String 实例
        String s=(String) cls.newInstance();
        // 调用 cls.newInstance() 时,可能会抛出异常
    }
}

```



### 动态加载

**JVM在执行Java程序的时候，并不是一次性把所有用到的class全部加载到内存，而是第一次需要用到class时才加载**。例如：

```java
// Main.java
public class Main {
    public static void main(String[] args) {
        if (args.length > 0) {
            create(args[0]);
        }
    }

    static void create(String name) {
        Person p = new Person(name);
    }
}
```

当执行`Main.java`时，由于用到了`Main`，因此，JVM首先会把`Main.class`加载到内存。然而，并不会加载`Person.class`，除非程序执行到`create()`方法，JVM发现需要加载`Person`类时，才会首次加载`Person.class`。如果没有执行`create()`方法，那么`Person.class`根本就不会被加载。

==这就是JVM动态加载`class`的特性==。

动态加载`class`的特性对于Java程序非常重要。利用JVM动态加载`class`的特性，我们才能在运行期根据条件加载不同的实现类。例如，Commons Logging总是优先使用Log4j，只有当Log4j不存在时，才使用JDK的logging。利用JVM动态加载特性，大致的实现代码如下：

```java
// Commons Logging优先使用Log4j:
LogFactory factory = null;
if (isClassPresent("org.apache.logging.log4j.Logger")) {
    factory = createLog4j();
} else {
    factory = createJdkLog();
}

boolean isClassPresent(String name) {
    try {
        Class.forName(name);
        return true;
    } catch (Exception e) {
        return false;
    }
}
```

这就是为什么我们只需要把Log4j的jar包放到classpath中，Commons Logging就会自动使用Log4j的原因。



## 3. 小结

JVM为每个加载的`class`及`interface`创建了对应的`Class`实例来保存`class`及`interface`的所有信息；

获取一个`class`对应的`Class`实例后，就可以获取该`class`的所有信息；

通过Class实例获取`class`信息的方法称为反射（Reflection）；

JVM总是动态加载`class`，可以在运行期根据条件来控制加载class。



# 2. 访问字段(`java.lang.reflect.Field`上的Field 实例 来获取)



## 1. 通过`Class`实例,获取对象的`class`定义的字段信息

对任意的一个`Object`实例，只要我们获取了它的`Class`，就可以获取它的一切信息。

我们先看看如何通过`Class`实例获取字段信息。`Class`类提供了以下几个方法来获取字段：

- `Field getField(name)`：根据字段名获取某个public的field（包括父类）
- `Field getDeclaredField(name)`：根据字段名获取当前类的某个field（不包括父类）
- `Field[] getFields()`：获取所有public的field（包括父类）
- `Field[] getDeclaredFields()`：获取当前类的所有field（不包括父类）

我们来看一下示例代码：

```java
public class Main {
    public static void main(String[] args) {
        try {
            Class stuCls = Student.class;
            // 获取public字段score
            System.out.println(stuCls.getField("score"));// public int Student.score
            // 获取继承的public字段 name
            System.out.println(stuCls.getField("name"));// public java.lang.String Person.name
            // 获取private字段 grade
            System.out.println(stuCls.getDeclaredField("grade"));// private int Student.grade
            // 获取protect字段 age
            System.out.println(stuCls.getDeclaredField("age"));// protected int Student.age
        } catch (Exception e) {
            e.printStackTrace();// 打印 Exception堆栈信息
        };
    }
}

class Person {
    public String name;
}

class Student extends Person {
    public int score;
    private int grade;
    protected int age;
}

```

一个`Field`对象包含了一个字段的所有信息：

- `getName()`：返回字段名称，例如，`"name"`；
- `getType()`：返回字段类型，也是一个`Class`实例，例如，`String.class`；
- `getModifiers()`：返回字段的修饰符，它是一个`int`，不同的bit表示不同的含义。

以`String`类的`value`字段为例，它的定义是：

```java
public final class String {
    private final byte[] value;
}
```

我们用反射获取该字段的信息，代码如下：

```java
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
public class Main {
    public static void main(String[] args) {
        try {
            Field f = String.class.getDeclaredField("value");
            f.getName(); // "value"
            f.getType(); // class [B 表示byte[]类型
            int m = f.getModifiers();
            Modifier.isFinal(m); // true
            Modifier.isPublic(m); // false
            Modifier.isProtected(m); // false
            Modifier.isPrivate(m); // true
            Modifier.isStatic(m); // false
        } catch (Exception e) {
            e.printStackTrace();// 打印 Exception堆栈信息
        };
    }
}
```

## 2.获取字段值

利用反射拿到字段的一个`Field`实例只是第一步，我们还可以拿到一个实例对应的该字段的值。

例如，对于一个`Person`实例，我们可以先拿到`name`字段对应的`Field`，再获取这个实例的`name`字段的值：

```java
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Main {
    public static void main(String[] args) {
        try {
            Object t=new Teacher("ybb");
            Class c=t.getClass();
            Field  f2=c.getDeclaredField("name");
             // 正常情况下，Main类无法访问Teacher类的private字段
            f2.setAccessible(true);// 授权访问, 无论是不是 public还是private
            Object value=f2.get(t);// 用于获取实例对应的字段的值
            System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();// 打印 Exception堆栈信息
        };
    }
}
class Teacher{
    private String name;

    public Teacher(String name) {
        this.name=name;
    }
}
```

上述代码先获取`Class`实例，再获取`Field`实例，然后，用`Field.get(Object)`获取指定实例的指定字段的值。

运行代码，如果不出意外，会得到一个`IllegalAccessException`，这是因为`name`被定义为一个`private`字段，正常情况下，`Main`类无法访问`Person`类的`private`字段。要修复错误，可以将`private`改为`public`，或者，在调用`Object value = f.get(p);`前，先写一句：

```java
 // 正常情况下，Main类无法访问Teacher类的private字段
f2.setAccessible(true);// 授权访问, 无论是不是 public还是private
```

调用`Field.setAccessible(true)`的意思是，别管这个字段是不是`public`，一律允许访问。

可以试着加上上述语句，再运行代码，就可以打印出`private`字段的值。

有童鞋会问：**如果使用反射可以获取`private`字段的值，那么类的封装还有什么意义？**

答案是正常情况下，我们总是通过`p.name`来访问`Person`的`name`字段，编译器会根据`public`、`protected`和`private`决定是否允许访问字段，这样就达到了数据封装的目的。

而反射是一种非常规的用法，使用反射，首先代码非常繁琐，其次，它更多地是给工具或者底层框架来使用，目的是在不知道目标实例任何信息的情况下，获取特定字段的值。

==**此外，`setAccessible(true)`可能会失败。如果JVM运行期存在`SecurityManager`，那么它会根据规则进行检查，有可能阻止`setAccessible(true)`**==。例如，某个`SecurityManager`可能不允许对`java`和`javax`开头的`package`的类调用`setAccessible(true)`，这样可以保证JVM核心库的安全。

### 设置字段值

通过Field实例既然可以获取到指定实例的字段值，自然也可以设置字段的值。

设置字段值是通过`Field.set(Object, Object)`实现的，其中第一个`Object`参数是指定的实例，第二个`Object`参数是待修改的值。示例代码如下：

```java
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Main {
    public static void main(String[] args) {
        try {
            Teacher t = new Teacher("ybb");
            System.out.println(t.getName());// ybb
            Class c = t.getClass();
            Field f2 = c.getDeclaredField("name");
            // 正常情况下，Main类无法访问Teacher类的private字段
            f2.setAccessible(true);// 授权访问, 无论是不是 public还是private
            Object value = f2.get(t);// 1. 用于获取实例对应的字段的值
            // 2. 使用 set方法 设置字段的值
            f2.set(t,"nanami");
            System.out.println(t.getName());// nanami
            System.out.println(value);// ybb
        } catch (Exception e) {
            e.printStackTrace();// 打印 Exception堆栈信息
        }
        ;
    }
}
class Teacher {
    private String name;

    public Teacher(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

运行上述代码，打印的`name`字段从`Xiao Ming`变成了`Xiao Hong`，说明通过反射可以直接修改字段的值。

同样的，修改非`public`字段，需要首先调用`setAccessible(true)`。

## 3.小结

Java的反射API提供的`Field`类封装了字段的所有信息：

通过`Class`实例的方法可以获取`Field`实例：`getField()`，`getFields()`，`getDeclaredField()`，`getDeclaredFields()`；

通过Field实例可以获取字段信息：`getName()`，`getType()`，`getModifiers()`；

通过Field实例可以读取或设置某个对象的字段，如果存在访问限制，要首先调用`setAccessible(true)`来访问非`public`字段。

通过反射读写字段是一种非常规方法，它会破坏对象的封装。
