# 1. java 核心类



## 1. String

## 1.1 java 中String的特点:(类似于 c++ 自带库里面的 string )

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

## 1.2 String 的比较 (`equals`,`equalIgnoreCase`)

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





## 1.3 类型转换 `String.valueOf()`

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

  