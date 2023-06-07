import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // 创建ArrayList<Integer> 类型
        ArrayList<Integer> integerList = new ArrayList<Integer>();
        // 添加一个Integer
        integerList.add(new Integer(23));
        // “向上转型” 为ArrayList<Number>
        ArrayList<Number> numberList=integerList;
        // 添加一个float, 因为Float 也是Number
        numberList.add(new Float(3.14));
        // 从ArrayList<Integer> 获取索引为1(Float)的元素
        Integer n=integerList.get(1);
    }
}
