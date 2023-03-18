public class Main {
    public static void main(String[] args){
        Person p=new Student("lxw");
        p.say();// lxwrun
    }
}


abstract class PersonClass {
    public abstract void run();
    public abstract String getName();
}

//接口定义的所有方法默认都是public abstract的，
// 所以这两个修饰符不需要写出来（写不写效果都一样）。
interface Person{
    void run();
    String getName();
    //interface的 default方法
    default void say(){
        System.out.println(getName()+"run");
    }
}

// class 继承 interface 使用 implements(实现,简单易懂, interface 是一种比 abstract class 更 abstract的接口)
class Student implements Person {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(this.name + " run");
    }

    @Override
    public String getName() {
        return this.name;
    }
}

interface Animal{
    void run();
}

interface Dog extends Animal{
    void run();
    String say();
}

interface Income{
    double getTax(double come);
}

class Salary implements Income {
    @Override
    public double getTax(double income){
        if(income>5000){
            return (income-5000)*0.03;
        }
        return 0;
    };
}

class ArticleIncome implements Income {
    @Override
    public double getTax(double articleIncome){
        return  articleIncome*0.14;
    }
}