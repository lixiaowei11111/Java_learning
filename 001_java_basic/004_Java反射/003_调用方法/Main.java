import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        try {
            /**
             * Method getMethod(name, Class...)：获取某个public的Method（包括父类）
             * Method getDeclaredMethod(name, Class...)：获取当前类的某个Method（不包括父类）
             * Method[] getMethods()：获取所有public的Method（包括父类）
             * Method[] getDeclaredMethods()：获取当前类的所有Method（不包括父类）
             */
            // 1. 调用方法 invoke
            // 获取 Class 的实例,
            Class clsStu = Student.class;
            // 获取public 方法 getScore,需要参数为 String
            System.out.println(clsStu.getMethod("getScore", String.class));// public int Student.getScore(java.lang.String)
            // 获取继承的public方法getName,无需参数
            System.out.println(clsStu.getMethod("getName"));// public java.lang.String Person.getName()
            // 获取 非public 方法getGrade,参数为Integer
            System.out.println(clsStu.getDeclaredMethod("getGrade", int.class));// private int Student.getGrade(int)

            // 使用反射 来调用 String的 substring 方法
            // String 对象
            String s = "HelloYbb";
            // 获取String substring(int)方法，参数为int:
            Method m2 = s.getClass().getMethod("substring", int.class);
            // 在s对象上调用该方法并获取结果(使用invoke调用):
            String r = (String) m2.invoke(s, 6);// bb
            System.out.println(r);

            // 2. 调用静态方法
            // 获取Integer.parseInt(String)方法，参数为String:
            Method m = Integer.class.getMethod("parseInt", String.class);
            // 调用该静态方法并获取结果
            Integer n = (Integer) m.invoke(null, "123456");
            System.out.println(n);// 123456

            // 3. 调用非 public method 使用 setAccessible(true)方法
            Student s2 = new Student();
            Method m3 = s2.getClass().getDeclaredMethod("getGrade", int.class);
            // 使用 setAccessible 来接触对 非public 成员的限制
            m3.setAccessible(true);
            System.out.println(m3.invoke(s2, 2023));// 1

            // 4. 多态原则 总是调用实际类型的覆写方法
            Person p2 = new Person();
            Method m4 = p2.getClass().getDeclaredMethod("hello");
            m4.invoke(new Student());// Student Hello

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class Person {
    public String getName() {
        return "Person";
    }

    public void hello() {
        System.out.println("Person Hello");
    }
}

class Student extends Person {
    public int getScore(String type) {
        return 99;
    }

    private int getGrade(int year) {
        return 1;
    }

    public void hello() {
        System.out.println("Student Hello");
    }
}