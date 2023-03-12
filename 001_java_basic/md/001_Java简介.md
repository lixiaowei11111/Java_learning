# 1. java语言类型 以及1版本关系

+ **Java介于编译型语言和解释型语言之间**。编译型语言如C、C++，代码是直接编译成机器码执行，但是不同的平台（x86、ARM等）CPU的指令集不同，因此，需要编译出每一种平台的对应机器码。解释型语言如Python、Ruby没有这个问题，可以由解释器直接加载源码然后运行，代价是运行效率太低。而Java是**将代码编译成一种“字节码”，它类似于抽象的CPU指令，然后，针对不同平台编写虚拟机，不同平台的虚拟机负责加载字节码并执行，**这样就实现了“一次编写，到处运行”的效果。当然，这是针对Java开发者而言。对于虚拟机，需要为每个平台分别开发。为了保证不同平台、不同公司开发的虚拟机都能正确执行Java字节码，SUN公司制定了一系列的Java虚拟机规范。从实践的角度看，JVM的兼容性做得非常好，低版本的Java字节码完全可以正常运行在高版本的JVM上。

+ 随着Java的发展，SUN给Java又分出了三个不同版本：

  - Java SE：Standard Edition

  - Java EE：Enterprise Edition

  - Java ME：Micro Edition

![001_java_ee_se_me_diff](./assets/001_java_ee_se_me_diff.png)

+ 简单来说，Java SE就是标准版，包含标准的JVM和标准库，而Java EE是企业版，它只是在Java SE的基础上加上了大量的API和库，以便方便开发Web应用、数据库、消息服务等，Java EE的应用使用的虚拟机和Java SE完全相同。

+ Java ME就和Java SE不同，它是一个针对嵌入式设备的“瘦身版”，Java SE的标准库无法在Java ME上使用，Java ME的虚拟机也是“瘦身版”。

+ 毫无疑问，Java SE是整个Java平台的核心，而Java EE是进一步学习Web应用所必须的。我们熟悉的Spring等框架都是Java EE开源生态系统的一部分。不幸的是，Java ME从来没有真正流行起来，反而是Android开发成为了移动平台的标准之一，因此，没有特殊需求，不建议学习Java ME。

  - JDK：Java Development Kit

  - JRE：Java Runtime Environment
  - JSR规范：Java Specification Request
  - JCP组织：Java Community Process

+ 简单地说，JRE就是运行Java字节码的虚拟机。但是，如果只有Java源码，要编译成Java字节码，就需要JDK，因为JDK除了包含JRE，还提供了编译器、调试器等开发工具。

+ 二者关系如下：

```ascii
  ┌─    ┌──────────────────────────────────┐
  │     │     Compiler, debugger, etc.     │
  │     └──────────────────────────────────┘
 JDK ┌─ ┌──────────────────────────────────┐
  │  │  │                                  │
  │ JRE │      JVM + Runtime Library       │
  │  │  │                                  │
  └─ └─ └──────────────────────────────────┘
        ┌───────┐┌───────┐┌───────┐┌───────┐
        │Windows││ Linux ││ macOS ││others │
        └───────┘└───────┘└───────┘└───────┘
```

# 2. java 8下载

+ 配置环境变量,方便全局调用

# 3. bin目录

- java：这个可执行程序其实就是JVM，运行Java程序，就是启动JVM，然后让JVM执行指定的编译后的代码；
- javac：这是Java的编译器，它用于把Java源码文件（以`.java`后缀结尾）编译为Java字节码文件（以`.class`后缀结尾）；
- jar：用于把一组`.class`文件打包成一个`.jar`文件，便于发布；
- javadoc：用于从Java源码中自动提取注释并生成文档；
- jdb：Java调试器，用于开发阶段的运行调试。

# 2. Hello World

+ **文件名必须和类名相同**

  `Hello.java`

  ```java
  public class Hello {
      public static void main(String[] args){
          System.out.println("Hello world");
      }
  }
  ```

+ 在 Hello这个class 中, 定义了一个名为`main`的方法

+ Java规定，**某个类定义的`public static void main(String[] args)`是Java程序的固定入口方法**，因此，Java程序总是从`main`方法开始执行。

+ 注意到Java源码的缩进不是必须的，但是用缩进后，格式好看，很容易看出代码块的开始和结束，缩进一般是4个空格或者一个tab。

+ 最后，当我们把代码保存为文件时，文件名必须是`Hello.java`，而且文件名也要注意大小写，因为要和我们定义的类名`Hello`完全保持一致。

