# 1. Java Exception

在计算机程序运行的过程中，总是会出现各种各样的错误。

有一些错误是用户造成的，比如，希望用户输入一个`int`类型的年龄，但是用户的输入是`abc`：

```
// 假设用户输入了abc：
String s = "abc";
int n = Integer.parseInt(s); // NumberFormatException!
```

程序想要读写某个文件的内容，但是用户已经把它删除了：

```
// 用户删除了该文件：
String t = readFile("C:\\abc.txt"); // FileNotFoundException!
```

还有一些错误是随机出现，并且永远不可能避免的。比如：

- 网络突然断了，连接不到远程服务器；
- 内存耗尽，程序崩溃了；
- 用户点“打印”，但根本没有打印机；
- ……

所以，一个健壮的程序必须处理各种各样的错误。

所谓错误，就是程序调用某个函数的时候，如果失败了，就表示出错。

调用方如何获知调用失败的信息？有两种方法：

方法一：约定返回错误码。

例如，处理一个文件，如果返回`0`，表示成功，返回其他整数，表示约定的错误码：

```
int code = processFile("C:\\test.txt");
if (code == 0) {
    // ok:
} else {
    // error:
    switch (code) {
    case 1:
        // file not found:
    case 2:
        // no read permission:
    default:
        // unknown error:
    }
}
```

因为使用`int`类型的错误码，想要处理就非常麻烦。这种方式常见于底层C函数(嵌入式)。

方法二：在语言层面上提供一个异常处理机制。

Java内置了一套异常处理机制，总是使用异常来表示错误。

异常是一种`class`，因此它本身带有类型信息。异常可以在任何地方抛出，但只需要在上层捕获，这样就和方法调用分离了：

```
try {
    String s = processFile(“C:\\test.txt”);
    // ok:
} catch (FileNotFoundException e) {
    // file not found:
} catch (SecurityException e) {
    // no read permission:
} catch (IOException e) {
    // io error:
} catch (Exception e) {
    // other error:
}
```

因为Java的异常是`class`，它的继承关系如下：

```ascii
                     ┌───────────┐
                     │  Object   │
                     └───────────┘
                           ▲
                           │
                     ┌───────────┐
                     │ Throwable │
                     └───────────┘
                           ▲
                 ┌─────────┴─────────┐
                 │                   │
           ┌───────────┐       ┌───────────┐
           │   Error   │       │ Exception │
           └───────────┘       └───────────┘
                 ▲                   ▲
         ┌───────┘              ┌────┴──────────┐
         │                      │               │
┌─────────────────┐    ┌─────────────────┐┌───────────┐
│OutOfMemoryError │... │RuntimeException ││IOException│...
└─────────────────┘    └─────────────────┘└───────────┘
                                ▲
                    ┌───────────┴─────────────┐
                    │                         │
         ┌─────────────────────┐ ┌─────────────────────────┐
         │NullPointerException │ │IllegalArgumentException │...
         └─────────────────────┘ └─────────────────────────┘
```

从继承关系可知：`Throwable`是异常体系的根，它继承自`Object`。**`Throwable`有两个体系：`Error`和`Exception`**，`Error`表示==严重的错误==，程序对此一般无能为力，例如：

- `OutOfMemoryError`：内存耗尽
- `NoClassDefFoundError`：无法加载某个Class
- `StackOverflowError`：栈溢出

而**`Exception`则是==运行时的错误==，它可以被捕获并处理**。

某些异常是应用程序逻辑处理的一部分，应该捕获并处理。例如：

- `NumberFormatException`：数值类型的格式错误
- `FileNotFoundException`：未找到文件
- `SocketException`：读取网络失败

还有一些异常是程序逻辑编写不对造成的，应该修复程序本身。例如：

- `NullPointerException`：对某个`null`的对象调用方法或字段
- `IndexOutOfBoundsException`：数组索引越界

+ **`Exception`又分为两大类**：

1. `RuntimeException`以及它的子类；
2. 非`RuntimeException`（包括`IOException`、`ReflectiveOperationException`等等）

Java规定：

