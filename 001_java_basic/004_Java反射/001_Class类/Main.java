public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        // 反射 获取的都是同一个 interface 或者 class 定义 ,所以获取Class的实例 都是相等的
        // c++ 中 structure class 是一个东西
        //java中 interface 是比 class 更抽象的 class
        Class cls1 = String.class;
        Class cls2 = "".getClass();
        // cls1 和 cls2 都是Class 类的 同一个实例对象String.class
        System.out.println(cls1 == cls2);// true

        // instanceof 可以匹配类型, 和 类型的子类
        // == 只能精确匹配class,因为那是不同类型的Class对应的实例不一样
        Integer n = new Integer(234);

        // 1. instanceof
        System.out.println(n instanceof Integer);// true 因为n是Integer类型
        System.out.println(n instanceof Number);// true 因为n是Number类型的子类

        // 2. ==
        System.out.println(n.getClass() == Integer.class);// true 因为n.getClass()返回Integer.class
        //boolean b4=n.getClass()==Number.class;// compiler error
        // java: 不可比较的类型: java.lang.Class<capture#1,
        // 共 ? extends java.lang.Integer>和java.lang.Class<java.lang.Number>
        // System.out.println(b4);// error 因为Integer.class!=Number.class

        printClassInfo("".getClass());
        /**
         * Class name: java.lang.String
         * Simple name: String
         * Package name: java.lang
         * is interface: false
         * is enum: false
         * is array: false
         * is primitive: false
         * */
        printClassInfo(Runnable.class);
        /**
         * Class name: java.lang.Runnable
         * Simple name: Runnable
         * Package name: java.lang
         * is interface: true
         * is enum: false
         * is array: false
         * is primitive: false
         */
        printClassInfo(java.time.Month.class);
        /**
         * Class name: java.time.Month
         * Simple name: Month
         * Package name: java.time
         * is interface: false
         * is enum: true
         * is array: false
         * is primitive: false
         */
        printClassInfo(String[].class);
        /**
         * Class name: [Ljava.lang.String;
         * Simple name: String[]
         * is interface: false
         * is enum: false
         * is array: true
         * is primitive: false
         */
        printClassInfo(int.class);
        /**
         * Class name: int
         * Simple name: int
         * is interface: false
         * is enum: false
         * is array: false
         * is primitive: true
         */
        // 获取 String类型的 Class 实例
        Class cls=String.class;
        // 创建一个String 实例
        String s=(String) cls.newInstance();


    }

    static void printClassInfo(Class cls) {
        System.out.println("Class name: " + cls.getName());
        System.out.println("Simple name: " + cls.getSimpleName());
        if (cls.getPackage() != null) {
            System.out.println("Package name: " + cls.getPackage().getName());
        }
        System.out.println("is interface: " + cls.isInterface());
        System.out.println("is enum: " + cls.isEnum());
        System.out.println("is array: " + cls.isArray());
        System.out.println("is primitive: " + cls.isPrimitive());
    }
}
