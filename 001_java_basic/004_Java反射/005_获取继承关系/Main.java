public class Main {
    public static void main(String[] args) {

        // 1. getSuperClass 获取 父级的class
        Class i = Integer.class;
        Class n = i.getSuperclass();
        System.out.println(n); //class java.lang.Number
        Class o = n.getSuperclass();
        System.out.println(o);// class java.lang.Object
        System.out.println(o.getSuperclass());// null

        // 获取interface 使用getInterfaces() 方法
        Class s = Integer.class;
        Class[] is = s.getInterfaces();
        for (Class item : is) {
            System.out.println(item);// interface java.lang.Comparable
        }

        System.out.println(java.io.DataInputStream.class.getSuperclass());// class java.io.FilterInputStream
        System.out.println(java.io.Closeable.class.getSuperclass());// null

        Object n2 = Integer.valueOf(123);
        System.out.println(n2 instanceof Double);// false
        System.out.println(n2 instanceof Integer);// true
        System.out.println(n2 instanceof Number);// true
        System.out.println(n2 instanceof java.io.Serializable);// true

        // 如果是两个Class实例，要判断一个向上转型是否成立，可以调用isAssignableFrom()：
        System.out.println(Integer.class.isAssignableFrom(Integer.class));// true ，因为Integer可以赋值给Integer
        System.out.println(Number.class.isAssignableFrom(Integer.class));// true ，因为Integer可以赋值给Number
        System.out.println(Object.class.isAssignableFrom(Integer.class));// true ，因为Integer可以赋值给Object
        System.out.println(Integer.class.isAssignableFrom(Number.class));// false ，因为Number不能赋值给Integer
    }
}
