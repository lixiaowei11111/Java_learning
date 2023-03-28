# 1. java 核心类



## 1. String

### 1.1 java 中String的特点:(类似于 c++ 自带库里面的 string )

1. 在Java中，`String`是一个引用类型，它本身也是一个`class`。但是，Java编译器对`String`有特殊处理，即可以直接用`"..."`来表示一个字符串
2. 实际上字符串在`String`内部是通过一个`char[]`数组表示的
3. Java字符串的一个重要特点就是字符串*不可变*。这种不可变性是通过内部的`private final char[]`字段，以及没有任何修改`char[]`的方法实现的。

```java
import static java.lang.System.*;
public class Main {
    public static void main(String[] args){
        String s1="Hello";
        String s2=new String(new char[] {'H','e','l','l','o'});
        out.println(s1+"\n"+s2);
        out.println(s1.equals(s2));// true
    }
}
```

### 1.2 String 的比较 (`equals`,`equalIgnoreCase`)

+ 当我们想要比较两个字符串是否相同时，要特别注意，我们实际上是想比较字符串的内容是否相同。必须使用`equals()`方法而不能用`==`(引用类型 使用 == 是比较的堆地址 是否相等(c++实现要利用 overload operator == ));

+ 使用 `==`,从表面上看，两个字符串用`==`和`equals()`比较都为`true`，但实际上那只是Java编译器在编译期，会自动把所有相同的字符串当作一个对象放入常量池，自然`s1`和`s2`的引用就是相同的。

  ```java
  import static java.lang.System.*;
  public class Main {
      public static void main(String[] args){
          String s1="Hello";
          String s2=new String(new char[] {'H','e','l','l','o'});
          String s3="Hello";
          out.println(s1+"\n"+s2);
          out.println(s1.equals(s2));// true
          out.println(s1==s2);// false
          out.println(s1==s3);// true
      }
  }
  ```

  结论：两个字符串比较，必须总是使用`equals()`方法。

  要忽略大小写比较，使用`equalsIgnoreCase()`方法。

