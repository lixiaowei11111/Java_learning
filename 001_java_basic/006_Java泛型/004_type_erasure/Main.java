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

        //3.  无法判断带泛型的类型
//        if (pair1 instanceof Pair<String>) {
//            // compile error instanceof 的泛型类型非法
//        }
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

//public class Pair<T> {
//    private T first;
//    private T last;
//
//    public Pair() {
//        // Compile error:
//        first = new T();//类型形参 'T' 不能直接实例化
//        last = new T();
//    }
//}
// 要实例化 T类型, 必须要借用 Reflection的newInstance()方法
class Person<T> {
    private T name;
    private T age;

    public Person(Class<T> classObj) throws InstantiationException, IllegalAccessException {
        name = classObj.newInstance();
        age = classObj.newInstance();
    }
}

class Teacher<T> {
    public boolean equals(T t) {
        // 'Teacher' 中的 'equals(T)' 与 'java.lang.Object' 中的 'equals(Object)' 冲突；两个方法具有相同的擦除，但都没有重写另一个
        return this == t;
    }

    public boolean same(T t) {
        return this == t;
    }
}

// 一个正常的类 可以继承 自 泛型类
class IntPair extends Pair<Integer>{

    public IntPair(Integer first, Integer last) {
        super(first, last);
    }
}