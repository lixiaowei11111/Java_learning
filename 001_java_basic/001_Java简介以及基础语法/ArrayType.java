public class ArrayType {
    public static void main(String[] args){
        int[] ns=new int[5];
        System.out.println(ns.length);// 5
       // System.out.println(ns[5]);// 访问超出范围限制,报错
        int[] arr={1,2,3,4,5};
        String[] names={"ABC","DEF","XYA"};
        String s1=names[1];
        System.out.println(s1);
        names[1]="YBB";
        System.out.println(s1);
    }
}
