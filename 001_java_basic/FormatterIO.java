import java.util.Scanner;
public class FormatterIO {
    //    格式化输入和输出
    //    %d	格式化输出整数
    //    %x	格式化输出十六进制整数
    //    %f	格式化输出浮点数
    //    %e	格式化输出科学计数法表示的浮点数
    //    %s	格式化字符串
    public static void main(String[] args){
        int a=123456;
        float b=2.34343f;
        System.out.printf("%s,%s",a,b);
        System.out.print(a);
        System.out.printf("%s,%.2f\n",a,b);

        // 输入
        Scanner scanner=new Scanner(System.in);
        System.out.print("Input your name");
        String name=scanner.nextLine();// 读取一行输入并获取字符串
        System.out.print("Input your age");
        int age=scanner.nextInt();//    读取一行输入并获取整数
        System.out.printf("Hi, %s,you are %d\n",name,age);

        System.out.print("请输入上次考试的成绩");
        int beforeScore= scanner.nextInt();
        System.out.print("请输入本次考试的成绩");
        int nowScore=scanner.nextInt();
        System.out.printf("你的成绩相对于上次成绩提升得到百分比为: %.2f%%",(float)(nowScore-beforeScore)/nowScore*100);

    }
}