- 必须捕获的异常，包括`Exception`及其子类，但不包括`RuntimeException`及其子类，这种类型的异常称为Checked Exception。
- 不需要捕获的异常，包括`Error`及其子类，`RuntimeException`及其子类。

 注意：编译器对RuntimeException及其子类不做强制捕获要求，不是指应用程序本身不应该捕获并处理RuntimeException。是否需要捕获，具体问题具体分析。

## 1. 捕获异常

捕获异常使用`try...catch`语句，把可能发生异常的代码放到`try {...}`中，然后使用`catch`捕获对应的`Exception`及其子类：

```java
import java.io.UnsupportedEncodingException;
public class Main {
    public static void main(String[] args){

    }
    static byte[] toGBK(String s){
        try{
            // 用指定编码转换 String 为byte
            return s.getBytes("GBK");
        }
        catch(UnsupportedEncodingException e){
            System.out.println("异常对象信息: "+ e);// 打印异常信息
            return s.getBytes();// 使用默认编码
        }
    }
}
```

如果我们不捕获`UnsupportedEncodingException`，会出现编译失败的问题：

编译器会报错，错误信息类似：unreported exception UnsupportedEncodingException; must be caught or declared to be thrown，并且准确地指出需要捕获的语句是`return s.getBytes("GBK");`。意思是说，像`UnsupportedEncodingException`这样的Checked Exception，必须被捕获。

## 2. 声明异常

这是因为`String.getBytes(String)`方法定义是：

```java
public byte[] getBytes(String charsetName) throws UnsupportedEncodingException {
    ...
}
```

==**在方法定义的时候，使用`throws Xxx`表示该方法可能抛出的异常类型。调用方在调用的时候，必须强制捕获这些异常，否则编译器会报错。**==

在`toGBK()`方法中，因为调用了`String.getBytes(String)`方法，就必须捕获`UnsupportedEncodingException`。我们也可以不捕获它，而是在方法定义处用throws表示`toGBK()`方法可能会抛出`UnsupportedEncodingException`，就可以让`toGBK()`方法通过编译器检查：

```java
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        byte[] bs=toGBK("中文");// java: 未报告的异常错误java.io.UnsupportedEncodingException; 必须对其进行捕获或声明以便抛出
        System.out.println(Arrays.toString(bs));
    }
    // 2. 声明异常 告诉编译器可能抛出异常
    static byte[] toGBK(String s) throws UnsupportedEncodingException {
        return s.getBytes("GBK");
    }
}

```

上述代码仍然会得到编译错误，但这一次，编译器提示的不是调用`return s.getBytes("GBK");`的问题，而是`byte[] bs = toGBK("中文");`。因为在`main()`方法中，调用`toGBK()`，没有捕获它声明的可能抛出的`UnsupportedEncodingException`。

修复方法是在`main()`方法中捕获异常并处理：

```java
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        byte[] bs= new byte[0];// java: 未报告的异常错误java.io.UnsupportedEncodingException; 必须对其进行捕获或声明以便抛出
        try {
            bs = toGBK("中文");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Arrays.toString(bs));// [-42, -48, -50, -60]
    }
    // 2. 声明异常 告诉编译器可能抛出异常
    static byte[] toGBK(String s) throws UnsupportedEncodingException {
        return s.getBytes("GBK");
    }
}

```

可见，只要是方法声明的Checked Exception，不在调用层捕获，也必须在更高的调用层捕获。所有未捕获的异常，最终也必须在`main()`方法中捕获，不会出现漏写`try`的情况。这是由编译器保证的。`main()`方法也是最后捕获`Exception`的机会。

如果是测试代码，上面的写法就略显麻烦。如果不想写任何`try`代码，可以直接把`main()`方法定义为`throws Exception`：

```java
public class Main {
    public static void main(String[] args) throws Exception {
        byte[] bs = toGBK("中文");
        System.out.println(Arrays.toString(bs));
    }

    static byte[] toGBK(String s) throws UnsupportedEncodingException {
        // 用指定编码转换String为byte[]:
        return s.getBytes("GBK");
    }
}
```

## 3. `printStackTrace()`  用于打印异常栈

因为`main()`方法声明了可能抛出`Exception`，也就声明了可能抛出所有的`Exception`，因此在内部就无需捕获了。代价就是一旦发生异常，程序会立刻退出。

还有一些童鞋喜欢在`toGBK()`内部“消化”异常：

