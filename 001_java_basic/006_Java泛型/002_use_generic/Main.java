import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 泛型的默认值
        List list = new ArrayList();// 默认为ArrayList<Object>()
        list.add("hello");
        list.add("world");
        // String first= list.get(0);// 编译器报错 需要的类型为Object; 需要强制转换
        String first = (String) list.get(0);
        System.out.println(first);// hello
        // 2. 泛型的缩写
        List<Number> list2 = new ArrayList<>();
        list2.add(2);
        list2.add(3.14);

        // 3. 泛型接口
        Person[] ps = new Person[]{
                new Person("Bob", 21),
                new Person("Alice", 23),
                new Person("Cc", 99)
        };
        Arrays.sort(ps);
        System.out.println(Arrays.toString(ps));// [Alice,23, Bob,21, Cc,99]

    }
}

class Person implements Comparable<Person> {
    private String name;
    private int score;

    Person(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(Person other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return this.name + "," + this.score;
    }
}