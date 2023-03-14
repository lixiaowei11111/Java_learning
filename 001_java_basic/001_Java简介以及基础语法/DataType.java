public class DataType {
    /* constructor func */
    DataType(){
        test();
    }
    public static void main(String[] args){
        /**
         * 溢出处理
         * */
        int x = 2147483640;
        int y = 15;
        int sum = x + y;
        System.out.println(sum); // -2147483641

        double f1=1.0/10;
        double f2=1-9.0/10;
        System.out.println("f1: "+f1);
        System.out.println("f2: "+f2);// 0.09999999999999998
        System.out.println("f1==f2 "+(f1==f2));// false
        int j=0xff22;//65314 16进制表示
        int b=0b010101;// 21 2进制表示
        long l=12131233313L;// 12131233313 long类型结尾要加l(L,不区分大小写)
        float f=2.3234242f;// 2.323424 类似于c/c++ float类型结尾需要加上f来和double做区分
        boolean b1=true;
        boolean b2=false;

        char c1='c';
        char c2='哈';

        final double PI=3.14;// 常量在定义时进行初始化后就不可再次赋值，再次赋值会导致编译错误。
        // PI=55;// error

        System.out.print(j+"\n");
        System.out.print(b+"\n");
        System.out.print(l+"\n");
        System.out.print(f+"\n");
        System.out.print(b1+"\n");
        System.out.print(b2+"\n");
        System.out.print(c1+"\n");
        System.out.print(c2+"\n");
        System.out.print(PI+"\n");// 3.14
    };

    public void test(){
        System.out.print(this+"this");
    };
}