```java
static byte[] toGBK(String s) {
    try {
        return s.getBytes("GBK");
    } catch (UnsupportedEncodingException e) {
        // 什么也不干
    }
    return null;
```

这种捕获后不处理的方式是非常不好的，即使真的什么也做不了，也要先把异常记录下来：

```java
 static byte[] toGBK(String s){
        try {
            return s.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();// 打印异常栈信息
        }
        return null;
    }
```



### 小结

Java使用异常来表示错误，并通过`try ... catch`捕获异常；

Java的异常是`class`，并且从`Throwable`继承；

`Error`是无需捕获的严重错误，`Exception`是应该捕获的可处理的错误；

`RuntimeException`无需强制捕获，非`RuntimeException`（Checked Exception）需强制捕获，或者用`throws`声明；

不推荐捕获了异常但不进行任何处理。



# 2. Catch Exception | 捕获异常

在Java中，凡是可能抛出异常的语句，都可以用`try ... catch`捕获。把可能发生异常的语句放在`try { ... }`中，然后使用`catch`捕获对应的`Exception`及其子类。

## 1. 多catch语句

可以使用多个`catch`语句，每个`catch`分别捕获对应的`Exception`及其子类。JVM在捕获到异常后，会从上到下匹配`catch`语句，匹配到某个`catch`后，执行`catch`代码块，然后*不再*继续匹配。

简单地说就是：**多个`catch`语句只有一个能被执行**。例如：

```java
public class Main {
    public static void main(String[] args) {
        // 多个 catch 只会匹配并且执行一个,便不再执行
        try {
            process1();
            process2();
            process3();
        } catch (IOException e) {
            System.out.println(e);
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }
}
```

**存在多个`catch`的时候，`catch`的顺序非常重要：子类必须写在前面。**例如：

```java
public class Main {
    public static void main(String[] args) {
        // 多个 catch 只会匹配并且执行一个,便不再执行
        try {
            process1();
            process2();
            process3();
        } catch (IOException e) {
            System.out.println(e);
        } catch (UnsupportedEncodingException e) { // 永远捕获不到
            System.out.println("Bad encoding");
        }
    }
}
```

对于上面的代码，`UnsupportedEncodingException`异常是永远捕获不到的，因为它是`IOException`的子类。当抛出`UnsupportedEncodingException`异常时，会被`catch (IOException e) { ... }`捕获并执行。

因此，正确的写法是把子类放到前面：

```java
public class Main {
    public static void main(String[] args) {
        // 多个 catch 只会匹配并且执行一个,便不再执行
        try {
            process1();
            process2();
            process3();
        }catch (UnsupportedEncodingException e) {
            System.out.println("Bad encoding");
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
```

## 2. finally语句

无论是否有异常发生，如果我们都希望执行一些语句，例如清理工作，怎么写？

可以把执行语句写若干遍：正常执行的放到`try`中，每个`catch`再写一遍。例如：

```java
public class Main {
    public static void main(String[] args) {
        try {
            process1();
            process2();
            process3();
        } catch (UnsupportedEncodingException e) {
            System.out.println("Bad encoding");
        } catch (IOException e) {
            System.out.println("IO error");
        } finally {
            System.out.println("END");
        }
    }
}
```

注意`finally`有几个特点：

1. `finally`语句不是必须的，可写可不写；
2. `finally`总是最后执行。

如果没有发生异常，就正常执行`try { ... }`语句块，然后执行`finally`。如果发生了异常，就中断执行`try { ... }`语句块，然后跳转执行匹配的`catch`语句块，最后执行`finally`。

可见，`finally`是用来保证一些代码必须执行的。

**某些情况下，可以没有`catch`，只使用`try ... finally`结构**。例如：

```java
void process(String file) throws IOException {
        try {
        } finally {
            System.out.println("END");
        }
    }
```

**因为方法声明了可能抛出的异常，所以可以不写`catch`。**

## 3. 捕获多种不同类型的异常(合并异常)

**如果某些异常的处理逻辑相同，但是异常本身不存在继承关系**，那么就得编写多条`catch`子句：

```java
public class Main {
    public static void main(String[] args) {
        try {
            process1();
            process2();
            process3();
        } catch (IOException e) {
            System.out.println("Bad input");
        } catch (NumberFormatException e) {
            System.out.println("Bad input");
        } catch (Exception e) {
            System.out.println("Unknown error");
        } finally {
            System.out.println("END");
        }
    }
}
```

