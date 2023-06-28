import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        Class clazz = String.class;
        String str = (String) clazz.newInstance();

        Class<String> clazz2 = String.class;
        String str2 = clazz2.newInstance();

        // 调用 Class 实例的getSuperClass 方法 返回的类型是 Class<? super T>
        Class<? super String> clazz3 = String.class.getSuperclass();

        // 构造方法 constructor<T>也是泛型
        Class<Integer> clazz4 = Integer.class;
        Constructor<Integer> cons = clazz4.getConstructor(int.class);
        Integer i = cons.newInstance(123);

        // 可以声明带泛型的数组，但不能用new操作符创建带泛型的数组(类似于ts中的不能将类型作为值来使用的错误提示)
        Pair<String>[] ps = null;// ok
        // Pair<String>[] ps2 = new Pair<String>[2];// compiler error
        // 必须通过强制转型实现带泛型的数组
        @SuppressWarnings("unchecked")
        Pair<String>[] ps3 = (Pair<String>[]) new Pair[2];

        Pair[] arr = new Pair[2];
        Pair<String>[] ps4 = (Pair<String>[]) new Pair[2];
        ps4[0] = new Pair<String>("a", "b");
        arr[1] = new Pair<Integer>(1, 2);

        // ClassCastException:
        Pair<String> p = ps4[0];
        String s = p.getFirst();

        Pair[] arr2 = new Pair[2];
        Pair<String>[] ps5 = (Pair<String>[]) arr;

        System.out.println(ps5.getClass() == Pair[].class); // true

        String s1 = (String) arr2[0].getFirst();
        String s2 = ps5[0].getFirst();

        String[] ss = ArrayHelper.asArray("a", "b");
        Integer[] ii = ArrayHelper.asArray(1, 2, 3);

        String[] arr3 = asArray("one", "two", "three");
        System.out.println(Arrays.toString(arr3));
        // ClassCastException:
        String[] firstTwo = pickTwo("one", "two", "three");
        System.out.println(Arrays.toString(firstTwo));


    }

    static <K> K[] pickTwo(K k1, K k2, K k3) {
        return asArray(k1, k2);
    }

    static <T> T[] asArray(T... objs) {
        return objs;
    }
}

class Pair<T> {
    private T first;// 在Main的setName方法中 实际类型变为 ? super Integer
    private T last;// 在Main的setName方法中 实际类型变为 ? super Integer
    // windows上

    public Pair(T first, T last) {
        this.first = first;
        this.last = last;
    }
    // 在Main的setName方法中 实际变为了

    /**
     * public Pair (? super Integer T\,? super Integer T){
     * this.first = first;// 两者类型始终相等且皆为 ? super Integer,可以赋值
     * this.last = last;// 两者类型始终相等且皆为 ? super Integer,可以赋值
     * }
     */

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }
    // 在Main的setName方法中 实际签名为 void setFirst(? super Integer)

    /**
     * public void setFirst(? super T first){
     * this.first = first; // 两者类型始终相等且皆为 ? super Integer,可以赋值
     * }
     */

    public T getLast() {
        return last;
    }
    // 在Main的setName 方法中的 实际类型签名  ? super Integer getLast

    /**
     * public ? super Integer getLast(){
     * return last;// last类型为 ? super Integer ,可以安全返回
     * }
     */

    public void setLast(T last) {
        this.last = last;
    }


}

// compile error:
class Abc<T> {
    //    T[] createArray() {
//        // 类型形参 'T' 不能直接实例化 compile error
//        return new T[5];
//    }
    T[] createArray(Class<T> classobj) {
        return (T[]) Array.newInstance(classobj, 5);
    }

}

class ArrayHelper {
    @SafeVarargs
    static <T> T[] asArray(T... args) {
        return args;
    }
}