public class StringAndChar {
    public static void main(String[] args){
        String s="";
        String s1="A";
        // String字符串 在Java中是引用类型
        String s2="Hello world";
        String s3=s2;
        s2="ybb"; // 赋值改变的只是栈内存中地址指向, 堆内存中的字符串内容并没有改变
        System.out.println("原来s2指向的堆内存地址的内容 : "+s3);
        System.out.println("现在的s2 : "+s2);

        // null 和 "" (空字符串不相等)
        System.out.println(null=="");// false

         // 字符串拼接
        int a=123;
        int b=456;
        String s5=a+""+b;// 123456
        System.out.println(s5);
    }
}