**因为处理`IOException`和`NumberFormatException`的代码是相同的**，所以我们可以把它两用`|`合并到一起：

```java
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            process1();
            process2();
            process3();
        } catch (IOException | NumberFormatException e) {
            System.out.println("Bad Input");
        } catch (Exception e) {
            System.out.println("Unknown error");
        } finally {
            System.out.println("END");
        }
    }
}

```



# 3. Throw Exception | 抛出异常

## 1.异常的传播

**当某个方法抛出了异常时，如果当前方法没有捕获异常，异常就会被抛到上层调用方法，直到遇到某个`try ... catch`被捕获为止**：

```java
public class Main {
    public static void main(String[] args) {
        try {
            process1();
        } catch (Exception e) {
            e.printStackTrace();// 打印异常堆栈信息
        }
        ;
    }

    static void process1() {
        process2();
    }

    private static void process2() {
        Integer.parseInt(null);// 会抛出NumberFormatException
    }
}
```

通过`printStackTrace()`可以打印出方法的调用栈，类似：

```shell
java.lang.NumberFormatException: null
	at java.base/java.lang.Integer.parseInt(Integer.java:614)
	at java.base/java.lang.Integer.parseInt(Integer.java:770)
	at Main.process2(Main.java:14)
	at Main.process1(Main.java:10)
	at Main.main(Main.java:4)

```

`printStackTrace()`对于调试错误非常有用，上述信息表示：`NumberFormatException`是在`java.lang.Integer.parseInt`方法中被抛出的，从下往上看，调用层次依次是：

1. `main()`调用`process1()`；
2. `process1()`调用`process2()`；
3. `process2()`调用`Integer.parseInt(String)`；
4. `Integer.parseInt(String)`调用`Integer.parseInt(String, int)`。

查看`Integer.java`源码可知，抛出异常的方法代码如下：

```java
public static int parseInt(String s, int radix) throws NumberFormatException {
    if (s == null) {
        throw new NumberFormatException("null");
    }
    ...
}
```

并且，每层调用均给出了源代码的行号，可直接定位。



## 2. 抛出异常

当发生错误时，例如，用户输入了非法的字符，我们就可以抛出异常。

如何抛出异常？参考`Integer.parseInt()`方法，抛出异常分两步：

1. 创建某个`Exception`的实例；
2. 用`throw`语句抛出。

下面是一个例子：

```java
private static void process3(String s) {
    if (s == null) {
        NullPointerException e=new NullPointerException();
        // throw e;//
        throw new NullPointerException();
    }
}
```

如果一个方法捕获了某个异常后，又在`catch`子句中抛出新的异常，就相当于把抛出的异常类型“转换”了：

```java
    private static void process3(String s) {
        if (s == null) {
            NullPointerException e = new NullPointerException();
            // throw e;//
            throw new NullPointerException();
        }
    }

    private static void process4(String s) {
        try {
            process3(s);
        } catch (NullPointerException e) {
            // 转换错误类型
            throw new IllegalArgumentException();
        } ;
    }
```

当`process2()`抛出`NullPointerException`后，被`process1()`捕获，然后抛出`IllegalArgumentException()`。

如果在`main()`中捕获`IllegalArgumentException`，我们看看打印的异常栈：

```java
public class Main {
    public static void main(String[] args) {
        try {
            process4(null);
        } catch (Exception e) {
            e.printStackTrace();// 打印异常堆栈信息
        }
        ;
    }
    private static void process3(String s) {
        if (s == null) {
            NullPointerException e = new NullPointerException();
            // throw e;//
            throw new NullPointerException();
        }
    }

    private static void process4(String s) {
        try {
            process3(s);
        } catch (NullPointerException e) {
            // 转换错误类型
            throw new IllegalArgumentException();
        } ;
    }
}

```

打印出的异常栈类似：

```shell
java.lang.IllegalArgumentException
	at Main.process4(Main.java:32)
	at Main.main(Main.java:4)
```

这说明新的异常丢失了原始异常信息，我们已经看不到原始异常`NullPointerException`的信息了。

