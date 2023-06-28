import static java.lang.System.*;


public class Main {
    public static void main(String[] args) {
        Pair<Number> p1 = new Pair<>(12.3, 456);
        Pair<Integer> p2 = new Pair<>(123, 456);
        setName(p1, 100);
        setName(p2, 200);
        out.println(p1.getFirst() + "," + p1.getLast());// 100,100
        out.println(p2.getFirst() + "," + p2.getLast());// 200,200

        // <?> 通用通配符的特点, Pair<?> 是 所有 Pair<T>的超类
        Pair<Integer> p3 = new Pair<>(123, 456);
        Pair<?> p4 = p3;// 安全地向上转型
        out.println(p4.getFirst() + "" + p4.getLast());
    }

    static void setName(Pair<? super Integer> p, Integer n) {
        p.setFirst(n);
        p.setLast(n);
        // (? super Integer) x = p.getFirst();// 通配符只能做引用形参
        // Integer x=p.getFirst();//这里不能写死为某个特定类型,因为 getFirst方法的必须保证类型和 ? super Integer 相等
    }

    void sample(Pair<?> p) {

    }

    static boolean isNull(Pair<?> p) {
        return p.getFirst() == null || p.getLast() == null;
    }

    static <T> boolean isNull2(Pair<T> p) {
        return p.getFirst() == null || p.getLast() == null;
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

