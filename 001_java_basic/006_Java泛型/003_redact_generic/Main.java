public class Main {
    public static void main(String[] args) {

    }
}


// 编写一个泛型 类,多种泛型变量
class Pair<T,V> {
    public Pair(T first, T last){
        this.first=first;
        this.last=last;
    }
    private T first;
    private T last;
    private V center;

    public V getCenter() {
        return center;
    }

    public void setCenter(V center) {
        this.center = center;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T getLast() {
        return last;
    }

    public void setLast(T last) {
        this.last = last;
    }
    // 静态方法，需要单独开一个属性
    // 静态泛型方法应该使用其他类型区分:
    public static <K,S> Pair<K,S> create(K first, K last) {
        return new Pair<K,S>(first, last);
    }
}