**为了能追踪到完整的异常栈**，**在构造异常的时候，把原始的`Exception`实例传进去，新的`Exception`就可以持有原始`Exception`信息。**对上述代码改进如下：

```java
private static void process3(String s) {
        if (s == null) {
            NullPointerException e = new NullPointerException();
            // throw e;//
            throw new NullPointerException();
        }
    }

    private static void process4(String s) {
        try {
            process3(s);
        } catch (NullPointerException e) {
            // 转换错误类型
            throw new IllegalArgumentException(e);
        } ;
    }
```

运行上述代码，打印出的异常栈类似：

```shell
java.lang.IllegalArgumentException: java.lang.NullPointerException
	at Main.process4(Main.java:35)
	at Main.main(Main.java:5)
Caused by: java.lang.NullPointerException
	at Main.process3(Main.java:24)
	at Main.process4(Main.java:30)
	... 1 more
```

注意到`Caused by: Xxx`，说明捕获的`IllegalArgumentException`并不是造成问题的根源，根源在于`NullPointerException`，是在`Main.process3()`方法抛出的。

在代码中**获取原始异常可以使用`Throwable.getCause()`方法。如果返回`null`，说明已经是“根异常”了**。

有了完整的异常栈的信息，我们才能快速定位并修复代码的问题。

==捕获到异常并再次抛出时，一定要留住原始异常，否则很难定位第一案发现场！==

如果我们在`try`或者`catch`语句块中抛出异常，`finally`语句是否会执行？例如：

```java
public class Main {
    public static void main(String[] args) {
        try {
            process5();
        } catch (Exception e) {
            e.printStackTrace();// 打印异常堆栈信息
        }
        ;
    }
    private static void process5() {
        try {
            Integer.parseInt("abc");
        } catch (Exception e) {
            System.out.println("caught");
            throw new RuntimeException(e);
        } finally {
            System.out.println("finally");
        }
    }
}

```

上述代码执行结果如下：

```shell
caught
finally
java.lang.RuntimeException: java.lang.NumberFormatException: For input string: "abc"
	at Main.process5(Main.java:46)
	at Main.main(Main.java:6)
Caused by: java.lang.NumberFormatException: For input string: "abc"
	at java.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
	at java.base/java.lang.Integer.parseInt(Integer.java:652)
	at java.base/java.lang.Integer.parseInt(Integer.java:770)
	at Main.process5(Main.java:43)
	... 1 more
```

第一行打印了`catched`，说明进入了`catch`语句块。第二行打印了`finally`，说明执行了`finally`语句块。

**因此，在`catch`中抛出异常，不会影响`finally`的执行。JVM会先执行`finally`，然后抛出异常。**



## 3. 异常屏蔽 和 `addSuppressed()`

**如果在执行`finally`语句时抛出异常，会把`catch`语句的异常屏蔽掉**.例如：

```java
public class Main {
    public static void main(String[] args) {
        try {
            // process1(); // 异常的传播
            // process4(null); // 异常的类型转换
            // process5(); catch语句中抛出的异常 不会影响finally正常的语句执行
            process6();
        } catch (Exception e) {
            e.printStackTrace();// 打印异常堆栈信息
        }
        ;
    }
    private static void process6(){
        try {
            Integer.parseInt("abc");
        } catch (Exception e) {
            System.out.println("caught");
            throw new RuntimeException(e);
        } finally {
            System.out.println("finally");
            throw new IllegalArgumentException();
        }
    }
}

```

执行上述代码，发现异常信息如下：

```java
caught
finally
java.lang.IllegalArgumentException
	at Main.process6(Main.java:61)
	at Main.main(Main.java:7)
```

这说明`finally`抛出异常后，原来在`catch`中准备抛出的异常就“消失”了，因为只能抛出一个异常。**没有被抛出的异常称为“被屏蔽”的异常（Suppressed Exception）**。

在极少数的情况下，我们需要获知所有的异常。如何保存所有的异常信息？方法是先用`origin`变量保存原始异常，然后调用`Throwable.addSuppressed()`，把原始异常添加进来，最后在`finally`抛出：

```java
private static void process7() throws Exception {
        Exception origin = null;
        try {
            Integer.parseInt("abc");
        } catch (Exception e) {
            origin= e;
            throw e;
        } finally {
            Exception e=new IllegalArgumentException();
            if(origin!=null){
                e.addSuppressed(origin);
            }
            throw e;
        }
    }
```

