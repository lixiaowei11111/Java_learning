
public class Main{
    // 一个java文件中最外部 只能由一个 public class
    public static void main(String[] args){
        Person p1=new Person();
        // p1.age=22;// 无法被实例或者外部访问
        // p1.name="lxw";
        // System.out.print(p1.age);
        p1.setName("lc");
        //p1.setAge(-22);// 报错
        p1.setAge(22);
        System.out.println(p1);// Person@5594a1b5
        System.out.println(p1.getAge());//22
        System.out.println(p1.getName());// lc

        // 可变参数, 用 `type...`表示
        Group g1=new Group();
        g1.setNames("123","hahah","aa");
        g1.setNames("dad","dad");
        g1.setNames();
        g1.setNames(new String[]{"123","234"});

        // java
        Pet p2=new Pet();
        String s1[]={"hello","java"};
        p2.setName(s1);
        System.out.print(p2.getName());// hello java
        s1[1]="javascript";
        System.out.println(p2.getName());// hello javascript

        // class 构造函数 静态重载
        PersonExample pe1=new PersonExample("lxw",22);
        System.out.println(pe1.getAge());// 22
        System.out.println(pe1.getName());// lxw

    };
}


class Person {
    private String name;
    private int age;
    String sex;// friendly
    public String getName(){
        return this.name;
    };
    public void setName(String s){
        this.name=s;
    };
    public int getAge(){
        return  this.age;
    };
    public void setAge(int age){
        if(age<0||age>150){
            throw new IllegalArgumentException("invalid age value");
        }
        this.age=age;
    };

};

// 可变参数
class Group{
    public String[] names;
    public void setNames(String... names){
        this.names=names;
    };

}

class Pet{
    private String[] name;
    public String getName(){
        return  this.name[0]+""+this.name[1];
    }
    public void setName(String... name){
        this.name=name;
    }
}