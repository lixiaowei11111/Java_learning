public class Main {
    public static void main(String[] args){
        Student s1=new Student("local",22);
        s1.hello();
        // 向上转型
        // 因为Student继承自Person，因此，它拥有Person的全部功能。Person类型的变量，如果指向Student类型的实例，对它进行操作，是没有问题的！
        Person p1=new Student("lxw",34);
        Person p2=s1;
        Object p3=p2;

        // 向下转型 向下转型可能会产生 exception
        Person p5=new Student("xx",12);
        Person p6=new Person("ww",44);
        Student s5=(Student) p5;// 将p5的类型强制转换为 Student
        // Student s6=(Student) p6;//error class cast exception

        // 使用 instanceof 来检测实例的类型(class)
        System.out.println(p5 instanceof Person);// true
        System.out.println(p5 instanceof Student);// true
        System.out.println(p6 instanceof Student);// false
        System.out.println(p6 instanceof Student);// false
        System.out.println(s5 instanceof Student);// true

        Student s7=null;
        System.out.println(s7 instanceof Student);//flase
        // 实现根据 条件来判断是否转换类型
        // if(p6 instanceof Student s6){//java 14
        if(p6 instanceof Student ){
            // ...
        }
    };
}


class Person{
    public Person(String name,int age){
        this.name=name;
        this.age=age;
    };
    protected String name;// private 子类继承不了,需要使用 public或者protected
    protected int age;
    public String getName(){
        return  name;
    }
    public int getAge(){
        return age;
    }
}

class Student extends Person{
    private String name="Son";

    public Student(String name, int age) {
        super(name, age);
        // 当父类中,有带参数的构造函数时,子类参数必须使用super函数来携带父类构造函数时,所需要的参数
    }

    public void hello(){
        System.out.println("Hello"+ name);// Hello Son
        System.out.println("Hello"+super.name);// Hello null
    }
}
