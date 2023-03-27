
import java.math.BigInteger;
public class Main {
    public static void main(String[] args){
        BigInteger bi=new BigInteger("131231231321313");
        System.out.println(bi.pow(3));// 2260016507372730652493204303175998390811297
        BigInteger bi2=new BigInteger("1");
        BigInteger bi3=bi2.add(bi);
        System.out.println(bi3);// 131231231321314
        System.out.println(bi3.longValue());// 转为 long型 131231231321314
        // System.out.println(bi3.multiply(bi3).longValueExact());//compiler error BigInteger out of long range
        System.out.println(0.1+0.2);// 0.30000000000000004

        BigInteger n = new BigInteger("999999").pow(99);
        float f = n.floatValue();
        System.out.println(f);// Infinity
    }
}
