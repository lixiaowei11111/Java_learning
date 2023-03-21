

import static java.lang.System.*;
public class Main {
    public static void main(String[] args){
        String s1="Hello";
        String s2=new String(new char[] {'H','e','l','l','o'});
        String s3="Hello";
        out.println(s1+"\n"+s2);
        out.println(s1.equals(s2));// true
        out.println(s1==s2);// false
        out.println(s1==s3);// true
        out.println(s1.length());// 5
        out.println(s1.contains(" World"));// false contains 是否包含
        out.println(s1.indexOf(" World"));// -1 不包含
        out.println(s1+":\n"+s1.lastIndexOf('o'));// 4
        out.println(s1.startsWith("He"));// true
        out.println(s1.endsWith("He"));// false
        out.println(s1.substring(1));// ello
        out.println(s1.substring(1,3));// el 开闭区间 [n,m)
        String s4=" Hel lo ";
        out.println(s4.trim());//Hel lo
        String s5=" \u3000Hel lo ";
        out.println(s5.trim());//　Hel lo trim方法不能去除中文空格
        out.println(s5.strip());//Hel lo stripe方法可以去除中文空格\u3000
        out.println(s5.stripTrailing());// 　Hel lo 只去除 尾部的
        String s6=" \u3000Hel lo \u3000";
        out.println(s6.stripLeading());//Hel lo 　 只去除头部的
        out.println("".isEmpty());// true
        out.println(" ".isEmpty());//false isEmpty会检测不可见字符
        out.println(" ".isBlank());//true isBlank不会检测不可见字符

        String s7="Hello";
        out.println(s7.replace("l","w"));//Hewwo
        String[] arrS=s7.split("l");//arrS[]={"He","","","o"}
        out.println(String.join("",arrS));//Heo

        // formatted 替换占位符
        String s8="Hi %s,you score is %d";
        out.println(String.format(s8,"Alice",80));// Hi Alice,you score is 80


        // 基本类型 和引用类型 转为 String
        out.println(String.valueOf(123456));//123456
        out.println(String.valueOf(123.456f));//123.456
        out.println(String.valueOf(true));//true
        out.println(String.valueOf(new Object()));// java.lang.Object@39aeed2f// 后面这个乱码为 hashCode

        // String 转为 整形数字
        int n1=Integer.parseInt("123");// 123
        int n2=Integer.parseInt("123",16);// 转为16进制 291
        out.println("n1: "+n1+"\n"+"n2: "+n2);
        /*
        * n1: 123
        * n2: 291
        * */

        // String 类型转为 Boolean 类型
        boolean b1=Boolean.parseBoolean("true");
        boolean b2=Boolean.parseBoolean("false");
        boolean b3=Boolean.parseBoolean("falsy");
        boolean b4=Boolean.parseBoolean("error");
        out.println(b1);// true
        out.println(b2);// false
        out.println(b3);// flase
        out.println(b4);// flase
    }
}