+ [其余java 常见的 String方法有](https://www.runoob.com/java/java-string.html)(很多方法和 js一样):

  + length

  + toLowerCase

  + toUpperCase

  + 字符串内容比较

    + `equals` 比较字符串内容是否相同
    + `equalIgnoreCase` 比较字符串内容是否相同,不区分大小写

  + 搜索字串

    + contains 是否包含字串
      + 注意到`contains()`方法的参数是`CharSequence`而不是`String`，因为`CharSequence`是`String`实现的一个接口。
    + indexOf 
    + lastIndexOf 
    + startWith
    + endWith
    + `subString` 
      + 两个参数时为开闭区间 `"hello".substring(n,m)==>[n,m)`

  + 去除首尾空白字符

    + trim
      + `trim()`并没有改变字符串的内容，而是返回了一个新字符串。
      + 用`trim()`方法可以移除字符串首尾空白字符。空白字符包括空格，`\t`，`\r`，`\n`：
    + strip
      + `strip()`方法也可以移除字符串首尾空白字符。它和`trim()`不同的是，类似中文的空格字符`\u3000`也会被移除：
    + stripLeading
    + stripTrailing

  + 判断是否为空

    + `isEmpty` 判断字符串 是否为空(**有不见字符为false**)
    + `isBlank` 判断字符串是否为 空白(**有不可见字串时也为true**)

  + 替换子串

    + replace
    + replaceAll

  + 分割子串

    + split

  + 拼接字串使用**静态方法**`join()`，它用指定的字符串连接字符串数组

    + String.join

  + 格式化字符串(字符串提供了`formatted()`方法和`format()`静态方法，可以传入其他参数，替换占位符，然后生成新的字符串：)

    + format

    + 有几个占位符，后面就传入几个参数。参数类型要和占位符一致。我们经常用这个方法来格式化信息。常用的占位符有：

      - `%s`：显示字符串；
      - `%d`：显示整数；
      - `%x`：显示十六进制整数；
      - `%f`：显示浮点数。

      占位符还可以带格式，例如`%.2f`表示显示两位小数。如果你不确定用啥占位符，那就始终用`%s`，因为`%s`可以显示任何数据类型。要查看完整的格式化语法，请参考[JDK文档](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/util/Formatter.html#syntax)。

  ```java
  import static java.lang.System.*;
  public class Main {
      public static void main(String[] args){
          String s1="Hello";
          String s2=new String(new char[] {'H','e','l','l','o'});
          String s3="Hello";
          out.println(s1+"\n"+s2);
          out.println(s1.equals(s2));// true
          out.println(s1==s2);// false
          out.println(s1==s3);// true
          out.println(s1.length());// 5
          out.println(s1.contains(" World"));// false contains 是否包含
          out.println(s1.indexOf(" World"));// -1 不包含
          out.println(s1+":\n"+s1.lastIndexOf('o'));// 4
          out.println(s1.startsWith("He"));// true
          out.println(s1.endsWith("He"));// false
          out.println(s1.substring(1));// ello
          out.println(s1.substring(1,3));// el 开闭区间 [n,m)
          String s4=" Hel lo ";
          out.println(s4.trim());//Hel lo
          String s5=" \u3000Hel lo ";
          out.println(s5.trim());//　Hel lo trim方法不能去除中文空格
          out.println(s5.strip());//Hel lo stripe方法可以去除中文空格\u3000
          out.println(s5.stripTrailing());// 　Hel lo 只去除 尾部的
          String s6=" \u3000Hel lo \u3000";
          out.println(s6.stripLeading());//Hel lo 　 只去除头部的
          out.println("".isEmpty());// true
          out.println(" ".isEmpty());//false isEmpty会检测不可见字符
          out.println(" ".isBlank());//true isBlank不会检测不可见字符
  
          String s7="Hello";
          out.println(s7.replace("l","w"));//Hewwo
          String[] arrS=s7.split("l");//arrS[]={"He","","","o"}
          out.println(String.join("",arrS));//Heo
  
          // formatted 替换占位符
          String s8="Hi %s,you score is %d";
          out.println(String.format(s8,"Alice",80));// Hi Alice,you score is 80
      }
  }
  ```





### 1.3 类型转换 `String.valueOf()`

+ 要把**任意基本类型或引用类型转换为字符串**，可以使用静态方法`valueOf()`。这是一个重载方法，编译器会根据参数自动选择合适的方法：([对象后面的乱码为hashCode 码](https://blog.csdn.net/qq_22076345/article/details/81369281))

  ```java
  out.println(String.valueOf(123456));//123456
  out.println(String.valueOf(123.456f));//123.456
  out.println(String.valueOf(true));//true
  out.println(String.valueOf(new Object()));// java.lang.Object@39aeed2f// 后面这个乱码为 hashCode
  ```

+ 要把字符串转换为其他类型，就需要根据情况。例如，把字符串转换为`int`类型：`Integer.parseInt`

  ```java
  // String 转为 整形数字
  int n1=Integer.parseInt("123");// 123
  int n2=Integer.parseInt("123",16);// 转为16进制 291
  out.println("n1: "+n1+"\n"+"n2: "+n2);
  /*
  * n1: 123
  * n2: 291
  * */
  ```

+ 把字符串转换为`boolean`类型：`Boolean.parseBoolean`

  ```java
  // String 类型转为 Boolean 类型
  boolean b1=Boolean.parseBoolean("true");
  boolean b2=Boolean.parseBoolean("false");
  boolean b3=Boolean.parseBoolean("falsy");
  boolean b4=Boolean.parseBoolean("error");
  out.println(b1);// true
  out.println(b2);// false
  out.println(b3);// flase
  out.println(b4);// flase
  ```


+ 要特别注意，`Integer`有个`getInteger(String)`方法，它不是将字符串转换为`int`，而是把该字符串对应的系统变量转换为`Integer`：

  ```java
  out.println(Integer.getInteger("java.version"));// null
  ```

+ 转换为char[]

  + `String`和`char[]`类型可以互相转换，方法是：

  ```java
  // String 和 char 互相转换
  char sc1[]={'t','h','i','s'};
  String s9=new String(sc1);
  out.println(sc1);// this
  out.println(s9);// this
  ```

+ 如果修改了`char[]`数组，`String`并不会改变：

  ```java
  // 修改 char []; s9不会改变
  sc1[0]='a';
  out.println(s9);// this
  out.println(sc1);// ahis
  ```

这是因为通过`new String(char[])`创建新的`String`实例时，它并不会直接引用传入的`char[]`数组，而是会复制一份，所以，修改外部的`char[]`数组不会影响`String`实例内部的`char[]`数组，因为这是两个不同的数组。

**从`String`的不变性设计可以看出，如果传入的对象有可能改变，我们需要复制而不是直接引用**。(可以和c++ 一样, 利用拷贝构造 实现)





## 2. [字符编码(byte类型)](https://baijiahao.baidu.com/s?id=1746639653838456336&wfr=spider&for=pc)

在早期的计算机系统中，为了给字符编码，美国国家标准学会（American National Standard Institute：ANSI）制定了一套英文字母、数字和常用符号的编码，它占用一个字节，编码范围从`0`到`127`，最高位始终为`0`，称为`ASCII`编码。例如，字符`'A'`的编码是`0x41`，字符`'1'`的编码是`0x31`。

如果要把汉字也纳入计算机编码，很显然一个字节是不够的。`GB2312`标准使用两个字节表示一个汉字，其中第一个字节的最高位始终为`1`，以便和`ASCII`编码区分开。例如，汉字`'中'`的`GB2312`编码是`0xd6d0`。

类似的，日文有`Shift_JIS`编码，韩文有`EUC-KR`编码，这些编码因为标准不统一，同时使用，就会产生冲突。

为了统一全球所有语言的编码，全球统一码联盟发布了`Unicode`编码，它把世界上主要语言都纳入同一个编码，这样，中文、日文、韩文和其他语言就不会冲突。

`Unicode`编码需要两个或者更多字节表示，我们可以比较中英文字符在`ASCII`、`GB2312`和`Unicode`的编码：

英文字符`'A'`的`ASCII`编码和`Unicode`编码：

```ascii
         ┌────┐
ASCII:   │ 41 │
         └────┘
         ┌────┬────┐
Unicode: │ 00 │ 41 │
         └────┴────┘
```

英文字符的`Unicode`编码就是简单地在前面添加一个`00`字节。

中文字符`'中'`的`GB2312`编码和`Unicode`编码：

```ascii
         ┌────┬────┐
GB2312:  │ d6 │ d0 │
         └────┴────┘
         ┌────┬────┐
Unicode: │ 4e │ 2d │
         └────┴────┘
```

那我们经常使用的`UTF-8`又是什么编码呢？因为英文字符的`Unicode`编码高字节总是`00`，包含大量英文的文本会浪费空间，所以，出现了`UTF-8`编码，它是一种变长编码，用来把固定长度的`Unicode`编码变成1～4字节的变长编码。通过`UTF-8`编码，英文字符`'A'`的`UTF-8`编码变为`0x41`，正好和`ASCII`码一致，而中文`'中'`的`UTF-8`编码为3字节`0xe4b8ad`。

`UTF-8`编码的另一个好处是容错能力强。如果传输过程中某些字符出错，不会影响后续字符，因为`UTF-8`编码依靠高字节位来确定一个字符究竟是几个字节，它经常用来作为传输编码。

在Java中，`char`类型实际上就是两个字节的`Unicode`编码。如果我们要手动把字符串转换成其他编码，可以这样做：

```java
byte[] by1 = "Hello".getBytes();// // 按系统默认编码转换，不推荐
byte[] by2 = "Hello".getBytes("UTF-8");// // 按UTF-8编码转换
byte[] by3 = "Hello".getBytes("GBK");// // 按GBK编码转换
byte[] by4 = "Hello".getBytes(StandardCharsets.UTF_8);//  按UTF-8编码转换
```

如果要把已知编码的`byte[]`转换为`String`，可以这样做：

```java
byte[] b = ...
String s1 = new String(b, "GBK"); // 按GBK转换
String s2 = new String(b, StandardCharsets.UTF_8); // 按UTF-8转换
```

始终牢记：Java的`String`和`char`在内存中总是以Unicode编码表示。

### 延伸阅读

对于不同版本的JDK，`String`类在内存中有不同的优化方式。具体来说，早期JDK版本的`String`总是以`char[]`存储，它的定义如下：

```
public final class String {
    private final char[] value;
    private final int offset;
    private final int count;
}
```

而较新的JDK版本的`String`则以`byte[]`存储：如果`String`仅包含ASCII字符，则每个`byte`存储一个字符，否则，每两个`byte`存储一个字符，这样做的目的是为了节省内存，因为大量的长度较短的`String`通常仅包含ASCII字符：

```
public final class String {
    private final byte[] value;
    private final byte coder; // 0 = LATIN1, 1 = UTF16
```

对于使用者来说，`String`内部的优化不影响任何已有代码，因为它的`public`方法签名是不变的。

### 总结

- Java字符串`String`是不可变对象；
- 字符串操作不改变原字符串内容，而是返回新字符串；
- 常用的字符串操作：提取子串、查找、替换、大小写转换等；
- Java使用Unicode编码表示`String`和`char`；
- 转换编码就是将`String`和`byte[]`转换，需要指定编码；
- 转换为`byte[]`时，始终优先考虑`UTF-8`编码。



## 3. String builder(append,delete,ttoString)

+ Java编译器对`String`做了特殊处理，使得我们可以直接用`+`拼接字符串。(操作符重载?)

  ```java
  String s = "";
  for (int i = 0; i < 1000; i++) {
      s = s + "," + i;
  }
  ```

+ 虽然可以直接拼接字符串，但是，在循环中，每次循环都会创建新的字符串对象，然后扔掉旧的字符串。这样，绝大部分字符串都是临时对象，不但浪费内存，还会影响GC效率。

+ **为了能高效拼接字符串，Java标准库提供了`StringBuilder`，它是一个可变对象，可以==预分配==缓冲区，这样，往`StringBuilder`中新增字符时，不会创建新的临时对象**：

+ StringBuilder 还可以做链式调用, 因为 StringBuilder的append方法返回的this指针(的引用)

```java
public class Main {
    public static void main(String[] args){
        StringBuilder sb1=new StringBuilder(1024);
        for (int i=0;i<100;i++){
            // 链式调用, 因为 StringBuilder的append方法返回的this指针(的引用)
            sb1.append("\n").append(",").append(i);
        }
        String s1=sb1.toString();
        System.out.println(s1);
    }
}
```

+ 仿照 StringBuilder 写一个返回 `this`引用的方法来做链式调用

```java
public class Main {
    public static void main(String[] args){
        Counter c1=new Counter();
        c1.add(10).inc().add(10);
        System.out.println(c1.value());// 21
    }
}

// 仿照 StringBuilder 做一个链式调用的类

class Counter{
    private int sum=0;
    // 返回当前引用
    public Counter  add(int n){
        sum=sum+n;
        return this;
    }
    // 自增
    public Counter inc(){
        sum++;
        return this;
    }
    public int value(){
        return sum;// this.sum 省略了this
    }
}
```

注意：对于普通的字符串`+`操作，并不需要我们将其改写为`StringBuilder`，因为Java编译器在编译时就自动把多个连续的`+`操作编码为`StringConcatFactory`的操作。在运行期，`StringConcatFactory`会自动把字符串连接操作优化为数组复制或者`StringBuilder`操作。

你可能还听说过`StringBuffer`，这是Java早期的一个`StringBuilder`的线程安全版本，它通过同步来保证多个线程操作`StringBuffer`也是安全的，但是同步会带来执行速度的下降。

**`StringBuilder`和`StringBuffer`接口完全相同，现在完全没有必要使用`StringBuffer`。**

### 练习

请使用`StringBuilder`构造一个`INSERT`语句：

```java
public class Main {
    public static void main(String[] args){
        String[] fields = { "name", "position", "salary" };
        String table = "employee";
        String insert = buildInsertSql(table, fields);
        System.out.println(insert);
        String s = "INSERT INTO employee (name, position, salary) VALUES (?, ?, ?)";
        System.out.println(s.equals(insert) ? "测试成功" : "测试失败");
    }

    private static String buildInsertSql(String table, String[] fields) {
        System.out.println(fields.length+"==>field,length");
        StringBuilder initStr=new StringBuilder("INSERT INTO ");
        initStr.append(table+" (");
        for (int i=0;i< fields.length;i++){
            initStr.append(fields[i]);
            if(i< fields.length-1){
                initStr.append(", ");
            };
        }
        initStr.append(") VALUES (");
        for (int i=0;i< fields.length;i++){
            initStr.append("?");
            if(i< fields.length-1){
                initStr.append(", ");
            };
        }
        initStr.append(")");
        return initStr.toString();
    }
}
```

### 小结

`StringBuilder`是可变对象，用来高效拼接字符串；

`StringBuilder`可以支持链式操作，实现链式操作的关键是返回实例本身；

`StringBuffer`是`StringBuilder`的线程安全版本，现在很少使用。



## 4. StringJoiner

+ 要高效拼接字符串，应该使用`StringBuilder(delimiter,prefix,suffix)`。

很多时候，我们拼接的字符串像这样：

```java
// Hello Bob, Alice, Grace!
public class Main {
    public static void main(String[] args){
        String names[]={"Zhang","Li","Yu"};
        var sb1=new StringBuilder();//var 关键字用于自动类型推导
        sb1.append("Hello ");
        for (String name:names
             ) {
            sb1.append(name).append(", ");
        }
        // 去掉最后的 ", "
        sb1.delete(sb1.length()-2,sb1.length());
        sb1.append("!");
        System.out.println(sb1.toString());// Hello Zhang, Li, Yu!
    }
}
```

类似用分隔符拼接数组的需求很常见，所以Java标准库还提供了一个`StringJoiner`来干这个事：

```java
public class Main {
    public static void main(String[] args){
        String names[]={"Zhang","Li","Yu"};
        var sb1=new StringBuilder();//var 关键字用于自动类型推导
        StringJoiner sj1=new StringJoiner(", ","Hello ","!");// 三个参数 分隔符,开头,结尾
        sb1.append("Hello ");
        for (String name:names
             ) {
            sb1.append(name).append(", ");
            sj1.add(name);
        }

        // 去掉最后的 ", "
        sb1.delete(sb1.length()-2,sb1.length());
        sb1.append("!");
        System.out.println(sb1.toString());// Hello Zhang, Li, Yu!
        System.out.println(sj1);// Hello Zhang, Li, Yu!
    }
}
```

### String.join()

`String`还提供了一个静态方法`join()`，这个方法在内部使用了`StringJoiner`来拼接字符串，在不需要指定“开头”和“结尾”的时候，用`String.join()`更方便：

```
String[] names = {"Bob", "Alice", "Grace"};
var s = String.join(", ", names);
```

### 练习

请使用`StringJoiner`构造一个`SELECT`语句：

```java
import java.util.StringJoiner;

public class Main {
    public static void main(String[] args){
        // 练习
        String[] fields = { "name", "position", "salary" };
        String table = "employee";
        String select = buildSelectSql(table, fields);
        System.out.println(select);
        System.out.println("SELECT name, position, salary FROM employee".equals(select) ? "测试成功" : "测试失败");
    }
    private static String buildSelectSql(String table, String[] fields) {
        var sj=new StringJoiner(", ","SELECT "," FROM "+table);
        for (String field:fields
             ) {
            sj.add(field);
        };
        return sj.toString();
    }
}
```

## 5. wrapper class | 包装类型

Java的数据类型分两种：

- 基本类型：`byte`，`short`，`int`，`long`，`boolean`，`float`，`double`，`char`
- 引用类型：所有`class`和`interface`类型

引用类型可以赋值为`null`，表示空，但基本类型不能赋值为`null`：

```java
String s = null;
int n = null; // compile error!
```

那么，如何把一个基本类型视为对象（引用类型）？

比如，想要把`int`基本类型变成一个引用类型，我们可以定义一个`Integer`类，它只包含一个实例字段`int`，这样，`Integer`类就可以视为`int`的包装类（Wrapper Class）：

```java
class Integer{
    private int value;
    public Integer(int val){
        this.value=val;
    }
    public int intValue(){
        return this.value;
    }
}
```

定义好了`Integer`类，我们就可以把`int`和`Integer`互相转换：

```java
Integer n = null;
Integer n2 = new Integer(99);
int n3 = n2.intValue();
```

实际上，因为包装类型非常有用，Java核心库为每种基本类型都提供了对应的包装类型：

| 基本类型 | 对应的引用类型      |
| :------- | :------------------ |
| boolean  | java.lang.Boolean   |
| byte     | java.lang.Byte      |
| short    | java.lang.Short     |
| int      | java.lang.Integer   |
| long     | java.lang.Long      |
| float    | java.lang.Float     |
| double   | java.lang.Double    |
| char     | java.lang.Character |

我们可以直接使用，并不需要自己去定义：

```java
//通过new操作符创建Integer实例(不推荐使用,会有编译警告):
int i=100;
Integer n4=new Integer(i);
// 通过静态方法valueOf(int)创建Integer实例:
Integer n5=Integer.valueOf(i);
// 通过静态方法valueOf(String)创建Integer实例:
Integer n6=Integer.valueOf("100");
System.out.println(n6);
```

### Auto Boxing

因为`int`和`Integer`可以互相转换：

```java
int i2=99;
Integer n7=Integer.valueOf(i2);
int x=n7.intValue();
```

所以，Java编译器可以帮助我们自动在`int`和`Integer`之间转型：

```java
Integer n8=34;// 编译器自动使用Integer.valueOf(int)
int x2=n8;// 编译器自动使用Integer.intValue()
```

**这种直接把`int`变为`Integer`的赋值写法，称为自动装箱（Auto Boxing），反过来，把`Integer`变为`int`的赋值写法，称为自动拆箱（Auto Unboxing）。**

注意：**自动装箱和自动拆箱只发生在编译阶段，目的是为了少写代码**。

**装箱和拆箱会影响代码的执行效率，因为编译后的`class`代码是严格区分基本类型和引用类型的**。并且，自动拆箱执行时可能会报`NullPointerException`：

```java
Integer n9=null;
int x3=n9;// 拆箱的 'n9' 可能产生 'java.lang.NullPointerException'
```

### 不变类

所有的包装类型都是不变类。我们查看`Integer`的源码可知，它的核心代码如下：

```
public final class Integer {
    private final int value;
}
```

因此，一旦创建了`Integer`对象，该对象就是不变的。

对两个`Integer`实例进行比较要特别注意：**绝对不能用`==`比较，因为`Integer`是引用类型，必须使用`equals()`比较**：

```java
public class Main {
    public static void main(String[] args) {
        Integer x = 127;
        Integer y = 127;
        Integer m = 99999;
        Integer n = 99999;
        System.out.println("x == y: " + (x==y)); // true
        System.out.println("m == n: " + (m==n)); // false
        System.out.println("x.equals(y): " + x.equals(y)); // true
        System.out.println("m.equals(n): " + m.equals(n)); // true
    }
}
```

+ 仔细观察结果的童鞋可以发现，`==`比较，较小的两个相同的`Integer`返回`true`，较大的两个相同的`Integer`返回`false`，这是因为`Integer`是不变类，编译器把`Integer x = 127;`自动变为`Integer x = Integer.valueOf(127);`，**为了节省内存**，**`Integer.valueOf()`对于较小的数，始终返回相同的实例**，因此，`==`比较“恰好”为`true`，但我们*绝不能*因为Java标准库的`Integer`内部有缓存优化就用`==`比较，必须用`equals()`方法比较两个`Integer`。

 按照语义编程，而不是针对特定的底层实现去“优化”。

因为`Integer.valueOf()`可能始终返回同一个`Integer`实例，因此，在我们自己创建`Integer`的时候，以下两种方法：

- 方法1：`Integer n = new Integer(100);`
- 方法2：`Integer n = Integer.valueOf(100);`

==**方法2更好**==，因为方法1总是创建新的`Integer`实例，方法2把内部优化留给`Integer`的实现者去做，即使在当前版本没有优化，也有可能在下一个版本进行优化。

+ 我们把能创建“新”对象的静态方法称为**静态工厂方法**。`Integer.valueOf()`就是静态工厂方法，它尽可能地返回缓存的实例以节省内存。

+ ==创建新对象时，优先选用静态工厂方法而不是new操作符==。

如果我们考察`Byte.valueOf()`方法的源码，可以看到，标准库返回的`Byte`实例全部是缓存实例，但调用者并不关心静态工厂方法以何种方式创建新实例还是直接返回缓存的实例。

### 进制转换

`Integer`类本身还提供了大量方法，例如，最常用的静态方法`parseInt()`可以把字符串解析成一个整数：

```java
int x1 = Integer.parseInt("100"); // 100
int x2 = Integer.parseInt("100", 16); // 256,因为按16进制解析
```

`Integer`还可以把整数格式化为指定进制的字符串：

```java
public class Main {
    public static void main(String[] args) {
        // Integer 输出 指定进制, 将 字符串转换为指定进制数
        System.out.println(Integer.parseInt("20"));// 20
        System.out.println(Integer.parseInt("20",16));// 32 把20看成16进制来解析成 10进制,所以是32

        System.out.println(Integer.toString(100));// "100",表示为10进制
        System.out.println(Integer.toString(100,16));// "64",表示为16进制
        System.out.println(Integer.toHexString(100));// "64",表示为16进制
        System.out.println(Integer.toOctalString(100));// "144",表示为8进制
        System.out.println(Integer.toBinaryString(100));// "1100100",表示为2进制
    }
}
```

注意：上述方法的输出都是`String`，在计算机内存中，只用二进制表示，不存在十进制或十六进制的表示方法。`int n = 100`在内存中总是以4字节的二进制表示：

```ascii
┌────────┬────────┬────────┬────────┐
│00000000│00000000│00000000│01100100│
└────────┴────────┴────────┴────────┘
```

我们经常使用的`System.out.println(n);`是依靠核心库自动把**整数格式化为10进制输出并显示在屏幕上**，使用`Integer.toHexString(n)`则通过核心库自动把整数格式化为16进制。

这里我们注意到程序设计的一个重要原则：**数据的存储和显示要分离**。

### Java的包装类型还定义了一些有用的静态变量

+  boolean只有两个值``true/false`，其包装类型只需要引用`Boolean`提供的静态字段;(注意type `boolean` 和 包装类型`Boolean`的区别)
+ int可表示的最大/最小值:`Integer.MAX_VALUE;Integer.MIN_VALUE;`
+ long类型占用的bit和byte数量:`Long.SIZE;Long.BYTE`

```java
// boolean只有两个值true/false，其包装类型只需要引用Boolean提供的静态字段:
boolean t=Boolean.TRUE;
boolean f=Boolean.FALSE;

int max=Integer.MAX_VALUE;
int min=Integer.MIN_VALUE;
System.out.println(max);// 2147483647
System.out.println(min);//-2147483648
// int可表示的最大/最小值:
int sizeOfLong= Long.SIZE;
int byteOfLong=Long.BYTES;
// long类型占用的bit和byte数量:
System.out.println(sizeOfLong);// 64(bits)
System.out.println(byteOfLong);// 8(Bytes)
```

+ 最后，所有的**整数和浮点数的包装类型**都==继承==自`Number`，因此，可以非常方便地直接通过包装类型获取各种基本类型：

```java
Number num=new Integer(100);// 向上转型为Number
// 获取byte, int, long, float, double:
byte b1=num.byteValue();
int n1=num.intValue();
long l1=num.longValue();
float f1=num.floatValue();
double d1=num.doubleValue();
System.out.println(num);// 100
System.out.println(b1);// 100
System.out.println(l1);// 100
System.out.println(n1);// 100
System.out.println(f1);// 100.0
System.out.println(d1);// 100.0
```

### 处理无符号整型(Byte.toUnsignedInt()等静态方法)

在Java中，并**没有**无符号整型（Unsigned）的基本数据类型。`byte`、`short`、`int`和`long`都是**带符号整型，最高位是符号位**。而C语言则提供了**CPU支持的全部数据类型**，包括无符号整型。==**无符号整型和有符号整型的转换在Java中就需要借助包装类型的静态方法完成**==。

例如，byte是有符号整型，范围是`-128`~`+127`，但如果把`byte`看作无符号整型，它的范围就是`0`~`255`。我们把一个负的`byte`按无符号整型转换为`int`：

```java
byte b2=-1;
byte b3=127;
System.out.println(Byte.toUnsignedInt(b2));// 255
System.out.println(Byte.toUnsignedInt(b3));// 127
```

因为`byte`的`-1`的二进制表示是`11111111`，以无符号整型转换后的`int`就是`255`。

类似的，可以把一个`short`按unsigned转换为`int`，把一个`int`按unsigned转换为`long`。

### 小结

Java核心库提供的包装类型可以把基本类型包装为`class`；

自动装箱和自动拆箱都是在编译期完成的（JDK>=1.5）；

装箱和拆箱会影响执行效率，且拆箱时可能发生`NullPointerException`；

包装类型的比较必须使用`equals()`；

整数和浮点数的包装类型都继承自`Number`；

包装类型提供了大量实用方法。



## 6. JavaBean

在Java中，有很多`class`的定义都符合这样的规范：

- 若干`private`实例字段；
- 通过`public`方法来读写实例字段。

例如：

```java
public class Person {
    private String name;
    private int age;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```



如果读写方法符合以下这种命名规范：

```java
// 读方法:
public Type getXyz()
// 写方法:
public void setXyz(Type value)
```

那么这种`class`被称为`JavaBean`

上面的字段是`xyz`，那么读写方法名分别以`get`和`set`开头，并且后接大写字母开头的字段名`Xyz`，因此两个读写方法名分别是`getXyz()`和`setXyz()`。

`boolean`字段比较特殊，它的读方法一般命名为`isXyz()`：

```java
// 读方法:
public boolean isChild()
// 写方法:
public void setChild(boolean value)
```

我们通常把一组对应的读方法（`getter`）和写方法（`setter`）称为属性（`property`）。例如，`name`属性：

- 对应的读方法是`String getName()`
- 对应的写方法是`setName(String)`

只有`getter`的属性称为只读属性（read-only），例如，定义一个age只读属性：

- 对应的读方法是`int getAge()`
- 无对应的写方法`setAge(int)`

类似的，只有`setter`的属性称为只写属性（write-only）。

很明显，只读属性很常见，只写属性不常见。



属性只需要定义`getter`和`setter`方法，不一定需要对应的字段。例如，`child`只读属性定义如下：

```java
public class Person {
    private String name;
    private int age;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isChild() {
        return this.age <= 6;
    }
}
```

可以看出，`getter`和`setter`也是一种数据封装的方法。

### JavaBean 的作用

JavaBean主要用来传递数据，即把一组数据组合成一个JavaBean便于传输。此外，JavaBean可以方便地被IDE工具分析，生成读写属性的代码，主要用在图形界面的可视化设计中。





### 枚举JavaBean属性

要枚举一个JavaBean的所有属性，可以直接使用Java核心库提供的`Introspector`：

```java
import java.beans.*;// 引入Introspector
import static java.lang.System.*;// 引入System上的所有静态属性
public class Main {
    public static void main(String[] args) throws Exception{
        BeanInfo info=Introspector.getBeanInfo(Student.class);// Student上的class为静态属性
        for (PropertyDescriptor pd:info.getPropertyDescriptors()
             ) {
            out.println(pd.getName());
            out.println(""+pd.getReadMethod());
            out.println(""+pd.getWriteMethod()   );
        }
    }
}
class Student {
    private String name;
    private int age;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isChild() {
        return this.age <= 6;
    }
}
```

运行上述代码，可以列出所有的属性，以及对应的读写方法。注意`class`属性是从`Object`继承的`getClass()`方法带来的。

### 小结

JavaBean是一种符合命名规范的`class`，它通过`getter`和`setter`来定义属性；

属性是一种通用的叫法，并非Java语法规定；

可以利用IDE快速生成`getter`和`setter`；

使用`Introspector.getBeanInfo()`可以获取属性列表。



## 7. Enum class | 枚举类

在Java中，我们可以通过`static final`来定义常量。例如，我们希望定义周一到周日这7个常量，可以用7个不同的`int`表示：

```java
public class Main {
    public static void main(String[] args){
        int day=0;
        if(day==Weekday.SUN||day==Weekday.SAT){
            // TODO
        }
    }
}

class Weekday{
    // Java 中通过 static final 来定义常量
    public static final int SUN=0;
    public static final int MON=1;
    public static final int TUS=2;
    public static final int WED=3;
    public static final int THU=4;
    public static final int FRI=5;
    public static final int SAT=6;
}
```

使用常量的时候，可以这么引用：

```java
if (day == Weekday.SAT || day == Weekday.SUN) {
    // TODO: work at home
}
```

也可以把常量定义为字符串类型，例如，定义3种颜色的常量：

```java
public class Color {
    public static final String RED = "r";
    public static final String GREEN = "g";
    public static final String BLUE = "b";
}
```

使用常量的时候，可以这么引用：

```java
String color = ...
if (Color.RED.equals(color)) {
    // TODO:
}
```

无论是`int`常量还是`String`常量，使用这些常量来表示一组枚举值的时候，有一个严重的问题就是，编译器无法检查每个值的合理性。例如：

```java
if (weekday == 6 || weekday == 7) {
    if (tasks == Weekday.MON) {
        // TODO:
    }
}
```

上述代码编译和运行均不会报错，但存在两个问题：

- 注意到`Weekday`定义的常量范围是`0`~`6`，并不包含`7`，编译器无法检查不在枚举中的`int`值；
- 定义的常量仍可与其他变量比较，但其用途并非是枚举星期值。

### enum类型

通过`enum`定义的枚举类，和其他的`class`有什么区别？

答案是没有任何区别。`enum`定义的类型就是`class`，只不过它有以下几个特点：

- 定义的`enum`类型总是继承自`java.lang.Enum`，且**无法被继承**；
- 只能定义出`enum`的实例，而无法通过`new`操作符创建`enum`的实例；
- 定义的每个实例都是引用类型的唯一实例；
- 可以将`enum`类型用于`switch`语句。

例如，我们定义的`Color`枚举类：

```java
public enum Color {
    RED, GREEN, BLUE;
}
```

编译器编译出的`class`大概就像这样：

```java
public final class Color extends Enum { // 继承自Enum，标记为final class
    // 每个实例均为全局唯一:
    public static final Color RED = new Color();
    public static final Color GREEN = new Color();
    public static final Color BLUE = new Color();
    // private构造方法，确保外部无法调用new操作符:
    private Color() {}
}
```

所以，编译后的`enum`类和普通`class`并没有任何区别。但是我们自己无法按定义普通`class`那样来定义`enum`，必须使用`enum`关键字，这是Java语法规定的。

因为`enum`是一个`class`，每个枚举的值都是`class`实例，因此，这些实例有一些方法：

#### name()

返回常量名，例如：

```java
String s = Weekday.SUN.name(); // "SUN"
```

#### ordinal()

返回定义的常量的顺序，从0开始计数，例如：

```java
int n = Weekday.MON.ordinal(); // 1
```

改变枚举常量定义的顺序就会导致`ordinal()`返回值发生变化。例如：

```java
public enum Weekday {
    SUN, MON, TUE, WED, THU, FRI, SAT;
}
```

和

```java
public enum Weekday {
    MON, TUE, WED, THU, FRI, SAT, SUN;
}
```

的`ordinal`就是不同的。如果在代码中编写了类似`if(x.ordinal()==1)`这样的语句，就要保证`enum`的枚举顺序不能变。新增的常量必须放在最后。

有些童鞋会想，`Weekday`的枚举常量如果要和`int`转换，使用`ordinal()`不是非常方便？比如这样写：

```java
String task = Weekday.MON.ordinal() + "/ppt";
saveToFile(task);
```

但是，如果不小心修改了枚举的顺序，编译器是无法检查出这种逻辑错误的。**要编写健壮的代码，就不要依靠`ordinal()`的返回值**。因为`enum`本身是`class`，所以我们可以定义`private`的构造方法，并且，给每个枚举常量添加字段：

```java
public class Main {
    public static void main(String[] args){
        Weekday2 wk2=Weekday2.MON;
        if(wk2.dayValue==6||wk2.dayValue==0){
            System.out.println("at home");
        }else {
            System.out.println("on work");
        };
    }
}

enum Weekday2{
    SUN(0),MON(1),TUE(2),WED(3),THU(4),FRI(5),SAT(6);
    public final int dayValue;
    private Weekday2(int dayValue){
        this.dayValue=dayValue;
    }
}

```

这样就无需担心顺序的变化，新增枚举常量时，也需要指定一个`int`值。

 注意：枚举类的字段也可以是非final类型，即可以在运行期修改，但是不推荐这样做！

默认情况下，对枚举常量调用`toString()`会返回和`name()`一样的字符串。**但是，`toString()`可以被覆写，而`name()`则不行**。我们可以给`Weekday`添加`toString()`方法：

```java
public class Main {
    public static void main(String[] args) {
        Weekday2 wk2 = Weekday2.MON;
        if (wk2.dayValue == 6 || wk2.dayValue == 0) {
            System.out.println("" + wk2 + "at home");
        } else {
            System.out.println("" + wk2 + "on work");
        }
        ;
    }
}
enum Weekday2 {
    SUN(0, "星期日"), MON(1, "星期一"), TUE(2, "星期二"), WED(3, "星期三"), THU(4, "星期四"), FRI(5, "星期五"), SAT(6, "星期六");
    public final int dayValue;
    public final String chinese;

    private Weekday2(int dayValue, String chinese) {
        this.dayValue = dayValue;
        this.chinese = chinese;
    }

    @Override
    public String toString() {
        return this.chinese;
    }
}

```

**覆写`toString()`的目的是在输出时更有可读性。**

### switch

最后，枚举类可以应用在`switch`语句中。因为枚举类天生具有类型信息和有限个枚举常量，所以比`int`、`String`类型更适合用在`switch`语句中：

```java
switch (wk2) {
    case SUN:
    case MON:
    case TUE:
    case WED:
    case SAT:
    case FRI:
        System.out.println("Today is" + day + ". work at office");
        break;
    default:
        throw new RuntimeException("cannot process " + day);
}
```

加上`default`语句，可以在漏写某个枚举常量时自动报错，从而及时发现错误。

### 小结

Java使用`enum`定义枚举类型，==它被编译器编译为`final class Xxx extends Enum { … }`==；

通过`name()`获取常量定义的字符串，注意不要使用`toString()`；

通过`ordinal()`返回常量定义的顺序（无实质意义）；

可以为`enum`编写构造方法、字段和方法

`enum`的构造方法要声明为`private`，字段强烈建议声明为`final`；

`enum`适合用在`switch`语句中。



## 8. Record class | java 14

使用`String`、`Integer`等类型的时候，这些类型都是不变类，一个不变类具有以下特点：

1. 定义class时使用`final`，无法派生子类；
2. 每个字段使用`final`，保证创建实例后无法修改任何字段。

假设我们希望定义一个`Point`类，有`x`、`y`两个变量，同时它是一个不变类，可以这么写：

```java
public final class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }
}
```

为了保证不变类的比较，还需要正确覆写`equals()`和`hashCode()`方法，这样才能在集合类中正常使用。后续我们会详细讲解正确覆写`equals()`和`hashCode()`，这里演示`Point`不变类的写法目的是，这些代码写起来都非常简单，但是很繁琐。

### record

从Java 14开始，引入了新的`Record`类。我们定义`Record`类时，使用关键字`record`。把上述`Point`类改写为`Record`类，代码如下：

```java
// Record
public class Main {
    public static void main(String[] args) {
        Point p = new Point(123, 456);
        System.out.println(p.x());
        System.out.println(p.y());
        System.out.println(p);
    }
}

public record Point(int x, int y) {}
```

 Run

仔细观察`Point`的定义：

```java
public record Point(int x, int y) {}
```

把上述定义改写为class，相当于以下代码：

```java
public final class Point extends Record {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public String toString() {
        return String.format("Point[x=%s, y=%s]", x, y);
    }

    public boolean equals(Object o) {
        ...
    }
    public int hashCode() {
        ...
    }
}
```

除了用`final`修饰class以及每个字段外，编译器还自动为我们创建了构造方法，和字段名同名的方法，以及覆写`toString()`、`equals()`和`hashCode()`方法。

换句话说，使用`record`关键字，可以一行写出一个不变类。

和`enum`类似，我们自己不能直接从`Record`派生，只能通过`record`关键字由编译器实现继承。

### 构造方法

编译器默认按照`record`声明的变量顺序自动创建一个构造方法，并在方法内给字段赋值。那么问题来了，如果我们要检查参数，应该怎么办？

假设`Point`类的`x`、`y`不允许负数，我们就得给`Point`的构造方法加上检查逻辑：

```java
public record Point(int x, int y) {
    public Point {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException();
        }
    }
}
```

注意到方法`public Point {...}`被称为Compact Constructor，它的目的是让我们编写检查逻辑，编译器最终生成的构造方法如下：

```java
public final class Point extends Record {
    public Point(int x, int y) {
        // 这是我们编写的Compact Constructor:
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException();
        }
        // 这是编译器继续生成的赋值代码:
        this.x = x;
        this.y = y;
    }
    ...
}
```

作为`record`的`Point`仍然可以添加静态方法。一种常用的静态方法是`of()`方法，用来创建`Point`：

```java
public record Point(int x, int y) {
    public static Point of() {
        return new Point(0, 0);
    }
    public static Point of(int x, int y) {
        return new Point(x, y);
    }
}
```

这样我们可以写出更简洁的代码：

```java
var z = Point.of();
var p = Point.of(123, 456);
```

### 小结

从Java 14开始，提供新的`record`关键字，可以非常方便地定义Data Class：

- 使用`record`定义的是不变类；
- 可以编写Compact Constructor对参数进行验证；
- 可以定义静态方法。



## 9. BigInteger

在Java中，由**CPU原生提供的整型最大范围是64位`long`型整数**。使用`long`型整数可以直接通过CPU指令进行计算，速度非常快。

如果我们使用的整数范围超过了`long`型怎么办？这个时候，就只能用软件来模拟一个大整数。`java.math.BigInteger`就是用来表示任意大小的整数。`BigInteger`内部用一个`int[]`数组来模拟一个非常大的整数：

```java
BigInteger bi=new BigInteger("131231231321313");
System.out.println(bi.pow(3));// 2260016507372730652493204303175998390811297
```

对`BigInteger`做运算的时候，只能使用实例方法，例如，加法运算：

```java
BigInteger bi=new BigInteger("131231231321313");
System.out.println(bi.pow(3));// 2260016507372730652493204303175998390811297
BigInteger bi2=new BigInteger("1");
BigInteger bi3=bi2.add(bi);
System.out.println(bi3);// 131231231321314
```

**和`long`型整数运算比，`BigInteger`不会有范围限制**，但缺点是==速度比较慢==。

也可以把`BigInteger`**转换**成`long`型：

```java
BigInteger bi=new BigInteger("131231231321313");
System.out.println(bi.pow(3));// 2260016507372730652493204303175998390811297
BigInteger bi2=new BigInteger("1");
BigInteger bi3=bi2.add(bi);
System.out.println(bi3);// 131231231321314
System.out.println(bi3.longValue());// 转为 long型 131231231321314
System.out.println(bi3.multiply(bi3).longValueExact());//compiler error BigInteger out of long range
System.out.println(0.1+0.2);// 0.30000000000000004
```

使用`longValueExact()`方法时，如果超出了`long`型的范围，会抛出`ArithmeticException`。

`BigInteger`和`Integer`、`Long`一样，也是不可变类，并且也继承自`Number`类。因为`Number`定义了转换为基本类型的几个方法：

- 转换为`byte`：`byteValue()`
- 转换为`short`：`shortValue()`
- 转换为`int`：`intValue()`
- 转换为`long`：`longValue()`
- 转换为`float`：`floatValue()`
- 转换为`double`：`doubleValue()`

因此，通过上述方法，可以把`BigInteger`转换成基本类型。如果`BigInteger`表示的范围超过了基本类型的范围，转换时将丢失高位信息，即结果不一定是准确的。**如果需要==准确==地转换成基本类型，可以使用`intValueExact()`、`longValueExact()`等方法，在转换时如果超出范围，将直接抛出`ArithmeticException`异常**。

如果`BigInteger`的值甚至超过了`float`的最大范围（3.4x1038），那么返回的float是什么呢？

```java
BigInteger n = new BigInteger("999999").pow(99);
float f = n.floatValue();
System.out.println(f);// Infinity
```



## 10. BigDecimal(不重要,暂时跳过)

1. `scale()`小数点位数
   1. 可以对一个`BigDecimal`设置它的`scale`，如果精度比原始值低，那么按照指定的方法进行四舍五入或者直接截断：
2. `stripTrailingZeros()` 去掉末尾的0
   1. 不会改变原数据, 返回一个新的 `BigDecimal`类型
   2. 经过stripeTrailingZeros方法处理后,如果一个`BigDecimal`的`scale()`返回负数，例如，`-2`，表示这个数是个整数，并且末尾有2个0。

和`BigInteger`类似，**`BigDecimal`可以表示一个任意大小且精度完全准确的浮点数**。

```java
BigDecimal bd1=new BigDecimal("1234.567");
System.out.println(bd1.multiply(bd1));// 1524155.677489
```

+ `BigDecimal`用`scale()`查询小数位数，例如：

```java
BigDecimal bd1=new BigDecimal("1234.567");
System.out.println(bd1.multiply(bd1));// 1524155.677489
// 用scale() 查询小数位数
System.out.println(bd1.scale());// 3
```

+ 通过`BigDecimal`的`stripTrailingZeros()`方法，可以将一个`BigDecimal`格式化为一个相等的，但去掉了末尾0的`BigDecimal`：

```java
// stripTrailingZeros() 去掉末尾的0;
BigDecimal bd2=new BigDecimal("123456.4500");
System.out.println(bd2.scale());//4
// stripTrailingZeros 返回一个新变量,不会改变原来的
BigDecimal bd3=bd2.stripTrailingZeros();
System.out.println(bd3.scale());// 2

BigDecimal bd4=new BigDecimal("13131321000");
// 经过stripTrailingZeros 处理后scale 返回的是一个负数, 则表示末尾有几个0的整数
BigDecimal bd5=bd4.stripTrailingZeros();
System.out.println(bd5.scale());// -3
```

+ 可以对一个`BigDecimal`设置它的`scale`，如果精度比原始值低，那么按照指定的方法进行四舍五入或者直接截断：

  ```java
  ```

  
