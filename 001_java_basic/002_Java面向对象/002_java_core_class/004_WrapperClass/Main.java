public class Main {
    public static void main(String[] args){
        String s=null;
        // int n=null;// compiler error  只有引用类型可以直接赋值为null
        Integer n=null;
        Integer n2=new Integer(99);
        int n3= n2.intValue();

        //通过new操作符创建Integer实例(不推荐使用,会有编译警告):
        int i=100;
        Integer n4=new Integer(i);
        // 通过静态方法valueOf(int)创建Integer实例:
        Integer n5=Integer.valueOf(i);
        // 通过静态方法valueOf(String)创建Integer实例:
        Integer n6=Integer.valueOf("100");
        System.out.println(n6);

        // Auto Boxing
        //  int 和 Integer 互换
        int i2=99;
        Integer n7=Integer.valueOf(i2);
        int x=n7.intValue();
        Integer n8=34;// 编译器自动使用Integer.valueOf(int)
        int x2=n8;// 编译器自动使用Integer.intValue()
        Integer n9=null;
        // int x3=n9;// 拆箱的 'n9' 可能产生 'java.lang.NullPointerException'

        // 不变类
        Integer x4 = 127;
        Integer y = 127;
        Integer m = 99999;
        Integer n10 = 99999;

        // Integer 输出 指定进制, 将 字符串转换为指定进制数
        System.out.println(Integer.parseInt("20"));// 20
        System.out.println(Integer.parseInt("20",16));// 32 把20看成16进制来解析成 10进制,所以是32

        System.out.println(Integer.toString(100));// "100",表示为10进制
        System.out.println(Integer.toString(100,16));// "64",表示为16进制
        System.out.println(Integer.toHexString(100));// "64",表示为16进制
        System.out.println(Integer.toOctalString(100));// "144",表示为8进制
        System.out.println(Integer.toBinaryString(100));// "1100100",表示为2进制

        // boolean只有两个值true/false，其包装类型只需要引用Boolean提供的静态字段:
        boolean t=Boolean.TRUE;
        boolean f=Boolean.FALSE;

        int max=Integer.MAX_VALUE;
        int min=Integer.MIN_VALUE;
        System.out.println(max);// 2147483647
        System.out.println(min);//-2147483648
        // int可表示的最大/最小值:
        int sizeOfLong= Long.SIZE;
        int byteOfLong=Long.BYTES;
        // long类型占用的bit和byte数量:
        System.out.println(sizeOfLong);// 64(bits)
        System.out.println(byteOfLong);// 8(Bytes)

        Number num=new Integer(100);// 向上转型为Number
        // 获取byte, int, long, float, double:
        byte b1=num.byteValue();
        int n1=num.intValue();
        long l1=num.longValue();
        float f1=num.floatValue();
        double d1=num.doubleValue();
        System.out.println(num);// 100
        System.out.println(b1);// 100
        System.out.println(l1);// 100
        System.out.println(n1);// 100
        System.out.println(f1);// 100.0
        System.out.println(d1);// 100.0

        byte b2=-1;
        byte b3=127;
        System.out.println(Byte.toUnsignedInt(b2));// 255
        System.out.println(Byte.toUnsignedInt(b3));// 127
    }
}

//class Integer{
//    private int value;
//    public Integer(int val){
//        this.value=val;
//    }
//    public int intValue(){
//        return this.value;
//    }
//}