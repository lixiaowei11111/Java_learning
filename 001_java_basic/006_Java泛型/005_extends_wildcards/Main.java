import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        int sum = PairHelper.add(new Pair<>(1, 2));
        Pair<Integer> p = new Pair<>(123, 456);// 定义 p的类型为 Pair<Integer>
        int n = add(p);// compile error 需要的类 Pair <Number> 提供的类型:Pair <Integer>
        System.out.println(n);
    }

    //    static int add(Pair<Number> p) {// 定义参数类型为 Pair<Number>
//        Number first = p.getFirst();
//        Number last = p.getLast();
//        return first.intValue() + last.intValue();
//    }
    // 使用 extends 通配符 用于获取 泛型T 为 Number 或者 Number子类的类型
    static int add(Pair<? extends Number> p) {// 定义参数类型为 Pair<Number>
        Number first = p.getFirst();
        Number last = p.getLast();
        //p.setFirst(new Integer(first.intValue() + 100));
        p.setFirst(null);
        //p.setLast(new Integer(last.intValue() + 100));
        return first.intValue() + last.intValue();
    }

    int sumofList(List<? extends Integer> list) {
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            Integer n = list.get(i);
            sum += n;
        }
        return sum;
    }

    // extends 限制 泛型类型,这时候的作用等价于 ts的 extends
    Student<Number> s = null;
    Student<Integer> s2 = new Student<>(1, 2);
    Student<Double> s3 = null;

    Student<Objects> s4 = null;
    Student<String> s5 = null;
}


class Pair<T> {
    private T first;
    private T last;

    public Pair(T first, T last) {
        this.first = first;
        this.last = last;
    }


    public T getFirst() {
        return first;
    }

    public T getLast() {
        return last;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public void setLast(T last) {
        this.last = last;
    }
};

class PairHelper {
    static int add(Pair<Number> pair) {
        Number first = pair.getFirst();
        Number last = pair.getLast();
        return first.intValue() + last.intValue();
    }
}

class Student<T extends Number> {
    private T t1;
    private T t2;

    public Student(T t1, T t2) {
        this.t1 = t1;
        this.t2 = t2;
    }
}