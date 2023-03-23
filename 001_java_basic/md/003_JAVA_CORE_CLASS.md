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