## 2.1 java文件的运行过程

Java源码本质上是一个文本文件，我们需要先用`javac`把`Hello.java`编译成字节码文件`Hello.class`，然后，用`java`命令执行这个字节码文件：

```ascii
┌──────────────────┐
│    Hello.java    │◀── source code
└──────────────────┘
          │ compile
          ▼
┌──────────────────┐
│   Hello.class    │◀── byte code
└──────────────────┘
          │ execute
          ▼
┌──────────────────┐
│    Run on JVM    │
└──────────────────┘
```

因此，可执行文件`javac`是**编译器**，而可执行文件`java`就是**虚拟机**。

第一步，在保存`Hello.java`的目录下执行命令`javac Hello.java`：

```
$ javac Hello.java
```

如果源代码无误，上述命令不会有任何输出，而当前目录下会产生一个`Hello.class`文件：

```
$ ls
Hello.class	Hello.java
```

第二步，执行`Hello.class`，使用命令`java Hello`：

```
$ java Hello
Hello, world!
```

注意：给虚拟机传递的参数`Hello`是我们定义的类名，虚拟机自动查找对应的class文件并执行。

有一些童鞋可能知道，直接运行`java Hello.java`也是可以的：

```
$ java Hello.java 
Hello, world!
```

这是Java 11新增的一个功能，它可以直接运行一个单文件源码！

需要注意的是，在实际项目中，单个不依赖第三方库的Java源码是非常罕见的，所以，绝大多数情况下，我们无法直接运行一个Java源码文件，原因是它需要依赖其他的库。

+ 总结

  + 一个Java源码只能定义一个`public`类型的class，并且class名称和文件名要完全一致；

  + 使用`javac`可以将`.java`源码编译成`.class`字节码；

  + 使用`java`可以运行一个已编译的Java程序，参数是类名。

# 3. Java 程序基本结构 太简单跳过

类名要求：

- 类名必须以英文字母开头，后接字母，数字和下划线的组合
- 习惯以大写字母开头

要注意遵守命名习惯，好的类命名：

- Hello
- NoteBook
- VRPlayer

不好的类命名：

- hello
- Good123
- Note_Book
- _World

注意到`public`是访问修饰符，表示该`class`是公开的。

**不写`public`，也能正确编译，但是这个类将无法从命令行执行。**



# 4.  变量和数据类型

## 4.1 基本数据类型

+ 基本数据类型是CPU可以直接进行运算的类型。Java定义了以下几种基本数据类型：

  - 整数类型：byte，short，int，long

  - 浮点数类型：float，double

  - 字符类型：char

  - 布尔类型：boolean

### 4.1.1 整型

+ 不同的数据类型占用的字节数不一样。我们看一下Java基本数据类型占用的字节数(Byte)：

  ```ascii
         ┌───┐
    byte │   │
         └───┘
         ┌───┬───┐
   short │   │   │
         └───┴───┘
         ┌───┬───┬───┬───┐
     int │   │   │   │   │
         └───┴───┴───┴───┘
         ┌───┬───┬───┬───┬───┬───┬───┬───┐
    long │   │   │   │   │   │   │   │   │
         └───┴───┴───┴───┴───┴───┴───┴───┘
         ┌───┬───┬───┬───┐
   float │   │   │   │   │
         └───┴───┴───┴───┘
         ┌───┬───┬───┬───┬───┬───┬───┬───┐
  double │   │   │   │   │   │   │   │   │
         └───┴───┴───┴───┴───┴───┴───┴───┘
         ┌───┬───┐
    char │   │   │
         └───┴───┘
  ```

  `byte`恰好就是一个字节，而`long`和`double`需要8个字节。

  - byte：``-2^7~2^7-1`               -128 ~ 127          

  - short: `-2^15~2^15-1`             -32768 ~ 32767 

  - int: `-2^31~2^31-1`                  -2147483648 ~ 2147483647

  - long:`-2^63~2^63-1`                -9223372036854775808 ~ 9223372036854775807

+ 注意:

  1. long类型 结尾需要 l(L)

  2. float结尾 需要f(F)

  3. 浮点数运算由于二进制小数原因,并不准确

     1. 整数运算在除数为`0`时会报错，而浮点数运算在除数为`0`时，不会报错，但会返回几个特殊值：
  
        1. `NaN`表示Not a Number
        2. `Infinity`表示无穷大
        3. `-Infinity`表示负无穷大
  
     2. 由于浮点数存在运算误差，所以比较两个浮点数是否相等常常会出现错误的结果。正确的比较方法是判断两个浮点数之差的绝对值是否小于一个很小的数：
  
        ```java
        // 比较x和y是否相等，先计算其差的绝对值:
        double r = Math.abs(x - y);
        // 再判断绝对值是否足够小:
        if (r < 0.00001) {
            // 可以认为相等
        } else {
            // 不相等
        }
        ```
  
  
  `DataType.java`
  
  ```java
  public class DataType {
      public static void main(String[] args){
         /**
           * 溢出处理
           * */
          int x = 2147483640;
          int y = 15;
          int sum = x + y;
          System.out.println(sum); // -2147483641
          int j=0xff22;//65314 16进制表示
          int b=0b010101;// 21 2进制表示
          long l=12131233313L;// 12131233313 long类型结尾要加l(L,不区分大小写)
          float f=2.3234242f;// 2.323424 类似于c/c++ float类型结尾需要加上f来和double做区分
  
          System.out.print(j+"\n");
          System.out.print(b+"\n");
          System.out.print(l+"\n");
          System.out.print(f+"\n");
      }
  }
  ```

### 4.1.2 Boolean类型(4 byte)

+ Java语言对布尔类型的存储并没有做规定，因为理论上存储布尔类型只需要1 bit，但是通常JVM内部会把`boolean`表示为4字节整数。

`DataType.java`

```java
public class DataType {
    public static void main(String[] args){
        boolean b1=true;
        boolean b2=false;
        System.out.print(b1+"\n");
        System.out.print(b2+"\n");
    }
}
```

### 4.1.3  char 类型

+ 注意`char`类型使用单引号`'`，且仅有一个字符，要和双引号`"`的字符串类型区分开。

