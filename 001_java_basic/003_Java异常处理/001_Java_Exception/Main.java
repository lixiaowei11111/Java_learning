import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        byte[] bs=toGBK("中文");// java: 未报告的异常错误java.io.UnsupportedEncodingException; 必须对其进行捕获或声明以便抛出
        System.out.println(Arrays.toString(bs));
    }
    // 1. 使用try catch 捕获异常
    //    static byte[] toGBK(String s){
//        try{
//            // 用指定编码转换 String 为byte
//            return s.getBytes("GBK");
//        }
//        catch(UnsupportedEncodingException e){
//            System.out.println("异常对象信息: "+ e);// 打印异常信息
//            return s.getBytes();// 使用默认编码
//        }
//    }
    // 2. 声明异常 告诉编译器可能抛出异常
//    static byte[] toGBK(String s) throws UnsupportedEncodingException {
//        return s.getBytes("GBK");
//    }
    // 3. 打印异常栈信息
    static byte[] toGBK(String s){
        try {
            return s.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();// 打印异常栈信息
        }
        return null;
    }
}
