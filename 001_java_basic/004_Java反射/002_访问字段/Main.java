import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Main {
    public static void main(String[] args) {
        /**
         * - Field getField(name)：根据字段名获取某个public的field（包括父类）
         * - Field getDeclaredField(name)：根据字段名获取当前类的某个field（不包括父类）
         * - Field[] getFields()：获取所有public的field（包括父类）
         * - Field[] getDeclaredFields()：获取当前类的所有field（不包括父类）
         */
        try {
            Class stuCls = Student.class;
            // 获取public字段score
            System.out.println(stuCls.getField("score"));// public int Student.score
            // 获取继承的public字段 name
            System.out.println(stuCls.getField("name"));// public java.lang.String Person.name
            // 获取private字段 grade
            System.out.println(stuCls.getDeclaredField("grade"));// private int Student.grade
            // 获取protect字段 age
            System.out.println(stuCls.getDeclaredField("age"));// protected int Student.age

            Field f = String.class.getDeclaredField("value");
            f.getName(); // "value"
            f.getType(); // class [B 表示byte[]类型
            int m = f.getModifiers();
            Modifier.isFinal(m); // true
            Modifier.isPublic(m); // false
            Modifier.isProtected(m); // false
            Modifier.isPrivate(m); // true
            Modifier.isStatic(m); // false

            Teacher t = new Teacher("ybb");
            System.out.println(t.getName());// ybb
            Class c = t.getClass();
            Field f2 = c.getDeclaredField("name");
            // 正常情况下，Main类无法访问Teacher类的private字段
            f2.setAccessible(true);// 授权访问, 无论是不是 public还是private
            Object value = f2.get(t);// 1. 用于获取实例对应的字段的值
            // 2. 使用 set方法 设置字段的值
            f2.set(t,"nanami");
            System.out.println(t.getName());// nanami
            System.out.println(value);// ybb


        } catch (Exception e) {
            e.printStackTrace();// 打印 Exception堆栈信息
        }
        ;
    }
}


class Person {
    public String name;
}


class Student extends Person {
    public int score;
    private int grade;
    protected int age;
}

class Teacher {
    private String name;

    public Teacher(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}