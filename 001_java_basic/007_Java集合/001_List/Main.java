import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        // 可以添加重复元素 和 null
        // add size get 方法
        String p1 = "app";
        list.add(p1);
        list.add("pear");
        list.add(p1);
        System.out.println(list.size());// 3
        list.add(null);
        list.add(p1);
        System.out.println(list.size());// 5
        System.out.println(list.get(3));// null

        // 创建list ,使用list 接口提供的of方法
        List<Integer> list2 = List.of(1, 2, 3, 4);

        // for 循环实现 List 遍历
        for (int i = 0; i < list2.size(); i++) {
            System.out.println(list2.get(i));
        }

        // 使用Iterator + for循环 实现 遍历
        List<Double> list3 = List.of(1.2, 2.2, 3.14);
        for (Iterator<Double> it = list3.iterator(); it.hasNext(); ) {
            System.out.println(it.next());
        }

        // 使用 forEach 循环遍历
        for (Double item : list3
        ) {
            System.out.println(item.toString() + ": item");
        }

        // List 转换为Array的三种方法
        // 1. List 实例直接使用 toArray方法,必须使用Object[] 类型 来接收
        List<String> list4 = new ArrayList<>();
        String[] s1 = {"apple", "pear", "watermelon"};
        for (String item : s1
        ) {
            list4.add(item);
        }

        Object[] obj1 = list4.toArray();
        for (Object item : obj1
        ) {
            System.out.println(item.toString());
        }

        // 2. 给toArray(T[])传入一个类型相同的Array，List内部自动把元素复制到传入的Array中
        List<Byte> list5 = List.of((byte) 1, (byte) 2);
        Byte[] bt1 = list5.toArray(new Byte[2]);
        for (Byte item : bt1
        ) {
            System.out.println(item);
        }
        //注意到这个toArray(T[])方法的泛型参数<T>并不是List接口定义的泛型参数<E>，
        // 所以，我们实际上可以传入其他类型的数组，例如我们传入Number类型的数组，返回的仍然是Number类型：
        List<Byte> list6 = List.of((byte) 1, (byte) 2);
        Number[] bt2 = list5.toArray(new Number[list6.size()]);
        Number[] bt3 = list5.toArray(Number[]::new);
        for (Number item : bt2
        ) {
            System.out.println(item);
        }

        // Array 转 List
        Byte[] bt4 = {1, 2, 3, 4};
        List<Byte> list7 = List.of(bt4);    
    }
}