+ 字符类型`char`表示一个字符。Java的`char`类型除了可表示标准的ASCII外，还可以表示<mark>一个Unicode字符</mark>：

`DataType.java`

```java
public class DataType {
    /* constructor func */
    DataType(){
        test();
    }
    public static void main(String[] args){
        /**
         * 溢出处理
         * */
        int x = 2147483640;
        int y = 15;
        int sum = x + y;
        System.out.println(sum); // -2147483641

        double f1=1.0/10;
        double f2=1-9.0/10;
        System.out.println("f1: "+f1);
        System.out.println("f2: "+f2);// 0.09999999999999998
        System.out.println("f1==f2 "+(f1==f2));// false
        int j=0xff22;//65314 16进制表示
        int b=0b010101;// 21 2进制表示
        long l=12131233313L;// 12131233313 long类型结尾要加l(L,不区分大小写)
        float f=2.3234242f;// 2.323424 类似于c/c++ float类型结尾需要加上f来和double做区分
        boolean b1=true;
        boolean b2=false;

        char c1='c';
        char c2='哈';

        final double PI=3.14;// 常量在定义时进行初始化后就不可再次赋值，再次赋值会导致编译错误。
        // PI=55;// error

        System.out.print(j+"\n");
        System.out.print(b+"\n");
        System.out.print(l+"\n");
        System.out.print(f+"\n");
        System.out.print(b1+"\n");
        System.out.print(b2+"\n");
        System.out.print(c1+"\n");
        System.out.print(c2+"\n");
        System.out.print(PI+"\n");// 3.14
    };

    public void test(){
        System.out.print(this+"this");
    };
}
```

## 4.2 引用类型

引用类型最常用的就是`String`字符串：

```
String s = "hello";
```

引用类型的变量类似于C语言的指针，它内部存储一个“地址”，指向某个对象在内存的位置

## 4.3 常量定义(final 修饰符)

```java
public class DataType {
    public static void main(String[] args){
      
        final double PI=3.14;// 常量在定义时进行初始化后就不可再次赋值，再次赋值会导致编译错误。
        // PI=55;// error
        System.out.print(PI+"\n");// 3.14
    }
}

```

## 4.4 var关键字(类似于自动类型推导 auto?)

有些时候，类型的名字太长，写起来比较麻烦。例如：

```
StringBuilder sb = new StringBuilder();
```

这个时候，如果想省略变量类型，可以使用`var`关键字：

```
var sb = new StringBuilder();
```

编译器会根据赋值语句自动推断出变量`sb`的类型是`StringBuilder`。对编译器来说，语句：

```
var sb = new StringBuilder();
```

实际上会自动变成：

```
StringBuilder sb = new StringBuilder();
```

因此，使用`var`定义变量，仅仅是少写了变量类型而已。