当`catch`和`finally`都抛出了异常时，虽然`catch`的异常被屏蔽了，但是，`finally`抛出的异常仍然包含了它：

```shell
java.lang.IllegalArgumentException
	at Main.process7(Main.java:75)
	at Main.main(Main.java:8)
	Suppressed: java.lang.NumberFormatException: For input string: "abc"
		at java.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
		at java.base/java.lang.Integer.parseInt(Integer.java:652)
		at java.base/java.lang.Integer.parseInt(Integer.java:770)
		at Main.process7(Main.java:70)
		... 1 more
```

通过`Throwable.getSuppressed()`可以获取所有的`Suppressed Exception`。

绝大多数情况下，在`finally`中不要抛出异常。因此，我们通常不需要关心`Suppressed Exception`。



# 4. 自定义异常

Java标准库定义的常用异常包括：

```lua
Exception
│
├─ RuntimeException
│  │
│  ├─ NullPointerException
│  │
│  ├─ IndexOutOfBoundsException
│  │
│  ├─ SecurityException
│  │
│  └─ IllegalArgumentException
│     │
│     └─ NumberFormatException
│
├─ IOException
│  │
│  ├─ UnsupportedCharsetException
│  │
│  ├─ FileNotFoundException
│  │
│  └─ SocketException
│
├─ ParseException
│
├─ GeneralSecurityException
│
├─ SQLException
│
└─ TimeoutException
```

当我们在代码中需要抛出异常时，尽量使用JDK已定义的异常类型。例如，参数检查不合法，应该抛出`IllegalArgumentException`：

```java
static void process1(int age){
        if(age <= 0){
            throw new IllegalArgumentException();
        }
    }
```

**在一个大型项目中，可以自定义新的异常类型，但是，保持一个合理的异常继承体系是非常重要的。**

一个常见的做法是自定义一个`BaseException`作为“根异常”，然后，派生出各种业务类型的异常。

`BaseException`需要从一个适合的`Exception`派生，通常建议从`RuntimeException`派生：

```java
public class BaseException extends RuntimeException {
}
```

其他业务类型的异常就可以从`BaseException`派生：

```java
class UserNotFoundException extends BaseException{};
class LoginFailedException extends BaseException{};
```

自定义的`BaseException`应该提供多个构造方法：

```java
class BaseException extends RuntimeException {
    public BaseException(){};
    public BaseException(String msg,Throwable cause){
        super(msg,cause);
    }
    public BaseException(String msg){
        super(msg);
    }
    public BaseException(Throwable cause){
        super(cause);
    }
};
```

上述构造方法实际上都是原样照抄`RuntimeException`。这样，抛出异常的时候，就可以选择合适的构造方法。通过IDE可以根据父类快速生成子类的构造方法。



# 5. NullPointerException

`NullPointerException`即空指针异常，俗称NPE。如果一个对象为`null`，调用其方法或访问其字段就会产生`NullPointerException`，这个异常通常是由JVM抛出的，例如：

```java
public class Main {
    public static void main(String[] args){
        String s = null;
        System.out.println(s.toLowerCase());
    }
}
```

## 1. 处理NullPointerException

如果遇到`NullPointerException`，我们应该如何处理？首先，必须明确，`NullPointerException`是一种代码逻辑错误，遇到`NullPointerException`，**遵循原则是早暴露，早修复，严禁使用`catch`来隐藏这种编码错误**：

好的编码习惯可以极大地降低`NullPointerException`的产生，例如：

成员变量在定义时初始化：

```java
public class Person {
    private String name = "";
}
```

使用空字符串`""`而不是默认的`null`可避免很多`NullPointerException`，编写业务逻辑时，用空字符串`""`表示未填写比`null`安全得多。

返回空字符串`""`、空数组而不是`null`：

```java
public String[] readLinesFromFile(String file) {
    if (getFileSize(file) == 0) {
        // 返回空数组而不是null:
        return new String[0];
    }
    ..
```

这样可以使得调用方无需检查结果是否为`null`。

如果调用方一定要根据`null`判断，比如返回`null`表示文件不存在，那么考虑返回`Optional<T>`：

