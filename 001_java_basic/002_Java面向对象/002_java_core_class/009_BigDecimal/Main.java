import java.math.BigDecimal;

public class Main {
    public static void main(String[] args){
        BigDecimal bd1=new BigDecimal("1234.567");
        System.out.println(bd1.multiply(bd1));// 1524155.677489
        // 用scale() 查询小数位数
        System.out.println(bd1.scale());// 3

        // stripTrailingZeros() 去掉末尾的0;
        BigDecimal bd2=new BigDecimal("123456.4500");
        System.out.println(bd2.scale());//4
        // stripTrailingZeros 返回一个新变量,不会改变原来的
        BigDecimal bd3=bd2.stripTrailingZeros();
        System.out.println(bd3.scale());// 2

        BigDecimal bd4=new BigDecimal("13131321000");
        // 经过stripTrailingZeros 处理后scale 返回的是一个负数, 则表示末尾有几个0的整数
        BigDecimal bd5=bd4.stripTrailingZeros();
        System.out.println(bd5.scale());// -3
    }
}
