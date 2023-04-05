import java.lang.reflect.Constructor;

public class Main {
    public static void main(String[] args) {
        try {
            // 获取构造方法Integer(int)
            Constructor cons1 = Integer.class.getConstructor(int.class);
            Integer n1 = (Integer) cons1.newInstance(123);
            System.out.println(n1);// 123
            // 获取构造方法Integer(String)
            Constructor cons2 = Integer.class.getConstructor(String.class);
            Integer n2 = (Integer) cons2.newInstance("456");
            System.out.println(n2);// 456
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