```java
public Optional<String> readFromFile(String file) {
    if (!fileExist(file)) {
        return Optional.empty();
    }
    ...
}
```

这样调用方必须通过`Optional.isPresent()`判断是否有结果。

## 2. 定位NullPointerException

果产生了`NullPointerException`，例如，调用`a.b.c.x()`时产生了`NullPointerException`，原因可能是：

- `a`是`null`；
- `a.b`是`null`；
- `a.b.c`是`null`；

确定到底是哪个对象是`null`以前只能打印这样的日志：

```java
System.out.println(a);
System.out.println(a.b);
System.out.println(a.b.c);
```

从==Java 14==开始，如果产生了`NullPointerException`，JVM可以给出详细的信息告诉我们`null`对象到底是谁。我们来看例子：

```java
public class Main {
    public static void main(String[] args) {
        Person p = new Person();
        System.out.println(p.address.city.toLowerCase());
    }
}

class Person {
    String[] name = new String[2];
    Address address = new Address();
}

class Address {
    String city;
    String street;
    String zipcode;
}
```

可以在`NullPointerException`的详细信息中看到类似`... because "<local1>.address.city" is null`，意思是`city`字段为`null`，这样我们就能快速定位问题所在。

这种增强的`NullPointerException`详细信息是Java 14新增的功能，但默认是关闭的，我们可以给JVM添加一个`-XX:+ShowCodeDetailsInExceptionMessages`参数启用它：

```
java -XX:+ShowCodeDetailsInExceptionMessages Main.java
```

## 3. 小结

`NullPointerException`是Java代码常见的逻辑错误，应当早暴露，早修复；

可以启用Java 14的增强异常信息来查看`NullPointerException`的详细错误信息。



# 6.  使用断言 assert

断言（Assertion）是一种调试程序的方式。在Java中，使用`assert`关键字来实现断言。

```java
public class Main {
    public static void main(String[] args){
        double x=Math.abs(-123.45);
        assert x>=0;
        System.out.println(x);
    }
}
```

语句`assert x >= 0;`即为断言，断言条件`x >= 0`预期为`true`。**如果计算结果为`false`，则断言失败，抛出`AssertionError`**。

使用`assert`语句时，还可以添加一个可选的断言消息：

```java
assert x>=0:"x must >= 0";// 增加断言消息
```

这样，断言失败的时候，`AssertionError`会带上消息`x must >= 0`，更加便于调试。

这样，断言失败的时候，`AssertionError`会带上消息`x must >= 0`，更加便于调试。

Java断言的特点是：断言失败时会抛出`AssertionError`，导致程序结束退出。因此，断言不能用于可恢复的程序错误，只应该用于开发和测试阶段。

对于可恢复的程序错误，不应该使用断言。例如：

```java
void sort(int[] arr) {
        assert arr != null;// 不能使用断言
        if (arr == null) {
            throw new IllegalArgumentException("array cannot be null!");
        }
    }
```

## 1. 开启断言, 断言在jvm中 默认是关闭的

```java
public class Main {
    public static void main(String[] args) {
        int y=-1;
        assert y>=0:"y must >= 0";
    }
}

```



这是因为JVM默认关闭断言指令，即遇到`assert`语句就自动忽略了，不执行。

要执行`assert`语句，必须给Java虚拟机传递`-enableassertions`（可简写为`-ea`）参数启用断言。所以，上述程序必须在命令行下运行才有效果：

```shell
$ java -ea Main.java 
123.45
Exception in thread "main" java.lang.AssertionError: y must >= 0
        at Main.main(Main.java:8)
```

还可以有选择地对特定地类启用断言，命令行参数是：`-ea:com.itranswarp.sample.Main`，表示只对`com.itranswarp.sample.Main`这个类启用断言。

或者对特定地包启用断言，命令行参数是：`-ea:com.itranswarp.sample...`（注意结尾有3个`.`），表示对`com.itranswarp.sample`这个包启动断言。

实际开发中，很少使用断言。更好的方法是编写单元测试，后续我们会讲解`JUnit`的使用。

## 2. 小结

断言是一种调试方式，断言失败会抛出`AssertionError`，只能在开发和测试阶段启用断言；

对可恢复的错误不能使用断言，而应该抛出异常；

断言很少被使用，更好的方法是编写单元测试。
