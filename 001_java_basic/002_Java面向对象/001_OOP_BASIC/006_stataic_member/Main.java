public class Main {
    public static void main(String[] args){
        Person y=new Person("ybb",22);
        Person x=new Person("xbb",23);
        y.number=22;
        System.out.println(x.number);// 22
        System.out.println(Person.number);//22  static number是公共属性,每个实例访问修改读取的都是同一片内存地址;
        Person.hobby="22ybb";
        System.out.println(Person.hobby);
        x.number=988;
        System.out.println(x.number);// 988
        System.out.println(Person.number);//988  static number是公共属性,每个实例访问修改读取的都是同一片内存地址;

        Person.setNumber(00);
        System.out.println(Person.number);// 0
        AnimalClass a1=new AnimalClass();
        AnimalClass a2=new AnimalClass();// error 实例个数不能超过两个
    }

}

class Person{
    public String name;
    public int age;
    public static int number;// 定义static 属性 number
    static String hobby;
    public Person(String name,int age){
        this.name=name;
        this.age=age;
    }
    public static void setNumber(int val){
        Person.number=val;
        // number=val;
        // this.number=val;// compiler error 无法从 static 上下文引用 'Person.this'
    }

}

interface Animal{
    public static final int MALE=1;
    //public static final int FEMALE=2;
    // 可以简写成
    int FEMALE=2;// 编译器会自动补上 public static final
}

class AnimalClass{
    public static int instanceCount=0;
    public AnimalClass(){
        AnimalClass.instanceCount++;
        if(AnimalClass.instanceCount>1){
            throw new RuntimeException("实例个数不能超过2");
        };
    }
}